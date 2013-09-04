<%-- 
    Document   : editrefresh
    Created on : Oct 16, 2011, 5:44:00 PM
    Author     : Chirag Sangani
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Redirecting...</title>
	<style type="text/css">
	    body {
		margin: 0;
		padding: 0;
		background: #fff;
		font-family: 'Segoe UI',Arial,sans-serif;
		color: #666;
		font-size: 16px;
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
	<script type="text/javascript">
	  window.onload = function () {
	      top.location.href = "edit.jsp";
	  }  
	</script>    
    </head>
    <body>
        <p>Redirecting... please wait.</p>
	<p>If this page does not refresh, click <a href="edit.jsp" target="_top">here</a>.</p>
    </body>
</html>
