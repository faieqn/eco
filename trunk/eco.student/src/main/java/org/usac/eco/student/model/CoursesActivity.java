/**
 * 
 */
package org.usac.eco.student.model;

import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.libdto.DTOCourseStatus;
import org.usac.eco.student.R;
import org.usac.eco.student.Session;
import org.usac.eco.student.controller.CourseControllerMessage;
import org.usac.eco.student.controller.CoursesController;
import org.usac.eco.student.controller.LoginController;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Brian Estrada <brianseg014@gmail.com>
 *
 */
public class CoursesActivity extends EcoActivity 
		implements CoursesController.OnCourseControllerAction, AdapterView.OnItemClickListener {
	
	private CoursesController coursesController;
	
	private ProgressDialog progressDialog;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.layout.menu_courses, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.btUpdate:
			loadSubscribedCrouses();
			return true;
		default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.courses);
				
		ListView courseList = (ListView)findViewById(R.id.listCourses);
		
		View header = (View)getLayoutInflater().inflate(R.layout.listview_header, null);
		TextView txtTitle = (TextView)header.findViewById(R.id.txtHeader);
		txtTitle.setText("Cursos Suscritos");
		courseList.addHeaderView(header);
		courseList.setOnItemClickListener(this);
		
		coursesController = new CoursesController(this);
		loadSubscribedCrouses();
	}
	
	private void loadSubscribedCrouses(){
		progressDialog = ProgressDialog.show(CoursesActivity.this, "", getString(R.string.loading));
		coursesController.listCourses(Session.getSession().getUser());
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public void listCourse(DTOCourse[] courses) {
		// TODO Auto-generated method stub
		CourseList cl = new CourseList(this, R.layout.listview_row, courses);
		
		ListView courseList = (ListView)findViewById(R.id.listCourses);
		courseList.setAdapter(cl);
		progressDialog.dismiss();
	}

	public void onError(CourseControllerMessage ccm) {
		// TODO Auto-generated method stub
		
	}
	
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		// TODO Auto-generated method stub
		if(position > 0){
			ListView courseList = (ListView)findViewById(R.id.listCourses);
			DTOCourse dtoCourse = (DTOCourse)courseList.getItemAtPosition(position);
			if(dtoCourse.getStatus().getStatusId() == DTOCourseStatus.CONNECTED.getStatusId()){
				Intent intent = new Intent(this, PlayerActivity.class);
				intent.putExtra("course", dtoCourse.toXML());
				startActivity(intent);
			} else {
				showCourseDisconnected();
			}
		}
	}
	
	private void showCourseDisconnected(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.error));
        builder.setMessage(getString(R.string.errorStreaming));
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", null);
        AlertDialog alert = builder.create();
        alert.show();
	}

}
