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
package com.yoshio3.rest.entities.luis.childelements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yoshio Terada
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Entity {

    private String entity;
    private String type;
    private Long startIndex;
    private Long endIntex;
    private String score;

    /**
     * @return the entity
     */
    public String getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(String entity) {
        this.entity = entity;
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
     * @return the startIndex
     */
    public Long getStartIndex() {
        return startIndex;
    }

    /**
     * @param startIndex the startIndex to set
     */
    public void setStartIndex(Long startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * @return the endIntex
     */
    public Long getEndIntex() {
        return endIntex;
    }

    /**
     * @param endIntex the endIntex to set
     */
    public void setEndIntex(Long endIntex) {
        this.endIntex = endIntex;
    }

    /**
     * @return the score
     */
    public String getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Entity{" + "entity=" + entity + ", type=" + type + ", startIndex=" + startIndex + ", endIntex=" + endIntex + ", score=" + score + '}';
    }

}
