CREATE OR REPLACE VIEW mfs.VIEW_AUDIT_TRAIL AS(
	select
		row_number() OVER () as row_id,
		log.module_id,
		log.action_name,
		log.menu_name,
		log.cid,
		log.data_before,
		log.data_after,
		log.app_type,
		log.created_date,
		usr.name
	from mfs.log_audit_trail log
	inner join
	(
		SELECT agent_id as id, 'AGENT' as type, agent_name as name FROM mfs.m_agent
		UNION
		SELECT user_id as id, 'WEB' as type, user_name as name FROM mfs.m_user
		UNION
		SELECT cust_id as id, 'CUSTOMER' as type, cust_name as name FROM mfs.m_cust
	) usr on usr.id=log.created_by and usr.type=log.user_type
)