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
package org.usac.eco.classroom.da;

import com.zodiac.db.DAO;
import java.sql.SQLException;
import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.libdto.DTOCycle;
import org.usac.eco.libdto.DTOUser;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public interface DAOCourseOpen extends DAO<DTOCourse> {
    
    public void getAllCoursesOpen(DTOCycle dtoCycle) throws SQLException;
    
    public void getCourseOpen(DTOCourse dtoCourse) throws SQLException;
    
    public void getCoursesOpen(DTOUser dtoUser) throws SQLException;
    
    public void getCoursesOpenSubscribed(DTOUser dtoUser) throws SQLException;
    
    public void searchCourseOpen(DTOCourse dtoCourse) throws SQLException;
    
    public void subscribe(DTOUser dtoUser, DTOCourse dtoCourse) throws SQLException;
    
    public void changeState(DTOCourse dtoCourse) throws SQLException;
    
    public void connectCourseOpen(DTOCourse dtoCourse) throws SQLException;
    
    public void unpublish(DTOCourse dtoCourse) throws SQLException;
    
    public void publish(DTOCourse dtoCourse) throws SQLException;
            
}
