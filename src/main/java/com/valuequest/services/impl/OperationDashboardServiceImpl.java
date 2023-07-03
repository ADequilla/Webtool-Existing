package com.valuequest.services.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.dao.DashboardTempDao;
import com.valuequest.entity.Client;
import com.valuequest.entity.DashboardTemp;
import com.valuequest.entity.ViewDashboardTransaction;
import com.valuequest.services.OperationDashboardService;

@Service
@Transactional(readOnly = false)
public class OperationDashboardServiceImpl extends SimpleServiceImpl<Client> implements OperationDashboardService{

	@Autowired
	private DashboardTempDao dashboardTempDao;
	
	@Override
	public Class<Client> getRealClass() {
		return Client.class;
	}
	
	@Override
	public Long countAgent() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Client.class).setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("type", "AGENT"));
		return (Long) criteria.uniqueResult();
	}

	@Override
	public Long countMember() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Client.class).setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("type", "MEMBER"));
		return (Long) criteria.uniqueResult();
	}

	@Override
	public Long countNonMember() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Client.class).setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("type", "NON MEMBER"));
		return (Long) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewDashboardTransaction> getAll(){
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewDashboardTransaction.class);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewDashboardTransaction> getAllByParam(String dateStart, String dateEnd, String branch){
		String sql = "SELECT mfs.t_trans_finance.trans_type AS transaction_type,"
				+ " count(mfs.t_trans_finance.trans_type) AS number, "
				+ " sum(mfs.t_trans_finance.trans_amount) AS amount, "
				+ " sum(mfs.t_trans_finance.agent_income) AS agent_income, "
				+ " sum(mfs.t_trans_finance.bank_income) AS bank_income "
				+ " FROM mfs.t_trans_finance "
				+ " WHERE mfs.t_trans_finance.trans_type in ('CASH_IN','CASH_OUT','CIP','AIP','FUND_TRANSFER','BALANCE_INQUERY','TRANS_HISTORY','MINI_STATEMENT')";
				
				if (StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd)){
					sql = sql +	" AND to_char(mfs.t_trans_finance.trans_date, 'dd-MMM-yyyy') >= '"+ dateStart +"'" 
						  + " AND to_char(mfs.t_trans_finance.trans_date, 'dd-MMM-yyyy') <= '"+ dateEnd +"'";
				}
				
				if(StringUtils.isNotBlank(branch)){
					sql = sql + " AND mfs.t_trans_finance.branch =  '"+ branch + "'";
				}
				
				sql = sql + " GROUP BY mfs.t_trans_finance.trans_type "
				+ " ORDER BY mfs.t_trans_finance.trans_type";
				
				System.out.println("####### sql = "+sql);
				
		SQLQuery query = this.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addEntity(ViewDashboardTransaction.class);
		return query.list();
	}
	
	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		
		String dateStart = (String) searchMap.get("dateStart");
		String dateEnd	= (String) searchMap.get("dateEnd");
		String branch	= (String) searchMap.get("branch");
		
		dashboardTempDeleteAndSave(dateStart, dateEnd, branch);
		
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(DashboardTemp.class);

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	public void dashboardTempDeleteAndSave(String dateStart, String dateEnd, String branch){
		dashboardTempDao.deleteTruncate();
		dashboardTempDao.saveData(dateStart, dateEnd, branch);
	}

}