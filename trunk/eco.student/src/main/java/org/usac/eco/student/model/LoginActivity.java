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
package org.usac.eco.student.model;

import org.usac.eco.libdto.DTOUser;
import org.usac.eco.libdto.DTOUserProfile;
import org.usac.eco.student.R;
import org.usac.eco.student.Configure;
import org.usac.eco.student.controller.LoginControllerMessage;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.PendingIntent;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.usac.eco.student.controller.LoginController;

/**
 * @author Brian Estrada <brianseg014@gmail.com>
 *
 */
public class LoginActivity extends Activity 
			implements View.OnClickListener, LoginController.OnLoginControllerAction {
	
	private LoginController loginController;
	
	private ProgressDialog progressDialog;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.layout.menu_login, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.btPreference:
			Intent intent = new Intent(LoginActivity.this, EcoPreferenceActivity.class);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Configure.load(PreferenceManager.getDefaultSharedPreferences(this));
		
		setContentView(R.layout.login);
		
		Button btLogin = (Button) findViewById(R.id.btnLogin);
		btLogin.setOnClickListener(this);
		
		loginController = new LoginController(this);
		loginController.getRegisterLink();
		
		CheckBox rememberPassword = (CheckBox) findViewById(R.id.chkRememberMe);
		EditText username = (EditText) findViewById(R.id.txtUser);
		EditText password = (EditText) findViewById(R.id.txtPass);
		rememberPassword.setChecked(Configure.REMEMBER_PASSWORD);
		username.setText(Configure.USERNAME);
		password.setText(Configure.PASSWORD);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		EditText username = (EditText)findViewById(R.id.txtUser);
		EditText password = (EditText)findViewById(R.id.txtPass);
		
		progressDialog = ProgressDialog.show(LoginActivity.this, "", getString(R.string.logingin));
		loginController.validateSession(
				new DTOUser(
						0,
						null,
						username.getText().toString(),
						password.getText().toString(),
						null,
						DTOUserProfile.STUDENT,
						null)
				);
		
	}

	public void login() {
		// TODO Auto-generated method stub
		CheckBox rememberPassword = (CheckBox) findViewById(R.id.chkRememberMe);
		if(rememberPassword.isChecked()){
			EditText username = (EditText) findViewById(R.id.txtUser);
			EditText password = (EditText) findViewById(R.id.txtPass);
			Configure.rememberLogin(
					username.getText().toString(), 
					password.getText().toString());
		} else {
			Configure.forgetLogin();
		}
		
		Intent intent = new Intent(LoginActivity.this, CoursesActivity.class);
		startActivity(intent);
		progressDialog.dismiss();
	}

	public void onError(LoginControllerMessage lcm) {
		// TODO Auto-generated method stub
		switch(lcm){
		case ERROR_ON_LOGIN:
			showErrorOnLogin();
			break;
		case ERROR_CANNOT_CONNECT_CLASSROOM:
			showErrorCannotConnectClassroom();
			break;
		}
	}
	
	private void showErrorOnLogin(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.error));
        builder.setMessage(getString(R.string.errorUserPass));
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.ok), null);
        AlertDialog alert = builder.create();
        alert.show();
	}
	
	private void showErrorCannotConnectClassroom(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.error));
        builder.setMessage(getString(R.string.classroomNotResponding));
        builder.setCancelable(false);
        builder.setNegativeButton(getString(R.string.no), null);
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, EcoPreferenceActivity.class);
				startActivity(intent);
			}
		});
        AlertDialog alert = builder.create();
        alert.show();
	}

	public void recoveryPasswordLink(String link) {
		// TODO Auto-generated method stub
		TextView txtvWebsite = (TextView)findViewById(R.id.txtvWebsite);
		txtvWebsite.setText(Html.fromHtml(
					"<a href=\"" + link + "\">"+getString(R.string.forgotPassword)+"</a>"));
		txtvWebsite.setMovementMethod(LinkMovementMethod.getInstance());
	}

}
