SELECT code, trans_id, trans_date, client_id, trans_type, l.lookup_desc as trans_desc, note, status
FROM
(SELECT code, trans_id, trans_date, (select max(client_id) from mfs.m_client where cust_cid=trans.cust_cid) as client_id, 'CASH_OUT' AS trans_type 
FROM
	(SELECT 
		to_char(trans_date, 'DDMMYYYY') || '-' || cust_cid as code,
		MAX(trans_id) AS trans_id,
		MAX(trans_date) AS trans_date,
		cust_cid,
		COUNT(*) AS total 
	FROM mfs.t_trans_finance 
	WHERE trans_type='CASH_OUT' and trans_status='SUCCESS'
	GROUP BY code, cust_cid) trans
WHERE trans.total >= 3
UNION ALL
SELECT 
	'BLOCK-' || log_audit_id as code,
	null as trans_id,
	created_date AS trans_date,
	created_by AS client_id,
	CASE
		WHEN (action_name = 'BLOCKED_PIN') OR (action_name = 'USER_BLOCKED' AND module_id=1021) THEN 'WRONG_PIN'
		WHEN (action_name = 'BLOCKED_PASS') OR (action_name = 'USER_BLOCKED' AND module_id=1003) THEN 'WRONG_PASS'
	END AS trans_type
FROM mfs.log_audit_trail
WHERE action_name in ('BLOCKED_PIN','BLOCKED_PASS','USER_BLOCKED')) as v
INNER JOIN mfs.m_lookup l ON l.lookup_value=v.trans_type AND l.lookup_type='SUSPICIOUS_TYPE'
LEFT JOIN mfs.t_trans_suspicious s ON s.suspicious_code=v.code