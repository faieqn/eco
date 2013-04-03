/**
 * 
 */
package org.usac.eco.student.controller;

import java.util.List;

import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.libdto.DTOUser;
import org.usac.eco.student.Configure;
import org.usac.eco.student.Session;

import com.zodiac.soa.Request;
import com.zodiac.soa.client.android.DynamicServiceHandler;
import com.zodiac.soa.client.android.DynamicServiceHandlerListener;

/**
 * @author Brian Estrada <brianseg014@gmail.com>
 *
 */
public class SubscriberController {
	
	private OnSubscriberControllerAction onSubscriberControllerAction;
	
	public SubscriberController(OnSubscriberControllerAction onSubscriberControllerAction){
		setOnSubscriberControllerListener(onSubscriberControllerAction);
	}

	public void setOnSubscriberControllerListener(OnSubscriberControllerAction onSubscriberControllerAction){
		if(onSubscriberControllerAction != null){
			this.onSubscriberControllerAction = onSubscriberControllerAction;
		}
	}
	
	public void listCourses(DTOCourse course) {
		String clazz = "org.usac.eco.classroom.bl.CourseOpen";
        Class paramsConstructor[] = null;
        Object argsConstructor[] = null;
        String method = "searchCourseOpen";
        Class paramsMethod[] = {DTOCourse.class};
        Object argsMethod[] = {course};
        Request request = new Request(clazz, paramsConstructor, argsConstructor, 
                method, paramsMethod, argsMethod);
        request.setNoException(true);
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
        		fireOnError(SubscriberControllerMessage.ERROR_CANNOT_CONNECT_CLASSROOM);
        	}
        });
	}
	
	public void subscribe(DTOCourse course) {
		String clazz = "org.usac.eco.classroom.bl.CourseOpen";
        Class paramsConstructor[] = null;
        Object argsConstructor[] = null;
        String method = "subscribe";
        Class paramsMethod[] = {DTOUser.class, DTOCourse.class};
        Object argsMethod[] = {Session.getSession().getUser(), course};
        Request request = new Request(clazz, paramsConstructor, argsConstructor, 
                method, paramsMethod, argsMethod);
        request.setNoException(true);
        DynamicServiceHandler dsh = Session.getSession().getDynamicServiceHandler();
        dsh.removeAllDynamicServiceHandlerListener();
        dsh.run(request, new DynamicServiceHandlerListener() {

        	public void onResponse(Object arg0) {
        		// TODO Auto-generated method stub
        		if((Boolean)arg0 == true){
        			fireSubscribed();
        		}
        	}

        	public void onException(Exception arg0) {
        		// TODO Auto-generated method stub
        		fireOnError(SubscriberControllerMessage.ERROR_CANNOT_CONNECT_CLASSROOM);
        	}
        });
	}
	
	private void fireListCourse(DTOCourse[] courses){
		if(onSubscriberControllerAction != null){
			onSubscriberControllerAction.listCourse(courses);
		}
	}
	
	private void fireSubscribed(){
		if(onSubscriberControllerAction != null){
			onSubscriberControllerAction.subscribed();
		}
	}
	
	private void fireOnError(SubscriberControllerMessage lcm){
		if(onSubscriberControllerAction != null){
			onSubscriberControllerAction.onError(lcm);
		}
	}
	
	public interface OnSubscriberControllerAction {
		
		public void listCourse(DTOCourse[] courses);
		
		public void subscribed();
		
		public void onError(SubscriberControllerMessage scm);
		
	}
	
}
