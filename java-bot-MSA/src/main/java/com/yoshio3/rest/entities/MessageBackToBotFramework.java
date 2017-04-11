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
import com.yoshio3.rest.entities.childelements.Members;
import com.yoshio3.rest.entities.childelements.Conversation;
import java.util.Arrays;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yoshio Terada
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageBackToBotFramework {

    private String type;
    private String timestamp;
    private From from;
    private Conversation conversation;
    private Recipient recipient;
    private String text;
    private String replyToId;
    private Members[] members;

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
     * @return the from
     */
    public From getFrom() {
        return from;
    }

    /**
     * @param from the from to set
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
     * @return the replyToId
     */
    public String getReplyToId() {
        return replyToId;
    }

    /**
     * @param replyToId the replyToId to set
     */
    public void setReplyToId(String replyToId) {
        this.replyToId = replyToId;
    }

    /**
     * @return the members
     */
    public Members[] getMembers() {
        return members;
    }

    /**
     * @param members the members to set
     */
    public void setMembers(Members[] members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "MessageBackToBotFramework{" + "type=" + type + ", timestamp=" + timestamp + ", from=" + from + ", conversation=" + conversation + ", recipient=" + recipient + ", text=" + text + ", replyToId=" + replyToId + ", members=" + Arrays.toString(members) + '}';
    }
}

