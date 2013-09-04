/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitk.cimam;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Chirag Sangani
 */
public class GUIInterface extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("text/html;charset=UTF-8");
	PrintWriter out = response.getWriter();
	try {
	    StringBuilder tagList = new StringBuilder("");
	    String[] queryList = request.getParameterValues("q");
	    if (queryList != null) {
		if (queryList.length == 1) {
		    String query = queryList[0];
		    String[] tags = query.split(" ");
		    for (String tag : tags) {
			String[] parameters = tag.split(":");
			if (parameters.length == 1) {
			    if (parameters[0].matches("[a-zA-Z0-9]*") && !parameters[0].isEmpty()) {
				tagList.append("<tag><name>").append(parameters[0].toLowerCase(Locale.ENGLISH)).append("</name><highlight>005566</highlight></tag>");
			    }
			}
			if (parameters.length == 2) {
			    if (parameters[0].matches("[a-zA-Z0-9]*") && parameters[1].matches("[a-fA-F0-9][a-fA-F0-9][a-fA-F0-9][a-fA-F0-9][a-fA-F0-9][a-fA-F0-9]") && !parameters[0].isEmpty()) {
				tagList.append("<tag><name>").append(parameters[0].toLowerCase(Locale.ENGLISH)).append("</name><highlight>").append(parameters[1]).append("</highlight></tag>");
			    }
			}
		    }
		}
	    }
	    String XMLQuery =
		    "<request><format>TagsAndHighlighting</format><query><tagList>" + tagList.toString() + "</tagList></query><interface><size><width>1024</width><height>768</height></size><edit>false</edit></interface></request>";
	    out.println(
		    "<html>"
		    + "	<head>"
		    + "	    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
		    + "	    <title>Redirecting...</title>"
		    + "	    <style type=\"text/css\">"
		    + "		body {margin: 0;padding: 0;background: #fff;font-family: 'Segoe UI',Arial,sans-serif;color:#666;font-size: 16px;}"
		    + "		a {color: #aaa;text-decoration: none;}"
		    + "		a:hover {color: #ccc;}"
		    + "		p {text-align:center;}"
		    + "	    </style>"
		    + "	    <script type=\"text/javascript\">"
		    + "		window.onload = function () {document.forms[\"CIMAMMapGUIRedirectForm\"].submit()}"
		    + "	    </script>"
		    + "	</head>"
		    + "	<body>"
		    + "	    <p>Redirecting... please wait.</p>"
		    + "	    <p>"
		    + "		If this page does not refresh, click <a onclick=\"document.forms[\'CIMAMMapGUIRedirectForm\'].submit()\" style=\"cursor:pointer\">here</a>."
		    + "	    </p>"
		    + "	    <form name=\"CIMAMMapGUIRedirectForm\" action=\"XMLInterface\" method=\"POST\">"
		    + "		<input type=\"hidden\" name=\"q\" value=\"" + XMLQuery + "\">"
		    + "	    </form>"
		    + "	</body>"
		    + "</html>");
	} finally {
	    out.close();
	}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "Short description";
    }// </editor-fold>
}
