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
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import org.usac.eco.classroom.da.DAOCourse;
import org.usac.eco.libdto.DTOCourse;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class Course extends PrivateBussinessLogic {

    public Course() {
    }
    
    public List<DTOCourse> getAllCourses() 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, 
            SQLException {
        DAOCourse daoCourse = getDAOCourse();
        daoCourse.getAllCourses();
        return daoCourse.getListDTO();
    }
    
    private DAOCourse getDAOCourse() 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (DAOCourse)DAODriver.getDAODriver("DAOCourse");
    }
    
}
