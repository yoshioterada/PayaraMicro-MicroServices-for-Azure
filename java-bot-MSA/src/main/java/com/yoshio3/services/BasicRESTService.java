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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;

/**
 *
 * @author Yoshio Terada
 */
public class BasicRESTService {

    /*
     Check the REST Invocation is success or failed.
     */
    protected boolean isRequestSuccess(Response response) {
        Response.StatusType statusInfo = response.getStatusInfo();
        Response.Status.Family family = statusInfo.getFamily();
        return family != null && family == Response.Status.Family.SUCCESSFUL;
    }

    /*
     Operate when the REST invocaiton failed.
     */
    protected void handleIllegalState(Response response)
            throws IllegalStateException {
        String error = response.readEntity(String.class);
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, error);
        throw new IllegalStateException(error);
    }
}
