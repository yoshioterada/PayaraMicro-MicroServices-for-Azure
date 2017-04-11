package com.yoshio3.facedetect.services;

/*
* Copyright 2017 Yoshio Terada
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
 */
import com.yoshio3.facedetect.services.entities.FaceDetectRequestJSONBody;
import com.yoshio3.facedetect.services.entities.FaceDetectResponseJSONBody;
import com.yoshio3.facedetect.services.entities.MyObjectMapperProvider;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 *
 * @author Yoshio Terada
 */
@Path("")
public class FaceDetectService implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(FaceDetectService.class.getName());
    private static final String BASE_URI
            = "https://api.projectoxford.ai/face/v1.0/detect?returnFaceId=true"
            + "&returnFaceLandmarks=false&returnFaceAttributes=age,gender";
    private final static String FACE_API_SUBSCRIPTION = "********************************";
    
    /*
        対応ォーマット： JPEG, PNG, GIF(最初のフレーム), BMP
        画像サイズ： 4MB 以下
        検知可能な顔のサイズ：36x36 〜 4096x4096
        一画像辺り検知可能な人数：64 名
        指定可能な属性オプション(まだ実験的不正確)：
            age, gender, headPose, smile and facialHair, and glasses
            HeadPose の pitch 値は 0 としてリザーブ
     */
    @GET
    @Path("/facedetect")
    @Produces("application/json")
    public String getFaceInfo(@QueryParam("url") String pictURI)
            throws InterruptedException, ExecutionException {
        Client client = ClientBuilder.newBuilder()
                .register(MyObjectMapperProvider.class)
                .register(JacksonFeature.class)
                .build();

        WebTarget target = client.target(BASE_URI);
        FaceDetectRequestJSONBody entity = new FaceDetectRequestJSONBody();
        entity.setUrl(pictURI);

        Future<Response> futureResponse = target
                .request(MediaType.APPLICATION_JSON)
                .header("Ocp-Apim-Subscription-Key", FACE_API_SUBSCRIPTION)
                .async()
                .post(Entity.entity(entity, MediaType.APPLICATION_JSON_TYPE));
        Response response = futureResponse.get();
        return jobForFace(response);
    }

    private String jobForFace(Response faceRes) {
        FaceDetectResponseJSONBody[] persons = null;
        if (checkRequestSuccess(faceRes)) {
            persons = faceRes.readEntity(FaceDetectResponseJSONBody[].class);
        } else {
            return faceRes.readEntity(String.class);
        }
        //現在は一人のみ解析処理
        FaceDetectResponseJSONBody faceDetectData = persons[0];

        //年齢、性別を取得
        Map<String, Object> faceAttributes = faceDetectData.getFaceAttributes();
        Double age = (Double) faceAttributes.get("age");
        String gender = (String) faceAttributes.get("gender"); 
        
        JsonObject value = Json.createObjectBuilder()
                .add("age", age)
                .add("gender", gender)
                .build();
        return value.toString();
    }

    /*
     REST 呼び出し成功か否かの判定
     */
    protected boolean checkRequestSuccess(Response response) {
        Response.StatusType statusInfo = response.getStatusInfo();
        Response.Status.Family family = statusInfo.getFamily();
        return family != null && family == Response.Status.Family.SUCCESSFUL;
    }
}
