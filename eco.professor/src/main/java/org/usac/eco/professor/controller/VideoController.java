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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class VideoController {
    
    private List<IVideoController> listeners;

    public VideoController(IVideoController ivc) {
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
    
    public final boolean addVideoControllerListener(IVideoController ivc){
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
    
}
