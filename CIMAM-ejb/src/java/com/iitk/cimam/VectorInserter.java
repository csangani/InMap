/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitk.cimam;

import com.iitk.cimam.data.Vectors;
import com.iitk.cimam.data.VectorsFacade;
import com.iitk.cimam.exception.InternalErrorException;
import com.iitk.cimam.svg.SVGParser;
import com.iitk.cimam.svg.exception.InvalidSVGFileException;
import com.iitk.cimam.svg.exception.SVGParserException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Chirag Sangani
 */
@Stateless
@LocalBean
public class VectorInserter {

    public void insert(String SVG, double X, double Y, VectorsFacade VF) throws InvalidSVGFileException, SVGParserException, InternalErrorException {
	SVGParser parser = new SVGParser(SVG);

	parser.validate();
	
	try {
	    Vectors[] vectors = parser.getObjects(X, Y, VF);
	    for (Vectors vector : vectors) {
		VF.create(vector);
	    }
	} catch (Exception ex) {
	    throw new InternalErrorException("unable to access database");
	}
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
