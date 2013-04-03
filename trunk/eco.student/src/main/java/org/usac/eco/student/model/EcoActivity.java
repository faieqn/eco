package org.usac.eco.student.model;

import org.usac.eco.student.R;
import org.usac.eco.student.controller.LoginController;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.view.MenuItem;

public abstract class EcoActivity extends Activity {
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (item.getItemId()) {
		case R.id.btLogout:
			LoginController loginController = new LoginController(null);
			loginController.logout();
			
			cancelNotifications();
			
			intent = new Intent(EcoActivity.this, LoginActivity.class);
			startActivity(intent);
			return true;
		case R.id.btSubscribe:
			intent = new Intent(EcoActivity.this, SubscriberActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void cancelNotifications(){
		Intent notificationIntent = new Intent(EcoActivity.this, NotificationService.class);
		PendingIntent pendingIntent = PendingIntent.getService(
				EcoActivity.this, 
				0, 
				notificationIntent, 
				0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
	}
	
	private void startNotifications(){
		int interval = 30*1000;
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent(EcoActivity.this, NotificationService.class);
		PendingIntent pendingIntent = PendingIntent.getService(EcoActivity.this, 0, intent, 0);
		alarmManager.setInexactRepeating(
				AlarmManager.ELAPSED_REALTIME_WAKEUP, 
				SystemClock.elapsedRealtime() + interval, 
				interval, 
				pendingIntent);
		
	}
	
	
	private void clearNotifications(){
		NotificationManager notificationManager = 
				(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancel(NotificationService.NOTIFICATION_ID);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		cancelNotifications();
		clearNotifications();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		startNotifications();
		super.onPause();
	}

}
