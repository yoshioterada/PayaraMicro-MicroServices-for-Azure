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

import com.yoshio3.rest.entities.AccessTokenEntity;
import com.yoshio3.rest.entities.MyObjectMapperProvider;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 *
 * @author Yoshio Terada
 */
public class AccessTokenService extends BasicRESTService {

    private final static Logger LOGGER = Logger.getLogger(AccessTokenService.class.getName());
    
    private final static String GRANT_TYPE = "client_credentials";
    private final static String BOT_APP_CLIENT_ID;
    private final static String BOT_APP_CLIENT_SECRET;
    private final static String SCOPE = "https://api.botframework.com/.default";
    private final static String LOGIN_SERVER = "https://login.microsoftonline.com/botframework.com/oauth2/v2.0/token";
    private final static int EXPIRE_SEC = 3600;
    
    static {
        BOT_APP_CLIENT_ID = PropertyReaderService.getPropertyValue("BOT_APP_CLIENT_ID");
        BOT_APP_CLIENT_SECRET = PropertyReaderService.getPropertyValue("BOT_APP_CLIENT_SECRET");
    }

    private static String accessToken;
    private static LocalDateTime oldDate;

    public static String getAccesToken() {
        if (accessToken == null) {
            createToken();
        } else {
            LocalDateTime newDate = LocalDateTime.now();
            Duration duration = Duration.between(oldDate, newDate);
            long expireTime = duration.getSeconds();
            LOGGER.log(Level.FINE, "\tEXPIRE TIME : {0}", expireTime);             

            if(expireTime > EXPIRE_SEC){
                createToken();
            }
        }
        return accessToken;
    }

    private static void createToken() {
        accessToken = getAccesTokenFromServer().getAccess_token();
        oldDate = LocalDateTime.now();        
        LOGGER.log(Level.FINE, "CREATED or REFRESHED Token : {0}\tDate: {1}", new Object[]{accessToken, oldDate});
    }

    private static AccessTokenEntity getAccesTokenFromServer() {
        Client client = ClientBuilder.newBuilder()
                .register(MyObjectMapperProvider.class)
                .register(JacksonFeature.class)
                .build();

        MultivaluedHashMap<String, String> formdata = new MultivaluedHashMap<>();
        formdata.putSingle("grant_type", GRANT_TYPE);
        formdata.putSingle("client_id", BOT_APP_CLIENT_ID);
        formdata.putSingle("client_secret", BOT_APP_CLIENT_SECRET);
        formdata.putSingle("scope", SCOPE);

        WebTarget target = client
                .target(LOGIN_SERVER);
        Response response = target
                .request(MediaType.APPLICATION_FORM_URLENCODED)
                .post(Entity.form(formdata));
        Response.StatusType statusInfo = response.getStatusInfo();
        Response.Status.Family family = statusInfo.getFamily();

        AccessTokenEntity readEntity;
        if (family == Response.Status.Family.SUCCESSFUL) {
            readEntity = response.readEntity(AccessTokenEntity.class);
            return readEntity;
        } else {
            return null;
        }
    }
}
