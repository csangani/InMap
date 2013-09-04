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
	<title>CIMAM Map Admin Edit Entry</title>
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
		height: 600px;
	    }
	    input.CIMAM-Map-Admin-Inputbox {
		width: 150px;
		text-align: left;
		height: 14px;
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
	</style>
	<script src="CIMAMMapLib1.js" type="text/javascript"></script>
        <script src="CIMAMMapLib2.js" type="text/javascript"></script>
	<script type="text/javascript">
	    $(window).load(function() {
		$('#CIMAM-Map-Admin-Edit-Iframe').load("XMLInterface",{ q: "<request><format>TagsAndHighlighting</format><query><tagList><tag><name>untagged</name><highlight>FF0000</highlight></tag></tagList></query><interface><size><width>800</width><height>600</height></size><edit>true</edit></interface></request>" });
	    });
	</script>
    </head>

    <body>

	<div id="CIMAM-Map-Admin-Container">
	    <div id="CIMAM-Map-Admin-Logout">
		<a href="./">home</a> <a href="admin.jsp">admin</a> <a href="logout.jsp">logout</a></div>
	    <div id="CIMAM-Map-Admin-Wrapper">
		<div id="CIMAM-Map-Admin-Edit-Iframe">
		</div>
		<div id="CIMAM-Map-Admin-Login">
		    <h4>Edit Entry</h4>
		    <iframe height="600" name="CIMAMMapEditFormFrame" scrolling="no" src="editform.jsp" style="border: none;" width="500">
		    </iframe></div>
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