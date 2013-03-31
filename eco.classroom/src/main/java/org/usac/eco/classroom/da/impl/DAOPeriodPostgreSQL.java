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
import org.usac.eco.classroom.da.DAOPeriod;
import org.usac.eco.libdto.DTOPeriod;

/**
 *
 * @author Brian Estrada
 */
public class DAOPeriodPostgreSQL extends AbstractDAO<DTOPeriod> implements DAOPeriod {

    public DAOPeriodPostgreSQL() {
        super(SingletonConnection.getInstance().getConnection());
    }

    @Override
    public DTOPeriod getDTO() throws SQLException {
        return new DTOPeriod(
                getResultSet().getInt("period_id"), 
                getResultSet().getString("period_name"), 
                getResultSet().getDate("start_date"), 
                getResultSet().getDate("end_date"));
    }

    @Override
    public void getAllPeriods() throws SQLException {
        String sql = "SELECT "
                + "  period_id, period_name, start_date, end_date "
                + "FROM "
                + "  period ";
        setQuery();
        getQuery().setQueryString(sql);
        getQuery().execute();
        setResultSet(getQuery().getResultSet());
    }
    
    
}
