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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author ronyHeat3203
 */
public class ECOFrame extends MainFrame implements ActionListener{

     private JMenuBar jmbMenuBar;

    private JMenu jmFile, jmHelp;

    private JMenuItem jmiLogOut, jmiExit, jmiAbout;

    private JLabel userName, user;
    
    private JStatusBar statusBar;

    public ECOFrame ()
    {
        super();

        this.jmbMenuBar = new JMenuBar();
        this.jmbMenuBar.setVisible(true);

        this.jmFile = new JMenu("Archivo");
        this.jmFile.setVisible(true);
        this.jmHelp = new JMenu("Ayuda");
        this.jmHelp.setVisible(true);

        this.jmiLogOut = new JMenuItem("Cerrar Sesión");
        this.jmiExit   = new JMenuItem("Salir");
        this.jmiAbout  = new JMenuItem("Acerca De");

        this.jmbMenuBar.add(this.jmFile);
        this.jmbMenuBar.add(this.jmHelp);

        this.jmFile.add(this.jmiLogOut);
        this.jmFile.add(this.jmiExit);

        this.jmHelp.add(this.jmiAbout);

        this.setJMenuBar(this.jmbMenuBar);

        this.user = new JLabel();
        this.userName = new JLabel();
        
        statusBar = new JStatusBar();
        statusBar.setVisible(true);
        statusBar.setBounds(1, 507, 650, 20);
        this.add(statusBar);

        jmiLogOut.addActionListener(this);
        jmiExit.addActionListener(this);
        jmiAbout.addActionListener(this);

    }

    public JMenu getJmFile() {
        return jmFile;
    }

    public void setJmFile(JMenu jmFile)
    {
        this.jmFile = jmFile;
    }

    public JMenu getJmHelp()
    {
        return jmHelp;
    }

    public void setJmHelp(JMenu jmHelp)
    {
        this.jmHelp = jmHelp;
    }

    public JMenuBar getJmbMenuBar() {
        return jmbMenuBar;
    }

    public void setJmbMenuBar(JMenuBar jmbMenuBar)
    {
        this.jmbMenuBar = jmbMenuBar;
    }

    public JMenuItem getJmiAbout()
    {
        return jmiAbout;
    }

    public void setJmiAbout(JMenuItem jmiAbout)
    {
        this.jmiAbout = jmiAbout;
    }

    public JMenuItem getJmiExit() {
        return jmiExit;
    }

    public void setJmiExit(JMenuItem jmiExit) {
        this.jmiExit = jmiExit;
    }

    public JMenuItem getJmiLogOut()
    {
        return jmiLogOut;
    }

    public void setJmiLogOut(JMenuItem jmiLogOut)
    {
        this.jmiLogOut = jmiLogOut;
    }

    public JLabel getUser()
    {
        return user;
    }

    public void setUser(JLabel user)
    {
        this.user = user;
    }

    public JLabel getUserName()
    {
        return userName;
    }

    public void setUserName(JLabel userName)
    {
        this.userName = userName;
    }

    public JStatusBar getStatusBar() {
        return statusBar;
    }

    public void setStatusBar(JStatusBar statusBar) {
        this.statusBar = statusBar;
    }    

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
