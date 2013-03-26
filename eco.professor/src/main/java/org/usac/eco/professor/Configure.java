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
package org.usac.eco.professor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class Configure {
    
    private final static String FILENAME = "conf/eco.professor.properties";
    
    public static URL CLASSROOM = null;
    
    public static File VIDEO_ECO_PIC = null;
    
    public static File ABOUT_PIC = null;
    
    public static String APP_TITLE = null;
    
    public static String APP_WEBSITE = null;
    
    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(FILENAME));
            
            CLASSROOM = new URL(properties.getProperty("classroom"));            
        } catch (IOException ex) {
            Log.fatal("Configurator not found: " + FILENAME, ex);
        }
        VIDEO_ECO_PIC = new File("ecoimg.jpg");
        
        ABOUT_PIC = new File("about.jpg");
        
        APP_TITLE = "ECO Professor 1.0";
        APP_WEBSITE = "http://eco.googlecode.com";
    }
    
}
