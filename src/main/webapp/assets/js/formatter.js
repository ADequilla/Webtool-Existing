var EditlinkFormatter = function(data, type, row) {
	return '<a href="edit/' + data + '"><span class="icon-edit"><i class="fa fa-pencil"></i></span></a>';
};

var CheckboxFormatter = function(data, type, row) {
	return '<input type="checkbox" value="' + data + '"/>';
};


var UserWebStatusFormatter = function(data, type, row) {
	var value = "";
	if (data == 'ACTIVE') {
		value = '<span class="label label-success"><i class="fa fa-check"></i> <b>Active</b> </span>';
	} else if (data == 'INACTIVE') {
		value = '<span class="label label-warning"><i class="fa fa-ban"></i> <b>Inactive</b> </span>';
	} else if (data == 'LOCK') {
		value = '<span class="label label-danger"><i class="fa fa-lock"></i> <b>Locked</b> </span>';
	} else {
		value = '<span class="label label-default">' + data + ' </span>';
	}

	return value;
};

var MCUserWebStatusFormatter = function(data, type, row) {
	var value = "";
	if (data == 'ACTIVE') {
		value = '<span class="label label-success"><i class="fa fa-check"></i> <b>Active</b> </span>';
	} else if (data == 'INACTIVE' && row.modified == false) {
		value = '<span class="label label-warning"><i class="fa fa-ban"></i> <b>Inactive</b> </span>';
	} else if (data == 'BLOCKED_PASS') {
		value = '<span class="label label-danger"><i class="fa fa-lock"></i> <b>Bloked Password</b> </span>';
	}else if (data == 'BLOCKED_PIN') {
		value = '<span class="label label-danger"><i class="fa fa-lock"></i> <b>Blocked Pin</b> </span>';
	}else if (data == 'BLOCKED_ACTCODE') {
		value = '<span class="label label-danger"><i class="fa fa-lock"></i> <b>Blocked Act. Code</b> </span>';
	}else if (data == 'WAIT_ACTIVATION') {
		value = '<span class="label label-danger"><i class="fa fa-clock-o"></i> <b>Wait for Activation</b> </span>';
	}else if (data == 'WAIT_REGISTRATION') {
		value = '<span class="label label-danger"><i class="fa fa-clock-o"></i> <b>Wait for Registration</b> </span>';
	} else if (row.modified) {
		value = '<span class="label label-default"><i class="fa fa-clock-o"></i> <b>Wait for Authorization</b> </span>';
	}else {
		value = '<span class="label label-default">' + data + ' </span>';
	}

	return value;
};

var ConcurrentUserStatusFormatter = function(data, type, row) {
	var value = "";
	if (data == 'ACTIVE') {
		value = '<span class="label label-info"><i class="fa fa-lock"></i> <b>Used/Locked</b> </span>';
	} else if (data == 'INACTIVE') {
		value = '<span class="label label-warning"><i class="fa fa-ban"></i> <b>Unused</b> </span>';
	} else {
		value = '<span class="label label-default">' + data + ' </span>';
	}

	return value;
};

var EnabledStatusFormatter = function(data, type, row) {
	var value = '';
	if (data == 0) {
		value = '<i class="fa fa-circle-thin text-danger"></i>';
	} else if (data == 1) {
		value = '<i class="fa fa-circle text-success"></i>';
	} else {
		value = '<i class="fa fa-exclamation text-warning"></i>';
	}

	return value;
};


var ApprovalStatusFormatter = function(data, type, row) {
	var value = '';
	if (data == 'APPROVED') {
		value = '<span class="label label-success"><i class="fa fa-check"> <b>Approved</b> </span>';
	} else if (data == 'REJECTED') {
		value = '<span class="label label-danger"><i class="fa fa-times"> <b>Rejected</b> </span>';
	} else if (data == 'PENDING_APPROVAL') {
		value = '<span class="label label-warning"><i class="fa fa-ellipsis-h"> <b>Pending Approval</b> </span>';
	} else {
		value = '<span class="label label-default"> ' + data + '</span>';
	}

	return value;
};


var ApprovalCheckboxFormatter = function(data, type, row) {
	var value = '';
	if (data == 'PENDING_APPROVAL') {
		value = '<input type="checkbox" value="' + row.id + '"/>';
	}

	return value;
};

var ApprovalMcCheckboxFormatter = function(data, type, row) {
	var value = '';
	if (data == 'PENDING') {
		value = '<input type="checkbox" value="' + row.id + '"/>';
	}

	return value;
};

var FullNameFormatter = function(data, type, row) {
	var value =  '' 
		if(row != null){
			value = row.givenName + ' ' +row.middleName +' '+ row.surname;
		}
	return value;
};

var FullNameCreatedByFormatter = function(data, type, row) {
	var value =  '' 
		if(row.createdBy != null){
			value = row.createdBy.givenName + ' ' +row.createdBy.middleName +' '+ row.createdBy.lastName;
		}
	return value;
};

var FullNameUpdatedByFormatter = function(data, type, row) {
	var value =  '' 
		if(row.updatedBy != null){
			value = row.updatedBy.givenName + ' ' +row.updatedBy.middleName +' '+ row.updatedBy.lastName;
		}
	return value;
};

