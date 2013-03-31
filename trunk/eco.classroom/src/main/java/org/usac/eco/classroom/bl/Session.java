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
package org.usac.eco.classroom.bl;

import com.zodiac.db.DAODriver;
import com.zodiac.soa.server.SessionBussinessLogic;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import org.usac.eco.classroom.Configure;
import org.usac.eco.classroom.EcoSession;
import org.usac.eco.classroom.da.DAOUser;
import org.usac.eco.libdto.DTOUser;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class Session extends SessionBussinessLogic {
    
    public Session(HttpSession session){
        super(session);
    }

    public String geRecoveryPasswordLink() {
        return Configure.PAGE_RECOVERY_PASSWORD;
    }
    
    public DTOUser createSession(DTOUser dtoUser) 
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        DAOUser daoUser = (DAOUser)DAODriver.getDAODriver("DAOUser");
        daoUser.getUser(dtoUser);
        
        if(daoUser.next()){
            DTOUser loggedUser = daoUser.getDTO();
            set(new EcoSession(loggedUser));
            return loggedUser;
        } else {
            return null;
        }
    }
    
    public void destroySession() {
        super.destroy();
        
    }
    
}
