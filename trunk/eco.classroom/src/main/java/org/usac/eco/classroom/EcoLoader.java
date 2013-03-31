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

import com.zodiac.db.ConnectionFactory;
import com.zodiac.db.ConnectionParameter;
import com.zodiac.db.ConnectionString;
import com.zodiac.db.ConnectionStringJDBC;
import com.zodiac.db.DAODriver;
import com.zodiac.db.SingletonConnection;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class EcoLoader {
   
    public static void load(){
        ConnectionParameter cp = new ConnectionParameter(
                        Configure.DB_HOST, 
                        Configure.DB_PORT, 
                        Configure.DB_DATABASE, 
                        Configure.DB_USERNAME, 
                        Configure.DB_PASSWORD, 
                        Configure.DB_DRIVER, 
                        Configure.DB_DBMS);
            ConnectionString cs = new ConnectionStringJDBC(cp);
            DAODriver.setDAOPackage("org.usac.eco.classroom.da.impl");
            DAODriver.setDBMS(Configure.DB_DBMS);
        try {
            Connection c = ConnectionFactory.getConnection(cp, cs);
            SingletonConnection.getInstance().setConnection(c);
        } catch (ClassNotFoundException ex) {
            Log.fatal("Database driver not found.", ex);
        } catch (SQLException ex) {
            Log.fatal("Cannot connecto to database " + cs.getConnectionString() + ".", ex);
        }
    }
    
}
