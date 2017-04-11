package com.yoshio3.faceemotionui.services;
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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yoterada
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EmotionResponse {
    private Double anger;
    private Double contempt;
    private Double disgust;    
    private Double fear;    
    private Double happiness;    
    private Double neutral;
    private Double sadness;
    private Double surprise;

    /**
     * @return the anger
     */
    public Double getAnger() {
        return anger;
    }

    /**
     * @param anger the anger to set
     */
    public void setAnger(Double anger) {
        this.anger = anger;
    }

    /**
     * @return the contempt
     */
    public Double getContempt() {
        return contempt;
    }

    /**
     * @param contempt the contempt to set
     */
    public void setContempt(Double contempt) {
        this.contempt = contempt;
    }

    /**
     * @return the disgust
     */
    public Double getDisgust() {
        return disgust;
    }

    /**
     * @param disgust the disgust to set
     */
    public void setDisgust(Double disgust) {
        this.disgust = disgust;
    }

    /**
     * @return the fear
     */
    public Double getFear() {
        return fear;
    }

    /**
     * @param fear the fear to set
     */
    public void setFear(Double fear) {
        this.fear = fear;
    }

    /**
     * @return the happiness
     */
    public Double getHappiness() {
        return happiness;
    }

    /**
     * @param happiness the happiness to set
     */
    public void setHappiness(Double happiness) {
        this.happiness = happiness;
    }

    /**
     * @return the neutral
     */
    public Double getNeutral() {
        return neutral;
    }

    /**
     * @param neutral the neutral to set
     */
    public void setNeutral(Double neutral) {
        this.neutral = neutral;
    }

    /**
     * @return the sadness
     */
    public Double getSadness() {
        return sadness;
    }

    /**
     * @param sadness the sadness to set
     */
    public void setSadness(Double sadness) {
        this.sadness = sadness;
    }

    /**
     * @return the surprise
     */
    public Double getSurprise() {
        return surprise;
    }

    /**
     * @param surprise the surprise to set
     */
    public void setSurprise(Double surprise) {
        this.surprise = surprise;
    }


}
