package org.usac.eco.student.model;

import java.util.ArrayList;
import java.util.List;

import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.libdto.DTOCourseStatus;
import org.usac.eco.libdto.DTOUser;
import org.usac.eco.student.R;
import org.usac.eco.student.Session;
import org.usac.eco.student.controller.CourseControllerMessage;
import org.usac.eco.student.controller.CoursesController;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.SystemClock;

public class NotificationService extends Service implements CoursesController.OnCourseControllerAction {
	
	public static int NOTIFICATION_ID = 0;
	
	private CoursesController coursesController;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		handleIntent(intent);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		handleIntent(intent);
		return START_NOT_STICKY;
	}
	
	private void handleIntent(Intent intent){
		coursesController = new CoursesController(this);
		coursesController.listCourses(Session.getSession().getUser());
	}

	public void listCourse(DTOCourse[] courses) {
		// TODO Auto-generated method stub
		System.out.println("EXECUTE SERVICE");
		List<DTOCourse> connectedCourses = 
				new ArrayList<DTOCourse>();
		
		int countCourses = courses.length;
		for(int i = 0; i < countCourses; i++){
			DTOCourse dtoCourse = courses[i];
			if(dtoCourse.getStatus().getStatusId() == DTOCourseStatus.CONNECTED.getStatusId()){
				connectedCourses.add(dtoCourse);
			}
		}
		
		Notification notification = null;
		
		int countConnectedCourses = connectedCourses.size();
		if(countConnectedCourses == 1){
			DTOCourse dtoCourse = connectedCourses.get(0);
			Intent courseIntent = new Intent(NotificationService.this, PlayerActivity.class);
			courseIntent.putExtra("course", dtoCourse.toXML());
			
			PendingIntent pendingIntent = PendingIntent.getActivity(
					NotificationService.this, 
					0, 
					courseIntent, 
					0); 
			notification = new Notification(
					R.drawable.logo, 
					"Nuevo curso conectado.", 
					System.currentTimeMillis());
			notification.number = 1;
			notification.setLatestEventInfo(
					NotificationService.this, 
					dtoCourse.getCourseName(), 
					dtoCourse.getProfessor().getName() + " esta conectado.", 
					pendingIntent);
		} else if (countConnectedCourses > 1){
			String coursesConnectedString = "";
			for(int i = 0; i < countConnectedCourses; i++){
				DTOCourse dtoCourse = connectedCourses.get(i);
				if(i > 0){
					coursesConnectedString += ", ";
				}
				coursesConnectedString += dtoCourse.getCourseName();
			}
			coursesConnectedString += " estan concetados.";
			
			Intent courseIntent = new Intent(NotificationService.this, CoursesActivity.class);
			
			PendingIntent pendingIntent = PendingIntent.getActivity(
					NotificationService.this, 
					0, 
					courseIntent, 
					0); 
			notification = new Notification(
					R.drawable.logo, 
					"Nuevo curso conectado.", 
					System.currentTimeMillis());
			notification.number = countConnectedCourses;
			notification.setLatestEventInfo(
					NotificationService.this, 
					"Nuevo curso conectado", 
					coursesConnectedString, 
					pendingIntent);
		}
		
		if(notification != null) {
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			
			AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
		    int ringerMode = audioManager.getRingerMode();
		    switch( ringerMode )
		    {
		        case AudioManager.RINGER_MODE_VIBRATE:
		        	notification.vibrate = new long[]{100, 250, 100, 500};
		            break;
		             
		        case AudioManager.RINGER_MODE_NORMAL:
		        	notification.defaults = Notification.DEFAULT_SOUND;
		            break;
		             
		        case AudioManager.RINGER_MODE_SILENT:
		        	notification.ledARGB = 0xff0000ff;
		        	notification.ledOnMS = 500;
		        	notification.ledOffMS = 3000;
		        	notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		            break;
		 
		        default:
		            break;
		    }
			
			NotificationManager notificationManager = 
					(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
			notificationManager.notify(NOTIFICATION_ID, notification);
		}
		
	}

	public void onError(CourseControllerMessage ccm) {
		// TODO Auto-generated method stub
		
	}

}
