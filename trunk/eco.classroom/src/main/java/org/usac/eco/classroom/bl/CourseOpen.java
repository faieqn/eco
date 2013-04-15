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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.usac.eco.classroom.da.DAOCourseOpen;
import org.usac.eco.classroom.da.DAOCourseSchedule;
import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.libdto.DTOCourseSchedule;
import org.usac.eco.libdto.DTOCourseStatus;
import org.usac.eco.libdto.DTOCycle;
import org.usac.eco.libdto.DTOUser;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class CourseOpen extends PrivateBussinessLogic {

    public CourseOpen() {
    }
    
    public List<DTOCourse> getAllCoursesOpen(DTOCycle dtoCycle) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        DAOCourseOpen daoCourseOpen = getDAOCourse();
        daoCourseOpen.getAllCoursesOpen(dtoCycle);
        
        DAOCourseSchedule daoCourseSchedule = getDAOCourseSchedule();
        
        List<DTOCourse> listCourses = new ArrayList<DTOCourse>();
        Iterator<DTOCourse> iterator = daoCourseOpen.getListDTO().iterator();
        while(iterator.hasNext()){
            DTOCourse dtoCourse = iterator.next();
            daoCourseSchedule.getSchedule(dtoCourse);
            List<DTOCourseSchedule> listDTOCourseSchedule = daoCourseSchedule.getListDTO();
            DTOCourseSchedule[] dtoCourseSchedules = new DTOCourseSchedule[listDTOCourseSchedule.size()];
            listDTOCourseSchedule.toArray(dtoCourseSchedules);
            listCourses.add(new DTOCourse(
                        dtoCourse.getCourseId(), 
                        dtoCourse.getCycle(), 
                        dtoCourse.getSection(), 
                        dtoCourse.getCourseName(), 
                        dtoCourse.getSubscribers(), 
                        dtoCourse.getConnected(), 
                        dtoCourse.getURI(), 
                        dtoCourse.getProfessor(), 
                        dtoCourse.getStatus(), 
                        dtoCourseSchedules));
        }
        
        return listCourses;
    }
    
    public List<DTOCourse> getCoursesOpen(DTOUser dtoUser)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        DAOCourseOpen daoCourse = getDAOCourse();
        daoCourse.getCoursesOpen(dtoUser);
        return daoCourse.getListDTO();
    }
    
    public List<DTOCourse> searchCourseOpen(DTOCourse dtoCourse)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        DAOCourseOpen daoCourseOpen = getDAOCourse();
        daoCourseOpen.searchCourseOpen(dtoCourse);
        
        DAOCourseSchedule daoCourseSchedule = getDAOCourseSchedule();
        
        List<DTOCourse> listCourses = new ArrayList<DTOCourse>();
        Iterator<DTOCourse> iterator = daoCourseOpen.getListDTO().iterator();
        while(iterator.hasNext()){
            DTOCourse newDTOCourse = iterator.next();
            daoCourseSchedule.getSchedule(newDTOCourse);
            List<DTOCourseSchedule> listDTOCourseSchedule = daoCourseSchedule.getListDTO();
            DTOCourseSchedule[] dtoCourseSchedules = new DTOCourseSchedule[listDTOCourseSchedule.size()];
            listDTOCourseSchedule.toArray(dtoCourseSchedules);
            listCourses.add(new DTOCourse(
                        newDTOCourse.getCourseId(), 
                        newDTOCourse.getCycle(), 
                        newDTOCourse.getSection(), 
                        newDTOCourse.getCourseName(), 
                        newDTOCourse.getSubscribers(), 
                        newDTOCourse.getConnected(), 
                        newDTOCourse.getURI(), 
                        newDTOCourse.getProfessor(), 
                        newDTOCourse.getStatus(), 
                        dtoCourseSchedules));
        }
        
        
        return listCourses;
    }
    
    public boolean subscribe(DTOUser dtoUser, DTOCourse dtoCourse) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        DAOCourseOpen daoCourse = getDAOCourse();
        daoCourse.subscribe(dtoUser, dtoCourse);
        return true;
    }
    
    public boolean publish(DTOCourse dtoCourse) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        DAOCourseOpen daoCourse = getDAOCourse();
        daoCourse.publish(dtoCourse);
        return true;
    }
    
    public boolean unpublish(DTOCourse dtoCourse) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        DAOCourseOpen daoCourse = getDAOCourse();
        daoCourse.unpublish(dtoCourse);
        return true;
    }
    
    public boolean connect(DTOCourse dtoCourse) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        DAOCourseOpen daoCourse = getDAOCourse();
        daoCourse.connectCourseOpen(dtoCourse);
        return checkStatusConnected(dtoCourse);
    }
    
    public boolean checkStatusConnected(DTOCourse dTOCourse)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        DAOCourseOpen daoCourse = getDAOCourse();
        daoCourse.getCourseOpen(dTOCourse);
        daoCourse.next();
        DTOCourse dtoCourse = daoCourse.getDTO();
        if(dtoCourse.getStatus().getStatusId() == DTOCourseStatus.CONNECTED.getStatusId()){
            return true;
        } else {
            return false;
        }
    }
    
    public int getCourseConnected(DTOCourse dtoCourse) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        DAOCourseOpen daoCourseOpen = getDAOCourse();
        daoCourseOpen.getCourseOpen(dtoCourse);
        daoCourseOpen.next();
        dtoCourse = daoCourseOpen.getDTO();
        return dtoCourse.getConnected();
    }
    
    public boolean disable(DTOCourse dtoCourse) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        DAOCourseOpen daoCourse = getDAOCourse();
        daoCourse.changeState(dtoCourse);
        return true;
    }
    
    public List<DTOCourse> getCoursesOpenSubscribed(DTOUser dtoUser) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        DAOCourseOpen daoCourseOpen = getDAOCourse();
        daoCourseOpen.getCoursesOpenSubscribed(dtoUser);
        
        DAOCourseSchedule daoCourseSchedule = getDAOCourseSchedule();
        
        List<DTOCourse> listCourses = new ArrayList<DTOCourse>();
        Iterator<DTOCourse> iterator = daoCourseOpen.getListDTO().iterator();
        while(iterator.hasNext()){
            DTOCourse dtoCourse = iterator.next();
            daoCourseSchedule.getSchedule(dtoCourse);
            List<DTOCourseSchedule> listDTOCourseSchedule = daoCourseSchedule.getListDTO();
            DTOCourseSchedule[] dtoCourseSchedules = new DTOCourseSchedule[listDTOCourseSchedule.size()];
            listDTOCourseSchedule.toArray(dtoCourseSchedules);
            listCourses.add(new DTOCourse(
                        dtoCourse.getCourseId(), 
                        dtoCourse.getCycle(), 
                        dtoCourse.getSection(), 
                        dtoCourse.getCourseName(), 
                        dtoCourse.getSubscribers(), 
                        dtoCourse.getConnected(), 
                        dtoCourse.getURI(), 
                        dtoCourse.getProfessor(), 
                        dtoCourse.getStatus(), 
                        dtoCourseSchedules));
        }
        
        return listCourses;
    }
    
    private DAOCourseOpen getDAOCourse() 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException{
        return (DAOCourseOpen)DAODriver.getDAODriver("DAOCourseOpen");
    }
    
    private DAOCourseSchedule getDAOCourseSchedule() 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException{
        return (DAOCourseSchedule)DAODriver.getDAODriver("DAOCourseSchedule");
    }
    
}
