<%-- 
    Document   : logout
    Created on : Oct 11, 2011, 9:08:29 PM
    Author     : Chirag Sangani
--%>

<%@page import="com.iitk.cimam.cookies.Cookies"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
Cookie cookie = new Cookie(Cookies.getAuthName(), "");
cookie.setMaxAge(0);
response.addCookie(cookie);
response.sendRedirect("admin.jsp");
%>