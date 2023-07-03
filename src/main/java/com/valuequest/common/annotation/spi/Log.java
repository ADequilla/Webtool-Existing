/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.valuequest.common.annotation.spi;

/**
 *
 * @author Nugie
 */
public final class Log {

    private final StringBuilder stringBuilder = new StringBuilder();
    private int inner = 0;

    public Log() {

    }

    public void increamentDepth() {
        inner++;
    }

    public void decreamentDepth() {
        inner--;
    }
    
    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    public int getInner() {
        return inner;
    }

    
    
}
