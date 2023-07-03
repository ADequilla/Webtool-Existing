package com.valuequest.dao;

import javax.servlet.http.HttpSession;

import com.valuequest.entity.Client;

public interface ClientProfileDao extends InterfaceBaseDao<Client> {
    
    public void updateAgent(String cid, Integer status, HttpSession session);

}
