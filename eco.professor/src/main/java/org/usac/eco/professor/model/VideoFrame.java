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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.professor.Configure;
import org.usac.eco.professor.Session;
import org.usac.eco.professor.controller.Encoder;
import org.usac.eco.professor.controller.EncoderEvent;
import org.usac.eco.professor.controller.EncoderListener;
import org.usac.eco.professor.controller.EncoderMessage;
import org.usac.eco.professor.controller.IVideoController;
import org.usac.eco.professor.controller.VideoController;
import org.usac.eco.professor.controller.VideoControllerMessage;
import org.usac.eco.professor.controller.VideoDevice;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class VideoFrame extends ECOFrame 
        implements ActionListener, IVideoController, EncoderListener {
    
    private VideoController videoController;
    
    private DTOCourse dtoCourse;
    
    private JComboBox cmbSource;
    
    private JButton btStart, btStop;
    
    private JLabel video;
    
    private Encoder encoder;
    
    private int statusBarConnectionId;
    
    private int statusBarConnectedId;
    
    public VideoFrame(DTOCourse dtoCourse) {
        this.dtoCourse = dtoCourse;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));
        
        JLabel lblSource = new JLabel("Fuente");
        
        cmbSource = new JComboBox();
        cmbSource.setMaximumSize(new Dimension(1000, 25));
        cmbSource.setActionCommand("sourceChange");
        cmbSource.addActionListener(this);
        
        btStart = new JButton("Iniciar");
        btStart.setActionCommand("start");
        btStart.addActionListener(this);
        
        btStop = new JButton("Detener");
        btStop.setActionCommand("stop");
        btStop.addActionListener(this);
        
        optionsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        optionsPanel.add(lblSource);
        optionsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        optionsPanel.add(cmbSource);
        optionsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        optionsPanel.add(btStart);
        optionsPanel.add(btStop);
        optionsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        
        JPanel videoPanel = new JPanel();
        videoPanel.setLayout(new BoxLayout(videoPanel, BoxLayout.X_AXIS));
        
        video = new JLabel();
        video.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        try {
            if(Configure.VIDEO_ECO_PIC != null){
                video.setIcon(new ImageIcon(ImageIO.read(Configure.VIDEO_ECO_PIC)));
            }
        } catch (IOException ex) {
            Logger.getLogger(VideoFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        videoPanel.add(Box.createRigidArea(new Dimension(5,0)));
        videoPanel.add(video);
        videoPanel.add(Box.createRigidArea(new Dimension(5,0)));
        
        add(Box.createRigidArea(new Dimension(0,5)));
        add(optionsPanel);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(videoPanel);
        add(Box.createRigidArea(new Dimension(0,10)));
        
        this.getStatusBar().setMaximumSize(new Dimension(screen.width,25));
        this.getStatusBar().setMinimumSize(new Dimension(660,25));
        this.getStatusBar().addMessage(Session.getSession().getUser().getName());
        this.getStatusBar().addMessage(dtoCourse.getCourseName());
        statusBarConnectionId = this.getStatusBar().addMessage("Cargando dispositivos");
        statusBarConnectedId = this.getStatusBar().addMessage("Conectados: " + dtoCourse.getConnected());
        this.getContentPane().add(this.getStatusBar(),BorderLayout.SOUTH);
        
        videoController = new VideoController(this);
        new Thread(){

            @Override
            public void run() {
                btStart.setEnabled(false);
                videoController.loadDevices();
            }
            
        }.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("start") && encoder == null){
            encoder = new Encoder((VideoDevice)cmbSource.getSelectedItem(), dtoCourse.getURI());
            encoder.addEncoderListener(this);
            encoder.start();
            this.getStatusBar().editMessage(statusBarConnectionId, "Conectado");
        } else if (e.getActionCommand().equals("stop")){
            if(encoder != null) {
                encoder.interrupt();
                encoder = null;
                this.getStatusBar().editMessage(statusBarConnectionId, "Desconectado");
            }
        } else if (e.getActionCommand().equals("sourceChange")){
            if(encoder != null){
                encoder.setVideoDevice((VideoDevice)cmbSource.getSelectedItem());
            }
        }
    }

    public void listWebcam(List<VideoDevice> devices) {
        Iterator<VideoDevice> iterator = devices.iterator();
        while(iterator.hasNext()){
            cmbSource.addItem(iterator.next());
        }
        btStart.setEnabled(true);
        this.getStatusBar().editMessage(statusBarConnectionId, "Desconectado");
    }

    public void listDesktop(List<VideoDevice> devices) {
        Iterator<VideoDevice> iterator = devices.iterator();
        while(iterator.hasNext()){
            cmbSource.addItem(iterator.next());
        }
    }

    public void onError(DTOCourse dtoCourse, VideoControllerMessage vcm) {
        
    }

    public void onStart(EncoderEvent ee) {
        
    }

    public void onError(EncoderEvent ee, EncoderMessage em) {
        
    }

    public void onTerminated(EncoderEvent ee) {
        
    }

    public void onSizeChange(EncoderEvent ee, int size) {
        
    }

    public void onImageChange(EncoderEvent ee, BufferedImage image) {
        video.setIcon(new ImageIcon(image));
    }

    public void recoveryPasswordLink(String link) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
       
}
