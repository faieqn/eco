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

package org.usac.eco.professor.controller;

import com.zodiac.soa.Request;
import com.zodiac.soa.client.DynamicServiceHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.usac.eco.libdto.DTOUser;
import org.usac.eco.professor.Configure;
import org.usac.eco.professor.Log;
import org.usac.eco.professor.Session;

/**
 *
 * @author ronyHeat3203
 */
public class LoginController {

    private List<ILoginController> listeners;

    public LoginController(ILoginController iLogin){
        listeners = new ArrayList<ILoginController>();
        addLoginControllerListener(iLogin);
    }
    
    public final boolean addLoginControllerListener(ILoginController ilc){
        if(ilc == null){
            return false;
        }
        return listeners.add(ilc);
    }
    
    private void fireOnError(DTOUser dtoUser, LoginControllerMessage vcm){
        Iterator<ILoginController> iterator = listeners.iterator();
        while(iterator.hasNext()){
            iterator.next().onError(dtoUser, vcm);
        }
    }
    
    public void getRecoveryPasswordLink() throws Exception{
        String clazz = "org.usac.eco.classroom.bl.Session";
        Class paramsConstructor[] = null;
        Object argsConstructor[] = null;
        String method = "geRecoveryPasswordLink";
        Class paramsMethod[] = null;
        Object argsMethod[] = null;
        Request request = new Request(clazz, paramsConstructor, argsConstructor, 
                method, paramsMethod, argsMethod);
        DynamicServiceHandler dsh = new DynamicServiceHandler(Configure.CLASSROOM);
        String link = dsh.run(request).toString();
        fireRecoveryPasswordLink(link);
    }

    public void validateSession(DTOUser dtoUser) throws Exception
    {
        String clazz = "org.usac.eco.classroom.bl.Session";
        Class paramsConstructor[] = null;
        Object argsConstructor[] = null;
        String method = "createSession";
        Class paramsMethod[] = {DTOUser.class};
        Object argsMethod[] = {dtoUser};
        Request request = new Request(clazz, paramsConstructor, argsConstructor, 
                method, paramsMethod, argsMethod);
        DynamicServiceHandler dsh = new DynamicServiceHandler(Configure.CLASSROOM);
        DTOUser loggedUser = (DTOUser)dsh.run(request);
        if(loggedUser != null){
            Session.getSession(loggedUser);
            fireListLogin();
        } else {
            fireOnError(dtoUser, LoginControllerMessage.ERROR_ON_LOGIN);
            return;
        }
    }
        
    public void logOut(DTOUser dtoUser) throws Exception{
        String clazz = "org.usac.eco.classroom.bl.Session";
        Class paramsConstructor[] = null;
        Object argsConstructor[] = null;
        String method = "destroySession";
        Class paramsMethod[] = null;
        Object argsMethod[] = null;
        Request request = new Request(clazz, paramsConstructor, argsConstructor, 
                method, paramsMethod, argsMethod);
        DynamicServiceHandler dsh = new DynamicServiceHandler(Configure.CLASSROOM);
        boolean unDestroySession = (Boolean)dsh.run(request);
        if(!unDestroySession){
            Log.fatal("Could not DestroySession: unknown cause.");
            fireOnError(dtoUser, LoginControllerMessage.ERROR_ON_LOGIN);
        }
    }
    
    private void fireListLogin(){
        Iterator<ILoginController> iterator = listeners.iterator();
        while(iterator.hasNext()){
            iterator.next().Login();
        }
    }
    
    private void fireRecoveryPasswordLink(String link){
        Iterator<ILoginController> iterator = listeners.iterator();
        while(iterator.hasNext()){
            iterator.next().recoveryPasswordLink(link);
        }
    }
}
