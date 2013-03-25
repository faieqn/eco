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
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import org.usac.eco.libdto.DTOUser;
import org.usac.eco.libdto.DTOUser.Profile;
import org.usac.eco.professor.Log;
import org.usac.eco.professor.controller.ILoginController;
import org.usac.eco.professor.controller.LoginController;
import org.usac.eco.professor.controller.LoginControllerMessage;

/**
 *
 * @author ronyHeat3203
 */
public class LoginFrame extends MainFrame implements ActionListener
{

    private JLabel lbUser, lbPass, lbRecoverPassword;

    private JTextField tfUser;

    private JPasswordField pfPass;

    private JPanel principalPanel;

    private JButton btnLogIn;
    
    private DTOUser dtoUser;

    public LoginFrame()
    {
        super();

        principalPanel = new JPanel();
        principalPanel.setVisible(true);
        principalPanel.setLayout(null);
        principalPanel.setBounds(150, 140, 350, 250);

        principalPanel.setBorder(new CompoundBorder(
                new SoftBevelBorder(BevelBorder.LOWERED),new LineBorder(Color.red)
        ));

        this.add(this.principalPanel);

        this.lbUser = new JLabel("Usuario");
        this.principalPanel.add(this.lbUser);
        this.lbUser.setVisible(true);
        this.lbUser.setBounds(75,40,60,10);

        this.lbPass = new JLabel("Contraseña");
        this.principalPanel.add(this.lbPass);
        this.lbPass.setVisible(true);
        this.lbPass.setBounds(75,100,80,20);

        this.tfUser = new JTextField();
        this.principalPanel.add(this.tfUser);
        this.tfUser.setVisible(true);
        this.tfUser.setBounds(75, 60, 200, 30);

        this.pfPass = new JPasswordField();
        this.principalPanel.add(this.pfPass);
        this.pfPass.setVisible(true);
        this.pfPass.setBounds(75, 125, 200, 30);

        this.btnLogIn = new JButton("Ingresar");
        this.principalPanel.add(this.btnLogIn);
        this.btnLogIn.setVisible(true);
        this.btnLogIn.setBounds(175, 160, 100, 30);

        this.lbRecoverPassword = new JLabel("<html><span style='color: blue;'><u>Recuperar Contraseña</u></span></html>");
        this.principalPanel.add(this.lbRecoverPassword);
        this.lbRecoverPassword.setVisible(true);
        this.lbRecoverPassword.setBounds(150, 190, 130, 30);

                        /*Agregar efecto de cursor de hiper link*/

        this.lbRecoverPassword.addMouseListener(new MouseAdapter()
        {

            @Override
            public void mouseExited(MouseEvent arg0)
            {
                lbRecoverPassword.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseEntered(MouseEvent arg0)
            {
                lbRecoverPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        this.btnLogIn.setActionCommand("login");
        this.btnLogIn.addActionListener(this);
    }

    public void onError(DTOUser dtoUser, LoginControllerMessage lcm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean ValidateData(){
        if (this.tfUser.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this,"¡Debe ingresar usuario!");
            return false;
        }
        if (this.pfPass.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this,"¡Debe ingresar contraseña!");
            return false;
        }
        return true;
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("login")){
            if (ValidateData()){
                dtoUser = new DTOUser(0,this.tfUser.getText(),this.pfPass.getSelectedText(),Profile.PROFESSOR);
                try {
                    super.getController().ValidateSession(dtoUser);
                } catch (Exception ex) {
                    Log.fatal("Could not validateSession in LoginFrame: Error: "+ex.getMessage());
                }
            }
            
        }
    }
}
