package org.usac.eco.student;

import android.content.SharedPreferences;

public class Configure {
	
	private static SharedPreferences preferences;
	
	public static String CLASSROOM;
	
	public static boolean REMEMBER_PASSWORD;
	
	public static String USERNAME;
	
	public static String PASSWORD;
	
	public static void load(SharedPreferences preferences){
		Configure.preferences = preferences;
		
		CLASSROOM = preferences.getString("classroom_uri", "http://");
		REMEMBER_PASSWORD = preferences.getBoolean("remember_password", false);
		USERNAME = preferences.getString("username", "");
		PASSWORD = preferences.getString("password", "");
	}
	
	public static void rememberLogin(String username, String password){
		SharedPreferences.Editor preferences = Configure.preferences.edit();
		preferences.putBoolean("remember_password", true);
		preferences.putString("username", username);
		preferences.putString("password", password);
		preferences.commit();
	}
	
	public static void forgetLogin(){
		SharedPreferences.Editor preferences = Configure.preferences.edit();
		preferences.remove("remember_password");
		preferences.remove("username");
		preferences.remove("password");
		preferences.commit();
	}
	
}
