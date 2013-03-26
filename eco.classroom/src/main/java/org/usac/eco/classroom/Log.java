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
package org.usac.eco.classroom;

import java.net.URL;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class Log {
    
    private static Logger log = Logger.getLogger("Log");
    
    static {
        BasicConfigurator.configure();
        URL url = Loader.getResource("C:/eco/log4j.properties");
        PropertyConfigurator.configure(url);
    }
    
    public static void trace(Object message){
        log.trace(message);
    }
    
    public static void trace(Object message, Throwable t){
        log.trace(message, t);
    }
    
    public static void debug(Object message){
        log.debug(message);
    }
    
    public static void debug(Object message, Throwable t){
        log.debug(message, t);
    }
    
    public static void info(Object message){
        log.info(message);
    }
    
    public static void info(Object message, Throwable t){
        log.info(message, t);
    }
    
    public static void warn(Object message){
        log.warn(message);
    }
    
    public static void warn(Object message, Throwable t){
        log.warn(message, t);
    }
    
    public static void error(Object message){
        log.error(message);
    }
    
    public static void error(Object message, Throwable t){
        log.error(message, t);
    }
    
    public static void fatal(Object message){
        log.error(message);
    }
    
    public static void fatal(Object message, Throwable t){
        log.error(message, t);
    }
    
}
