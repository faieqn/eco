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
    
    private DTOCycle cycle;
    
    private DTOSection section;
    
    private String courseName;
    
    private int subscribers;
    
    private int connected;
    
    private String URI;
    
    private DTOUser professor;
    
    private Status status;
    
    private DTOCourseSchedule courseSchedule;

    public DTOCourse() {}

    public DTOCourse(int courseId, DTOCycle cycle, DTOSection section, String courseName, int subscribers, int connected, String URI, DTOUser professor, Status status, DTOCourseSchedule courseSchedule) {
        this.courseId = courseId;
        this.cycle = cycle;
        this.section = section;
        this.courseName = courseName;
        this.subscribers = subscribers;
        this.connected = connected;
        this.URI = URI;
        this.professor = professor;
        this.status = status;
        this.courseSchedule = courseSchedule;
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

    public DTOCourseSchedule getCourseSchedule() {
        return courseSchedule;
    }

    public DTOCycle getCycle() {
        return cycle;
    }

    public DTOUser getProfessor() {
        return professor;
    }

    public DTOSection getSection() {
        return section;
    }

    public Status getStatus() {
        return status;
    }

    public int getSubscribers() {
        return subscribers;
    }

    public String getURI() {
        return URI;
    }

    
    
    public enum Status {
        DISCONNECTED(1,  "Disconnected"),
        CONNECTED(2, "Connected"),
        DISABLED(3, "Disabled");
        
        private int status_id;
        
        private String status_name;

        private Status(int status_id, String status_name) {
            this.status_id = status_id;
            this.status_name = status_name;
        }

        public int getStatusId() {
            return status_id;
        }

        public String getStatusName() {
            return status_name;
        }
        
        
    }
    
    
}
