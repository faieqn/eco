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
    
    private String username;
    
    private String password;
    
    private String salt;
    
    private Profile profile;
    
    private String email;

    public DTOUser() {
    }
    
    public DTOUser(int userId, String name, String username, String password, String salt, Profile profile, String email) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.profile = profile;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }
    
    public Profile getProfile() {
        return profile;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
    
    public enum Profile {
        ADMIN (1, "Admin"),
        PROFESSOR (2, "Professor"),
        STUDENT (3, "Student");
        
        private int profile_id;
        
        private String profile_name;

        private Profile(int profile_id, String profile_name) {
            this.profile_id = profile_id;
            this.profile_name  = profile_name;
        }
        
    }
    
}
