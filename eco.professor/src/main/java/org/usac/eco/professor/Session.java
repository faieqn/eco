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

import com.zodiac.security.AbstractSession;
import org.usac.eco.libdto.DTOUser;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class Session {
    
    private static Session INSTANCE = null;
    
    private DTOUser user;

    public static Session getSession(){
        return getSession(null);
    }
    
    public static Session getSession(DTOUser user) {
        if(INSTANCE == null){
            synchronized(Session.class) {
                INSTANCE = new Session(user);
            }
        }
        return INSTANCE;
    }
    
    private Session(DTOUser user) {
        this.user = user;
    }

    public DTOUser getUser() {
        return user;
    }
        
}
