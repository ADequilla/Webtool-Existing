<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Transaction Log | Monitoring</title>
<script type="text/javascript">
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/monitoring/transactionLog/search",
                "sServerMethod"	: "POST",
                "bProcessing"	: false,
                "scrollX"		: true,
                "fnServerData"	: function (sSource, aoData, fnCallback) {
                    var coreId			= $.trim($("#coreId").val());
                    var mobileId		= $.trim($("#mobileId").val());
                    var transType 	    = $.trim($("#searchTransType").val());
                    var dateStart		= $.trim($("#searchDateStart").val());
                    var dateEnd			= $.trim($("#searchDateEnd").val());
                    var sourceCid      = $.trim($("#sourceCid").val()); 
                    var targetCid      = $.trim($("#targetCid").val()); 
                    var sourceAccount	= $.trim($("#sourceAccount").val());
                    var targetAccount   = $.trim($("#targetAccount").val()); 
                    var status      	= $.trim($("#status").val()); 
                   
                    
                    aoData.push({ "name": "coreId",			"value": coreId });
                    aoData.push({ "name": "mobileId",		"value": mobileId });
                    aoData.push({ "name": "transType",		"value": transType });
                    aoData.push({ "name": "dateStart",		"value": dateStart });
                    aoData.push({ "name": "dateEnd",		"value": dateEnd });
                    aoData.push({ "name": "sourceCid",	    "value": sourceCid });
                    aoData.push({ "name": "targetCid",	    "value": targetCid });
                    aoData.push({ "name": "sourceAccount",	"value": sourceAccount });
                    aoData.push({ "name": "targetAccount",	"value": targetAccount });
                    aoData.push({ "name": "status",	    	"value": status });
                    jQuery.ajax({
                        "dataType"	: 'json',
                        "type"		: "POST",
                        "url"		: sSource,
                        "data"		: aoData,
                        "success"	: fnCallback
                    });
                },
                "order"		: [ 16, "desc" ],  
                "aoColumns"	: [
                    { "mDataProp": "transMobileRefNo" },
                    { "mDataProp": "transCoreRefNo" },
					{ "mDataProp": "sourceBranch" ,  "defaultContent": "" },
					
					{ "mDataProp": "sourceCid" ,  "defaultContent": "" },
					{ "mDataProp": "sourceClientType" ,  "defaultContent": "" },
					{ "mDataProp": "sourceAccountTypeName" ,  "defaultContent": "" },
					
	            	{ "mDataProp": "account",  "defaultContent": "" },
                    { "mDataProp": "sourceName" , "defaultContent": ""},
                    { "mDataProp": "refBranch", "defaultContent": "" },
                    
					{ "mDataProp": "targetCid" ,  "defaultContent": "" },
					{ "mDataProp": "bankName" ,  "defaultContent": "" },
					{ "mDataProp": "targetClientType" ,  "defaultContent": "" },
					{ "mDataProp": "targetAccountTypeName" ,  "defaultContent": "" },
					
                    { "mDataProp": "targetAccount" },
                    { "mDataProp": "targetName" },
                    { "mDataProp": "transType" },
                    { "mDataProp": "transAmount" },
                    { "mDataProp": "transactionCharge" },
                    { "mDataProp": "agentIncome" },
                    { "mDataProp": "bankIncome" },
                    { "mDataProp": "bancnetIncome" },
                    { "mDataProp": "transDate" },
                    { "mDataProp": "transPostDate" },
                    { "mDataProp": "status" },
                    { "mDataProp": "msg" },
                    { "mDataProp": "agentFeatureStr" },
                    { "mDataProp": "arOrNumber" },
                    { "mDataProp": "transRemark" },
                ]
            });
            
            $("#btn-reset").click(function(){
            	$("#coreId").val("");
            	$("#mobileId").val("");
            	$("#searchTransType").val("");
           	  	$("#searchDateStart").val("");
                $("#searchDateEnd").val("");
                $("#sourceAccount").val("");
                $("#targetAccount").val("");
                $("#sourceCid").val("");
                $("#targetCid").val("");
				$("#status").val("");
            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
            });
            
        });

		if (localStorage.getItem('isPageOpen')) {
      alert('Page is already open in another tab!');
      window.location.href = '${pageContext.request.contextPath}/logout'; 
    } else {
      localStorage.setItem('isPageOpen', true);
      window.addEventListener('beforeunload', function () {
        localStorage.removeItem('isPageOpen');
      });
    }
	// function DisableBackButton(){
