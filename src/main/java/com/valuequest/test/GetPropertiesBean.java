package com.valuequest.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GetPropertiesBean {

    private final String foo;

    @Autowired
    public GetPropertiesBean(@Value("${foo.bar}") String foo) {
        this.foo = foo;
        System.out.println(foo);
    }

}
