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
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import java.awt.image.BufferedImage;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class WebcamDevice extends AbstractVideoDevice implements WebcamListener{
    
    private Webcam webcam;
    
    static {
        Webcam.setAutoOpenMode(true);
    }

    public WebcamDevice(Webcam webcam) {
        if(webcam == null){
            throw new IllegalArgumentException("webcam cannot be null or empty");
        }
        this.webcam = webcam;
        webcam.setViewSize(EncoderConstants.VIDEO_DIMENSION);
    }
    
    public synchronized BufferedImage getImage() {
        return webcam.getImage();
    }

    public String getName() {
        return webcam.getName();
    }
    
    public void webcamOpen(WebcamEvent we) {
        super.fireOnOpen(new VideoDeviceEvent(this));
    }

    public void webcamClosed(WebcamEvent we) {
        super.fireOnClosed(new VideoDeviceEvent(this));
    }

    public void webcamDisposed(WebcamEvent we) {}
        
}
