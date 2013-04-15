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
import com.zodiac.soa.server.BussinessLogic;
import com.zodiac.soa.server.MessageContext;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.usac.eco.classroom.bl.Course;
import org.usac.eco.classroom.bl.CourseOpen;
import org.usac.eco.classroom.bl.CourseSection;
import org.usac.eco.classroom.bl.Cycle;
import org.usac.eco.classroom.bl.User;
import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.libdto.DTOCourseSchedule;
import org.usac.eco.libdto.DTOCourseStatus;
import org.usac.eco.libdto.DTOCycle;
import org.usac.eco.libdto.DTOSection;
import org.usac.eco.libdto.DTOUser;
import org.usac.eco.libdto.DTOUserProfile;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class ClassroomFunction {
    
    public static void registerMessageContext(MessageContext messageContext){
        try {
            BussinessLogic.setMessageContext(messageContext);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(ClassroomFunction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ClassroomFunction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ClassroomFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static JSONObject login(String username, String password) 
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        DTOUser dtoUser = new DTOUser(
                0, 
                null, 
                username, 
                password, 
                null, 
                DTOUserProfile.ADMIN, 
                null);
        org.usac.eco.classroom.bl.Session session = 
                new org.usac.eco.classroom.bl.Session();
        
        DTOUser loggedUser = session.createSession(dtoUser);
        
        JSONObject jsonObject = new JSONObject();
        if(loggedUser != null){
            jsonObject.put("logged", true);
        } else {
            jsonObject.put("logged", false);
        }
        
        return jsonObject;
    }
    
    public static JSONObject list_all_courses_open(DTOCycle dtoCycle) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        CourseOpen course = new CourseOpen();
        List<DTOCourse> listCourses = course.getAllCoursesOpen(dtoCycle);
        
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
            
            JSONArray jsonSchedules = new JSONArray();
            DTOCourseSchedule[] schedules = dtoCourse.getCourseSchedule();
            for(int i = 0; i < schedules.length; i++){
                DTOCourseSchedule dtoSchedule = schedules[i];
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                
                JSONObject jsonSchedule = new JSONObject();
                jsonSchedule.put("dayId", dtoSchedule.getDay().getDayId());
                jsonSchedule.put("dayName", dtoSchedule.getDay().getDayName());
                jsonSchedule.put("startTime", format.format(dtoSchedule.getStartTime()));
                jsonSchedule.put("endTime", format.format(dtoSchedule.getEndTime()));
                jsonSchedules.add(jsonSchedule);
            }
            jsonCourse.put("schedules", jsonSchedules);
            jsonCourses.add(jsonCourse);
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("courses", jsonCourses);
        
        return jsonObject;
    }
    
    public static JSONObject list_all_users() 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        User user = new User();
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
    
    public static JSONObject disable_course_open(int course_id) 
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
        CourseOpen course = new CourseOpen();
        course.disable(dtoCourse);
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("disabled", true);
        
        return jsonObject;
    }
    
    public static JSONObject list_all_courses() 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        Course course = new Course();
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
    
    public static JSONObject list_all_cycles() 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        Cycle cycle = new Cycle();
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
    
    public static JSONObject list_all_courses_sections() 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        CourseSection courseSection = new CourseSection();
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
    
    public static DTOCycle getCurrentCycle() 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Cycle cycle = new Cycle();
        return cycle.getCurrentCycle();
    }
    
    private static Session getSession(HttpSession session){
        return (Session)session.getAttribute("session");
    }
    
}
