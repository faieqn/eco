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

import com.zodiac.security.Session;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.usac.eco.classroom.bl.Course;
import org.usac.eco.classroom.bl.CourseOpen;
import org.usac.eco.classroom.bl.CourseSection;
import org.usac.eco.classroom.bl.Cycle;
import org.usac.eco.classroom.bl.Period;
import org.usac.eco.classroom.bl.User;
import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.libdto.DTOCourseStatus;
import org.usac.eco.libdto.DTOCycle;
import org.usac.eco.libdto.DTOPeriod;
import org.usac.eco.libdto.DTOSection;
import org.usac.eco.libdto.DTOUser;
import org.usac.eco.libdto.DTOUserProfile;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class ClassroomFunction {
    
    public static JSONObject login(String username, String password, HttpSession session) 
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        DTOUser dtoUser = new DTOUser(
                0, 
                null, 
                username, 
                password, 
                null, 
                DTOUserProfile.ADMIN, 
                null);
        org.usac.eco.classroom.bl.Session newSession = 
                new org.usac.eco.classroom.bl.Session(session);
        DTOUser loggedUser = newSession.createSession(dtoUser);
        
        JSONObject jsonObject = new JSONObject();
        if(loggedUser != null){
            jsonObject.put("logged", true);
        } else {
            jsonObject.put("logged", false);
        }
        
        return jsonObject;
    }
    
    public static JSONObject list_all_courses_open(HttpSession session) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        CourseOpen course = new CourseOpen((Session)session.getAttribute("session"));
        List<DTOCourse> listCourses = course.getAllCoursesOpen();
        
        JSONArray jsonCourses = new JSONArray();
        
        Iterator<DTOCourse> iterator = listCourses.iterator();
        while(iterator.hasNext()){
            DTOCourse dtoCourse = iterator.next();
            JSONObject jsonCourse = new JSONObject();
            jsonCourse.put("courseId", dtoCourse.getCourseId());
            jsonCourse.put("courseName", dtoCourse.getCourseName());
            jsonCourse.put("sectionName", dtoCourse.getSection().getSectionName());
            jsonCourse.put("professorName", dtoCourse.getProfessor().getName());
            jsonCourse.put("courseStatus", dtoCourse.getStatus().getStatusName());
            jsonCourse.put("subscribers", dtoCourse.getSubscribers());
            jsonCourse.put("connected", dtoCourse.getConnected());
            jsonCourse.put("uri", dtoCourse.getURI());
            jsonCourses.add(jsonCourse);
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("courses", jsonCourses);
        
        return jsonObject;
    }
    
    public static JSONObject list_all_users(HttpSession session) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        User user = new User((Session)session.getAttribute("session"));
        List<DTOUser> listUsers = user.getAllUsers();
        
        JSONArray jsonUsers = new JSONArray();
        Iterator<DTOUser> iterator = listUsers.iterator();
        while(iterator.hasNext()){
            DTOUser dtoUser = iterator.next();
            JSONObject jsonUser = new JSONObject();
            jsonUser.put("userId", dtoUser.getUserId());
            jsonUser.put("userName", dtoUser.getName());
            jsonUser.put("user", dtoUser.getUsername());
            jsonUser.put("userProfileName", dtoUser.getProfile().getProfileName());
            jsonUsers.add(jsonUser);
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("users", jsonUsers);
        
        return jsonObject;
    }
    
    public static JSONObject disable_course_open(int course_id, HttpSession session) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        DTOCourse dtoCourse = new DTOCourse(
                course_id, 
                null, 
                null, 
                null, 
                0, 
                0, 
                null,  
                null, 
                DTOCourseStatus.DISABLE, 
                null);
        CourseOpen course = new CourseOpen((Session)session.getAttribute("session"));
        course.disable(dtoCourse);
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("disabled", true);
        
        return jsonObject;
    }
    
    public static JSONObject list_all_courses(HttpSession session) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        Course course = new Course((Session)session.getAttribute("session"));
        List<DTOCourse> listCourses = course.getAllCourses();
        JSONArray jsonCourses = new JSONArray();
        Iterator<DTOCourse> iterator = listCourses.iterator();
        while(iterator.hasNext()){
            DTOCourse dtoCourse = iterator.next();
            JSONObject jsonUser = new JSONObject();
            jsonUser.put("courseId", dtoCourse.getCourseId());
            jsonUser.put("courseName", dtoCourse.getCourseName());
            jsonCourses.add(jsonUser);
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("courses", jsonCourses);
        
        return jsonObject;
    }
    
    public static JSONObject list_all_cycles(HttpSession session) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        Cycle cycle = new Cycle((Session)session.getAttribute("session"));
        List<DTOCycle> listCycles = cycle.getAllCycles();
        JSONArray jsonCycles = new JSONArray();
        Iterator<DTOCycle> iterator = listCycles.iterator();
        while(iterator.hasNext()){
            DTOCycle dtoCycle = iterator.next();
            JSONObject jsonCycle = new JSONObject();
            jsonCycle.put("cycleId", dtoCycle.getCycleId());
            jsonCycle.put("cycleName", dtoCycle.getCycleName());
            jsonCycles.add(jsonCycle);
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cycles", jsonCycles);
        
        return jsonObject;
    }
    
    public static JSONObject list_all_courses_sections(HttpSession session) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        CourseSection courseSection = new CourseSection((Session)session.getAttribute("session"));
        List<DTOSection> listSections = courseSection.getAllSections();
        JSONArray jsonSections = new JSONArray();
        Iterator<DTOSection> iterator = listSections.iterator();
        while(iterator.hasNext()){
            DTOSection dtoSection = iterator.next();
            JSONObject jsonSection = new JSONObject();
            jsonSection.put("sectionId", dtoSection.getSectionId());
            jsonSection.put("sectionName", dtoSection.getSectionName());
            jsonSections.add(jsonSection);
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sections", jsonSections);
        
        return jsonObject;
    }
    
    public static JSONObject list_all_periods(HttpSession session) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        Period period = new Period((Session)session.getAttribute("session"));
        List<DTOPeriod> listPeriods = period.getAllPeriods();
        JSONArray jsonPeriods = new JSONArray();
        Iterator<DTOPeriod> iterator = listPeriods.iterator();
        while(iterator.hasNext()){
            DTOPeriod dtoPeriod = iterator.next();
            JSONObject jsonPeriod = new JSONObject();
            jsonPeriod.put("periodId", dtoPeriod.getPeriodId());
            jsonPeriod.put("periodName", dtoPeriod.getPeriodName());
            jsonPeriods.add(jsonPeriod);
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("periods", jsonPeriods);
        
        return jsonObject;
    }
    
}
