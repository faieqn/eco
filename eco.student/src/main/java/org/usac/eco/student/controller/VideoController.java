package org.usac.eco.student.controller;

import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.libdto.DTOUser;
import org.usac.eco.student.Configure;
import org.usac.eco.student.Session;

import com.zodiac.soa.Request;
import com.zodiac.soa.client.android.DynamicServiceHandler;
import com.zodiac.soa.client.android.DynamicServiceHandlerListener;

public class VideoController {
	
	private OnVideoControllerListener onVideoControllerListener;
	
	public VideoController(OnVideoControllerListener onVideoControllerListener){
		setOnVideoControllerListener(onVideoControllerListener);
	}
	
	public void connectCourse(DTOCourse dtoCourse){
		String clazz = "org.usac.eco.classroom.bl.CourseOpen";
        Class paramsConstructor[] = null;
        Object argsConstructor[] = null;
        String method = "connect";
        Class paramsMethod[] = {DTOCourse.class};
        Object argsMethod[] = {dtoCourse};
        Request request = new Request(clazz, paramsConstructor, argsConstructor, 
                method, paramsMethod, argsMethod);
        request.setTextModeException(true);
        final DynamicServiceHandler dsh = Session.getSession().getDynamicServiceHandler();
        dsh.removeAllDynamicServiceHandlerListener();
        dsh.run(request, new DynamicServiceHandlerListener() {

        	public void onResponse(Object arg0) {
        		// TODO Auto-generated method stub
        		boolean connected = Boolean.parseBoolean(arg0.toString());
        		if(!connected){
        			fireonDisconnected();
        		}
        	}

        	public void onException(Exception arg0) {
        		// TODO Auto-generated method stub
        		fireOnError(VideoControllerMessage.ERROR_CANNOT_CONNECT_CLASSROOM);
        	}
        });
	}
	
	public void checkStatusConnected(DTOCourse dtoCourse){
		String clazz = "org.usac.eco.classroom.bl.CourseOpen";
        Class paramsConstructor[] = null;
        Object argsConstructor[] = null;
        String method = "checkStatusConnected";
        Class paramsMethod[] = {DTOCourse.class};
        Object argsMethod[] = {dtoCourse};
        Request request = new Request(clazz, paramsConstructor, argsConstructor, 
                method, paramsMethod, argsMethod);
        request.setTextModeException(true);
        final DynamicServiceHandler dsh = Session.getSession().getDynamicServiceHandler();
        dsh.removeAllDynamicServiceHandlerListener();
        dsh.run(request, new DynamicServiceHandlerListener() {

        	public void onResponse(Object arg0) {
        		// TODO Auto-generated method stub
        		boolean connected = Boolean.parseBoolean(arg0.toString());
        		if(!connected){
        			fireonDisconnected();
        		}
        	}

        	public void onException(Exception arg0) {
        		// TODO Auto-generated method stub
        		fireOnError(VideoControllerMessage.ERROR_CANNOT_CONNECT_CLASSROOM);
        	}
        });
	}
	
	public void setOnVideoControllerListener(
			OnVideoControllerListener onVideoControllerListener) {
		this.onVideoControllerListener = onVideoControllerListener;
	}
	
	private void fireonDisconnected(){
		if(onVideoControllerListener != null){
			onVideoControllerListener.onDisconnected();
		}
	}
	
	private void fireOnError(VideoControllerMessage vcm){
		if(onVideoControllerListener != null){
			onVideoControllerListener.onError(vcm);
		}
	}

	public interface OnVideoControllerListener {
		
		public void onDisconnected();
		
		public void onError(VideoControllerMessage vcm);
		
	}
	
}
