package com.valuequest.test;

import com.valuequest.util.GenerateAlphaNumeric;

import org.apache.commons.lang.RandomStringUtils;



public class PrintDemoProperties {
    
    public static void main(String []args) throws Exception{
        // GenerateAlphaNumeric tt = new GenerateAlphaNumeric();
        // System.out.print(tt.Generate());

        int length = 8;
        boolean useLetters = true;
        boolean useNumbers = true;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
    
        System.out.println(generatedString);
    }
}
