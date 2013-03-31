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
public class DTOUserProfile extends AbstractDTO {
    
    private int profileId;
    
    private String profileName;

    public DTOUserProfile() {
    }

    
    
    public DTOUserProfile(int profileId, String profileName) {
        this.profileId = profileId;
        this.profileName = profileName;
    }

    public int getProfileId() {
        return profileId;
    }

    public String getProfileName() {
        return profileName;
    }
    
    public static DTOUserProfile ADMIN = new DTOUserProfile(1, "ADMIN");
    public static DTOUserProfile PROFESSOR = new DTOUserProfile(2, "PROFESSOR");
    public static DTOUserProfile STUDENT = new DTOUserProfile(3, "STUDENT");
    
}
