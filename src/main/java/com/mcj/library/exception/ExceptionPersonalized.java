/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcj.library.exception;

import javax.lang.model.SourceVersion;

/**
 *
 * @author maxco
 */
public class ExceptionPersonalized extends Exception{

    /**
     * Creates a new instance of <code>Exception</code> without detail message.
     */
    public ExceptionPersonalized() {
    }

    /**
     * Constructs an instance of <code>Exception</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ExceptionPersonalized(String msg) {
        super(msg);
    }

    
}
