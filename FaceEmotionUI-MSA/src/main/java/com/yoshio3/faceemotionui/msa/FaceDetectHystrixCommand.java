package com.yoshio3.faceemotionui.msa;
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
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.yoshio3.faceemotionui.services.FaceResponse;
import com.yoshio3.faceemotionui.services.MyObjectMapperProvider;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 *
 * @author yoterada
 */
public class FaceDetectHystrixCommand extends HystrixCommand<FaceResponse> {

    private final String url;

    public FaceDetectHystrixCommand(String url) {
        super(HystrixCommandGroupKey.Factory.asKey("FaceGroup"));
        this.url = url;
    }

    @Override
    protected FaceResponse run() throws Exception {
        String faceMSAUrl = "http://10.0.115.12/rest/facedetect?url=" + url;

        Client client = ClientBuilder.newBuilder()
                .register(MyObjectMapperProvider.class)
                .register(JacksonFeature.class)
                .build();
        WebTarget target = client.target(faceMSAUrl);
        Future<Response> futureResponse = target
                .request(MediaType.APPLICATION_JSON)
                .async()
                .get();
        try {
            Response response = futureResponse.get();
            return response.readEntity(FaceResponse.class);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected FaceResponse getFallback() {
        FaceResponse fallRes = new FaceResponse();
        fallRes.setAge(-100d);
        fallRes.setGender("male");
        return fallRes;
    }

}
