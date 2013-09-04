<%-- 
    Document   : admin
    Created on : Oct 11, 2011, 7:28:49 PM
    Author     : Chirag Sangani
--%>

<%@page import="com.iitk.cimam.cookies.Cookies"%>
<%
    boolean flag = false;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
	for (Cookie cookie : cookies) {
	    if (Cookies.verifyCookie(cookie)) {
%>
<!DOCTYPE html>
<html>

    <head>
	<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
	<title>CIMAM Map Admin</title>
	<style type="text/css">
	    body {
		margin: 0;
		padding: 0;
		background: #fff;
		font-family: 'Segoe UI',Arial,sans-serif;
		color: #666;
		font-size:16px;
	    }
	    a {
		color:#aaa;
		text-decoration:none;
	    }
	    a:hover {
		color:#ccc;
	    }
	    #CIMAM-Map-Admin-Container {
		display: block;
		position: relative;
		width: 100%;
	    }
	    #CIMAM-Map-Admin-Wrapper {
		top: 40%;
		position: absolute;
		width: 100%;
	    }
	    #CIMAM-Map-Admin-Options {
		margin: auto;
		width: 300px;
		text-align: center;
	    }
	    input.CIMAM-Map-Admin-Inputbox {
		width: 150px;
		text-align: left;
		height:14px
	    }
	    #CIMAM-Map-Admin-Login-Label-Container {
		display: block;
		float: left;
		margin: 0 20px 0 30px;
		width: 70px;
	    }
	    #CIMAM-Map-Admin-Login-Input-Container {
		display: block;
		float: left;
	    }
	    #CIMAM-Map-Admin-Logout {
		text-align:right;
	    }
	</style>
	<script src="CIMAMMapLib1.js" type="text/javascript"></script>
	<script type="text/javascript">
	    $(window).load(function() {
		$('#CIMAM-Map-Admin-Container').height($(window).height());
	    });

	    $(window).resize(function() {
		$('#CIMAM-Map-Admin-Container').height($(window).height());
	    });

	</script>
    </head>

    <body>

	<div id="CIMAM-Map-Admin-Container">
	    <div id="CIMAM-Map-Admin-Logout"><a href="./">home</a> <a href="logout.jsp">logout</a></div>
	    <div id="CIMAM-Map-Admin-Wrapper">
		<div id="CIMAM-Map-Admin-Options">
		    <h4>Administrator Options</h4>
		    <p><a href="add.jsp">Add new entry</a></p>
		    <p><a href="edit.jsp">Edit entries</a></p>
		</div>
	    </div>
	</div>

    </body>

</html>

<%
	    flag = true;
	    break;
	}
    }
    if (!flag) {
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
	<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
	<title>CIMAM Map Admin Login</title>
	<style type="text/css">
	    body {
		margin: 0;
		padding: 0;
		background: #333;
		font-family: Calibri,'Trebuchet MS',sans-serif;
		color: #999;
		font-size:16px;
	    }
	    a {
		color:#aaa;
		text-decoration:none;
	    }
	    a:hover {
		color:#ccc;
	    }
	    #CIMAM-Map-Admin-Container {
		display: block;
		position: relative;
		width: 100%;
	    }
	    #CIMAM-Map-Admin-Wrapper {
		top: 40%;
		position: absolute;
		width: 100%;
	    }
	    #CIMAM-Map-Admin-Login {
		margin: auto;
		width: 300px;
		text-align: center;
	    }
	    input.CIMAM-Map-Admin-Inputbox {
		width: 150px;
		text-align: left;
		height:14px
	    }
	    #CIMAM-Map-Admin-Login-Label-Container {
		display: block;
		float: left;
		margin: 0 20px 0 30px;
		width: 70px;
	    }
	    #CIMAM-Map-Admin-Login-Input-Container {
		display: block;
		float: left;
	    }
	    #CIMAM-Map-Admin-Logout {
		text-align:right;
	    }
	</style>
	<script src="CIMAMMapLib1.js" type="text/javascript"></script>
	<script type="text/javascript">
	    $(window).load(function() {
		$('#CIMAM-Map-Admin-Container').height($(window).height());
	    });

	    $(window).resize(function() {
		$('#CIMAM-Map-Admin-Container').height($(window).height());
	    });

	</script>
    </head>

    <body>

	<div id="CIMAM-Map-Admin-Container"><div id="CIMAM-Map-Admin-Logout"><a href="./">home</a></div>
	    <div id="CIMAM-Map-Admin-Wrapper">
		<div id="CIMAM-Map-Admin-Login">
		    <h4>Administrator Login</h4>
		    <%
			if (request.getParameterValues("err") != null) {
		    %><p style="color:red;">
			<%
			    if (request.getParameterValues("err")[0].equals("0") || request.getParameterValues("err")[0].equals("1")) {
			%>
			Invalid username and/or password.
			<%} else {
			%>
			Internal server error, please try again later.
			<%    }
			%>
		    </p>	
		    <%
			}
		    %>	
		    <form action="AdminAuth" method="post" name="CIMAMMapLogin">
			<div id="CIMAM-Map-Admin-Login-Label-Container">
			    <p><label for="username">Username</label></p>
			    <p><label for="password">Password</label></p>
			</div>
			<div id="CIMAM-Map-Admin-Login-Input-Container">
			    <p>
				<input class="CIMAM-Map-Admin-Inputbox" maxlength="20" name="username" type="text"></p>
			    <p>
				<input class="CIMAM-Map-Admin-Inputbox" maxlength="20" name="password" type="password"></p>
			</div>
			<p><input type="submit" value="Login"></p>
		    </form>
		</div>
	    </div>
	</div>

    </body>

</html>
<%
	}
    }
%>