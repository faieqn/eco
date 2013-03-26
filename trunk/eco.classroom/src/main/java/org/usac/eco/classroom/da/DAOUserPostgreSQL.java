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
package org.usac.eco.classroom.da;

import com.zodiac.db.AbstractDAO;
import com.zodiac.db.SingletonConnection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.usac.eco.classroom.Log;
import org.usac.eco.libdto.DTOUser;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class DAOUserPostgreSQL extends AbstractDAO<DTOUser> implements DAOUser {

    public DAOUserPostgreSQL() {
        super(SingletonConnection.getInstance().getConnection());
    }
    
    @Override
    public DTOUser getDTO() throws SQLException {
            return new DTOUser(
                getResultSet().getInt("user_id"),
                getResultSet().getString("user_name"),
                getResultSet().getString("username"),
                getResultSet().getString("password"),
                getResultSet().getString("salt"),
                DTOUser.Profile.valueOf(getResultSet().getString("profile_name")),
                getResultSet().getString("email"));
    }

    @Override
    public void getUser(DTOUser dtoUser) throws SQLException{
        String sql = "SELECT "
                + "  user_id, user_name, username, password, salt, profile_name, email "
                + "FROM "
                + "  \"user\" u, user_profile up "
                + "WHERE "
                + "  u.profile_id = up.profile_id "
                + "  AND u.username = ? ";
        setQuery();
        getQuery().setQueryString(sql);
        getQuery().addQueryParams(dtoUser.getUsername());
        getQuery().executeSelect();
        setResultSet(getQuery().getResultSet());
    }

    @Override
    public void allUsers() throws SQLException {
        String sql = "SELECT "
                + "  user_id, user_name, username, profile_name, email "
                + "FROM "
                + "  \"user\" u, user_profile up "
                + "WHERE "
                + "   u.profile_id = up.profile_id ";
        
        setQuery();
        getQuery().setQueryString(sql);
        getQuery().executeSelect();
        setResultSet(getQuery().getResultSet());
    }

    
    
}
