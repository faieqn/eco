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

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.usac.eco.professor.Log;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class RobotWideScreenDevice extends AbstractVideoDevice {

    public RobotWideScreenDevice() {
        fireOnOpen(new VideoDeviceEvent(this));
    }
    
    public BufferedImage getImage() {
        try {
            BufferedImage desktopImage = new Robot().createScreenCapture(
                    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            BufferedImage image = new BufferedImage(EncoderConstants.VIDEO_DIMENSION.width, 
                                                    EncoderConstants.VIDEO_DIMENSION.height, 
                                                    desktopImage.getType());
            Graphics2D g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(desktopImage, 0, 0, 
                    EncoderConstants.VIDEO_DIMENSION.width, 
                    EncoderConstants.VIDEO_DIMENSION.height, 
                    0, 0, desktopImage.getWidth(), desktopImage.getHeight(), null);
            g2d.dispose();
            
            return image;
        } catch (AWTException ex) {
            Log.error("Error unknown", ex);
            super.fireOnError(new VideoDeviceEvent(this), VideoDeviceMessage.ERROR_UNKNOWN);
            return null;
        }
    }

    public String getName() {
        return "Widescreen";
    }

    @Override
    public String toString() {
        return "Desktop - " + super.toString();
    }
            
}
