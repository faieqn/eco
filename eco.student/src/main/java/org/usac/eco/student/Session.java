/**
 * 
 */
package org.usac.eco.student;

import org.usac.eco.libdto.DTOUser;

import com.zodiac.soa.client.android.DynamicServiceHandler;

/**
 * @author Brian Estrada <brianseg014@gmail.com>
 *
 */
public class Session {

	private static Session INSTANCE = null;
	
	private DTOUser user;
	
	private DynamicServiceHandler dynamicServiceHandler;
	
	public static Session getSession(){
		return getSession(null);
	}
	
	public static Session getSession(DTOUser user){
		if(INSTANCE == null){
			synchronized (Session.class) {
				INSTANCE = new Session(user);
			}
		}
		return INSTANCE;
	}
	
	public Session(DTOUser user){
		this.user = user;
	}
	
	public DTOUser getUser(){
		return user;
	}
	
	public DynamicServiceHandler getDynamicServiceHandler() {
		return dynamicServiceHandler;
	}
	
	public void setDynamicServiceHandler(
			DynamicServiceHandler dynamicServiceHandler) {
		this.dynamicServiceHandler = dynamicServiceHandler;
	}
	
}
