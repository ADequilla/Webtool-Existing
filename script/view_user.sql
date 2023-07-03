	(SELECT
		agent_id as row_id,
		created_by AS uploaded_by,
		created_date as upload_date,
		cid,
		agent_name as name,
		'AGENT' as type,
		branch_code,
		unit_code,
		center_code,
		approver_status,
		agent_status as status,
		agent_broadcast_status as sms_status,
		agent_dob as dob,
		agent_mobile_no mobile_no,
		enable_print,
		agent_expired_actcode AS expired_actcode, 
		agent_expired_date AS expired_date
	FROM mfs.m_agent
	)
	UNION ALL
	(SELECT
		cust_id as row_id,
		created_by AS uploaded_by,
		created_date as upload_date,
		cid,
		cust_name as name,
		'MEMBER' as type,
		branch_code,
		unit_code,
		center_code,
		approver_status,
		cust_status as status,
		cust_broadcast_status as sms_status,
		cust_dob as dob,
		cust_mobile_phone as mobile_no,
		0 as enable_print,
		cust_expired_actcode AS expired_actcode, 
		cust_expire_date AS expired_date
	FROM mfs.m_cust
	)