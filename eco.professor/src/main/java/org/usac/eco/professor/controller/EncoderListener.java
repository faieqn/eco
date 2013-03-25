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

import java.awt.image.BufferedImage;

/**
 *
 * @author brian
 */
public interface EncoderListener {
    
    public void onStart(EncoderEvent ee);
    
    public void onError(EncoderEvent ee, EncoderMessage em);
    
    public void onTerminated(EncoderEvent ee);
    
    public void onSizeChange(EncoderEvent ee, int size);
    
    public void onImageChange(EncoderEvent ee, BufferedImage image);
    
}