var ClientTypeFormatter = function(data, type, row) {
	var value = '';
	if (data == 'AGENT') {
		value = 'Agent';
	} else if (data == 'MEMBER') {
		value = 'Customer';
	} else if (data == 'NON MEMBER') {
		value = 'Non Member';
	} else if (data == 'BOTH') {
		value = 'All';
	} else {
		value = data;
	}

	return value;
};

var DesignationFormatter = function(data, type, row) {
	var value = '';
	if (data == 'AO') {
		value = 'Account Officer';
	} else if (data == 'UM') {
		value = 'Unit Manager';
	} else {
		value = data;
	}

	return value;
};

var TransactionTypeFormatter = function(data, type, row) {
	var value = "";
	if (row.transType == "CASH_IN") {
		value = "Cash In";
	} else if (row.transType == "CASH_OUT") {
		value = "Cash Out";
	} else if (row.transType == "BALANCE_INQUERY") {
		value = "Balance Inquiry";
	} else if (row.transType == "CIP") {
		value = "Client Initiate Payment";
	} else if (row.transType == "AIP") {
		value = "Agent Assisted Payment";
	} else if (row.transType == "FUND_TRANSFER") {
		value = "Fund Transfer";
	} else if (row.transType == "SLF_REQUEST") {
		value = "SLF Rqeuest";
	} else if (row.transType == "SLF_PAYMENT") {
		value = "SLF Payment";
	} else if (row.transType == "TRANS_HISTORY") {
		value = "Transaction History";
	} else if (row.transType == "OTHER") {
		value = "Other";
	} else if (row.transType == "AABC") {
		value = "Agent Assisted Billing Payment";
	} else if (row.transType == "CIBP") {
		value = "Client Inisiate Billing Payment";
	} else if (row.transType == "MINI_STATEMENT") {
		value = "Mini Statement";
	} else if (row.transType == "LOAD_PURCHASE") {
		value = "Load Purchase";
	} else if (row.transType == "SEND_REMITTANCE") {
		value = "Send Remittance";
	} else if (row.transType == "SELF_REMITTANCE") {
		value = "Self Remittance";
	} else {
		value = data;
	}

	return value;
};


var AccountStatusFormatter = function(data, type, row) {
	var labelClass 	= "";
	var fontIcon 	= "";
	if (row.accStatusCode == 'ACTIVE') {
		labelClass 	= 'medium';
		fontIcon	= 'check-circle-o';
	} else if (row.accStatusCode == 'ENROLLMENT') {
		labelClass 	= 'urgent';
		fontIcon	= 'address-card-o';
	} else if (row.accStatusCode == 'INACTIVE') {
		labelClass 	= 'low';
		fontIcon	= 'minus-circle';
	} else if (row.accStatusCode == 'WAIT_ACTIVATION') {
		labelClass 	= 'high';
		fontIcon	= 'clock-o';
	} else if (row.accStatusCode == 'EXPIRED') {
		labelClass 	= 'emergency';
		fontIcon	= 'calendar-times-o';
	} else if (row.accStatusCode == 'BLOCKED_PIN' || row.accStatusCode == 'BLOCKED_PASS' || row.accStatusCode == 'BLOCKED_ACTCODE') {
		labelClass 	= 'critical';
		fontIcon	= 'ban';
	} else {
		labelClass 	= 'default';
		fontIcon	= '';
	}

	return '<span class="label label-' + labelClass + '"><i class="fa fa-' + fontIcon + '"></i> <b>' + data + '</b> </span>';
};


var SmsStatusFormatter = function(data, type, row) {
	var labelClass 	= "";
	var fontIcon 	= "";
	if (row.smsStatusCode == 'SENT') {
		labelClass 	= 'success';
		fontIcon	= 'check';
	} else if (row.smsStatusCode == 'UNSENT') {
		labelClass 	= 'warning';
		fontIcon	= 'exclamation';
	} else {
		labelClass = 'default';
	}

	return '<span class="label label-' + labelClass + '"><i class="fa fa-' + fontIcon + '"></i> <b>' + data + '</b> </span>';
};	


var SuspiciousStatusFormatter = function(data, type, row) {
	var value = "";
	if (data == 'VALID') {
		value = '<span class="label label-success"><i class="fa fa-check"></i> <b>Valid</b> </span>';
	} else if (data == 'SUSPICIOUS') {
		value = '<span class="label label-critical"><i class="fa fa-flag"></i> <b>Suspicious<b> </span>';
	}

	return value;
};


var SmsLogStatusFormatter = function(data, type, row) {
	var value = "";
	if (data == 'SENT') {
		value = '<span class="label label-success"><i class="fa fa-check"></i> <b>Sent</b> </span>';
	} else if (data == 'FAILED') {
		value = '<span class="label label-danger"><i class="fa fa-exclamation"></i> <b>Failed</b> </span>';
	} else {
		value = '<span class="label label-default"> ' + data + ' </span>';
	}

	return value;
};	


