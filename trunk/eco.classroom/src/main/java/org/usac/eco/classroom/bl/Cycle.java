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
import com.zodiac.security.Session;
import com.zodiac.soa.server.PrivateBussinessLogic;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import org.usac.eco.classroom.da.DAOCycle;
import org.usac.eco.libdto.DTOCycle;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class Cycle extends PrivateBussinessLogic {

    public Cycle() {
    }

    
    
    public List<DTOCycle> getAllCycles() 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        DAOCycle daoCycle = getDAOCycle();
        daoCycle.getAllCycles();
        return daoCycle.getListDTO();
    }
    
    public int[] getSampleCycleYears(){
        int countYears = 4;
        int[] years = new int[countYears];
        
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        for(int i = 0; i < countYears; i++){
            years[i] = currentYear - 2 + i;
        }
        return years;
    }
    
    public DTOCycle getCurrentCycle() 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        DAOCycle daoCycle = getDAOCycle();
        daoCycle.getCurrentCycle();
        daoCycle.next();
        return daoCycle.getDTO();
    }
    
    private DAOCycle getDAOCycle() 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException{
        return (DAOCycle)DAODriver.getDAODriver("DAOCycle");
    }
    
}
