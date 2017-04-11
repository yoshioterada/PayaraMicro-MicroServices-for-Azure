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
package com.yoshio3;

import com.yoshio3.rest.entities.MessageFromBotFrameWork;
import com.yoshio3.rest.entities.facebook.FacebookMessageFromBotFramework;
import com.yoshio3.rest.entities.luis.ResponseFromLUIS;
import com.yoshio3.rest.entities.luis.childelements.Entity;
import com.yoshio3.services.AccessTokenService;
import com.yoshio3.services.LUISService;
import com.yoshio3.services.BotService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 *
 * @author Yoshio Terada
 */
@Path("message")
public class BotMessageReceiver {

    private final static Logger LOGGER = Logger.getLogger(BotMessageReceiver.class.getName());

    @Context
    private ResourceContext resourceContext;

//    @Resource (Java EE 7 Recomendation)
//    ManagedExecutorService managedExecsvc;
    private final ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();

    @POST
    @Consumes("application/json")
    public Response post(FacebookMessageFromBotFramework message) {
        executorService.submit(() -> invokeService(message));
        return Response.ok().status(202).build();
    }

    private void invokeService(MessageFromBotFrameWork requestMessage) {
        final String inputString = requestMessage.getText();
        LOGGER.log(Level.FINE, "Input Data from user : {0}", inputString);
        String action = requestMessage.getAction();

        if (action == null) {
            LOGGER.log(Level.FINE, "ACTION NULL");
            //Following is the action of bot behavior.
            if (!requestMessage.getId().isEmpty()) {
                LOGGER.log(Level.FINE, "Request Message is not Empty");
                invokeLUISAndSendResponse(requestMessage, inputString);
            }
        } else if (action.equals("add")) {
            String message = "Welcome to My SkypeBot";
            sendMessageToBotFramework(requestMessage, message);
        } else {
            LOGGER.log(Level.FINE, "ACTION IS INVALID ? :{0}", requestMessage);
        }
    }

    private void sendMessageToBotFramework(MessageFromBotFrameWork requestMessage, String message) {
        String token = AccessTokenService.getAccesToken();
        BotService botService = resourceContext.getResource(BotService.class);
        botService.sendResponse(requestMessage, token, message);
    }

    private String invokeLUISAndSendResponse(MessageFromBotFrameWork requestMessage, String inputString) {
        LUISService luisService = new LUISService();
        Optional<ResponseFromLUIS> responseFromLUIS = luisService.getResponseFromLUIS(inputString);

        String responseMessage = "";
        if (responseFromLUIS.isPresent()) {
            ResponseFromLUIS luis = responseFromLUIS.get();

            String topIntent = luis.getTopScoringIntent().getIntent();
            switch (topIntent) {
                case "favorite":
                case "faveng":
                    execForFavorite(requestMessage, luis, topIntent);
                    break;
                case "weather":
                    execForWeather(requestMessage,luis);
                    break;
                default:

            }
        }
        return responseMessage;
    }

    private void execForFavorite(MessageFromBotFrameWork requestMessage, ResponseFromLUIS luis, String intent) {
        List<Entity> entities = Arrays.asList(luis.getEntities());
        //Get the data of favorite things.
        Optional<String> favorite = entities.stream()
                .filter(ent -> ent.getType().equals("favorite"))
                .map(filterEnt -> filterEnt.getEntity())
                .findFirst();
        String fav = favorite.get();
        String message = "";        
        if (intent.equals("favorite")) {
            message = "あなたは「" + fav + "」がすきなんですね！！";
        } else {
            message = "You like [" + fav + "], don't you !";
        }
        sendMessageToBotFramework(requestMessage, message);
        // In this sample, I got the array of entities from JSON
        // After that, I only picked up the "favorite" Entity.
        // Finaly changed object(map) from Entity to entity as String.
        // In this time "桜" was picked up and returned.
        //     {
        //      "entity": "桜",
        //      "type": "favorite",
        //      "startIndex": 2,
        //      "endIndex": 2,
        //      "score": 0.9128002
        //    },
        //
    }

    public void execForWeather(MessageFromBotFrameWork requestMessage,ResponseFromLUIS luis) {
        List<Entity> entities = Arrays.asList(luis.getEntities());
        Optional<String> today = entities.stream()
                .filter(ent -> ent.getType().equals("today"))
                .map(filterEnt -> filterEnt.getEntity())
                .findFirst();
        String day = today.get();

        String message = "";
        switch (day) {
            case "今日":
                message = "今日の天気は晴れです！！";
                break;
            case "明日":
                message = "明日の天気はくもりの予定です！！";
                break;
            case "昨日":
                message = "昨日の天気は雨でした";
                break;
            case "明後日":
                message = "明後日の天気は晴れの予定です！！";
                break;
            default:
                break;
        }
        sendMessageToBotFramework(requestMessage, message);
    }

    /* 
 Actual Response Data(Example) FROM LUIS
  "query": "私は桜がすきです",
  "topScoringIntent": {
    "intent": "favorite",
    "score": 0.8490921
  },
  "intents": [
    {
      "intent": "favorite",
      "score": 0.8490921
    },
    {
      "intent": "None",
      "score": 0.3008063
    }
  ],
  "entities": [
    {
      "entity": "私 は",
      "type": "me",
      "startIndex": 0,
      "endIndex": 1,
      "score": 0.842303157
    },
    {
      "entity": "桜",
      "type": "favorite",
      "startIndex": 2,
      "endIndex": 2,
      "score": 0.9128002
    },
    {
      "entity": "が すき",
      "type": "like",
      "startIndex": 3,
      "endIndex": 5,
      "score": 0.881795466
    }
  ]
}
        
{
  "query": "今日の天気おしえて",
  "topScoringIntent": {
    "intent": "weather",
    "score": 0.999999046
  },
  "intents": [
    {
      "intent": "weather",
      "score": 0.999999046
    },
    {
      "intent": "favorite",
      "score": 0.02395542
    },
    {
      "intent": "faveng",
      "score": 0.0114427833
    },
    {
      "intent": "None",
      "score": 0.00914523
    }
  ],
  "entities": [
    {
      "entity": "天気",
      "type": "weather",
      "startIndex": 3,
      "endIndex": 4,
      "score": 0.9910516
    },
    {
      "entity": "今日",
      "type": "today",
      "startIndex": 0,
      "endIndex": 1,
      "score": 0.960057855
    },
    {
      "entity": "おしえ て",
      "type": "teach",
      "startIndex": 5,
      "endIndex": 8,
      "score": 0.6887552
    }
  ]
}        
     */
}
