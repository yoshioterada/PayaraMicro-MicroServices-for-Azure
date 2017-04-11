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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yoshio Terada
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AccessTokenEntity {
    private String token_type;
    private Long expires_in;
    private Long ext_expires_in;
    private String access_token;

    /**
     * @return the token_type
     */
    public String getToken_type() {
        return token_type;
    }

    /**
     * @param token_type the token_type to set
     */
    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    /**
     * @return the expires_in
     */
    public Long getExpires_in() {
        return expires_in;
    }

    /**
     * @param expires_in the expires_in to set
     */
    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    /**
     * @return the ext_expires_in
     */
    public Long getExt_expires_in() {
        return ext_expires_in;
    }

    /**
     * @param ext_expires_in the ext_expires_in to set
     */
    public void setExt_expires_in(Long ext_expires_in) {
        this.ext_expires_in = ext_expires_in;
    }

    /**
     * @return the access_token
     */
    public String getAccess_token() {
        return access_token;
    }

    /**
     * @param access_token the access_token to set
     */
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "AccessTokenEntity{" + "token_type=" + token_type + ", expires_in=" + expires_in + ", ext_expires_in=" + ext_expires_in + ", access_token=" + access_token + '}';
    }
}
