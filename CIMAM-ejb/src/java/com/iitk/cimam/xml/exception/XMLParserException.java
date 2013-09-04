/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitk.cimam.xml.exception;

/**
 *
 * @author Chirag Sangani
 */
public class XMLParserException extends Exception {

    /**
     * Creates a new instance of <code>XMLParserException</code> without detail message.
     */
    public XMLParserException() {
    }

    /**
     * Constructs an instance of <code>XMLParserException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public XMLParserException(String msg) {
        super(msg);
    }
}
