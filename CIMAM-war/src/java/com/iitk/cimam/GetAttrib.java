/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitk.cimam;

import com.iitk.cimam.data.Vectors;
import com.iitk.cimam.data.VectorsFacade;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Chirag Sangani
 */
public class GetAttrib extends HttpServlet {

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
	    if (request.getParameterValues("uid") == null) {
		response.sendError(400, "Missing UID");
	    } else {
		Vectors vector = VF.find(Integer.parseInt(request.getParameterValues("uid")[0]));
		if (vector == null) {
		    response.sendError(400, "Invalid UID");
		} else if (request.getParameterValues("attrname") == null) {
		    String attributes = vector.getAttributes();
		    for (String attribute : attributes.split(";")) {
			out.println(attribute.split(":")[0] + "," + attribute.split("")[1]);
		    }
		} else {
		    String attributes = vector.getAttributes();
		    for (String attribute : attributes.split(";")) {
			for (String attrname : request.getParameterValues("attrname")) {
			    if (attrname.equals(attribute.split(":")[0])) {
				out.println(attribute.split(":")[0] + "," + attribute.split("")[1]);
			    }
			}
		    }
		}
	    }
	} catch (NumberFormatException ex) {
	    response.sendError(400, "UID must be a valid integer");
	} catch (Exception ex) {
	    ex.printStackTrace();
	    response.sendError(500, "Unknown internal error");
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
