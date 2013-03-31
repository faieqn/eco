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
public class DTOCourseStatus extends AbstractDTO {
    
    private int statusId;
    
    private String statusName;

    public DTOCourseStatus() {
    }
    
    public DTOCourseStatus(int statusId, String statusName) {
        this.statusId = statusId;
        this.statusName = statusName;
    }

    public int getStatusId() {
        return statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public static DTOCourseStatus DISCONNECTED = new DTOCourseStatus(1, "Desconectado");
    public static DTOCourseStatus CONNECTED = new DTOCourseStatus(2, "Conectado");
    public static DTOCourseStatus DISABLE = new DTOCourseStatus(3, "Deshabilitado");
    
}
