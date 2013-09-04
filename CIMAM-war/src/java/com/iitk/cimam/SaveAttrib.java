/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitk.cimam;

import com.iitk.cimam.cookies.Cookies;
import com.iitk.cimam.data.Vectors;
import com.iitk.cimam.data.VectorsFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Locale;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Chirag Sangani
 */
public class SaveAttrib extends HttpServlet {

    @EJB
    VectorsFacade VF;

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
	    boolean flag = false;
	    Cookie[] cookies = request.getCookies();
	    for (Cookie cookie : cookies) {
		if (Cookies.verifyCookie(cookie)) {
		    flag = true;
		    String uid;
		    try {
			uid = request.getParameterValues("UID")[0];
		    } catch (NullPointerException ex) {
			response.sendRedirect("EditAttrib.jsp?err=0");
			break;
		    }

		    String[] attrname = new String[10];
		    String[] attrval = new String[10];
		    for (int i = 0; i < 10; i++) {
			if (request.getParameterValues("attrname" + i) != null && request.getParameterValues("attrval" + i) != null) {
			    attrname[i] = request.getParameterValues("attrname" + i)[0];
			    attrval[i] = request.getParameterValues("attrval" + i)[0];
			} else {
			    attrname[i] = "";
			    attrval[i] = "";
			}
		    }

		    //	UID must be a valid number

		    int UID;
		    try {
			UID = Integer.parseInt(uid);
		    } catch (NumberFormatException ex) {
			response.sendRedirect("EditAttrib?err=7&uid=" + URLEncoder.encode(uid, "UTF-8"));
			break;
		    }

		    //	Attribute names and values must be alphanumeric

		    for (int i = 0; i < 10; i++) {
			if (!attrname[i].matches("[a-zA-Z0-9 ,\\.]*") || !attrval[i].matches("[a-zA-Z0-9 ,\\.]*")) {
			response.sendRedirect("EditAttrib?err=5&uid=" + URLEncoder.encode(uid, "UTF-8"));
			break;
			}
		    }

		    //	Construct attribute string
		    
		    String attributes = "";
		    for (int i = 0; i < 10; i++) {
			if(!attrname[i].isEmpty() && !attrval[i].isEmpty()) {
			    attributes += attrname[i] + ":" + attrval[i] + ";";
			}
		    }
		    if(attributes.length() > 0) {
			attributes = attributes.substring(0, attributes.length() - 1);
		    }
		    
		    //	Attempt to get vector from database

		    Vectors vector;

		    try {
			vector = VF.find(UID);
		    } catch (Exception ex) {
			response.sendRedirect("EditAttrib?err=9&uid=" + URLEncoder.encode(uid, "UTF-8"));
			break;
		    }

		    if (vector == null) {
			response.sendRedirect("EditAttrib?err=8&uid=" + URLEncoder.encode(uid, "UTF-8"));
			break;
		    }

		    //	Update vector data

		    vector.setAttributes(attributes);

		    //	Store vector data

		    try {
			VF.edit(vector);
		    } catch (Exception ex) {
			response.sendRedirect("EditAttrib?err=9&uid=" + URLEncoder.encode(uid, "UTF-8"));
			break;
		    }

		    //	Send to refresh page

		    response.sendRedirect("editrefresh.jsp");
		    break;
		}
	    }
	    if (!flag) {
		response.sendRedirect("adminrefresh.jsp");
	    }
	} catch (Exception ex) {
	    response.sendRedirect("EditAttrib?err=9");
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
