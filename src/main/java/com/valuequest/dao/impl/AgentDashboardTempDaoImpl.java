package com.valuequest.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.AgentDashboardTempDao;
import com.valuequest.entity.AgentDashboardTemp;

@Repository
public class AgentDashboardTempDaoImpl extends BaseDao<AgentDashboardTemp> implements AgentDashboardTempDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void deleteTruncate() {
		sessionFactory.getCurrentSession().createSQLQuery("truncate table mfs.t_temp_agent_dashboard").executeUpdate();
	}

	@Override
	public void saveData(String branch) {
		String sql = "INSERT INTO mfs.t_temp_agent_dashboard (branch_code, branch_desc, cash_in, cash_out, agent_assisted_payment, bill_payment, total, total_income)"
				+ " select b.branch_code, b.branch_desc, "
				+ " CONCAT((select COUNT(tt.client_id) from mfs.t_trans_finance tt "
				+ " JOIN mfs. cc on(tt.client_id = cc.client_id) where tt.trans_type = 'CASH_IN' and cc.branch_code = b.branch_code )  , "
				+ " ' - ' , "
				+ " (select SUM(tt.trans_amount)  from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type = 'CASH_IN' and cc.branch_code = b.branch_code ) ) as CASH_IN, "
				+ " CONCAT((select COUNT(tt.client_id) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type = 'CASH_OUT' and cc.branch_code = b.branch_code )  ,"
				+ " ' - ' , "
				+ " (select SUM(tt.trans_amount) from mfs.t_trans_finance tt"
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type = 'CASH_OUT' and cc.branch_code = b.branch_code ) ) as CASH_OUT, "
				+ " CONCAT((select COUNT(tt.client_id) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type = 'AIP' and cc.branch_code = b.branch_code )  , "
				+ " ' - ' ,"
				+ " (select SUM(tt.trans_amount) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type = 'AIP' and cc.branch_code = b.branch_code ) ) as Agent_Assisted_Payment,"
				+ " CONCAT((select COUNT(tt.client_id) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type = 'YOUR_BILLS' and cc.branch_code = b.branch_code )  , "
				+ " ' - ' , "
				+ " (select SUM(tt.trans_amount) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type = 'YOUR_BILLS' and cc.branch_code = b.branch_code ) ) as BILL_PAYMENT, "
				+ " CONCAT((select COUNT(tt.client_id) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type in ('CASH_IN','CASH_OUT','AIP','YOUR_BILLS') and cc.branch_code = b.branch_code )  , "
				+ " ' - ' ,"
				+ " (select SUM(tt.trans_amount) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type in ('CASH_IN','CASH_OUT','AIP','YOUR_BILLS') and cc.branch_code = b.branch_code ) ) as TOTAL, "
				+ " (select SUM(tt.bank_income) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type in ('CASH_IN','CASH_OUT','AIP','YOUR_BILLS') and cc.branch_code = b.branch_code )  as TOTAL_INCOME"
				+ " from mfs.t_trans_finance t "
				+ " join mfs.m_client c on(c.client_id = t.client_id) "
				+ " join mfs.m_branch b on(b.branch_code = c.branch_code) "
				+ " where to_char(t.trans_date, 'YYYY-MM-DD') >= '2018-08-14' "
				+ " and c.agent_feature = '1' "
				+ " and c.client_type in ('MEMBER', 'NON MEMBER')";
				
				if(StringUtils.isNotBlank(branch)){
					sql = sql + " AND b.branch_code =  '"+ branch + "'";
				}
				
				sql = sql + " group by b.branch_desc, b.branch_code ";
		
		sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public void deleteTruncateClient() {
		sessionFactory.getCurrentSession().createSQLQuery("truncate table mfs.t_temp_client_dashboard").executeUpdate();
	}

	@Override
	public void saveDataClient(String branch) {
		String sql = "INSERT INTO mfs.t_temp_client_dashboard (client_id, client_name, cash_in, cash_out, agent_assisted_payment, bill_payment, total, total_income)"
				+ " select  c.client_id, c.client_name,  "
				+ " CONCAT((select COUNT(tt.client_id) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type = 'CASH_IN' and cc.client_id = c.client_id )  , "
				+ " ' - ' ,  "
				+ " (select SUM(tt.trans_amount)  from mfs.t_trans_finance tt "
				+ " JOIN  mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type = 'CASH_IN' and cc.client_id = c.client_id ) ) as CASH_IN, "
				+ " CONCAT((select COUNT(tt.client_id) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type = 'CASH_OUT' and cc.client_id = c.client_id )  , "
				+ " ' - ' ,  "
				+ " (select SUM(tt.trans_amount) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type = 'CASH_OUT' and cc.client_id = c.client_id ) ) as CASH_OUT, "
				+ " CONCAT((select COUNT(tt.client_id) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type = 'AIP' and cc.client_id = c.client_id )  , "
				+ " ' - ' , "
				+ " (select SUM(tt.trans_amount) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type = 'AIP' and cc.client_id = c.client_id ) ) as Agent_Assisted_Payment, "
				+ " CONCAT((select COUNT(tt.client_id) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type = 'YOUR_BILLS' and cc.client_id = c.client_id )  ,  "
				+ " ' - ' ,  "
				+ " (select SUM(tt.trans_amount) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type = 'YOUR_BILLS' and cc.client_id = c.client_id ) ) as BILL_PAYMENT, "
				+ " CONCAT((select COUNT(tt.client_id) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type in ('CASH_IN','CASH_OUT','AIP','YOUR_BILLS') and cc.client_id = c.client_id )  ,"
				+ " ' - ' ,  "
				+ " (select SUM(tt.trans_amount) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type in ('CASH_IN','CASH_OUT','AIP','YOUR_BILLS') and cc.client_id = c.client_id ) ) as TOTAL, "
				+ " (select SUM(tt.bank_income) from mfs.t_trans_finance tt "
				+ " JOIN mfs.m_client cc on(tt.client_id = cc.client_id) where tt.trans_type in ('CASH_IN','CASH_OUT','AIP','YOUR_BILLS') and cc.client_id = c.client_id )  as TOTAL_INCOME "
				+ " from mfs.t_trans_finance t "
				+ " join mfs.m_client c on(c.client_id = t.client_id)"
				+ " join mfs.m_branch b on(b.branch_code = c.branch_code)"
				+ " where to_char(t.trans_date, 'YYYY-MM-DD') >= '2018-08-14' "
				+ " and c.agent_feature = '1' "
				+ " and c.client_type in ('MEMBER', 'NON MEMBER')";
				
				if(StringUtils.isNotBlank(branch)){
					sql = sql + " AND b.branch_code =  '"+ branch + "'";
				}
				
				sql = sql + " group by c.client_id  ";
		
		sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

}
