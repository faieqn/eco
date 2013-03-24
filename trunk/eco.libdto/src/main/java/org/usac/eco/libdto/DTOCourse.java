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
package org.usac.eco.libdto;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class DTOCourse {
    
    private int courseId;
    
    private String courseName;
    
    private int subscribers;
    
    private int connected;
    
    private int URI;
    
    private DTOUser professor;

    public DTOCourse(int courseId, String courseName, int subscribers, int connected, int URI, DTOUser professor) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.subscribers = subscribers;
        this.connected = connected;
        this.URI = URI;
        this.professor = professor;
    }

    public int getConnected() {
        return connected;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public DTOUser getProfessor() {
        return professor;
    }

    public int getSubscribers() {
        return subscribers;
    }

    public int getURI() {
        return URI;
    }
    
    
    
}
