<%@page import="com.zodiac.soa.server.MessageContextImpl"%>
<%@page import="com.zodiac.soa.server.MessageContext"%>
<%@page import="org.usac.eco.libdto.DTOCycle"%>
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

MessageContext messageContext = 
        new MessageContext(new MessageContextImpl(application, request, response));
ClassroomFunction.registerMessageContext(messageContext);

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
    case LOGIN:{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        result = ClassroomFunction.login(username, password).toJSONString();
    }
    break;
    case LIST_ALL_COURSES_OPEN:{
        DTOCycle dtoCycle = ClassroomFunction.getCurrentCycle();
        result = ClassroomFunction.list_all_courses_open(dtoCycle).toJSONString();
    }
    break;
    case DISABLE_COURSE_OPEN:{
        String course_id = request.getParameter("course_id");
        result = ClassroomFunction.disable_course_open(Integer.parseInt(course_id)).toJSONString();
    }
    break;
    case LIST_ALL_USERS:{
        result = ClassroomFunction.list_all_users().toJSONString();
    }
    break;
    case LIST_ALL_COURSES_CYCLES_SECTIONS:{
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonCourses = ClassroomFunction.list_all_courses();
        JSONObject jsonCycles = ClassroomFunction.list_all_cycles();
        JSONObject jsonSections = ClassroomFunction.list_all_courses_sections();
        jsonObject.put("courses", jsonCourses.get("courses"));
        jsonObject.put("cycles", jsonCycles.get("cycles"));
        jsonObject.put("sections", jsonSections.get("sections"));

        JSONArray jsonYears = new JSONArray();
        Cycle cyle = new Cycle();
        int[] years = cyle.getSampleCycleYears();
        for(int i = 0; i < years.length; i++){
            jsonYears.add(years[i]);
        }
        jsonObject.put("years", jsonYears);

        result = jsonObject.toJSONString();
    }
    break;
    case LIST_ALL_CYCLES:{
        DTOCycle currentCycle = ClassroomFunction.getCurrentCycle();

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonCycles = ClassroomFunction.list_all_cycles();
        jsonObject.put("cycles", jsonCycles.get("cycles"));
        jsonObject.put("current_cycle", currentCycle.getCycleId());
        result = jsonObject.toJSONString();
    }
    break;
    case LIST_ALL_COURSES_OPEN_BY_CYCLE:{
        String cycleId = request.getParameter("cycle");
        DTOCycle dtoCycle = new DTOCycle(
                Integer.valueOf(cycleId),
                null, 
                null, 
                null);
        result = ClassroomFunction.list_all_courses_open(dtoCycle).toJSONString();
    }
    break;
}

out.println(result);
out.flush();

%>