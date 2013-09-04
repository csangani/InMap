/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitk.cimam;

import com.iitk.cimam.cookies.Cookies;
import com.iitk.cimam.data.VectorsFacade;
import com.iitk.cimam.svg.exception.InvalidSVGFileException;
import com.iitk.cimam.svg.exception.SVGParserException;
import java.io.DataInputStream;
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
public class AddEntry extends HttpServlet {

    @EJB
    VectorInserter VI;
    
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
		    String contentType = request.getContentType();
		    if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
			DataInputStream in = new DataInputStream(request.getInputStream());
			int formDataLength = request.getContentLength();
			byte dataBytes[] = new byte[formDataLength];
			int byteRead = 0;
			int totalBytesRead = 0;
			while (totalBytesRead < formDataLength) {
			    byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
			    totalBytesRead += byteRead;
			}
			String input = new String(dataBytes);

			//  Get SVG File

			int indexSVGStart = input.indexOf("<svg");
			int indexSVGEnd = input.lastIndexOf("</svg>") + 6;
			if (indexSVGStart == -1 || indexSVGEnd == 5) {
			    response.sendRedirect("add.jsp?err=1");
			    out.close();
			    return;
			} else {

			    String SVG = input.substring(indexSVGStart, indexSVGEnd);

			    //	Get x
			    int indexXStart = input.indexOf("Content-Disposition: form-data; name=\"x\"\r") + "Content-Disposition: form-data; name=\"x\"\r".length() + 3;
			    int indexXEnd = input.indexOf("\r", indexXStart);
			    double X, Y;
			    try {
				X = Double.parseDouble(input.substring(indexXStart, indexXEnd).trim());
			    } catch (NumberFormatException ex) {
				response.sendRedirect("add.jsp?err=2");
				out.close();
				return;
			    }

			    //	Get y
			    int indexYStart = input.indexOf("Content-Disposition: form-data; name=\"y\"\r") + "Content-Disposition: form-data; name=\"y\"\r".length() + 3;
			    int indexYEnd = input.indexOf("\r", indexYStart);
			    try {
				Y = Double.parseDouble(input.substring(indexYStart, indexYEnd).trim());
			    } catch (NumberFormatException ex) {
				response.sendRedirect("add.jsp?err=2");
				out.close();
				return;
			    }

			    //	Insert vector
			    try {
				VI.insert(SVG, X, Y, VF);
				response.sendRedirect("edit.jsp");
			    } catch (SVGParserException ex) {
				response.sendRedirect("add.jsp?err=1");
			    } catch (InvalidSVGFileException ex) {
				response.sendRedirect("add.jsp?err=" + URLEncoder.encode(ex.getMessage(), "UTF-8"));
				ex.printStackTrace();
			    } catch (Exception ex) {
				response.sendRedirect("add.jsp?err=0");
			    }
			}
		    }
		    break;
		}
	    }
	    if (!flag) {
		response.sendRedirect("adminrefresh.jsp");
	    }
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
