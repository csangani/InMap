<%-- 
    Document   : add
    Created on : Oct 11, 2011, 9:08:29 PM
    Author     : Chirag Sangani
--%>
<%@page import="com.iitk.cimam.cookies.Cookies"%>
<%
    boolean flag = false;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
	for (Cookie cookie : cookies) {
	    if (Cookies.verifyCookie(cookie)) {
		flag = true;
%>
<!DOCTYPE html>
<html>

    <head>
	<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
	<title>CIMAM Map Admin Add Entry</title>
	<style type="text/css">
	    body {
		margin: 0;
		padding: 0;
		background: #fff;
		font-family: 'Segoe UI',Arial,sans-serif;
		color: #666;
		font-size: 16px;
	    }
	    #CIMAM-Map-Admin-Container {
		display: block;
		position: relative;
		width: 100%;
	    }
	    #CIMAM-Map-Admin-Wrapper {
		top: 35%;
		position: absolute;
		width: 100%;
	    }
	    #CIMAM-Map-Admin-Options {
		margin: auto;
		width: 300px;
		text-align: center;
	    }
	    input.CIMAM-Map-Admin-Inputbox {
		width: 50px;
		text-align: left;
		height: 14px;
	    }
	    span.CIMAM-Map-Admin-Login-Label-Container {
		display: block;
		float: left;
		margin: 0 20px 0 30px;
		width: 100px;
		text-align: right;
	    }
	    span.CIMAM-Map-Admin-Login-Input-Container {
		display: block;
		float: left;
	    }
	    p.CIMAM-Map-Admin-InputEntry {
		clear: both;
		margin: 5px 0 5px 0;
		padding: 15px;
		text-align: center;
	    }
	    #CIMAM-Map-Admin-Logout {
		text-align: right;
	    }
	    a {
		color: #aaa;
		text-decoration: none;
	    }
	    a:hover {
		color: #ccc;
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
	    <div id="CIMAM-Map-Admin-Logout">
		<a href="./">home</a> <a href="admin.jsp">admin</a> <a href="logout.jsp">logout</a></div>
	    <div id="CIMAM-Map-Admin-Wrapper">
		<div id="CIMAM-Map-Admin-Options">
		    <form action="AddEntry" enctype="multipart/form-data" method="post" name="CIMAMMapAddEntry">
			<h4>Add New Entry</h4>
			<%
			    if (request.getParameterValues("err") != null) {
			%><p style="color:red">
			    <%
				if (request.getParameterValues("err")[0].equals("1") || request.getParameterValues("err")[0].equals("2")) {
				    if (request.getParameterValues("err")[0].equals("1")) {
			    %>
			    Invalid SVG file.
			    <%} else {
			    %>
			    Invalid offset.
			    <%	}
			    } else {
			    %><%=request.getParameterValues("err")[0]%><%
				}
			    %>
			</p>	
			<%
			    }
			%>	
			<p class="CIMAM-Map-Admin-InputEntry"><span><label for="file">Upload 
				    SVG</label></span></p>
			<p class="CIMAM-Map-Admin-InputEntry"><span>
				<input name="file" type="file"></span></p>
			<h4>Offset</h4>
			<p class="CIMAM-Map-Admin-InputEntry">
			    <span class="CIMAM-Map-Admin-Login-Label-Container">
				<label for="x">Horizontal</label></span><span class="CIMAM-Map-Admin-Login-Input-Container"><input class="CIMAM-Map-Admin-Inputbox" name="x" type="number"></span></p>
			<p class="CIMAM-Map-Admin-InputEntry">
			    <span class="CIMAM-Map-Admin-Login-Label-Container">
				<label for="y">Vertical</label></span><span class="CIMAM-Map-Admin-Login-Input-Container"><input class="CIMAM-Map-Admin-Inputbox" name="y" type="number"></span></p>
			<p class="CIMAM-Map-Admin-InputEntry">
			    <input type="submit" value="Upload"></p>
		    </form>
		</div>
	    </div>
	</div>

    </body>

</html>
<%
		break;
	    }
	}
    }
    if (!flag) {
	response.sendRedirect("admin.jsp");
    }
%>