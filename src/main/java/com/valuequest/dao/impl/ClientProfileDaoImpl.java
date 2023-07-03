package com.valuequest.dao.impl;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.valuequest.dao.ClientProfileDao;
import com.valuequest.entity.Client;
import com.valuequest.entity.security.SecUser;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional 
public class ClientProfileDaoImpl extends BaseDao<Client> implements ClientProfileDao {
    
    @Autowired
	SessionFactory sessionFactory;

    protected SecUser getLoginSecUser(HttpSession session) {
		return (SecUser) session.getAttribute("loginSecUser");
	}

    @Override
    public void updateAgent(String cid, Integer status , HttpSession session){
        SecUser user = this.getLoginSecUser(session);

        System.out.println("CID: " + cid);
        System.out.println("Status: " + status);
        System.out.println("Username: " + user.getId());

        
        sessionFactory.getCurrentSession().createQuery("update Client SET agentFeature = :status, enableAgentFeatures = NOW(), enableAgentFeaturesBy= :by WHERE cid = :cid")
				.setParameter("cid", cid)
                .setParameter("status", status)
                .setParameter("by", user.getId()).executeUpdate();
                
    }
}
