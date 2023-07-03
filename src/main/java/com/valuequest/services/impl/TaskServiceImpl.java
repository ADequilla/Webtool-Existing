package com.valuequest.services.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import com.valuequest.common.DataTables;
import com.valuequest.entity.ViewTask;
import com.valuequest.services.TaskService;
import com.valuequest.util.Constantas;
import com.valuequest.util.DateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TaskServiceImpl extends SimpleServiceImpl<ViewTask> implements TaskService{

    @Override
    public Class<ViewTask> getRealClass() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
        String taskName = (String) searchMap.get("taskName");
		String taskID = (String) searchMap.get("taskID");
		String searchDateStart = (String) searchMap.get("searchDateStart");
		String searchDateEnd = (String) searchMap.get("searchDateEnd");
        Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewTask.class);

		if (StringUtils.isNotBlank(taskID))
			criteria.add(Restrictions.eq("taskID", taskID));
		if (StringUtils.isNotBlank(taskName))
			criteria.add(Restrictions.ilike("taskName", taskName));
        if(StringUtils.isNotBlank(searchDateStart)){
            try {
                Date date = Constantas.fdate.parse(searchDateStart);
                criteria.add(Restrictions.le("createdAt", DateUtil.truncate(date)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(StringUtils.isNotBlank(searchDateEnd)){
            try {
                Date date = Constantas.fdate.parse(searchDateEnd);
                criteria.add(Restrictions.le("createdAt", DateUtil.endOfDay(date)));
            } catch (ParseException e) {
            }
        }

        



		return this.getDataTablesFromCriteria(criteria, dataTables);
    }
    
}
