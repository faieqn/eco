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
package org.usac.eco.professor.model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.usac.eco.professor.Configure;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class AboutDialog extends JDialog implements ActionListener {

    public AboutDialog(Frame owner) {
        super(owner, "Acerca de " + Configure.APP_TITLE, true);
        getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        JPanel imgPanel = new JPanel();
        //imgPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        try {
            JLabel aboutImg = new JLabel(new ImageIcon(ImageIO.read(Configure.ABOUT_PIC)));
            imgPanel.add(aboutImg);
        } catch (IOException ex) {
            Logger.getLogger(AboutDialog.class.getName()).log(Level.SEVERE, null, ex);
        }

        JPanel titlePanel = new JPanel();
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel(Configure.APP_TITLE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(new Font(lblTitle.getFont().getName(), Font.BOLD, 25));

        JLabel lblDescription = new JLabel("Programa para tutorias virtuales.");
        lblDescription.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDescription.setFont(new Font(lblTitle.getFont().getName(), Font.BOLD, 12));
        lblDescription.setForeground(Color.GRAY);

        titlePanel.add(lblTitle);
        titlePanel.add(lblDescription);

        JPanel creditsPanel = new JPanel();
        creditsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        creditsPanel.setLayout(new BoxLayout(creditsPanel, BoxLayout.Y_AXIS));

        JLabel lblCredits = new JLabel("Tesis (2013): Rony Arredondo y Brian Estrada");
        lblCredits.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLinkLabel website = new JLinkLabel(Configure.APP_WEBSITE);
        try {
            website.setURI(new URI(Configure.APP_WEBSITE));
        } catch (URISyntaxException ex) {
            Logger.getLogger(AboutDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        website.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        creditsPanel.add(lblCredits);
        creditsPanel.add(website);
        
        JPanel buttonPanel = new JPanel();

        JButton btClose = new JButton("Cerrar");
        btClose.setActionCommand("close");
        btClose.addActionListener(this);
        
        buttonPanel.add(btClose);
        
        add(imgPanel);
        add(titlePanel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(creditsPanel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(buttonPanel);
        add(Box.createRigidArea(new Dimension(0, 20)));

        pack();
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("close")){
            dispose();
        }
    }
}
