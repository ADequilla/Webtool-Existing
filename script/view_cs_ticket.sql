SELECT 
	usr.name as submitted_by,
	cs.subject,
	cs.created_date,
	cs.ticket_id,
	branch.branch_desc,
	center.center_desc,
	cs.client_cid,
	cs.client_name,
	cs.client_type,
	clientType.lookup_desc as client_desc,
	cs.concern_id,
	concern.concern_desc as concern_desc,
	received.given_name as received_by,
	cs.action_detail,
	cs.message,
	assigned.given_name as assigned_to,
	assigned.user_id as assigned_to_id,
	cs.action_taken,
	csAction.lookup_desc as action_desc,
	cs.status,
	csStatus.lookup_desc as status_desc,
	cs.closed_date,
	cs.turn_around,
  cs.customer_ticket_id
FROM mfs.t_cs_ticket cs
INNER JOIN
(
	SELECT 
		client_id as id,
		client_type as type,
		client_name as name,
		branch_code,
		center_code
	FROM mfs.m_client
	UNION
	SELECT 
		user_id as id,
		'WEB' as type,
		given_name as name,
		branch_code,
		center_code
	FROM mfs.m_user
) usr ON usr.id=cs.created_by AND usr.type=cs.user_type
LEFT JOIN
mfs.m_type_concern concern on concern.concern_code=cs.concern_id
LEFT JOIN
mfs.m_user received on received.user_id=cs.received_by
LEFT JOIN
mfs.m_user assigned on assigned.user_id=cs.assigned_to
LEFT JOIN 
mfs.m_branch branch on branch.branch_code=usr.branch_code
LEFT JOIN
mfs.m_center center on center.center_code=usr.center_code
LEFT JOIN 
mfs.m_lookup clientType on clientType.lookup_value=cs.client_type and clientType.lookup_type='CLIENT_TYPE'
LEFT JOIN
mfs.m_lookup csAction on csAction.lookup_value=cs.action_taken and csAction.lookup_type='CS_ACTION'
LEFT JOIN
mfs.m_lookup csStatus on csStatus.lookup_value=cs.status and csStatus.lookup_type='CS_STATUS'