<%@page import="com.iitk.cimam.cookies.Cookies"%>
<%
    boolean flag = false;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
	for (Cookie cookie : cookies) {
	    if (Cookies.verifyCookie(cookie)) {
		flag = true;
%>
<html>

    <head>
	<style type="text/css">
	    body {
		margin: 0;
		padding: 0;
		background: #fff;
		font-family: 'Segoe UI',Arial,sans-serif;
		color: #666;
		font-size: 16px;
		text-align:center;
	    }
	    #CIMAM-Map-Admin-Container {
		display: block;
		position: relative;
		width: 100%;
	    }
	    #CIMAM-Map-Admin-Wrapper {
		top: 0;
		position: absolute;
		width: 100%;
	    }
	    #CIMAM-Map-Admin-Login {
		margin: auto;
		width: 500px;
		text-align: center;
	    }
	    #CIMAM-Map-Admin-Edit-Iframe {
		margin: auto;
		width: 800px;
		text-align: center;
	    }
	    input.CIMAM-Map-Admin-Inputbox {
		width: 150px;
		text-align: left;
	    }
	    span.CIMAM-Map-Admin-Login-Label-Container {
		display: block;
		float: left;
		margin: 0 20px 0 30px;
		width: 150px;
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
	    p {
		text-align:center;
	    }
	</style>
    </head>

    <body>
	<div style="width:500px; margin:auto">
	    <%
		if (request.getParameterValues("uid") == null) {
	    %>
	    <p>To edit an element, select it to open the description and click on the "+" button.</p>
	    <%	    } else {
	    %>
	    <%
		if (request.getParameterValues("err") != null) {
	    %><p style="color:red;">
		<%
		    if (request.getParameterValues("err")[0].equals("0")) {
		%>
		Bad Request.
		<%} else if (request.getParameterValues("err")[0].equals("1")) {
		%>
		Name must be alphanumeric.
		<%} else if (request.getParameterValues("err")[0].equals("2")) {
		%>
		Tag List must be a comma-separated list of alphanumeric values without spaces.
		<%} else if (request.getParameterValues("err")[0].equals("3")) {
		%>
		Horizontal Offset must be a real number.
		<%} else if (request.getParameterValues("err")[0].equals("4")) {
		%>
		Vertical Offset must be a real number.
		<%} else if (request.getParameterValues("err")[0].equals("5")) {
		%>
		Floor must be an integer.
		<%} else if (request.getParameterValues("err")[0].equals("6")) {
		%>
		Highlight Colour must be a 6-digit hexadecimal number.
		<%} else if (request.getParameterValues("err")[0].equals("7")) {
		%>
		UID must be an integer.
		<%} else if (request.getParameterValues("err")[0].equals("8")) {
		%>
		Entity not found.
		<%} else if (request.getParameterValues("err")[0].equals("9")) {
		%>
		Internal server error.
		<%} else {
		%>
		Unknown error.
		<%		    }
		%>
	    </p>	
	    <%
		}
		String name = "", tagList = "", x = "", y = "", z = "", highlight = "", description = "", uid = "";
		try {
		    name = request.getParameterValues("name")[0];
		    tagList = request.getParameterValues("tagList")[0];
		    x = request.getParameterValues("x")[0];
		    y = request.getParameterValues("y")[0];
		    z = request.getParameterValues("z")[0];
		    highlight = request.getParameterValues("highlight")[0];
		    description = request.getParameterValues("description")[0];
		    uid = request.getParameterValues("uid")[0];
		} catch (NullPointerException ex) {
		    response.sendRedirect("editform.jsp");
		}
	    %>	
	    <p>UID: #<%=uid%></p>

	    <form action="SaveEntry" method="post" name="CIMAMMapEditEntry" id="CIMAM-Map-Admin-EditForm">
		<p class="CIMAM-Map-Admin-InputEntry">
		    <span class="CIMAM-Map-Admin-Login-Label-Container"><label for="name">Name</label></span>
		    <span class="CIMAM-Map-Admin-Login-Input-Container">
			<input class="CIMAM-Map-Admin-Inputbox" name="name" type="text" value="<%=name%>"></span> </p>
		<p class="CIMAM-Map-Admin-InputEntry">
		    <span class="CIMAM-Map-Admin-Login-Label-Container"><label for="tagList">Tag 
			    List (comma separated values)</label></span>
		    <span class="CIMAM-Map-Admin-Login-Input-Container">
			<textarea class="CIMAM-Map-Admin-Inputbox" name="tagList"><%=tagList%></textarea></span>
		</p>
		<p class="CIMAM-Map-Admin-InputEntry">
		    <span class="CIMAM-Map-Admin-Login-Label-Container"><label for="x">Horizontal 
			    Offset</label></span> <span class="CIMAM-Map-Admin-Login-Input-Container">
			<input class="CIMAM-Map-Admin-Inputbox" name="x" type="text" value="<%=x%>"></span> </p>
		<p class="CIMAM-Map-Admin-InputEntry">
		    <span class="CIMAM-Map-Admin-Login-Label-Container"><label for="y">Vertical 
			    Offset</label></span> <span class="CIMAM-Map-Admin-Login-Input-Container">
			<input class="CIMAM-Map-Admin-Inputbox" name="y" type="text" value="<%=y%>"></span> </p>
		<p class="CIMAM-Map-Admin-InputEntry">
		    <span class="CIMAM-Map-Admin-Login-Label-Container"><label for="z">Floor</label></span>
		    <span class="CIMAM-Map-Admin-Login-Input-Container">
			<input class="CIMAM-Map-Admin-Inputbox" maxlength="1" name="z" type="text" value="<%=z%>"></span>
		</p>
		<p class="CIMAM-Map-Admin-InputEntry">
		    <span class="CIMAM-Map-Admin-Login-Label-Container"><label for="highlight">Highlight 
			    Colour</label></span> <span class="CIMAM-Map-Admin-Login-Input-Container">
			<input class="CIMAM-Map-Admin-Inputbox" maxlength="6" name="highlight" type="text" value="<%=highlight%>"></span>
		</p>
		<p class="CIMAM-Map-Admin-InputEntry">
		    <span class="CIMAM-Map-Admin-Login-Label-Container">
			<label for="description">Description</label></span>
		    <span class="CIMAM-Map-Admin-Login-Input-Container">
			<input class="CIMAM-Map-Admin-Inputbox" name="description" type="text" value="<%=description%>"></span>
		</p>
		<p><a href="EditAttrib?uid=<%=uid%>">Edit Attributes</a></p>
		<p><input type="submit" value="Save"><input type="reset" onclick="window.navigate('EditForm?uid=<%=uid%>')"><input type="button" onclick="if(confirm('Are you sure you want to delete this entry?'))document.location.href=('DeleteEntry?uid=<%=uid%>');" value="Delete"</p>
		<input name="UID" type="hidden" value="<%=uid%>">
	    </form></div>
	    <% }%>
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