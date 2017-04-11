package com.yoshio3.faceemotionui.msa;
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
import com.yoshio3.faceemotionui.services.EmotionResponse;
import com.yoshio3.faceemotionui.services.FaceResponse;
import com.yoshio3.faceemotionui.services.StorageService;
import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import org.primefaces.event.CaptureEvent;

/**
 *
 * @author Yoshio Terada
 */
@Named(value = "photoup")
@RequestScoped
public class PhotoUploader implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(PhotoUploader.class.getName());

    //Please change following 4 lines (Blob URL)    
    protected final static String UPLOAD_DIRECTORY_NAME_OF_BLOB = "uploaded";
    private final static String AZURE_BLOG_UPLOAD_URL = "https://yoshiofileup.blob.core.windows.net/" + UPLOAD_DIRECTORY_NAME_OF_BLOB + "/";

    private final static String IMAGE_FORMAT_EXTENSION = ".jpg";

    private Double anger;
    private Double contempt;
    private Double disgust;
    private Double fear;
    private Double happiness;
    private Double neutral;
    private Double sadness;
    private Double surprise;
    private Double age;
    private String gender;

    @Inject
    private StorageService storageService;

    private String fileURL;

    public void sendPhoto(CaptureEvent captureEvent) {
        try {
            //ファイル名の作成
            UUID uuid = UUID.randomUUID();
            String fileName = uuid.toString() + IMAGE_FORMAT_EXTENSION;
            byte[] data = captureEvent.getData();

            //Azure Storage にファイルのアップロード
            storageService.uploadFile(data, fileName);
            //アップロードされたファイルの URL
            fileURL = AZURE_BLOG_UPLOAD_URL + fileName;

            FaceResponse faceRes = new FaceDetectHystrixCommand(fileURL).execute();
            age = faceRes.getAge();
            gender = faceRes.getGender();

            EmotionResponse emoRes = new EmotionDetectHystrixCommand(fileURL).execute();
            anger = emoRes.getAnger();
            contempt = emoRes.getContempt();
            disgust = emoRes.getDisgust();
            fear = emoRes.getFear();
            happiness = emoRes.getHappiness();
            neutral = emoRes.getNeutral();
            sadness = emoRes.getSadness();
            surprise = emoRes.getSurprise();

        } catch (Exception ex) {
            Logger.getLogger(PhotoUploader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     REST 呼び出し成功か否かの判定
     */
    protected boolean checkRequestSuccess(Response response) {
        Response.StatusType statusInfo = response.getStatusInfo();
        Response.Status.Family family = statusInfo.getFamily();
        return family != null && family == Response.Status.Family.SUCCESSFUL;
    }

    /*
     REST 呼び出しエラー時の処理
     */
    protected void handleIllegalState(Response response) throws IllegalStateException {
        String error = response.readEntity(String.class
        );
        LOGGER.log(Level.SEVERE, "{0}", error);
        throw new IllegalStateException(error);
    }

    public String getGender() {
        if (gender == null) {
            return gender;
        } else if (gender.equals("male")) {
            return "男性";
        } else {
            return "女性";
        }
    }

    public String getFileURL() {
        return fileURL;
    }

    /**
     * @return the anger
     */
    public Double getAnger() {
        return anger;
    }

    /**
     * @return the contempt
     */
    public Double getContempt() {
        return contempt;
    }

    /**
     * @return the disgust
     */
    public Double getDisgust() {
        return disgust;
    }

    /**
     * @return the fear
     */
    public Double getFear() {
        return fear;
    }

    /**
     * @return the happiness
     */
    public Double getHappiness() {
        return happiness;
    }

    /**
     * @return the neutral
     */
    public Double getNeutral() {
        return neutral;
    }

    /**
     * @return the sadness
     */
    public Double getSadness() {
        return sadness;
    }

    /**
     * @return the surprise
     */
    public Double getSurprise() {
        return surprise;
    }

    public Double getAge() {
        return age;
    }
}
