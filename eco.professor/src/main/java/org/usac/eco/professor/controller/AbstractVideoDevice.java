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
package org.usac.eco.professor.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public abstract class AbstractVideoDevice implements VideoDevice{
    
    private List<VideoDeviceListener> listener;

    public AbstractVideoDevice() {
        listener = new ArrayList<VideoDeviceListener>();
    }
    
    public boolean addVideoDeviceListener(VideoDeviceListener vdl){
        return listener.add(vdl);
    }
    
    public boolean removeVideoDeviceListener(VideoDeviceListener vdl){
        return listener.remove(vdl);
    }
    
    protected void fireOnError(VideoDeviceEvent vde, VideoDeviceMessage vdm){
        Iterator<VideoDeviceListener> iterator = listener.iterator();
        while(iterator.hasNext()){
            iterator.next().onError(vde, vdm);
        }
    }
    
    protected void fireOnOpen(VideoDeviceEvent vde){
        Iterator<VideoDeviceListener> iterator = listener.iterator();
        while(iterator.hasNext()){
            iterator.next().onOpen(vde);
        }
    }
    
    protected void fireOnClosed(VideoDeviceEvent vde){
        Iterator<VideoDeviceListener> iterator = listener.iterator();
        while(iterator.hasNext()){
            iterator.next().onClosed(vde);
        }
    }

    @Override
    public String toString() {
        return getName();
    }
    
}
