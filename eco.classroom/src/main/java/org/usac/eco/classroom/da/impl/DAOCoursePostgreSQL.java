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
package org.usac.eco.classroom.da.impl;

import com.zodiac.db.AbstractDAO;
import com.zodiac.db.SingletonConnection;
import java.sql.SQLException;
import org.usac.eco.classroom.da.DAOCourse;
import org.usac.eco.libdto.DTOCourse;

/**
 *
 * @author brian
 */
public class DAOCoursePostgreSQL extends AbstractDAO<DTOCourse> implements DAOCourse {

    public DAOCoursePostgreSQL() {
        super(SingletonConnection.getInstance().getConnection());
    }

    @Override
    public DTOCourse getDTO() throws SQLException {
        return new DTOCourse(
                getResultSet().getInt("course_id"), 
                null, 
                null, 
                getResultSet().getString("course_name"), 
                0, 
                0, 
                null, 
                null, 
                null, 
                null);
    }

    @Override
    public void getAllCourses() throws SQLException {
        String sql = "SELECT "
                + "  course_id, course_name "
                + "FROM "
                + "  course ";
        setQuery();
        getQuery().setQueryString(sql);
        getQuery().execute();
        setResultSet(getQuery().getResultSet());
    }
    
}