var TransStatusFormatter = function(data, type, row) {
	var value = "";
	if (data == 'REQUEST') {
		value = '<span class="label label-status label-warning"><i class="fa fa-warning"></i> <b>Request</b> </span>';
	} else if (data == 'REJECTED') {
		value = '<span class="label label-status label-danger"><i class="fa fa-times"></i> <b>Rejected</b> </span>';
	} else if (data == 'CONFIRMED') {
		value = '<span class="label label-status label-success"><i class="fa fa-check"></i> <b>Confirmed</b> </span>';
	} else {
		value = '<span class="label label-status label-default"> ' + data + ' </span>';
	}

	return value;
};


var ComplexityLevelFormatter = function(data, type, row) {
	var value = "";
	if (data == 'LOW') {
		value = '<span class="label label-low"><i class="fa fa-star-o"></i> <b>Low</b> </span>';
	} else if (data == 'MEDIUM') {
		value = '<span class="label label-high"><i class="fa fa-star-half-o"></i> <b>Medium</b> </span>';
	} else if (data == 'CRITICAL') {
		value = '<span class="label label-critical"><i class="fa fa-star"></i> <b>Critical</b> </span>';
	} else {
		value = '<span class="label label-default"> ' + data + ' </span>';
	}

	return value;
};


var CSStatusFormatter = function(data, type, row) {
	var value = '<button class="btn btn-sm btn-warning" disabled><i class="fa fa-flag-o"></i> <b>Open</b></button>';
	if (row.statusCode == 'CLOSED') {
		value = '<button class="btn btn-sm btn-default" disabled><i class="fa fa-circle"></i> <b>Closed</b></button>';
	} else {
		if (row.action != null && row.action != '') {
			value = '<button class="btn btn-sm btn-success" onclick="closeTicket(' + row.id + ');"><i class="fa fa-check"></i> <b>Close</b></button>';
		}
	}

	return value;
};


var ReportStatusFormatter = function(data, type, row) {
	var value = "";
	if (data == 'WAITING') {
		value = '<span class="label label-default"><i class="fa fa-clock-o"></i> Waiting </span>';
	} else if (data == 'PROGRESS') {
		value = '<span class="label label-info"><i class="fa fa-spinner"></i> Progress </span>';
	} else if (data == 'COMPLETED') {
		value = '<span class="label label-success"><i class="fa fa-check-circle"></i> Completed </span>';
	} else if (data == 'FAILED') {
		value = '<span class="label label-danger"><i class="fa fa-warning"></i> Failed </span>';
	} else {
		value = '<span class="label label-default"> ' + data + ' </span>';
	}

	return value;
};

var BillsParameterFormatter = function(data, type, row) {
	var value = '';
	if (data == 1) {
		value = '<span class="label label-success"><i class="fa fa-check"></i> <b>Active</b> </span>';
	} else if (data == 0) {
		value = '<span class="label label-warning"><i class="fa fa-ban"></i> <b>Inactive</b> </span>';
	} else {
		value = '<i class="fa fa-exclamation text-warning"></i>';
	}

	return value;
};

var RemittanceFormatter = function(data, type, row) {
	var value = '';
	if (data == 0) {
		value = '<span class="label label-info"><i class="fa fa-refresh"></i> <b>Pending</b> </span>';
	} else if (data == 1) {
		value = '<span class="label label-success"><i class="fa fa-check-square-o"></i> <b>Claimed</b> </span>';
	} else if (data == 2) {
		value = '<span class="label label-warning"><i class="fa fa-ban"></i> <b>Cancelled</b> </span>';
	} else {
		value = '<i class="fa fa-exclamation text-warning"></i>';
	}

	return value;
};

var SyncBillerFormatter = function(data, type, row) {
	return '<a href="synchronize/' + data + '"><span class="label label-info"><i class="fa fa-refresh"></i><b>Sync</b></span></a>';
};

var DeviceFormatter = function(data, type, row) {
	var value = '';
	if (data == 1) {
		value = '<span class="label label-success"><i class="fa fa-check"></i> <b>Used</b> </span>';
	} else if (data == 0) {
		value = '<span class="label label-warning"><i class="fa fa-ban"></i> <b>Unused</b> </span>';
	} else {
		value = '<i class="fa fa-exclamation text-warning"></i>';
	}

	return value;
};

var StatusPartnerFormatter = function(data, type, row) {
	var value = '';
	if (data == 1) {
		value = '<span class="label label-success"><i class="fa fa-check"></i> <b>Active</b> </span>';
	} else if (data == 0) {
		value = '<span class="label label-warning"><i class="fa fa-ban"></i> <b>Inactive</b> </span>';
	} else {
		value = '<i class="fa fa-exclamation text-warning"></i>';
	}

	return value;
};

var MriGroupFormatter = function(data, type, row) {
	var value = '';
	if (data == 1) {
		value = '<span class="label label-success"><i class="fa fa-check"></i> <b>Yes</b> </span>';
	} else if (data == 0) {
		value = '<span class="label label-warning"><i class="fa fa-ban"></i> <b>No</b> </span>';
	} else {
		value = '<i class="fa fa-exclamation text-warning"></i>';
	}

	return value;
};