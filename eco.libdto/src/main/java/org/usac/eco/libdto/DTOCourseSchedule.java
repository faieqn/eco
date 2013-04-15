/*
 * Copyright (C) 2013 Zodiac Innovation
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

import java.util.Date;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class DTOCourseSchedule extends AbstractDTO {
    
    private DTODay day;
    
    private Date startTime;
    
    private Date endTime;

    public DTOCourseSchedule() {
    }
    
    

    public DTOCourseSchedule(DTODay day, Date startTime, Date endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DTODay getDay() {
        return day;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getStartTime() {
        return startTime;
    }
    
    
    
}
