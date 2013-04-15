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

import java.util.Date;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class DTOCycle extends AbstractDTO {
    
    private int cycleId;
    
    private String cycleName;
    
    private Date startDate;
    
    private Date endDate;

    public DTOCycle() {
    }

    public DTOCycle(int cycleId, String cycleName, Date startDate, Date endDate) {
        this.cycleId = cycleId;
        this.cycleName = cycleName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getCycleId() {
        return cycleId;
    }

    public String getCycleName() {
        return cycleName;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    
}
