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

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author ronyHeat3203
 */
public class JStatusBar extends JPanel{
    
    public JStatusBar() 
    {
        this.setVisible(true);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 1));
        this.setLayout(new GridLayout());
    }
    
    public int addMessage(String msg)
    {
        JLabel lb = new JLabel(msg);
        this.add(lb);
        lb.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
        lb.setSize(lb.getWidth(), this.getHeight());
        lb.setVisible(true);
        return this.getComponentCount() -  1;
    }
    
    public void editMessage(int position, String msg){
        JLabel lb = (JLabel)this.getComponent(position);
        lb.setText(msg);
    }
    
}
