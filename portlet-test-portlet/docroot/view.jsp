<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>--%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<portlet:defineObjects/>
<%@ page import="java.util.*, java.text.*" %>

<%!
    String getFormattedDate()
    {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        return sdf.format(new Date());
    }
%>

<%String textBox = renderRequest.getParameter("textBox");
    if (textBox == null)
        textBox = "";
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Добро пожаловать, JSP!</title>
</head>
<body>
<h1>Добро пожаловать!</h1>
<i>Сегодня <%= getFormattedDate() %></i>
</body>
</html>


<FORM name="<portlet:namespace/>caseform" action="<portlet:actionURL/>">
    <INPUT type="text" name="textBox" size="20" value="<%=textBox%>">

    <INPUT type="submit" name="<portlet:namespace/>submitCase">
</FORM>