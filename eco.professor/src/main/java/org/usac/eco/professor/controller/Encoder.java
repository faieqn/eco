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

import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IContainerFormat;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.video.ConverterFactory;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import org.usac.eco.professor.Log;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class Encoder extends Thread {
    
    private VideoDevice videoDevice;
    
    private String URI;
    
    private List<EncoderListener> listener;
    
    public Encoder(VideoDevice videoDevice, String URI){
        this.URI = URI;
        setVideoDevice(videoDevice);
        
        listener = new ArrayList<EncoderListener>();
    }

    public final void setVideoDevice(VideoDevice videoDevice) {
        this.videoDevice = videoDevice;
    }
    
    public boolean addEncoderListener(EncoderListener el){
        if(el == null){
            return false;
        }
        
        return listener.add(el);
    }
    
    @Override
    public void run() {
        System.out.println("Init RTMP Container...");
        IContainer rtmpContainer = IContainer.make();
        IContainerFormat containerFormat = IContainerFormat.make();
        containerFormat.setOutputFormat("flv", URI, null);
        System.out.println("Connected " + URI);
        rtmpContainer.setInputBufferLength(0);
        
        int retVal;
        retVal = rtmpContainer.open(URI, IContainer.Type.WRITE, containerFormat);
        if (retVal < 0) {
            Log.fatal("Could not open output container for live streaming in " + URI);
            fireOnError(EncoderMessage.ERROR_OPEN_OUTPUT_CONTAINER);
            return;
        }
        
        System.out.println("Setting conf for video codec...");
        IStream videoStream = rtmpContainer.addNewStream(EncoderConstants.AUDIO_CODEC);
        IStreamCoder videoCoder = videoStream.getStreamCoder();
        videoCoder.setFlag(IStreamCoder.Flags.FLAG_QSCALE, true);
        
        videoCoder.setNumPicturesInGroupOfPictures(EncoderConstants.VIDEO_PICTURES_IN_GROUP);
        videoCoder.setBitRate(EncoderConstants.VIDEO_BIT_RATE);
        videoCoder.setBitRateTolerance(EncoderConstants.VIDEO_BIT_RATE_TOLERANCE);
        videoCoder.setPixelType(EncoderConstants.VIDEO_PIXELFORMAT_TYPE);
        videoCoder.setHeight(EncoderConstants.VIDEO_DIMENSION.height);
        videoCoder.setWidth(EncoderConstants.VIDEO_DIMENSION.width);
        videoCoder.setGlobalQuality(EncoderConstants.VIDEO_GLOBAL_QUALITY);
        videoCoder.setFrameRate(EncoderConstants.VIDEO_FRAME_RATE);
        videoCoder.setTimeBase(EncoderConstants.VIDEO_TIME_BASE);
        
        videoCoder.open(EncoderConstants.getVideoOptions(), 
                        EncoderConstants.getVideoUnsetOptions());
        
        System.out.println("Setting conf for audio codec...");
        IStream audioStream = rtmpContainer.addNewStream(EncoderConstants.AUDIO_CODEC);
        IStreamCoder audioCoder = audioStream.getStreamCoder();
        
        audioCoder.setGlobalQuality(EncoderConstants.AUDIO_GLOBAL_QUALITY);
        audioCoder.setChannels(EncoderConstants.AUDIO_CHANNEL_COUNT);
        audioCoder.setBitRate(EncoderConstants.AUDIO_BIT_RATE);
        audioCoder.setBitRateTolerance(EncoderConstants.AUDIO_BIT_RATE_TOLERANCE);
        audioCoder.setSampleRate(EncoderConstants.AUDIO_SAMPLE_RATE);
        audioCoder.setTimeBase(EncoderConstants.AUDIO_TIME_BASE);
        
        audioCoder.open(EncoderConstants.getAudioOptions(), 
                        EncoderConstants.getAudioUnsetOptions());
        
        System.out.println("Writing container header...");
        rtmpContainer.writeHeader();
        
        System.out.println("Loading logo usac...");
        BufferedImage logo = null;
        try {
            logo = ImageIO.read(new File(EncoderConstants.VIDEO_LOGO));
        } catch (IOException ex) {
            Log.error("Log not found: " + EncoderConstants.VIDEO_LOGO);
            fireOnError(EncoderMessage.ERROR_LOGO_NOT_FOUND);
        }
        
        AudioFormat audioFormat = new AudioFormat(EncoderConstants.AUDIO_BIT_RATE, 
                                                EncoderConstants.AUDIO_SAMPLE_SIZE_BITS, 
                                                EncoderConstants.AUDIO_SAMPLE_RATE, true, false);
        DataLine.Info audioInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
        TargetDataLine targetDataLine = null;
        try {
            targetDataLine = (TargetDataLine)AudioSystem.getLine(audioInfo);
        } catch (LineUnavailableException ex) {
            Log.fatal("Could not get Audio Info from Microphone", ex);
            fireOnError(EncoderMessage.ERROR_AUDIO_DATALINE);
            return;
        }
        
        int audioBufferSize = (int)audioFormat.getSampleRate() * audioFormat.getFrameSize();
        byte[] audioBuffer = new byte[audioBufferSize];
        try {
            targetDataLine.open(audioFormat);
        } catch (LineUnavailableException ex) {
            Log.error("Could not get Audio Info from Microphone", ex);
            fireOnError(EncoderMessage.ERROR_AUDIO_DATALINE);
            return;
        }
        targetDataLine.start();
        
        long timeStart = System.currentTimeMillis();
        int size = 0;
        
        System.out.println("Start capture...");
        while(true){
            long timeNow = System.currentTimeMillis();
            long timeStampMedia = (timeNow - timeStart) * 1000;
            
            BufferedImage frameImage = ConverterFactory.convertToType(videoDevice.getImage(), 
                                                                      BufferedImage.TYPE_3BYTE_BGR);
            if (logo != null){
                Graphics g = frameImage.getGraphics();
                    g.drawImage(logo, 10, 10, null);
                    g.dispose();
            }
            
            
        }
    }
    
    private void fireOnError(EncoderMessage em){
        Iterator<EncoderListener> iterator = listener.iterator();
        while(iterator.hasNext()){
            iterator.next().onError(em);
        }
    }
        
}
