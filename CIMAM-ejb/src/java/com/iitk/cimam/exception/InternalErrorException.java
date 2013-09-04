/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitk.cimam.exception;

/**
 *
 * @author Chirag Sangani
 */
public class InternalErrorException extends Exception {

    /**
     * Creates a new instance of <code>InternalErrorException</code> without detail message.
     */
    public InternalErrorException() {
    }

    /**
     * Constructs an instance of <code>InternalErrorException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InternalErrorException(String msg) {
        super(msg);
    }
}
