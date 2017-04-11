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
package com.yoshio3.rest.entities.facebook;

import com.yoshio3.rest.entities.MessageFromBotFrameWork;
import com.yoshio3.rest.entities.facebook.childelements.ChannelData;

/**
 *
 * @author Yoshio Terada
 */

/*
****************** From FaceBook ****************** 
{
    "type": "message",
    "id": "mid.$cAAaPAhT054Bg-EYnOFaxwDeJuUut",
    "timestamp": "2017-03-13T09:30:07.544Z",
    "serviceUrl": "https://facebook.botframework.com",
    "channelId": "facebook",
    "from": {
        "id": "1268112909951884",
        "name": "Yoshio Terada"
    },
    "conversation": {
        "isGroup": false,
        "id": "1268112909951884-1846089345658577"
    },
    "recipient": {
        "id": "1846089345658577",
        "name": "yosshibot"
    },
    "text": "私は食べ物がすきです",
    "channelData": {
        "sender": {
            "id": "1268112909951884"
        },
        "recipient": {
            "id": "1846089345658577"
        },
        "timestamp": 1489397407544,
        "message": {
            "mid": "mid.$cAAaPAhT054Bg-EYnOFaxwDeJuUut",
            "seq": 50006,
            "text": "私は食べ物がすきです",
            "is_echo": false
        }
    }
}
*/

public class FacebookMessageFromBotFramework extends MessageFromBotFrameWork{
    private String locale;
    private ChannelData channelData; 

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

    /**
     * @return the channelData
     */
    public ChannelData getChannelData() {
        return channelData;
    }

    /**
     * @param channelData the channelData to set
     */
    public void setChannelData(ChannelData channelData) {
        this.channelData = channelData;
    }

    

}
