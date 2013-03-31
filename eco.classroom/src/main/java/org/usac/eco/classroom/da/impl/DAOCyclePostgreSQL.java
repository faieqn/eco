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
import org.usac.eco.classroom.da.DAOCycle;
import org.usac.eco.libdto.DTOCycle;
import org.usac.eco.libdto.DTOPeriod;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class DAOCyclePostgreSQL extends AbstractDAO<DTOCycle> implements DAOCycle {

    public DAOCyclePostgreSQL() {
        super(SingletonConnection.getInstance().getConnection());
    }
    
    @Override
    public DTOCycle getDTO() throws SQLException {
        return new DTOCycle(
                getResultSet().getInt("cycle_id"), 
                getResultSet().getString("cycle_name"), 
                new DTOPeriod(
                    getResultSet().getInt("period_id"), 
                    getResultSet().getString("period_name"), 
                    getResultSet().getDate("start_date"), 
                    getResultSet().getDate("end_date")), 
                getResultSet().getInt("year"));
    }

    @Override
    public void getAllCycles() throws SQLException {
        String sql = "SELECT "
                + "  c.cycle_id, c.cycle_name, c.year, "
                + "  p.period_id, p.period_name, p.start_date, p.end_date "
                + "FROM "
                + "  cycle c, period p "
                + "WHERE "
                + "  c.period_id = p.period_id ";
        setQuery();
        getQuery().setQueryString(sql);
        getQuery().execute();
        setResultSet(getQuery().getResultSet());
    }
    
}
