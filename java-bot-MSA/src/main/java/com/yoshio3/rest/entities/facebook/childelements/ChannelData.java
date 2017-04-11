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
package com.yoshio3.rest.entities.facebook.childelements;

import com.yoshio3.rest.entities.childelements.Recipient;
import com.yoshio3.rest.entities.childelements.Sender;

/**
 *
 * @author Yoshio Terada
 */
public class ChannelData {
    private String clientActivityId;
    private Sender sender;
    private Recipient recipient;
    private String timestamp;
    private FaceBookMessage message;
    

    /**
     * @return the clientActivityId
     */
    public String getClientActivityId() {
        return clientActivityId;
    }

    /**
     * @param clientActivityId the clientActivityId to set
     */
    public void setClientActivityId(String clientActivityId) {
        this.clientActivityId = clientActivityId;
    }

    /**
     * @return the sender
     */
    public Sender getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(Sender sender) {
        this.sender = sender;
    }

    /**
     * @return the recipient
     */
    public Recipient getRecipient() {
        return recipient;
    }

    /**
     * @param recipient the recipient to set
     */
    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the message
     */
    public FaceBookMessage getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(FaceBookMessage message) {
        this.message = message;
    }
    
}
