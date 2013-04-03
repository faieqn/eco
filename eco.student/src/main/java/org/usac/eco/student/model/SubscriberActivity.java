/**
 * 
 */
package org.usac.eco.student.model;

import java.util.Calendar;

import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.libdto.DTOCourseSchedule;
import org.usac.eco.student.R;
import org.usac.eco.student.Session;
import org.usac.eco.student.controller.LoginController;
import org.usac.eco.student.controller.SubscriberController;
import org.usac.eco.student.controller.SubscriberControllerMessage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Brian Estrada <brianseg014@gmail.com>
 *
 */
public class SubscriberActivity extends EcoActivity 
		implements SubscriberController.OnSubscriberControllerAction, AdapterView.OnItemClickListener {

	private SubscriberController subscriberController;
	
	private ProgressDialog progressDialog;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.layout.menu_logout, menu);
		return true;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subsriber);
				
		ListView courseList = (ListView)findViewById(R.id.listCoursesFound);
		courseList.setOnItemClickListener(this);
		
		View header = (View)getLayoutInflater().inflate(R.layout.listview_header, null);
		TextView txtTitle = (TextView)header.findViewById(R.id.txtHeader);
		txtTitle.setText("Cursos encontrados");
		courseList.addHeaderView(header);
		
		handleIntent(getIntent());
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);
		handleIntent(intent);
	}
	
	private void handleIntent(Intent intent){
		if(Intent.ACTION_SEARCH.equals(intent.getAction())){
			subscriberController = new SubscriberController(this);
			
			String query = intent.getStringExtra(SearchManager.QUERY);
			progressDialog = ProgressDialog.show(SubscriberActivity.this, "", getString(R.string.loading));
			subscriberController.listCourses(
					new DTOCourse(
							0,
							null,
							null,
							query, 
							0, 
							0, 
							null, 
							null,
							null,
							null));
		}
		onSearchRequested();
	}
	
	public void listCourse(DTOCourse[] courses) {
		// TODO Auto-generated method stub
		CourseList cl = new CourseList(this, R.layout.listview_row, courses);
		
		ListView courseList = (ListView)findViewById(R.id.listCoursesFound);
		courseList.setAdapter(cl);
	}

	public void onError(SubscriberControllerMessage scm) {
		// TODO Auto-generated method stub
		Toast toast = Toast.makeText(this, "Error al suscribirse.", Toast.LENGTH_SHORT);
		toast.show();
	}

	public void subscribed() {
		// TODO Auto-generated method stub
		Toast toast = Toast.makeText(this, "Curso suscrito correctamente.", Toast.LENGTH_SHORT);
		toast.show();
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		// TODO Auto-generated method stub
		if(position > 0){
			ListView courseList = (ListView)findViewById(R.id.listCoursesFound);
			DTOCourse dtoCourse = (DTOCourse)courseList.getItemAtPosition(position);
			showSubscribeDialog(dtoCourse);
		}
	}
	
	private void showSubscribeDialog(final DTOCourse dtoCourse){
		LayoutInflater inflater =((Activity)this).getLayoutInflater();
		
		View view = inflater.inflate(R.layout.course_info, null);
		TextView txtCourseName = (TextView) view.findViewById(R.id.txtCourseName);
		TextView txtProfessorName = (TextView) view.findViewById(R.id.txtProfessorName);
		TextView txtSection = (TextView) view.findViewById(R.id.txtSectionName);
		LinearLayout listSchedule = (LinearLayout) view.findViewById(R.id.listSchedule);
		
		txtCourseName.setText(dtoCourse.getCourseName());
		txtProfessorName.setText(dtoCourse.getProfessor().getName());
		txtSection.setText(dtoCourse.getSection().getSectionName());
		
		int countSchedule = dtoCourse.getCourseSchedule().length;
		for(int i = 0; i < countSchedule; i++){
			DTOCourseSchedule dtoCourseSchedule = dtoCourse.getCourseSchedule()[i];
			
			TextView txtSchedule = new TextView(this);
			String text = dtoCourseSchedule.getDay().getDayName() + " " 
					+ DateFormat.format("h:mmaa", dtoCourseSchedule.getStartDate());
			txtSchedule.setText(text);
			listSchedule.addView(txtSchedule);
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Desea suscribirse?");
        builder.setCancelable(true);
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				subscriberController.subscribe(dtoCourse);
			}
		});
        builder.setNegativeButton("No", null);
        builder.setView(view);
        AlertDialog alert = builder.create();
        alert.show();
	}
	
}
