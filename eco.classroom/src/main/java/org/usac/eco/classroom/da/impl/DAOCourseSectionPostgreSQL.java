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
import org.usac.eco.classroom.da.DAOCourseSection;
import org.usac.eco.libdto.DTOSection;

/**
 *
 * @author Brian Estrada
 */
public class DAOCourseSectionPostgreSQL extends AbstractDAO<DTOSection> implements DAOCourseSection{

    public DAOCourseSectionPostgreSQL() {
        super(SingletonConnection.getInstance().getConnection());
    }
    
    @Override
    public DTOSection getDTO() throws SQLException {
        return new DTOSection(
                getResultSet().getInt("section_id"), 
                getResultSet().getString("section_name"));
    }

    @Override
    public void getAllSections() throws SQLException {
        String sql = "SELECT "
                + "  section_id, section_name "
                + "FROM "
                + "  course_section ";
        setQuery();
        getQuery().setQueryString(sql);
        getQuery().execute();
        setResultSet(getQuery().getResultSet());
    }
    
}
