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
public class EditForm extends HttpServlet {
    
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
		    if (request.getParameterValues("uid").length > 0) {
			int UID = Integer.parseInt(request.getParameterValues("uid")[0]);
			Vectors vector = VF.find(UID);
			String Name = vector.getName();
			String tagList = vector.getTagList();
			String X = vector.getX().toString();
			String Y = vector.getY().toString();
			String Z = vector.getZ().toString();
			String Highlight = vector.getHighlight();
			String Description = vector.getDescription();
			String err = "";
			try {
			    err = request.getParameterValues("err")[0];
			} catch (NullPointerException ex) {
			}
			response.sendRedirect("editform.jsp?name=" + URLEncoder.encode(Name, "UTF-8")
				+ "&tagList=" + URLEncoder.encode(tagList, "UTF-8")
				+ "&x=" + URLEncoder.encode(X, "UTF-8")
				+ "&y=" + URLEncoder.encode(Y, "UTF-8")
				+ "&z=" + URLEncoder.encode(Z, "UTF-8")
				+ "&highlight=" + URLEncoder.encode(Highlight, "UTF-8")
				+ "&description=" + URLEncoder.encode(Description, "UTF-8")
				+ "&uid=" + URLEncoder.encode(request.getParameterValues("uid")[0], "UTF-8")
				+ ((!err.isEmpty()) ? "&err=" + URLEncoder.encode(err, "UTF-8") : ""));
		    } else {
			response.sendRedirect("editrefresh.jsp");
		    }
		    break;
		}
	    }
	    if (!flag) {
		response.sendRedirect("adminrefresh.jsp");
	    }
	} catch (Exception ex) {
	    response.sendRedirect("editform.jsp?err=1");
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
