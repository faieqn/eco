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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.professor.Configure;
import org.usac.eco.professor.Session;
import org.usac.eco.professor.controller.IProfessorController;
import org.usac.eco.professor.controller.ProfessorController;

/**
 *
 * @author ronyHeat3203
 */
public class ProfessorFrame extends ECOFrame implements IProfessorController {
    
    private ProfessorController professorController;
    
    private JScrollPane pnInfoCourses;
    
    private JPanel pnInfoUser;
    
    private JPanel pnCourseContainer;
    
    private JPanel pnTitulo;
    
    public ProfessorFrame(){
        super();
        this.setVisible(true);
        this.setTitle(Configure.APP_TITLE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        
        pnCourseContainer = new JPanel();
        pnCourseContainer.setMaximumSize(new Dimension(screen.width,600));
        pnCourseContainer.setMinimumSize(new Dimension(600,400));
        pnCourseContainer.setLayout(new BoxLayout(pnCourseContainer, BoxLayout.Y_AXIS));
        
        pnInfoCourses = new JScrollPane(pnCourseContainer);
        pnInfoCourses.setVisible(true);
        pnInfoCourses.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pnInfoCourses.setMaximumSize(new Dimension(screen.width,600));
        pnInfoCourses.setMinimumSize(new Dimension(600,400));
        pnInfoCourses.setBorder(new LineBorder(Color.red));
        pnInfoCourses.add(Box.createRigidArea(new Dimension(0,15)));
        
        pnInfoUser = new JPanel();
        pnInfoUser.setLayout(new BorderLayout());
        pnInfoUser.setMaximumSize(new Dimension(screen.width,50));
        pnInfoUser.setMinimumSize(new Dimension(660,50));        
        pnInfoUser.setBorder(new LineBorder(Color.red));
        pnInfoUser.add(new JLabel("BIENVENIDO"),BorderLayout.NORTH);
        pnInfoUser.setVisible(true);
        
        pnTitulo = new JPanel();
        pnTitulo.setLayout(new BorderLayout());
        pnTitulo.setMaximumSize(new Dimension(screen.width,50));
        pnTitulo.setMinimumSize(new Dimension(300,50));
        
        JLabel lbTitulo = new JLabel("<html><h2><center>LISTADO DE CURSOS ASIGNADOS</center></h2></html>");
        lbTitulo.setBorder(new LineBorder(Color.BLUE));
        lbTitulo.setMinimumSize(new Dimension(310,100));
        pnTitulo.setMaximumSize(new Dimension(310,50));
        pnTitulo.add(lbTitulo,BorderLayout.CENTER);
        
        
        this.getUser().setText("  Nombre: "+Session.getSession().getUser().getUsername());
        this.getUserName().setText("Usuario: "+Session.getSession().getUser().getName());
        //this.getUser().setText("  Nombre: Rony Arredondo");
        //this.getUserName().setText("Usuario: roarredondo");
        this.getUser().setVisible(true);
        
        pnInfoUser.add(this.getUser(),BorderLayout.WEST);
        pnInfoUser.add(this.getUserName(), BorderLayout.EAST);
        pnInfoUser.add(Box.createRigidArea(new Dimension(0, 15)),BorderLayout.SOUTH);
        
        this.getContentPane().add(pnInfoUser,BorderLayout.NORTH);
        this.getContentPane().add(Box.createRigidArea(new Dimension(0,15)));
        this.getContentPane().add(pnTitulo,BorderLayout.CENTER);
        this.getContentPane().add(Box.createRigidArea(new Dimension(0,15)));
        this.getContentPane().add(this.pnInfoCourses,BorderLayout.CENTER);
        
        this.getStatusBar().setMaximumSize(new Dimension(screen.width,25));
        this.getStatusBar().setMinimumSize(new Dimension(660,25));
        this.getStatusBar().addMessage("mensaje de prueba de barra status");
        this.getContentPane().add(this.getStatusBar(),BorderLayout.SOUTH);
        try {
           // professorController = new ProfessorController(this);
        } catch (Exception ex) {
            Logger.getLogger(ProfessorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void listCourses(List<DTOCourse> courses) {
        //throw new UnsupportedOperationException("Not supported yet.");
        Iterator<DTOCourse> iCourse = courses.iterator();
        while(iCourse.hasNext()){
            this.pnCourseContainer.add(new Course(iCourse.next()));     
            this.pnCourseContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        }
    }

    public void recoveryPasswordLink(String link) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
