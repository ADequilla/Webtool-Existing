package com.valuequest.services.impl;

import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.maintenance.model.NewsModel;
import com.valuequest.entity.Inbox;
import com.valuequest.entity.StructureBranch;
import com.valuequest.entity.StructureInstitution;
import com.valuequest.entity.UserBranch;
import com.valuequest.entity.UserInstitution;
import com.valuequest.entity.ViewSmsLog;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.BranchService;
import com.valuequest.services.GenericService;
import com.valuequest.services.NewsService;
import com.valuequest.util.Constantas;
import com.valuequest.util.HttpClientMcu;
import com.valuequest.util.HttpClientUpload;

@Service
@Transactional(readOnly = true)
public class NewsServiceImpl extends SimpleServiceImpl<Inbox> implements NewsService {

	@Autowired
	protected GenericService genericService;
	
	@Autowired
	protected BranchService branchService;
	
	@Override
	public Class<Inbox> getRealClass() {
		return Inbox.class;
	}
	
	protected SecUser getLoginSecUser(HttpSession session) {
		return (SecUser) session.getAttribute("loginSecUser");
	}

	@Override
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(NewsModel model, SecUser userLogin) {
		try{
		
			Inbox inbox = null;
			if (model.getId() != null) {
				inbox = this.findById(model.getId());
			} else {
				inbox = new Inbox();
				inbox.setDate(new Date());
				inbox.setType(new String("BROADCAST"));
			}
			inbox.setSubject(model.getSubject());
			inbox.setDesc(model.getDesc());
			inbox.setClientType(model.getClientType());
			String branchCode = "";
			System.out.println("############# model.getBranch() = "+model.getBranch());
			
			String[] branchs = model.getBranch();
			List<StructureBranch> branchList = branchService.mappedList();
			System.out.println("############# model.length() = "+branchs.length);
			System.out.println("############# branchList = "+branchList.size());
			if (branchs != null && branchs.length == branchList.size()) {
				branchCode = "ALL";
			}
			else {
				for (String branch : branchs) {
					branchCode += branch +"-";
				}
				branchCode = branchCode.substring(0,branchCode.length() - 1);
			}
			
			inbox.setBranchCode(branchCode);
	
			if (StringUtils.isNotBlank(model.getPeriodStart())) {
				try {
					Date date = Constantas.fdate.parse(model.getPeriodStart());
					inbox.setPeriodStart(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
	
			if (StringUtils.isNotBlank(model.getPeriodEnd())) {
				try {
					Date date = Constantas.fdate.parse(model.getPeriodEnd());
					inbox.setPeriodEnd(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			this.saveOrUpdate(inbox);
			model.setId(inbox.getId());
			
			HttpClientMcu httpClient = new HttpClientMcu();
			String url = genericService.getConfigValueByName("URL_SENDMESSAGE");
			System.out.println("####### url = "+url);
			System.out.println("####### model.getClientType() = "+model.getClientType());
			System.out.println("####### model.getBranch() = "+branchCode);
			System.out.println("####### model.getSubject() = "+model.getSubject());
			System.out.println("####### model.getDesc() = "+model.getDesc());
			System.out.println("####### model.getId() = "+model.getId());
			
			String param = "clientType="+URLEncoder.encode(model.getClientType(), "UTF-8") +"&";
				   param += "branch="+URLEncoder.encode(branchCode, "UTF-8") +"&";
				   param += "subject="+URLEncoder.encode(model.getSubject(), "UTF-8") +"&";
				   param += "description="+URLEncoder.encode(model.getDesc(), "UTF-8") +"&";
				   param += "inboxId="+model.getId();
		    
			
			System.out.println("########### url = "+url+param);
			String json = "";
			if (StringUtils.isNotBlank(url)) {
				json = httpClient.callMbo(url + param);
			}
			System.out.println("########### json = "+json);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(List<StateModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (StateModel model : states) {
			ids.add(model.getId());
		}

		if (ids.size() > 0) {
			this.getSessionFactory().getCurrentSession().createQuery("delete from Inbox where id in :ids").setParameterList("ids", ids).executeUpdate();
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getUserBranchList(Long userId){
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(UserBranch.class);
		criteria.add(Restrictions.eq("user.id", userId));
		
		List r = criteria.list();
		
		String brnch = "";
		
		for (Iterator iterator = r.iterator(); iterator.hasNext();){
			UserBranch usrBranch = (UserBranch) iterator.next();
			brnch = brnch + "-";
           
		}
		
		brnch = brnch.substring(0,brnch.length() - 1);
		
		return brnch;
	}
	
	
	private DetachedCriteria criteriaBy(Long userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserBranch.class);
		criteria.createAlias("user", "user");
		criteria.createAlias("branch", "branch");
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("branch.code")));

		return criteria;
	}
	
	List<String> branchList = new ArrayList<String>();
	
	public void getUserBranch(Long userId) {
		branchList.clear();
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureBranch.class);
		criteria.add(Subqueries.propertyIn("code", criteriaBy(userId)));
		List r = criteria.list();
		for (Iterator iterator = r.iterator(); iterator.hasNext();){
			StructureBranch branch = (StructureBranch) iterator.next();		
			branchList.add(branch.getCode());
		}
		
		System.out.println("#####Arrray of Branch#####\n "+  branchList.toString());
		
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap,
			HttpSession session) {
		String subject 		= (String) searchMap.get("subject");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());
		criteria.add(Restrictions.eq("type", "BROADCAST"));
		
		if (StringUtils.isNotBlank(subject))
			criteria.add(Restrictions.ilike("subject", subject, MatchMode.ANYWHERE));
		
		
		SecUser user = this.getLoginSecUser(session);
		getUserBranch(user.getId()); 
		
		criteria.add(Restrictions.in("branchCode", branchList));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

	

	

}