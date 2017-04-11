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
package com.yoshio3.rest.entities.luis;

import com.yoshio3.rest.entities.luis.childelements.TopScoringIntent;
import com.yoshio3.rest.entities.luis.childelements.Intent;
import com.yoshio3.rest.entities.luis.childelements.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yoshio Terada
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseFromLUIS{

private String query;
private TopScoringIntent topScoringIntent;
private Intent[] intents;
private Entity[] entities;

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * @return the topScoringIntent
     */
    public TopScoringIntent getTopScoringIntent() {
        return topScoringIntent;
    }

    /**
     * @param topScoringIntent the topScoringIntent to set
     */
    public void setTopScoringIntent(TopScoringIntent topScoringIntent) {
        this.topScoringIntent = topScoringIntent;
    }

    /**
     * @return the intents
     */
    public Intent[] getIntents() {
        return intents;
    }

    /**
     * @param intents the intents to set
     */
    public void setIntents(Intent[] intents) {
        this.intents = intents;
    }

    /**
     * @return the entities
     */
    public Entity[] getEntities() {
        return entities;
    }

    /**
     * @param entities the entities to set
     */
    public void setEntities(Entity[] entities) {
        this.entities = entities;
    }

    @Override
    public String toString() {
        return "ResponseFromLUIS{" + "query=" + query + ", topScoringIntent=" + topScoringIntent + ", intents=" + intents + ", entities=" + entities + '}';
    }



} 
