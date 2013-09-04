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
public class EditAttrib extends HttpServlet {

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
			String attributeEncoding = "";
			String attributesEncoded = vector.getAttributes();
			if (attributesEncoded != null) {
			    if (!attributesEncoded.isEmpty()) {
				String[] attributes = attributesEncoded.split(";");
				for (int i = 0; i < attributes.length; i++) {
				    attributeEncoding += "&attrname" + i + "=" + attributes[i].split(":")[0] + "&attrval" + i + "=" + attributes[i].split(":")[1];
				}
			    }
			}
			String err = "";
			try {
			    err = request.getParameterValues("err")[0];
			} catch (NullPointerException ex) {
			}
			response.sendRedirect("editattrib.jsp?uid=" + URLEncoder.encode(request.getParameterValues("uid")[0], "UTF-8")
				+ attributeEncoding
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
	    ex.printStackTrace();
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
