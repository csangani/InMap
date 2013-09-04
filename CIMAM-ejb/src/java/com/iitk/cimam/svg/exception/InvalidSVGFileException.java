/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitk.cimam.svg.exception;

/**
 *
 * @author Chirag Sangani
 */
public class InvalidSVGFileException extends Exception {

    /**
     * Creates a new instance of <code>InvalidSVGFileException</code> without detail message.
     */
    public InvalidSVGFileException() {
    }

    /**
     * Constructs an instance of <code>InvalidSVGFileException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InvalidSVGFileException(String msg) {
	super(msg);
    }
}
