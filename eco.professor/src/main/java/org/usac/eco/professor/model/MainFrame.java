/*
 *  Copyright (C) 2013 ronyHeat3203
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.usac.eco.professor.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import org.usac.eco.libdto.DTOUser;
import org.usac.eco.professor.controller.ILoginController;
import org.usac.eco.professor.controller.LoginController;
import org.usac.eco.professor.controller.LoginControllerMessage;

/**
 *
 * @author ronyHeat3203
 */
public class MainFrame extends JFrame implements ILoginController{
    
    private LoginController controller;

    public MainFrame()
    {
        this.setTitle("ECO - Profesor");
        this.setMinimumSize(new Dimension(680, 590));
        this.setVisible(true);
        
               /* Agregar Evento Cerrar a la ventana*/
         addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public LoginController getController() {
        return controller;
    }

    public void setController(LoginController controller) {
        this.controller = controller;
    }

    public void Login() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onError(DTOUser dtoUser, LoginControllerMessage lcm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
