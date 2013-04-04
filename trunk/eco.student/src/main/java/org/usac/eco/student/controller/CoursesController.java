package org.usac.eco.student.controller;

import java.util.List;

import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.libdto.DTOUser;
import org.usac.eco.student.Configure;
import org.usac.eco.student.Session;

import com.zodiac.soa.Request;
import com.zodiac.soa.client.android.DynamicServiceHandler;
import com.zodiac.soa.client.android.DynamicServiceHandlerListener;

public class CoursesController {
	
	private OnCourseControllerAction onCourseControllerAction;
	
	public CoursesController(OnCourseControllerAction occa){
		setOnCourseControllerAction(occa);
	}
	
	public void setOnCourseControllerAction(
			OnCourseControllerAction onCourseControllerAction) {
		this.onCourseControllerAction = onCourseControllerAction;
	}
	
	public void listCourses(DTOUser dtoUser) {
		String clazz = "org.usac.eco.classroom.bl.CourseOpen";
        Class paramsConstructor[] = null;
        Object argsConstructor[] = null;
        String method = "getCoursesOpenSubscribed";
        Class paramsMethod[] = {DTOUser.class};
        Object argsMethod[] = {dtoUser};
        Request request = new Request(clazz, paramsConstructor, argsConstructor, 
                method, paramsMethod, argsMethod);
        request.setTextModeException(true);
        DynamicServiceHandler dsh = Session.getSession().getDynamicServiceHandler();
        dsh.removeAllDynamicServiceHandlerListener();
        dsh.run(request, new DynamicServiceHandlerListener() {

        	public void onResponse(Object arg0) {
        		// TODO Auto-generated method stub
        		List<DTOCourse> courses = (List<DTOCourse>)arg0;
        		DTOCourse[] arrayCourses = new DTOCourse[courses.size()];
        		fireListCourse(courses.toArray(arrayCourses));
        	}

        	public void onException(Exception arg0) {
        		// TODO Auto-generated method stub
        		arg0.printStackTrace();
        		fireOnError(CourseControllerMessage.ERROR_CANNOT_CONNECT_CLASSROOM);
        	}
        });
	}
	
	private void fireListCourse(DTOCourse[] courses){
		if(onCourseControllerAction != null){
			onCourseControllerAction.listCourse(courses);
		}
	}
	
	private void fireOnError(CourseControllerMessage lcm){
		if(onCourseControllerAction != null){
			onCourseControllerAction.onError(lcm);
		}
	}
	
	public interface OnCourseControllerAction {
		
		public void listCourse(DTOCourse[] courses);
		
		public void onError(CourseControllerMessage ccm);
		
	}

}
