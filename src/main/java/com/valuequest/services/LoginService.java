package com.valuequest.services;
import com.valuequest.entity.security.SecUser;
import com.valuequest.entity.StructureUser;

public interface LoginService {

    public void updateLastLogin(StructureUser model, SecUser user);
    
}
