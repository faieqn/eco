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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
import org.usac.eco.libdto.DTOUserProfile;
import org.usac.eco.professor.Log;
import org.usac.eco.professor.controller.LoginControllerMessage;

/**
 *
 * @author ronyHeat3203
 */
public class LoginFrame extends MainFrame implements ActionListener
{

    private JLabel lbUser, lbPass;
    
    private JLinkLabel lbRecoverPassword;

    private JTextField tfUser;

    private JPasswordField pfPass;

    private JPanel principalPanel;

    private JButton btnLogIn;
    
    private DTOUser dtoUser;

    public LoginFrame()
    {
        super();
        
        getContentPane().setLayout(new BorderLayout());

        principalPanel = new JPanel();
        principalPanel.setLayout(new BoxLayout(principalPanel, BoxLayout.X_AXIS));
        principalPanel.setVisible(true);
        principalPanel.setBorder(new CompoundBorder(
                new SoftBevelBorder(BevelBorder.LOWERED),new LineBorder(Color.red)
        ));
        
        JPanel dataPane = new JPanel();
        dataPane.setLayout(new BoxLayout(dataPane, BoxLayout.Y_AXIS));
        
        this.lbUser = new JLabel("Usuario");
        this.lbUser.setVisible(true);

        this.lbPass = new JLabel("Contraseña");
        this.lbPass.setVisible(true);

        this.tfUser = new JTextField();
        this.tfUser.setMaximumSize(new Dimension(4000,30));
        this.tfUser.setMinimumSize(new Dimension(1,35));
        this.tfUser.setVisible(true);

        this.pfPass = new JPasswordField();
        this.pfPass.setMaximumSize(new Dimension(4000,30));
        this.pfPass.setMinimumSize(new Dimension(1,35));
        this.pfPass.setVisible(true);

        this.btnLogIn = new JButton("Ingresar");
        this.btnLogIn.setVisible(true);

        this.lbRecoverPassword = new JLinkLabel("Recuperar Contraseña");
        this.lbRecoverPassword.setVisible(true);
        
        dataPane.add(Box.createRigidArea(new Dimension(0, 5)));
        dataPane.add(this.lbUser);
        dataPane.add(Box.createRigidArea(new Dimension(0, 5)));
        dataPane.add(this.tfUser);
        dataPane.add(Box.createRigidArea(new Dimension(0, 10)));
        dataPane.add(this.lbPass);
        dataPane.add(Box.createRigidArea(new Dimension(0, 5)));
        dataPane.add(this.pfPass);
        dataPane.add(Box.createRigidArea(new Dimension(0, 10)));
        dataPane.add(this.btnLogIn);
        dataPane.add(Box.createRigidArea(new Dimension(0, 5)));
        dataPane.add(this.lbRecoverPassword);
        dataPane.add(Box.createRigidArea(new Dimension(0, 5)));
        
        principalPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        principalPanel.add(dataPane);
        principalPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        
        getContentPane().add(principalPanel, BorderLayout.CENTER);
        getContentPane().add(Box.createRigidArea(new Dimension(0, 180)), BorderLayout.NORTH);
        getContentPane().add(Box.createRigidArea(new Dimension(0, 180)), BorderLayout.SOUTH);
        getContentPane().add(Box.createRigidArea(new Dimension(180, 0)), BorderLayout.WEST);
        getContentPane().add(Box.createRigidArea(new Dimension(180, 0)), BorderLayout.EAST);
        
        this.pack();
        this.btnLogIn.setActionCommand("login");
        this.btnLogIn.addActionListener(this);
    }

    @Override
    public void Login() {
        super.Login();
        new ProfessorFrame();
    }
    
    public void onError(DTOUser dtoUser, LoginControllerMessage lcm) {
        switch(lcm){
            case ERROR_ON_LOGIN:
                JOptionPane.showMessageDialog(null,"¡Error al tratar de ingresar a su sesión. Intente de nuevo!");
                break;
        }
    }
    
    public boolean ValidateParams(){
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
            if (ValidateParams()){
                dtoUser = new DTOUser(0, null,this.tfUser.getText(),this.pfPass.getSelectedText(), null, DTOUserProfile.PROFESSOR, null);
                try {
                    super.getController().validateSession(dtoUser);
                } catch (Exception ex) {
                    Log.error("Could not validateSession in LoginFrame: Error: "+ex.getMessage(),ex);
                    JOptionPane.showMessageDialog(this, "Error al obtener Conexión con servidor de aplicaciones. Intente ingresar nuevamente!. Error:"+ex.getMessage());
                }
            }            
        }
    }

    public void recoveryPasswordLink(String link) {
        try {
            this.lbRecoverPassword.setURI(new URI(link));
        } catch (URISyntaxException ex) {
            Log.error("Bad recovery password link receive from classroom server.");
        }
    }
}
