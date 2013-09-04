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
public class SaveEntry extends HttpServlet {

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
		    String name, tagList, X, Y, Z, highlight, description, uid;
		    try {
			name = request.getParameterValues("name")[0];
			tagList = request.getParameterValues("tagList")[0];
			X = request.getParameterValues("x")[0];
			Y = request.getParameterValues("y")[0];
			Z = request.getParameterValues("z")[0];
			highlight = request.getParameterValues("highlight")[0];
			description = request.getParameterValues("description")[0];
			uid = request.getParameterValues("UID")[0];
		    } catch (NullPointerException ex) {
			response.sendRedirect("editform.jsp?err=0");
			break;
		    }

		    //	Name must be alphanumeric

		    if (!name.matches("[a-zA-Z0-9 ]*")) {
			response.sendRedirect("EditForm?err=1&uid=" + URLEncoder.encode(uid, "UTF-8"));
			break;
		    }

		    //	tagList must be an alphanumeric, comma-separated list without spaces
		    
		    boolean tagsListFlag = true;
		    String[] tags = tagList.split(",");
		    for (String tag : tags) {
			if (!tag.matches("[a-zA-Z0-9]*")) {
			    response.sendRedirect("EditForm?err=2&uid=" + URLEncoder.encode(uid, "UTF-8"));
			    tagsListFlag = false;
			    break;
			}
		    }
		    if(!tagsListFlag) {
			break;
		    }

		    double x, y;
		    int z;

		    //	x and y must be double, z must be int

		    try {
			x = Double.parseDouble(X);
		    } catch (NumberFormatException ex) {
			response.sendRedirect("EditForm?err=3&uid=" + URLEncoder.encode(uid, "UTF-8"));
			break;
		    }
		    try {

			y = Double.parseDouble(Y);

		    } catch (NumberFormatException ex) {
			response.sendRedirect("EditForm?err=4&uid=" + URLEncoder.encode(uid, "UTF-8"));
			break;
		    }
		    try {
			z = Integer.parseInt(Z);
		    } catch (NumberFormatException ex) {
			response.sendRedirect("EditForm?err=5&uid=" + URLEncoder.encode(uid, "UTF-8"));
			break;
		    }

		    //	Highlight must be 6-digit hexadecimal number

		    if (highlight.length() != 6) {
			response.sendRedirect("EditForm?err=6&uid=" + URLEncoder.encode(uid, "UTF-8"));
			break;
		    }
		    try {
			Integer.parseInt(highlight, 16);
		    } catch (NumberFormatException ex) {
			response.sendRedirect("EditForm?err=6&uid=" + URLEncoder.encode(uid, "UTF-8"));
			break;
		    }

		    //	UID must be a valid number

		    int UID;
		    try {
			UID = Integer.parseInt(uid);
		    } catch (NumberFormatException ex) {
			response.sendRedirect("EditForm?err=7&uid=" + URLEncoder.encode(uid, "UTF-8"));
			break;
		    }

		    //	Attempt to get vector from database

		    Vectors vector;

		    try {
			vector = VF.find(UID);
		    } catch (Exception ex) {
			response.sendRedirect("EditForm?err=9&uid=" + URLEncoder.encode(uid, "UTF-8"));
			break;
		    }

		    if (vector == null) {
			response.sendRedirect("EditForm?err=8&uid=" + URLEncoder.encode(uid, "UTF-8"));
			break;
		    }

		    //	Update vector data

		    vector.setName(name);
		    vector.setTagList(tagList.toLowerCase(Locale.ENGLISH));
		    vector.setX(x);
		    vector.setY(y);
		    vector.setZ(z);
		    vector.setHighlight(highlight);
		    vector.setDescription(description);

		    //	Store vector data

		    try {
			VF.edit(vector);
		    } catch (Exception ex) {
			response.sendRedirect("EditForm?err=9&uid=" + URLEncoder.encode(uid, "UTF-8"));
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
	    response.sendRedirect("EditForm?err=9");
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
