package com.yoshio3.facedetect;
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
import com.yoshio3.facedetect.services.entities.MyObjectMapperProvider;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author yoterada
 */
@ApplicationPath("/rest")
public class FaceDetectRESTApplication extends ResourceConfig  {
    public FaceDetectRESTApplication() {
        packages(FaceDetectRESTApplication.class.getPackage().getName());
        super.register(MyObjectMapperProvider.class);
        super.register(JacksonFeature.class);
    }
    
}

