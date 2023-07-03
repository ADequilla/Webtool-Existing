package com.valuequest.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.valuequest.common.DataTables;
import com.valuequest.entity.StructureUser;
import org.springframework.stereotype.Service;
import com.valuequest.services.LoginService;
import com.valuequest.entity.security.SecUser;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;


@Service
public class LoginServiceImpl extends SimpleServiceImpl<StructureUser> implements LoginService {

    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    Date date = new Date(System.currentTimeMillis());   

    @Override
    public Class<StructureUser> getRealClass() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Modifying
	@Transactional(propagation = Propagation.REQUIRED)
    public void updateLastLogin(StructureUser model, SecUser user) {
        // StructureUser strucUser = new StructureUser();
		// strucUser.setLastLoginDate(formatter.format(date));
		// this.saveOrUpdate(strucUser);
        
    }
}
