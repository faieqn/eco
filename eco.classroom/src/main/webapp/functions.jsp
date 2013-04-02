<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.usac.eco.classroom.bl.Cycle"%>
<%@page import="java.util.Date"%>
<%@page import="org.usac.eco.classroom.ClassroomFunction"%>
<%@page import="java.util.List"%>
<%@page import="org.usac.eco.libdto.DTOCourse"%>
<%@page import="org.usac.eco.classroom.bl.CourseOpen"%>
<%@page import="org.usac.eco.classroom.EcoLoader"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.usac.eco.libdto.DTOUserProfile"%>
<%@page import="org.usac.eco.libdto.DTOUser"%>
<%@page import="com.zodiac.security.Session"%>
<%@page import="org.usac.eco.classroom.ClassroomFunctionName"%>
<%
EcoLoader.load();
String functionString = request.getParameter("function");

if(functionString == null){
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("error", "Function not found.");
    out.println(jsonObject);
    out.flush();
    return;
}
ClassroomFunctionName function = ClassroomFunctionName.valueOf(functionString);
String result = "";
switch(function){
    case LOGIN:
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        result = ClassroomFunction.login(username, password, request).toJSONString();
        break;
    case LIST_ALL_COURSES_OPEN:
        result = ClassroomFunction.list_all_courses_open(session).toJSONString();
        break;
    case DISABLE_COURSE_OPEN:
        String course_id = request.getParameter("course_id");
        result = ClassroomFunction.disable_course_open(Integer.parseInt(course_id), session).toJSONString();
        break;
    case LIST_ALL_USERS:
        result = ClassroomFunction.list_all_users(session).toJSONString();
        break;
    case LIST_ALL_COURSES_CYCLES_SECTIONS_PERIODS:
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonCourses = ClassroomFunction.list_all_courses(session);
        JSONObject jsonCycles = ClassroomFunction.list_all_cycles(session);
        JSONObject jsonSections = ClassroomFunction.list_all_courses_sections(session);
        JSONObject jsonPeriods = ClassroomFunction.list_all_periods(session);
        jsonObject.put("courses", jsonCourses.get("courses"));
        jsonObject.put("cycles", jsonCycles.get("cycles"));
        jsonObject.put("sections", jsonSections.get("sections"));
        jsonObject.put("periods", jsonPeriods.get("periods"));
        
        JSONArray jsonYears = new JSONArray();
        Cycle cyle = new Cycle((Session)session.getAttribute("session"));
        int[] years = cyle.getSampleCycleYears();
        for(int i = 0; i < years.length; i++){
            jsonYears.add(years[i]);
        }
        jsonObject.put("years", jsonYears);
        
        result = jsonObject.toJSONString();
        break;
}
out.println(result);
out.flush();

%>