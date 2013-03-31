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
package org.usac.eco.professor.controller;

import com.zodiac.soa.Request;
import com.zodiac.soa.client.DynamicServiceHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.professor.Configure;
import org.usac.eco.professor.Session;
import org.usac.eco.professor.model.VideoFrame;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class ProfessorController {
    
    private List<IProfessorController> listener;

    public ProfessorController(IProfessorController ipc) throws Exception {
        listener = new ArrayList<IProfessorController>();
        addProfessorControllerListener(ipc);
        
        String clazz = "org.usac.eco.classroom.bl.CourseOpen";
        Class paramsConstructor[] = null;
        Object argsConstructor[] = null;
        String method = "getCoursesOpen";
        Class paramsMethod[] = {DTOCourse.class};
        Object argsMethod[] = {Session.getSession().getUser()};
        Request request = new Request(clazz, paramsConstructor, argsConstructor, 
                method, paramsMethod, argsMethod);
        DynamicServiceHandler dsh = new DynamicServiceHandler(Configure.CLASSROOM);
        List<DTOCourse> courses = (List<DTOCourse>)dsh.run(request);
        fireListCourses(courses);
    }
    
    public void openVideoFrame(DTOCourse course){
        new VideoFrame(course);
    }
    
    private void fireListCourses(List<DTOCourse> c){
        Iterator<IProfessorController> iterator = listener.iterator();
        while(iterator.hasNext()){
            iterator.next().listCourses(c);
        }
    }
    
    public final boolean addProfessorControllerListener(IProfessorController ipc){
        return listener.add(ipc);
    }
    
    public final boolean removeProfessorControllerListener(IProfessorController ipc){
        return listener.remove(ipc);
    }
    
}
