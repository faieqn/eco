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
package org.usac.eco.student.controller;

import org.usac.eco.libdto.DTOUser;
import org.usac.eco.student.Configure;
import org.usac.eco.student.Session;

import android.os.Handler;
import android.os.Message;

import com.zodiac.soa.Request;
import com.zodiac.soa.client.android.DynamicServiceHandler;
import com.zodiac.soa.client.android.DynamicServiceHandlerListener;

/**
 * @author Brian Estrada <brianseg014@gmail.com>
 *
 */
public class LoginController {
	
	private OnLoginControllerAction onLoginListener;
	
	public LoginController(OnLoginControllerAction onLoginListener){
		setOnLoginListener(onLoginListener);
	}
	
	public final void setOnLoginListener(OnLoginControllerAction onLoginListener) {
		this.onLoginListener = onLoginListener;
	}
	
	public void getRegisterLink() {
		String clazz = "org.usac.eco.classroom.bl.ConfigureClient";
        Class paramsConstructor[] = null;
        Object argsConstructor[] = null;
        String method = "geRecoveryPasswordLink";
        Class paramsMethod[] = null;
        Object argsMethod[] = null;
        Request request = new Request(clazz, paramsConstructor, argsConstructor, 
                method, paramsMethod, argsMethod);
        request.setNoException(true);
        DynamicServiceHandler dsh = new DynamicServiceHandler(Configure.CLASSROOM);
        
        dsh.run(request, new DynamicServiceHandlerListener() {

        	public void onResponse(Object arg0) {
        		// TODO Auto-generated method stub
        		fireRecoveryPasswordLink((String)arg0);
        	}

        	public void onException(Exception arg0) {
        		// TODO Auto-generated method stub
        		arg0.printStackTrace();
        		fireOnError(LoginControllerMessage.ERROR_CANNOT_CONNECT_CLASSROOM);
        	}
        });

	}
	
	public void validateSession(DTOUser dtoUser) {
		String clazz = "org.usac.eco.classroom.bl.Session";
        Class paramsConstructor[] = null;
        Object argsConstructor[] = null;
        String method = "createSession";
        Class paramsMethod[] = {DTOUser.class};
        Object argsMethod[] = {dtoUser};
        Request request = new Request(clazz, paramsConstructor, argsConstructor, 
                method, paramsMethod, argsMethod);
        request.setNoException(true);
        final DynamicServiceHandler dsh = new DynamicServiceHandler(Configure.CLASSROOM);
        
        dsh.run(request, new DynamicServiceHandlerListener() {

        	public void onResponse(Object arg0) {
        		// TODO Auto-generated method stub
        		DTOUser loggedUser = (DTOUser)arg0;
        		if(loggedUser != null){ 
        			Session.getSession(loggedUser);
        			Session.getSession().setDynamicServiceHandler(dsh);
        			fireLogin();
        		} else {
        			fireOnError(LoginControllerMessage.ERROR_ON_LOGIN);
        		}
        	}

        	public void onException(Exception arg0) {
        		// TODO Auto-generated method stub
        		fireOnError(LoginControllerMessage.ERROR_CANNOT_CONNECT_CLASSROOM);
        	}
        });
        
	}
	
	public void logout(){
		String clazz = "org.usac.eco.classroom.bl.Session";
        Class paramsConstructor[] = null;
        Object argsConstructor[] = null;
        String method = "destroySession";
        Class paramsMethod[] = null;
        Object argsMethod[] = null;
        Request request = new Request(clazz, paramsConstructor, argsConstructor, 
                method, paramsMethod, argsMethod);
        request.setNoException(true);
        DynamicServiceHandler dsh = Session.getSession().getDynamicServiceHandler();
        dsh.removeAllDynamicServiceHandlerListener();
        dsh.run(request, new DynamicServiceHandlerListener() {

        	public void onResponse(Object arg0) {
        		// TODO Auto-generated method stub
        		
        	}

        	public void onException(Exception arg0) {
        		// TODO Auto-generated method stub
        		fireOnError(LoginControllerMessage.ERROR_CANNOT_CONNECT_CLASSROOM);
        	}
        });
	}
	
	private void fireLogin(){
		if(onLoginListener != null){
			onLoginListener.login();
		}
	}
	
	private void fireRecoveryPasswordLink(String link){
		if(onLoginListener != null){
			onLoginListener.recoveryPasswordLink(link);
		}
	}
	
	private void fireOnError(LoginControllerMessage lcm){
		if(onLoginListener != null){
			onLoginListener.onError(lcm);
		}
	}
	
	public interface OnLoginControllerAction {
		
		public void login();
		
		public void recoveryPasswordLink(String link);
		
		public void onError(LoginControllerMessage lcm);

	}

}
