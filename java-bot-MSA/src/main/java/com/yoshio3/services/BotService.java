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
package com.yoshio3.services;

import com.yoshio3.rest.entities.childelements.Conversation;
import com.yoshio3.rest.entities.childelements.From;
import com.yoshio3.rest.entities.childelements.Members;
import com.yoshio3.rest.entities.MessageBackToBotFramework;
import com.yoshio3.rest.entities.MessageFromBotFrameWork;
import com.yoshio3.rest.entities.MyObjectMapperProvider;
import com.yoshio3.rest.entities.childelements.Recipient;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
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
@RequestScoped
public class BotService extends BasicRESTService {

    private final static Logger LOGGER = Logger.getLogger(BotService.class.getName());

    
    public void sendResponse(MessageFromBotFrameWork requestMessage, String accessToken, String message) {
        MessageBackToBotFramework creaeteResponseMessage = creaeteResponseMessage(requestMessage, message);
        LOGGER.log(Level.FINE, "Back Messages:{0}", creaeteResponseMessage.toString());

        String serviceUrl = requestMessage.getServiceUrl();

        String id = requestMessage.getId();
        String convID = requestMessage.getConversation().getId();
        try {
            convID = URLEncoder.encode(convID, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, "UTF-8 is not supported", ex);
        }

        String uri = serviceUrl + "/v3/conversations/" + convID + "/activities"; // + id;

        Client client = ClientBuilder.newBuilder()
                .register(MyObjectMapperProvider.class)
                .register(JacksonFeature.class)
                .build();
        WebTarget target = client
                .target(uri);
        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken)
                .post(Entity.entity(creaeteResponseMessage, MediaType.APPLICATION_JSON));

        if (!isRequestSuccess(response)) {
            LOGGER.log(Level.SEVERE, "{0}:{1}", new Object[]{response.getStatus(), response.readEntity(String.class)});
            handleIllegalState(response);
        }
    }

    private MessageBackToBotFramework creaeteResponseMessage(MessageFromBotFrameWork requestMessage, String message) {
        MessageBackToBotFramework resMsg = new MessageBackToBotFramework();
        resMsg.setType("message");

        //// 2016-08-18T09:31:31.756Z (UTC Time)
        Instant instant = Instant.now();
        String currentUTC = instant.toString();
        resMsg.setTimestamp(currentUTC);

        From from = new From();
        from.setId(requestMessage.getRecipient().getId());
        from.setName(requestMessage.getRecipient().getName());
        resMsg.setFrom(from);

        Conversation conversation = new Conversation();
        conversation.setId(requestMessage.getConversation().getId());
        resMsg.setConversation(conversation);

        Recipient recipient = new Recipient();
        recipient.setId(requestMessage.getRecipient().getId());
        recipient.setName(requestMessage.getRecipient().getName());
        resMsg.setRecipient(recipient);

        Members member = new Members();
        member.setId(requestMessage.getFrom().getId());
        member.setName(requestMessage.getFrom().getName());
        Members[] members = new Members[1];
        members[0] = member;
        resMsg.setMembers(members);

        resMsg.setText(message);
        resMsg.setReplyToId(requestMessage.getId());
        return resMsg;
    }
}
