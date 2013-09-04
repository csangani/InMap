/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitk.cimam;

import com.iitk.cimam.exception.InternalErrorException;
import com.iitk.cimam.data.VectorsFacade;
import com.iitk.cimam.gen.Generator;
import com.iitk.cimam.xml.exception.BadRequestException;
import com.iitk.cimam.xml.RequestParser;
import com.iitk.cimam.xml.exception.XMLParserException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Chirag Sangani
 */
@Stateless
@LocalBean
public class MappingBean {

    public String getInterface(String xmlRequest, VectorsFacade VF) throws BadRequestException, InternalErrorException {
	// Parse request
	RequestParser parser;
	String output = "";
	try {
	    parser = new RequestParser(xmlRequest);
	} catch (XMLParserException ex) {
	    throw new InternalErrorException(ex.getMessage());
	}

	// Validate request
	parser.validate();
	
	//  Sanitize request
	parser.sanitize();

	//  Analyze request type
	int requestType = parser.getRequestType();
	
	//  Is editMode?
	boolean editMode = parser.getEditMode();

	//  Service request
	if (requestType == 1) {
	    // TagsAndHighlighting mode
	    Generator intGen = new Generator(parser.TAHGetTagList(), VF.findAll(), editMode);
	    output = intGen.getOutput(parser.getInterfaceSizeWidth(),parser.getInterfaceSizeHeight());
	}
	return output;
    }
}