// 		window.history.forward()
// 	}
// 	DisableBackButton();
// 	window.onload = DisableBackButton;
// 	window.onpageshow = function(evt) {if (evt.persisted) DisableBackButton}
// 	window.onunload = function(){ void (0)}
    </script>
</head>
<body>
	<div class="content">
		<div class="row">
			<div class="col-lg-5 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Monitoring</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/monitoring/transactionLog/">Transaction
								Log</a></li>
					</ul>
					<h3>
						<i class="fa fa-list-alt"></i> Transaction Log
					</h3>
					<em>Monitoring</em>
				</div>
			</div>
		</div>

		<!-- main -->
		<div class="main-content">
			<!-- SEARCH DATA TABLE -->
			<div class="widget widget-table">
				<div class="widget-header">
					<h3>
						<i class="fa fa-search"></i>
					</h3>
				</div>
				<div class="widget-content-search">

					<div class="row">
						<div class="col-sm-6">
							<input type="text" id="coreId" class="form-control"
								placeholder="Core ID">
						</div>
						<div class="col-sm-6">
							<input type="text" id="mobileId" class="form-control"
								placeholder="Mobile ID">
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<select id="searchTransType" class="form-control">
								<option value="">-- Transaction --</option>
								<c:forEach items="${listTransType}" var="lookup">
									<option value="${lookup.value}">${lookup.description}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchDateStart" style="cursor: pointer;" type="text"
									class="form-control startdatepicker" placeholder="Date Start">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchDateEnd" style="cursor: pointer;" type="text"
									class="form-control enddatepicker" placeholder="Date End">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<input type="text" id="sourceCid" class="form-control"
								placeholder="Source CID">
						</div>
						<div class="col-sm-6">
							<input type="text" id="targetCid" class="form-control"
								placeholder="Target CID">
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<input type="text" id="sourceAccount" class="form-control"
								placeholder="Source Account">
						</div>
						<div class="col-sm-6">
							<input type="text" id="targetAccount" class="form-control"
								placeholder="Target Account">
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<select id="status" class="form-control">
								<option value="">-- Status --</option>
								<c:forEach items="${listStatus}" var="lookup">
									<option value="${lookup.value}">${lookup.description}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="row widget-button-search">
						<div class="col-sm-6">
							<div class="form-group">
								<a id="btn-search" class="btn btn-custom-primary " type="button"><i
									class="fa fa-search"></i> Search</a> <a id="btn-reset"
									class="btn btn-default" type="button"><i
									class="fa fa-refresh"></i> Reset</a>
							</div>
						</div>
					</div>

				</div>
			</div>
			<!-- END SEARCH DATA TABLE -->

			<!-- SHOW HIDE COLUMNS DATA TABLE -->
			<div class="widget widget-table">
				<div class="widget-header">
					<h3>
						<i class="fa fa-table"></i>Transaction Log
					</h3>
				</div>



				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Mobile ID</th>
								<th>Core ID</th>
								<th>Source Branch</th>

								<th>Source CID</th>
								<th>Source Cid Client Type</th>
								<th>Source Account Type</th>

								<th>Source Account</th>
								<th>Source Name</th>
								<th>Target Branch</th>

								<th>Target CID</th>
								<th>Bank Name</th>
								<th>Target Cid Client Type</th>
								<th>Target Account Type</th>

								<th>Target Account</th>
								<th>Target Name</th>
								<th>Transaction Type</th>
								<th>Transaction Amount</th>
								<th>Transaction Charge</th>
								<th>Agent Income</th>
								<th>Bank Income</th>
								<th>AP Bancnet Instapay</th>
								<th>Date & Time</th>
								<th>Post Date & Time</th>
								<th>Status</th>
								<th>Message</th>
								<th>With Agent Features</th>
								<th>AR/OR Number</th>
								<th>Charge's Core Ref ID</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<!-- END SHOW HIDE COLUMNS DATA TABLE -->
		</div>
		<!-- /main-content -->
	</div>
	<!-- /main -->
</body>
</html>