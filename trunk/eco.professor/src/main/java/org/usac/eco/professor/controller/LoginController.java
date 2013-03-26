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
import org.usac.eco.professor.model.ProfessorFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author ronyHeat3203
 */
public class LoginController {

    private List<ILoginController> listeners;
    
    private ProfessorFrame professorFrame;

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

    public void ValidateSession(DTOUser dtoUser) throws Exception
    {
        String clazz = "org.usac.eco.classroom.bl.User";
        Class paramsConstructor[] = null;
        Object argsConstructor[] = null;
        String method = "createSession";
        Class paramsMethod[] = {DTOUser.class};
        Object argsMethod[] = {dtoUser};
        Request request = new Request(clazz, paramsConstructor, argsConstructor, 
                method, paramsMethod, argsMethod);
        DynamicServiceHandler dsh = new DynamicServiceHandler(Configure.CLASSROOM);
        boolean unCreateSession = (Boolean)dsh.run(request);
        if(!unCreateSession){
            JOptionPane.showMessageDialog(null,"¡Error al tratar de ingresar a su sesión. Intente de nuevo!");
            fireOnError(dtoUser, LoginControllerMessage.ERROR_ON_LOGIN);
            return;
        }
        fireListLogin();
        professorFrame = new ProfessorFrame();
             
    }

    public void RecoverPassword()
    {

    }
    
    public void LogOut(DTOUser dtoUser) throws Exception{
        String clazz = "org.usac.classroom.bl.User";
        Class paramsConstructor[] = null;
        Object argsConstructor[] = null;
        String method = "DestroySession";
        Class paramsMethod[] = {DTOUser.class};
        Object argsMethod[] = {dtoUser};
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
}
