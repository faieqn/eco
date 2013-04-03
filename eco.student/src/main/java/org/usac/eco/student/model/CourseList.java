package org.usac.eco.student.model;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.libdto.DTOCourseSchedule;
import org.usac.eco.libdto.DTOCourseStatus;

import org.usac.eco.student.R;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.app.Activity;
import android.text.format.DateFormat;

public class CourseList extends BaseAdapter {

	private Context context; 
    
	private int layoutResourceId;    
    
    private DTOCourse courses[] = null;
    
	public CourseList(Context context, int textViewResourceId,
			DTOCourse[] courses) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.layoutResourceId = textViewResourceId;
		this.courses = courses;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		CourseItem item = null;
		if(row == null){
			LayoutInflater inflater = 
					((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			
			item = new CourseItem();
			item.txtCourseName = (TextView)row.findViewById(R.id.txtCourseName);
			item.txtProfessorName = (TextView)row.findViewById(R.id.txtProfessorName);
			item.txtDayName = (TextView)row.findViewById(R.id.txtDayName);
			item.txtHour = (TextView)row.findViewById(R.id.txtHour);
			item.imgStatus = (ImageView)row.findViewById(R.id.imgStatus);			
			row.setTag(item);
		} else {
			item = (CourseItem)row.getTag();
		}
		
		
		DTOCourse course = courses[position];
		String courseName = course.getCourseName();
		String professorName = course.getProfessor().getName();
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		DTOCourseSchedule next = course.getCourseSchedule()[0];
		String dayName = next.getDay().getDayName();
		String hour = DateFormat.format(
				"hh:mmaa", 
				next.getStartDate()).toString();
		
		item.txtCourseName.setText(courseName);
		item.txtProfessorName.setText(professorName);
		item.txtDayName.setText(dayName);
		item.txtHour.setText(hour);
		if(course.getStatus().getStatusId() == DTOCourseStatus.DISCONNECTED.getStatusId()) {
			item.imgStatus.setImageResource(R.drawable.offline);
		} else if (course.getStatus().getStatusId() == DTOCourseStatus.CONNECTED.getStatusId()) {
			item.imgStatus.setImageResource(R.drawable.online);
		}
		return row;
	}
	
	static class CourseItem {
		TextView txtCourseName;
		TextView txtProfessorName;
		TextView txtDayName;
		TextView txtHour;
		ImageView imgStatus;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return courses.length;
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return courses[arg0];
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return courses[arg0].getCourseId();
	}
		
	
}
