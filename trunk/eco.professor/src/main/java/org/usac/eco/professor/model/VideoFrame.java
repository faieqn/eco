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
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import javax.swing.JOptionPane;
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
    
    private int statusBarConnectionStatusId;
    
    private int statusBarConnectedId;
    
    private Thread updateConnected;
    
    private String size;
    
    private String elapsedTime;
    
    public VideoFrame(final DTOCourse dtoCourse) {
        this.dtoCourse = dtoCourse;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.getContentPane().setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
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
        
        mainPanel.add(Box.createRigidArea(new Dimension(0,5)));
        mainPanel.add(optionsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,5)));
        mainPanel.add(videoPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
        
        this.getStatusBar().setMaximumSize(new Dimension(screen.width,25));
        this.getStatusBar().setMinimumSize(new Dimension(660,25));
        this.getStatusBar().addMessage(dtoCourse.getCourseName());
        statusBarConnectionStatusId = this.getStatusBar().addMessage("Cargando dispositivos...");
        statusBarConnectedId = this.getStatusBar().addMessage("Conectados: " + dtoCourse.getConnected());
        
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        this.getContentPane().add(this.getStatusBar(), BorderLayout.SOUTH);
        
        videoController = new VideoController(this);
        new Thread(){

            @Override
            public void run() {
                btStart.setEnabled(false);
                videoController.loadDevices();
            }
            
        }.start();
        
        updateConnected = new Thread() {
            @Override
            public void run() {
                while(true){
                    
                    try {
                        videoController.checkCountConnected(dtoCourse);
                    } catch (Exception ex) {
                        Logger.getLogger(VideoFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        Thread.sleep(20*1000);
                    } catch (InterruptedException ex) {}
                }
            }
        };
        updateConnected.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("start") && encoder == null){
            startStreaming();
        } else if (e.getActionCommand().equals("stop")){
            stopStreaming();
        } else if (e.getActionCommand().equals("sourceChange")){
            changeDevice();
        }
    }

    public void listWebcam(List<VideoDevice> devices) {
        Iterator<VideoDevice> iterator = devices.iterator();
        while(iterator.hasNext()){
            cmbSource.addItem(iterator.next());
        }
    }

    public void listDesktop(List<VideoDevice> devices) {
        Iterator<VideoDevice> iterator = devices.iterator();
        while(iterator.hasNext()){
            cmbSource.addItem(iterator.next());
        }
    }

    public void onError(DTOCourse dtoCourse, VideoControllerMessage vcm) {
        this.getStatusBar().editMessageAsError(statusBarConnectionStatusId, "Error Classroom");
        showErrorDialogMessage("No es posible comunicarse con el servidor classroom.\n"
                + "URL: " + Configure.CLASSROOM.toString());
    }

    public void onStart(EncoderEvent ee) {
        this.getStatusBar().editMessage(statusBarConnectionStatusId, "Conectado");
        publish();
    }

    public void onError(EncoderEvent ee, EncoderMessage em) {
        this.getStatusBar().editMessageAsError(statusBarConnectionStatusId, "Error on transmiting");
        unpublish();
        showErrorDialogMessage("No es posible comunicarse con el servidor multimedia.\n"
                + "URL: " + dtoCourse.getURI());
    }

    public void onTerminated(EncoderEvent ee) {
        this.getStatusBar().editMessage(statusBarConnectionStatusId, "Desconectado");
        unpublish();
    }
    
    public void onTimeChanged(EncoderEvent ee, long time) {
        time = time / 1000000;
        long hours = time / 3600; // whole hours
        long mins = time / 60 - hours * 60;
        long secs = time % 60;
        this.elapsedTime = String.format("%s:%2s:%2s",
            new DecimalFormat("00").format(hours), 
            new DecimalFormat("00").format(mins), 
            new DecimalFormat("00").format(secs));
        this.getStatusBar().editMessage(statusBarConnectionStatusId, "Conectado " + elapsedTime + " (" + this.size + ")");
    }

    public void onSizeChanged(EncoderEvent ee, int size) {    
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double sizeKB = size / 1024;
        if(sizeKB < 1024){
            this.size = decimalFormat.format(sizeKB) + "KB";
        } else {
            double sizeMB = sizeKB / 1024;
            this.size = decimalFormat.format(sizeMB) + "MB";
        }
        this.getStatusBar().editMessage(statusBarConnectionStatusId, "Conectado " + elapsedTime + " (" + this.size + ")");
    }

    public void onImageChanged(EncoderEvent ee, BufferedImage image) {
        video.setIcon(new ImageIcon(image));
    }
    
    public void onFinishedListingDevices() {
        btStart.setEnabled(true);
        this.getStatusBar().editMessage(statusBarConnectionStatusId, "Desconectado");
    }
    
    public void showErrorDialogMessage(String message){
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void onChangedConnected(int connected) {
        this.getStatusBar().editMessage(statusBarConnectedId, "Connectados: " + connected);
    }
    
    private void onCloseWindow(){
        updateConnected.interrupt();
        unpublish();
        stopStreaming();
    }
    
    @Override
    public void onLogout() {
        onCloseWindow();
        super.onLogout();
    }
    
    private void publish(){
        try {
            videoController.publish(dtoCourse);
        } catch (MalformedURLException ex) {
            Logger.getLogger(VideoFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(VideoFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void unpublish(){
        try {
            videoController.unpublish(dtoCourse);
        } catch (Exception ex) {
            Logger.getLogger(VideoFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void startStreaming() {
        encoder = new Encoder((VideoDevice)cmbSource.getSelectedItem(), dtoCourse.getURI());
        encoder.addEncoderListener(this);
        encoder.start();
    }
    
    private void stopStreaming(){
        if(encoder != null) {
            encoder.interrupt();
            encoder = null;
        }
    }
    
    private void changeDevice(){
        if(encoder != null){
            encoder.setVideoDevice((VideoDevice)cmbSource.getSelectedItem());
        }
    }
    
}
