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
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.usac.eco.libdto.DTOCourse;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class Course extends JPanel implements MouseListener {
    
    private DTOCourse dtoCourse;
    
    private List<ActionListener> listener;

    public Course(DTOCourse dtoCourse) {
        listener = new ArrayList<ActionListener>();
        this.dtoCourse = dtoCourse;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.addMouseListener(this);
        
        JLabel lblCourseName = new JLabel(dtoCourse.getCourseName());
        lblCourseName.setFont(lblCourseName.getFont().deriveFont(15));
        lblCourseName.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblCourseName.addMouseListener(this);
        
        JLabel lblSubscribers = new JLabel(String.valueOf(dtoCourse.getSubscribers()));
        lblSubscribers.setForeground(Color.GRAY);
        lblSubscribers.addMouseListener(this);
        
        JLabel lblConnected = new JLabel(String.valueOf(dtoCourse.getConnected()));
        lblConnected.setForeground(Color.GRAY);
        lblConnected.addMouseListener(this);
        
        add(lblCourseName);
        add(lblSubscribers);
        add(lblConnected);
    }
    
    public DTOCourse getDTOCourse(){
        return this.dtoCourse;
    }
    
    private void fireActionPerformed(){
        Iterator<ActionListener> iterator = listener.iterator();
        while(iterator.hasNext()){
            iterator.next().actionPerformed(new ActionEvent(this, 0, null));
        }
    }

    public void mouseClicked(MouseEvent e) {
        fireActionPerformed();
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}
    
    
    
}
