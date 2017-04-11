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
import com.netflix.hystrix.HystrixCommandProperties;
import com.yoshio3.faceemotionui.services.EmotionResponse;
import com.yoshio3.faceemotionui.services.MyObjectMapperProvider;
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
public class EmotionDetectHystrixCommand extends HystrixCommand<EmotionResponse> {

    private final String url;
    private static int counter;

    public EmotionDetectHystrixCommand(String url) {
        super(HystrixCommandGroupKey.Factory.asKey("EmotionGroup"));
        this.url = url;
    }

    private void resetCount() {
        if (counter > 5) {
            HystrixCommandProperties.Setter().withCircuitBreakerForceClosed(true);
            counter = 0;
        }
        counter ++;
    }

    @Override
    protected EmotionResponse run() throws Exception {
        String emotionMSAUrl = "http://10.0.12.57/rest/emotionservice?url=" + url;
        Client client = ClientBuilder.newBuilder()
                .register(MyObjectMapperProvider.class)
                .register(JacksonFeature.class)
                .build();
        WebTarget target = client.target(emotionMSAUrl);
        Future<Response> futureResponse = target
                .request(MediaType.APPLICATION_JSON)
                .async()
                .get();

        Response response = futureResponse.get();
        return response.readEntity(EmotionResponse.class);
    }

    @Override
    protected EmotionResponse getFallback() {
        EmotionResponse fallRes = new EmotionResponse();
        fallRes.setAnger(-100d);
        fallRes.setContempt(-100d);
        fallRes.setDisgust(-100d);
        fallRes.setFear(-100d);
        fallRes.setHappiness(-100d);
        fallRes.setNeutral(-100d);
        fallRes.setSadness(-100d);
        fallRes.setSurprise(-100d);
        resetCount();
        return fallRes;
    }
}
