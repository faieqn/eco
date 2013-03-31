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


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class Configure {
    
    private final static String FILENAME = "C:/eco/eco.classroom.properties";
    
    public static String DB_HOST;
    
    public static String DB_PORT;
    
    public static String DB_USERNAME;
    
    public static String DB_PASSWORD;
    
    public static String DB_DATABASE;
    
    public static String DB_DRIVER;
    
    public static String DB_DBMS;
    
    public static String PAGE_RECOVERY_PASSWORD;
        
    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(FILENAME));
            
            DB_HOST = properties.getProperty("classroom.db.host");
            DB_PORT = properties.getProperty("classroom.db.port");
            DB_USERNAME = properties.getProperty("classroom.db.username");
            DB_PASSWORD = properties.getProperty("classroom.db.password");
            DB_DATABASE = properties.getProperty("classroom.db.database");
            DB_DRIVER = properties.getProperty("classroom.db.driver");
            DB_DBMS = properties.getProperty("classroom.db.dbms");
            
            PAGE_RECOVERY_PASSWORD = "http://localhost:8080/eco.classroom/DynamicService";
        } catch (IOException ex) {
            Log.fatal("Configurator not found: " + FILENAME, ex);
        }
    }
    
}
