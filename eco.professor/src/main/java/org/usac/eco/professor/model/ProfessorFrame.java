/*
 * Copyright (C) 2013 ronyHeat3203
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
package org.usac.eco.professor.model;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.professor.controller.IProfessorController;
import org.usac.eco.professor.controller.ProfessorController;

/**
 *
 * @author ronyHeat3203
 */
public class ProfessorFrame extends ECOFrame implements IProfessorController {
    
    private ProfessorController professorController;
    
    public ProfessorFrame(){
        super();
        this.setVisible(true);
        try {
            professorController = new ProfessorController(this);
        } catch (Exception ex) {
            Logger.getLogger(ProfessorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void listCourses(List<DTOCourse> courses) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void recoveryPasswordLink(String link) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
