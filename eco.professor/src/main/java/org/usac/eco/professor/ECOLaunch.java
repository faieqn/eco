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
package org.usac.eco.professor;

import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.usac.eco.professor.controller.VideoController;
import org.usac.eco.professor.model.LoginFrame;
import org.usac.eco.professor.model.ProfessorFrame;
import org.usac.eco.professor.model.VideoFrame;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class ECOLaunch {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ProfessorFrame login = new ProfessorFrame();
        login.setVisible(true);
    }
}
