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

import com.github.sarxos.webcam.WebcamResolution;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IMetaData;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IRational;
import java.awt.Dimension;

/**
 *
 * @author brian
 */
public class EncoderConstants {
    
    public static final String SHORTNAME_OUTPUT_FORMAT = "flv";
    
    public IMetaData getContainerOptions(){return IMetaData.make();}
    
    /***** Video Attributes ******/
    
    public static final ICodec.ID VIDEO_CODEC = ICodec.ID.CODEC_ID_FLV1;
    
    public static final Dimension VIDEO_DIMENSION = WebcamResolution.VGA.getSize();
    
    public static final int VIDEO_PICTURES_IN_GROUP = 12;
    
    public static final int VIDEO_BIT_RATE = 200000;
    
    public static final int VIDEO_BIT_RATE_TOLERANCE = 4000000;
    
    public static final IPixelFormat.Type VIDEO_PIXELFORMAT_TYPE = IPixelFormat.Type.YUV420P;
    
    public static final int VIDEO_GLOBAL_QUALITY = 0;
    
    public static final IRational VIDEO_FRAME_RATE = IRational.make(0, 0);
    
    public static final IRational VIDEO_TIME_BASE = IRational.make(1, 1000000);
    
    public static IMetaData getVideoOptions(){return IMetaData.make();}
    
    public static IMetaData getVideoUnsetOptions(){return IMetaData.make();}
    
    public static String VIDEO_LOGO = "usac200.png";
    
    /***** Audio Attributes ******/
    
    public static final ICodec.ID AUDIO_CODEC = ICodec.ID.CODEC_ID_MP3;
    
    public static final int AUDIO_SAMPLE_RATE = 44100;
    
    public static final int AUDIO_CHANNEL_COUNT = 2;
    
    public static final int AUDIO_SAMPLE_SIZE_BITS = 16;
    
    public static final int AUDIO_GLOBAL_QUALITY = 0;
    
    public static final int AUDIO_BIT_RATE = 0;
    
    public static final int AUDIO_BIT_RATE_TOLERANCE = 0;
    
    public static final IRational AUDIO_TIME_BASE = IRational.make(1, 441000);
    
    public static IMetaData getAudioOptions(){return IMetaData.make();}
    
    public static IMetaData getAudioUnsetOptions(){return IMetaData.make();}
        
}
