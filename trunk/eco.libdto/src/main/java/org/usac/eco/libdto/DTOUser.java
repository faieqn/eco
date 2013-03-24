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
public class DTOUser extends AbstractDTO {
    
    private int userId;
    
    private String name;
    
    private String password;
    
    private Profile profile;

    public DTOUser(int userId, String name, String password, Profile profile) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Profile getProfile() {
        return profile;
    }

    public int getUserId() {
        return userId;
    }
    
    public enum Profile {
        ADMIN,
        PROFESSOR,
        STUDENT
    }
    
}
