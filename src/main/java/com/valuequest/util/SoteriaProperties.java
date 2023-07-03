
package com.valuequest.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SoteriaProperties{
    private String signinUsername;

    @Value("${soteria.auth.signinUsername}")
    public void setSigninUsername(String value) {
        this.signinUsername = value;
    }

    public String getSigninUsername() {
        return signinUsername;
    }

    

    
}
