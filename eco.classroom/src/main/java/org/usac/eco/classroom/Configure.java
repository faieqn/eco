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
    
    public static String db_host;
    
    public static String db_port;
    
    public static String db_username;
    
    public static String db_password;
    
    public static String db_database;
    
    public static String db_driver;
    
    public static String db_dbms;
    
    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(FILENAME));
            
            db_host = properties.getProperty("classroom.db.host");
            db_port = properties.getProperty("classroom.db.port");
            db_username = properties.getProperty("classroom.db.username");
            db_password = properties.getProperty("classroom.db.password");
            db_database = properties.getProperty("classroom.db.database");
            db_driver = properties.getProperty("classroom.db.driver");
            db_dbms = properties.getProperty("classroom.db.dbms");
            
        } catch (IOException ex) {
            Log.fatal("Configurator not found: " + FILENAME, ex);
        }
    }
    
}
