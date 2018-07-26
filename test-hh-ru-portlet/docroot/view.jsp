<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%--<%@ taglib uri="/WEB-INF/tld/liferay-portlet.tld" prefix="portlet" %>--%>
<portlet:defineObjects />

<%String textBox = renderRequest.getParameter("textBox");%>

<i><%=textBox%></i>



