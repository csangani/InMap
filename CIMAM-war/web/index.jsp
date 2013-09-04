<%-- 
    Document   : index
    Created on : Sep 26, 2011, 3:38:21 PM
    Author     : Chirag Sangani
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CIMAM Map</title>
	<style type="text/css">
	    body {
		margin: 0;
		padding: 0;
		background: #fff;
		font-family: 'Segoe UI',Arial,sans-serif;
		color: #666;
		font-size: 16px;
		text-align: center;
	    }
	</style>
    </head>
    <body>
        <h1>CIMAM Map</h1>
	<form method="POST" action="GUIInterface" name="CIMAMMapGUIForm" target="CIMAMMapGUIFrame">
	    <input type="text" name="q" style="width:300px"></input>
	    <input type="submit" value="Search"></input>
	</form>
	<iframe src="GUIInterface" name="CIMAMMapGUIFrame" width="1024" height="768" scrolling="no" style="border:none;margin-top:20px;">
    </body>
</html>
