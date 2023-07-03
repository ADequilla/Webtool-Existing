package com.valuequest.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.DashboardTempDao;
import com.valuequest.entity.DashboardTemp;

@Repository
public class DashboardTempDaoImpl extends BaseDao<DashboardTemp> implements DashboardTempDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void deleteTruncate() {
		sessionFactory.getCurrentSession().createSQLQuery("truncate table mfs.t_temp_dashboard").executeUpdate();
	}

	@Override
	public void saveData(String dateStart, String dateEnd, String branch) {
		String sql = "INSERT INTO mfs.t_temp_dashboard (transaction_type, number_total, amount, agent_income, bank_income) "
				+ " SELECT t.trans_type AS transaction_type, "
				+ " count(t.trans_type) AS number_total,  sum(t.trans_amount) AS amount,"
				+ " sum(t.agent_income) AS agent_income,  sum(t.bank_income) AS bank_income   "
				+ " FROM mfs.t_trans_finance t "
				+ " join mfs.m_client c on(c.client_id = t.client_id) "
				+ " join mfs.m_branch b on(b.branch_code = c.branch_code) "
				+ " WHERE t.trans_type in ('CASH_IN','CASH_OUT','CIP','AIP','FUND_TRANSFER','BALANCE_INQUERY','TRANS_HISTORY','MINI_STATEMENT')";
				
				if (StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd)){
					sql = sql +	" AND to_char(t.trans_date, 'dd-MMM-yyyy') >= '"+ dateStart +"'" 
						  + " AND to_char(t.trans_date, 'dd-MMM-yyyy') <= '"+ dateEnd +"'";
				}
				
				if(StringUtils.isNotBlank(branch)){
					sql = sql + " AND b.branch_code =  '"+ branch + "'";
				}
				
				sql = sql + " GROUP BY t.trans_type "
				+ " ORDER BY t.trans_type";
		
		sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

}
