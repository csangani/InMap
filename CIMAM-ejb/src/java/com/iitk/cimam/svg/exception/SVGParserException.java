/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitk.cimam.svg.exception;

/**
 *
 * @author Chirag Sangani
 */
public class SVGParserException extends Exception {

    /**
     * Creates a new instance of <code>SVGParserException</code> without detail message.
     */
    public SVGParserException() {
    }

    /**
     * Constructs an instance of <code>SVGParserException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SVGParserException(String msg) {
	super(msg);
    }
}
