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
 * @author Brian Estrada <briang014@gmail.com>
 */
public class DTOPeriod extends AbstractDTO {
    
    private int periodId;
    
    private String periodName;
    
    private Date startDate;
    
    private Date endDate;

    public DTOPeriod() {
    }

    
    
    public DTOPeriod(int periodId, String periodName, Date startDate, Date endDate) {
        this.periodId = periodId;
        this.periodName = periodName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getPeriodId() {
        return periodId;
    }

    public String getPeriodName() {
        return periodName;
    }

    public Date getStartDate() {
        return startDate;
    }
    
    
    
}
