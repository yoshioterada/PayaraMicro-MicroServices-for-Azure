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


import com.yoshio3.rest.entities.luis.ResponseFromLUIS;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Yoshio Terada
 */
public class LUISService extends BasicRESTService {

    private final static Logger LOGGER = Logger.getLogger(LUISService.class.getName());
    private final static String LUIS_SERVER_URL;

    static {
        LUIS_SERVER_URL = PropertyReaderService.getPropertyValue("LUIS_SERVER_URL");
    }
    
    public Optional<ResponseFromLUIS> getResponseFromLUIS(String inputString){
        LOGGER.log(Level.FINE, "invokeLUIS query: {0}", inputString);
        try {
            inputString = URLEncoder.encode(inputString, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, "UTF-8 is not supported", ex);
        }
        String url = LUIS_SERVER_URL + inputString;
        LOGGER.log(Level.FINE, "Request URL for LUIS : {0}", url);

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Response response = target
                .request(MediaType.WILDCARD)
                .get();

        if (isRequestSuccess(response)) {
            ResponseFromLUIS luis = response.readEntity(ResponseFromLUIS.class);
            return Optional.of(luis);
        }else{
            handleIllegalState(response);
            return Optional.empty();            
        }
    }
}
