/*
 * Copyright (C) 2013 USAC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.usac.eco.professor.controller;

import com.github.sarxos.webcam.Webcam;
import com.zodiac.soa.Request;
import com.zodiac.soa.client.DynamicServiceHandler;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.professor.Configure;
import org.usac.eco.professor.Log;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class VideoController {
    
    private List<IVideoController> listeners;
    
    public VideoController(IVideoController ivc) {
        listeners = new ArrayList<IVideoController>();
        addVideoControllerListener(ivc);
        
        List<VideoDevice> desktop = new ArrayList<VideoDevice>();
        desktop.add(new RobotWideScreenDevice());
        fireListDesktop(desktop);
        
        List<VideoDevice> webcams = new ArrayList<VideoDevice>();
        int countCameras = Webcam.getWebcams().size();
        for(int i = 0; i < countCameras; i++){
            webcams.add(new WebcamDevice(Webcam.getWebcams().get(i)));
        }
        fireListWebcam(webcams);
    }
    
    public void publish(DTOCourse dtoCourse) throws MalformedURLException, Exception{
        String clazz = "org.usac.classroom.bl.Course";
        Class paramsConstructor[] = null;
        Object argsConstructor[] = null;
        String method = "publish";
        Class paramsMethod[] = {DTOCourse.class};
        Object argsMethod[] = {dtoCourse};
        Request request = new Request(clazz, paramsConstructor, argsConstructor, 
                method, paramsMethod, argsMethod);
        DynamicServiceHandler dsh = new DynamicServiceHandler(Configure.CLASSROOM);
        boolean published = (Boolean)dsh.run(request);
        if(!published){
            Log.fatal("Could not publish: unknown cause.");
            fireOnError(dtoCourse, VideoControllerMessage.ERROR_ON_UNPUBLISH);
        }
    }
    
    public void unpublish(DTOCourse dtoCourse) throws Exception{
        String clazz = "org.usac.classroom.bl.Course";
        Class paramsConstructor[] = null;
        Object argsConstructor[] = null;
        String method = "unpublish";
        Class paramsMethod[] = {DTOCourse.class};
        Object argsMethod[] = {dtoCourse};
        Request request = new Request(clazz, paramsConstructor, argsConstructor, 
                method, paramsMethod, argsMethod);
        DynamicServiceHandler dsh = new DynamicServiceHandler(Configure.CLASSROOM);
        boolean unpublished = (Boolean)dsh.run(request);
        if(!unpublished){
            Log.fatal("Could not unpublish: unknown cause.");
            fireOnError(dtoCourse, VideoControllerMessage.ERROR_ON_PUBLISH);
        }
    }
    
    public final boolean addVideoControllerListener(IVideoController ivc){
        if(ivc == null){
            return false;
        }
        return listeners.add(ivc);
    }
    
    private void fireListWebcam(List<VideoDevice> devices){
        Iterator<IVideoController> iterator = listeners.iterator();
        while(iterator.hasNext()){
            iterator.next().listWebcam(devices);
        }
    }
    
    private void fireListDesktop(List<VideoDevice> devices){
        Iterator<IVideoController> iterator = listeners.iterator();
        while(iterator.hasNext()){
            iterator.next().listDesktop(devices);
        }
    }
    
    private void fireOnError(DTOCourse dtoCourse, VideoControllerMessage vcm){
        Iterator<IVideoController> iterator = listeners.iterator();
        while(iterator.hasNext()){
            iterator.next().onError(dtoCourse, vcm);
        }
    }
    
}
