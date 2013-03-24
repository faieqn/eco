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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author ronyHeat3203
 */
public class MainFrame extends JFrame{

    public MainFrame()
    {
        this.setBounds(300, 40, 660, 580);
        this.setVisible(true);
        this.setTitle("ECO.professor");
        this.setLayout(null);
        this.setBackground(Color.BLACK);

               /* Agregar Evento Cerrar a la ventana*/
         addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

}
