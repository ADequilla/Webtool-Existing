package com.valuequest.util;

import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

public class GenerateAlphaNumeric {

    public String Generate(){
        int length = 8;
        boolean useLetters = true;
        boolean useNumbers = true;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        return generatedString;
    }
    
}
