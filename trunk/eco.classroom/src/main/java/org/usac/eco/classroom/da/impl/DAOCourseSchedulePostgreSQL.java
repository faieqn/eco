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
package org.usac.eco.classroom.da.impl;

import com.zodiac.db.AbstractDAO;
import com.zodiac.db.SingletonConnection;
import java.sql.SQLException;
import org.usac.eco.classroom.da.DAOCourseSchedule;
import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.libdto.DTOCourseSchedule;
import org.usac.eco.libdto.DTODay;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class DAOCourseSchedulePostgreSQL extends AbstractDAO<DTOCourseSchedule> implements DAOCourseSchedule {

    public DAOCourseSchedulePostgreSQL(){
        super(SingletonConnection.getInstance().getConnection());
    }
    
    @Override
    public DTOCourseSchedule getDTO() throws SQLException {
        return new DTOCourseSchedule(
                new DTODay(
                        getResultSet().getInt("day_id"),
                        getResultSet().getString("day_name")), 
                getResultSet().getTime("start_time"), 
                getResultSet().getTime("end_time"));
    }

    @Override
    public void getSchedule(DTOCourse dtoCourse) throws SQLException{
        String sql = "SELECT "
                + "  d.day_id, d.day_name, cos.start_time, cos.end_time "
                + "FROM "
                + "  course_open_schedule cos, \"day\" d "
                + "WHERE "
                + "  cos.day_id = d.day_id "
                + "  AND cos.course_id = ? "
                + "  AND cos.cycle_id = ? "
                + "  AND cos.section_id = ? ";
        setQuery();
        getQuery().setQueryString(sql);
        getQuery().addQueryParams(dtoCourse.getCourseId(),
                                  dtoCourse.getCycle().getCycleId(),
                                  dtoCourse.getSection().getSectionId());
        getQuery().execute();
        setResultSet(getQuery().getResultSet());
    }
    
}
