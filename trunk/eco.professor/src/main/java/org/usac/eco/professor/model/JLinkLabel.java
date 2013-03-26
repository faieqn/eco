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
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import org.usac.eco.professor.Configure;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class JLinkLabel extends JLabel implements MouseListener {

    private URI URI;
    
    public JLinkLabel() {
        init();
    }

    public JLinkLabel(String text) {
        super(text);
        init();
    }
    
    public JLinkLabel(String text, URI URI) {
        super(text);
        setURI(URI);
        init();
    }
    
    private void init(){
        setForeground(Color.BLUE);
        addMouseListener(this);
    }
    
    @Override
    public void paint(Graphics g) {
        Rectangle r;
        super.paint(g);
        r = g.getClipBounds();
        g.drawLine(0, r.height - getFontMetrics(getFont()).getDescent(), getFontMetrics(getFont())
                .stringWidth(getText()), r.height - getFontMetrics(getFont()).getDescent());
    }

    public final void setURI(URI URI) {
        this.URI = URI;
    }

    public void mouseClicked(MouseEvent e) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(URI);
            } catch (IOException ex) {
                Logger.getLogger(JLinkLabel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void mouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
    
    
    
    
}
