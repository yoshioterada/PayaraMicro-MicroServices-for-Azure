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
package com.yoshio3.rest.entities;

import com.yoshio3.rest.entities.childelements.Recipient;
import com.yoshio3.rest.entities.childelements.From;
import com.yoshio3.rest.entities.childelements.Conversation;
import com.yoshio3.rest.entities.childelements.Entities;
import com.yoshio3.rest.entities.childelements.Members;
import java.util.Arrays;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yoshio Terada
 */

/*
****************** From Skype ****************** 
{
    "text": "私は魚がすきです",
    "type": "message",
    "timestamp": "2017-03-13T09:17:05.964Z",
    "id": "1489396625954",
    "channelId": "skype",
    "serviceUrl": "https://smba.trafficmanager.net/apis/",
    "from": {
        "id": "29:1eYLZC5KNOxyVZO2T1e3bh4a5ef0ufRHZr2fqnZ7DtOg",
        "name": "Yoshio Terada"
    },
    "conversation": {
        "id": "29:1eYLZC5KNOxyVZO2T1e3bh4a5ef0ufRHZr2fqnZ7DtOg"
    },
    "recipient": {
        "id": "28:99455c71-67f8-49bc-8e29-9d715293674a",
        "name": "yosshibot"
    },
    "entities": [
        {
            "locale": "ja-JP",
            "country": "US",
            "platform": "Windows",
            "type": "clientInfo"
        }
    ]
}
****************** From Web Chat ****************** 
{
    "type": "message",
    "id": "IDOLvGPvvLYGBCADqUWiDr|0000000",
    "timestamp": "2017-03-13T09:29:50.2841633Z",
    "serviceUrl": "https://directline.botframework.com/",
    "channelId": "webchat",
    "from": {
        "id": "I5LyzVvAjFT",
        "name": "You"
    },
    "conversation": {
        "id": "IDOLvGPvvLYGBCADqUWiDr"
    },
    "recipient": {
        "id": "yosshibot@j-FW8cIro70",
        "name": "yosshibot"
    },
    "locale": "ja-jp",
    "text": "私は飲み物がすきです",
    "channelData": {
        "clientActivityId": "1489397374069.6918785236775875.0"
    }
}
*/
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageFromBotFrameWork {

    private String action;
    private String text;
    private String type;
    private String timestamp;
    private String id;
    private String channelId;
    private String serviceUrl;
    private String textFormat;
    private String locale;
    private From from;
    private Conversation conversation;
    private Recipient recipient;
    private Entities[] entities;
    private Members[] membersAdded;


    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
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
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the channelId
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * @param channelId the channelId to set
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * @return the serviceUrl
     */
    public String getServiceUrl() {
        return serviceUrl;
    }

    /**
     * @param serviceUrl the serviceUrl to set
     */
    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    /**
     * @return the formObject
     */
    public From getFrom() {
        return from;
    }

    /**
     * @param formObject the formObject to set
     */
    public void setFrom(From from) {
        this.from = from;
    }

    /**
     * @return the conversation
     */
    public Conversation getConversation() {
        return conversation;
    }

    /**
     * @param conversation the conversation to set
     */
    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
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
     * @return the entities
     */
    public Entities[] getEntities() {
        return entities;
    }

    /**
     * @param entities the entities to set
     */
    public void setEntities(Entities[] entities) {
        this.setEntities(entities);
    }

    @Override
    public String toString() {
        return "MessageFromBotFrameWork{" + "action=" + action + ", text=" + text + ", type=" + type + ", timestamp=" + timestamp + ", id=" + id + ", channelId=" + channelId + ", serviceUrl=" + serviceUrl + ", from=" + from + ", conversation=" + conversation + ", recipient=" + recipient + ", entities=" + Arrays.toString(entities) + '}';
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the membersAdded
     */
    public Members[] getMembersAdded() {
        return membersAdded;
    }

    /**
     * @param membersAdded the membersAdded to set
     */
    public void setMembersAdded(Members[] membersAdded) {
        this.setMembersAdded(membersAdded);
    }

    /**
     * @return the textFormat
     */
    public String getTextFormat() {
        return textFormat;
    }

    /**
     * @param textFormat the textFormat to set
     */
    public void setTextFormat(String textFormat) {
        this.textFormat = textFormat;
    }

    /**
     * @return the locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

}


