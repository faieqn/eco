<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ECO Classroom</title>
        
        <script type="text/javascript" src="js/plugins/jquery-1.8.3.js"></script>
        <script type="text/javascript" src="js/plugins/jquery.jalert.js"></script>
<%
    String jsfile = request.getParameter("jsfile");
    if(jsfile != null){
        out.println("<script type=\"text/javascript\" src=\"js/" + jsfile + "\"></script>");
    }
%>
    </head>
    <body>