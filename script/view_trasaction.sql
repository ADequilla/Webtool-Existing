SELECT trx.trans_mobile_refno AS mobile_refno,
	trx.trans_core_refno AS core_refno,
	CASE
			WHEN (trx.trans_type = 'CASH_IN') THEN b2.branch_desc
			ELSE b1.branch_desc
	END AS source_branch,
	trx.src_acct_no as source_account,
	CASE
			WHEN (trx.trans_type = 'CASH_IN') THEN c2.client_name
			ELSE c1.client_name
	END AS source_name,
	CASE
			WHEN (trx.trans_type = 'CASH_OUT') THEN b2.branch_desc
			ELSE b1.branch_desc
	END AS target_branch,
	trx.trg_acct_no as target_account,
	CASE
			WHEN (trx.trans_type = 'CASH_OUT') THEN c2.client_name
			ELSE c1.client_name
	END AS target_name,
	trx.trans_type AS trans_type_code,
	l.lookup_desc AS trans_type_desc,
	trx.trans_amount AS amount,
	trx.trans_amount_fee,
	trx.agent_income,
	trx.bank_income,
	trx.cmit_fee,
	trx.fds_fee,
	trx.created_date,
	trx.trans_post_date AS post_date,
	trx.trans_status AS status,
	log.trans_msg_desc AS message
FROM mfs.t_trans_finance trx
INNER JOIN mfs.m_lookup l ON l.lookup_value = trx.trans_type AND l.lookup_type = 'TRAN_TYPE'
LEFT JOIN mfs.m_client c1 ON c1.client_id = trx.client_id
LEFT JOIN mfs.m_client c2 ON c2.client_id = trx.reff_id
LEFT JOIN mfs.m_branch b1 ON b1.branch_code = c1.branch_code
LEFT JOIN mfs.m_branch b2 ON b2.branch_code = c2.branch_code
LEFT JOIN mfs.log_igate log ON log.trans_id = trx.trans_id
UNION ALL
SELECT trx.trans_mobile_refno AS mobile_refno,
	trx.trans_core_refno AS core_refno,
	CASE
			WHEN (trx.trans_type = 'CASH_IN') THEN b2.branch_desc
			ELSE b1.branch_desc
	END AS source_branch,
	trx.src_acct_no as source_account,
	CASE
			WHEN (trx.trans_type = 'CASH_IN') THEN c2.client_name
			ELSE c1.client_name
	END AS source_name,
	CASE
			WHEN (trx.trans_type = 'CASH_OUT') THEN b2.branch_desc
			ELSE b1.branch_desc
	END AS target_branch,
	trx.trg_acct_no as target_account,
	CASE
			WHEN (trx.trans_type = 'CASH_OUT') THEN c2.client_name
			ELSE c1.client_name
	END AS target_name,
	trx.trans_type AS trans_type_code,
	l.lookup_desc AS trans_type_desc,
	trx.trans_amount AS amount,
	trx.trans_amount_fee,
	trx.agent_income,
	trx.bank_income,
	trx.cmit_fee,
	trx.fds_fee,
	trx.created_date,
	trx.trans_post_date AS post_date,
	trx.trans_status AS status,
	log.trans_msg_desc AS message
FROM mfs.t_trans_finance_failed trx
INNER JOIN mfs.m_lookup l ON l.lookup_value = trx.trans_type AND l.lookup_type = 'TRAN_TYPE'
LEFT JOIN mfs.m_client c1 ON c1.client_id = trx.client_id
LEFT JOIN mfs.m_client c2 ON c2.client_id = trx.reff_id
LEFT JOIN mfs.m_branch b1 ON b1.branch_code = c1.branch_code
LEFT JOIN mfs.m_branch b2 ON b2.branch_code = c2.branch_code
LEFT JOIN mfs.log_igate log ON log.trans_id = trx.trans_id