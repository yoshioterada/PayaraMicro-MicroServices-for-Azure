package com.yoshio3.emotiondetect.services;

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
import com.yoshio3.emotiondetect.services.entities.EmotionRequestJSONBody;
import com.yoshio3.emotiondetect.services.entities.EmotionResponseJSONBody;
import com.yoshio3.emotiondetect.services.entities.MyObjectMapperProvider;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
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
public class EmotionDetectService implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(EmotionDetectService.class.getName());
    private static final String BASE_URI = "https://api.projectoxford.ai/emotion/v1.0/recognize";
    private final static String EMOTIONAL_API_SUBSCRIPTION = "********************************";
    
    @GET
    @Path("/emotionservice")
    @Produces("application/json")
    public String getEmotionInfo(@QueryParam("url") String fileURL){
        try{
            String file = URLDecoder.decode(fileURL,"UTF-8");
            Future<Response> responseForEmotion = getEmotionalInfo(fileURL);
            Response emotionRes = responseForEmotion.get();
            return jobForEmotion(emotionRes);
        } catch (InterruptedException |ExecutionException | UnsupportedEncodingException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return "";
    }
    

    @GET
    @Path("/kill")
    public void kill() {
        System.out.println("Bye");
        System.exit(1);
    }

    private String jobForEmotion(Response emotionRes) {
        EmotionResponseJSONBody[] persons = null;
        if (checkRequestSuccess(emotionRes)) {
            persons = emotionRes.readEntity(EmotionResponseJSONBody[].class);
        } else {
            return emotionRes.readEntity(String.class);
        }
        //現在は一人のみ解析処理
        EmotionResponseJSONBody emotionalPerson = persons[0];
        Map<String, Object> scores = emotionalPerson.getScores();

        //感情の情報を取得
        Double anger = convert((Double) scores.get("anger"));
        Double contempt = convert((Double) scores.get("contempt"));
        Double disgust = convert((Double) scores.get("disgust"));
        Double fear = convert((Double) scores.get("fear"));
        Double happiness = convert((Double) scores.get("happiness"));
        Double neutral = convert((Double) scores.get("neutral"));
        Double sadness = convert((Double) scores.get("sadness"));
        Double surprise = convert((Double) scores.get("surprise"));

        JsonObject value = Json.createObjectBuilder()
                .add("anger", anger)
                .add("contempt", contempt)
                .add("disgust", disgust)
                .add("fear", fear)
                .add("happiness",happiness )
                .add("neutral",neutral )
                .add("sadness",sadness )
                .add("surprise", surprise)
                .build();
        return value.toString();
    }
    /* パーセント表示のためにデータをコンバート */
    private Double convert(Double before) {
        if (before == null) {
            return before;
        }
        String after = String.format("%.2f", before);
        return Double.valueOf(after) * 100;
    }
    /*
    REST 呼び出し成功か否かの判定
    */
    protected boolean checkRequestSuccess(Response response) {
        Response.StatusType statusInfo = response.getStatusInfo();
        Response.Status.Family family = statusInfo.getFamily();
        return family != null && family == Response.Status.Family.SUCCESSFUL;
    }


    public Future<Response> getEmotionalInfo(String pictURI) throws InterruptedException, ExecutionException {
        Client client = ClientBuilder.newBuilder()
                .register(MyObjectMapperProvider.class)
                .register(JacksonFeature.class)
                .build();
        WebTarget target = client.target(BASE_URI);

        EmotionRequestJSONBody entity = new EmotionRequestJSONBody();
        entity.setUrl(pictURI);

        Future<Response> response = target
                .request(MediaType.APPLICATION_JSON)
                .header("Ocp-Apim-Subscription-Key",EMOTIONAL_API_SUBSCRIPTION)
                .async()
                .post(Entity.entity(entity, MediaType.APPLICATION_JSON_TYPE));
        return response;
    }
}