<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Transaction Log | Monitoring</title>
<script type="text/javascript">
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource": "${pageContext.request.contextPath}/monitoring/remittance/search",
                "sServerMethod": "POST",
                "scrollX"		: true,
                "fnServerData": function (sSource, aoData, fnCallback) {
                    var startSendDate		= $.trim($("#startSendDate").val());
                    var endSendDate			= $.trim($("#endSendDate").val());
                    var sourceBranch		= $.trim($("#sourceBranch").val());
                    var targetBranch		= $.trim($("#targetBranch").val());
                    var status				= $.trim($("#status").val());
                    var senderMobileNumber	= $.trim($("#senderMobileNumber").val());
                    var mobileReference		= $.trim($("#mobileReference").val());
                    aoData.push({ "name": "startSendDate", "value": startSendDate });
                    aoData.push({ "name": "endSendDate",  "value": endSendDate });
                    aoData.push({ "name": "sourceBranch", "value": sourceBranch });
					aoData.push({ "name": "targetBranch", "value": targetBranch });
					aoData.push({ "name": "status", "value": status });
					aoData.push({ "name": "senderMobileNumber", 	"value": senderMobileNumber });
					aoData.push({ "name": "mobileReference", 	"value": mobileReference });
                    jQuery.ajax({
                        "dataType": 'json',
                        "type": "POST",
                        "url": sSource,
                        "data": aoData,
                        "success": fnCallback
                    });
                },
                "aoColumns": [
                	{ "mDataProp": "sentMobileRefId" },
                	{ "mDataProp": "sentCoreRefId" },
                	{ "mDataProp": "referenceNumber" },
	                { "mDataProp": "senderName" },
	                { "mDataProp": "receiverName"},
	                { "mDataProp": "amount"},
	                { "mDataProp": "senderMobileNumber"},
	                { "mDataProp": "createdDate" },
	                { "mDataProp": "sourceBranch" },
	                { "mDataProp": "processedByFullname" },
	                { "mDataProp": "updatedDate" },
	                { "mDataProp": "targetBranch" },
	                { "mDataProp": "disbursedByFullname" },
	                { "mDataProp": "cancelledDate" },
	                { "mDataProp": "cancelledByFullname" },
	                { "mDataProp": "status" },
	                { "mDataProp": "claimedCoreRefId" },
                	{ "mDataProp": "claimedMobileRefId" },
                ],
                "aoColumnDefs" : [
            		{	
    					class		: "text-center",
    		            "mRender"	: RemittanceFormatter,
    		            "aTargets"	: [15]
    				}
            	]
            });
            
            $("#btn-reset").click(function(){
                $("#startSendDate").val("");
                $("#endSendDate").val("");
                $("#sourceBranch").val("");
                $("#sourceBranchDesc").val("");
                $("#targetBranchDesc").val("");
                $("#targetBranch").val("");
                $("#status").val("");
                $("#senderMobileNumber").val("");
                $("#mobileReference").val("");
                
            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
            });
            
            popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showSourceBranchPopup",
                modalTitle	: "Branch List",
                hiddenId	: "sourceBranch",
                varValue	: "description",
               	ajax_data	: [
						{
							fieldVar	: "filter",
							fieldValue	: "Y"
						},
       					{
       						fieldVar: "type",
       						fieldValue: "BRANCH"
       					}
            		]
            });
            popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showTargetBranchPopup",
                modalTitle	: "Branch List",
                hiddenId	: "targetBranch",
                varValue	: "description",
               	ajax_data	: [
						{
							fieldVar	: "filter",
							fieldValue	: "Y"
						},
       					{
       						fieldVar: "type",
       						fieldValue: "BRANCH"
       					}
            		]
            });
        });

		// 	if (localStorage.getItem('isPageOpen')) {
    //   alert('Page is already open in another tab!');
    //   window.location.href = '${pageContext.request.contextPath}/logout'; 
    // } else {
    //   localStorage.setItem('isPageOpen', true);
    //   window.addEventListener('beforeunload', function () {
    //     localStorage.removeItem('isPageOpen');
    //   });
    // }
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
			<div class="col-lg-8 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Monitoring</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/monitoring/remittance/">Remittance
								Transaction Log</a></li>
					</ul>
					<h3>
						<i class="fa fa-clone"></i> Remittance Transaction Log
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
						<div class="col-sm-3">
							<div class="input-group">
								<input id="startSendDate" style="cursor: pointer;" type="text"
									class="form-control datepicker" placeholder="Start Send Date">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="endSendDate" style="cursor: pointer;" type="text"
									class="form-control datepicker" placeholder="End Send Date">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-6">
							<select id="status" class="form-control">
								<option value="">-- Status --</option>
								<c:forEach items="${listRemittanceStatus}" var="lookup">
									<option value="${lookup.value}">${lookup.description}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<input type="text" id="senderMobileNumber" class="form-control"
								placeholder="Sender Mobile Number">
						</div>
						<div class="col-sm-6">
							<input type="hidden" id="sourceBranch">
							<div class="input-group">
								<input id="sourceBranchDesc" type="text"
									style="cursor: pointer;"
									class="form-control showSourceBranchPopup" readonly="readonly"
									placeholder="Source Branch" /> <span class="input-group-addon"><i
									class="fa fa-search"></i></span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<input type="text" id="mobileReference" class="form-control"
								placeholder="Mobile Reference">
						</div>
						<div class="col-sm-6">
							<input type="hidden" id="targetBranch">
							<div class="input-group">
								<input id="targetBranchDesc" type="text"
									style="cursor: pointer;"
									class="form-control showTargetBranchPopup" readonly="readonly"
									placeholder="Target Branch" /> <span class="input-group-addon"><i
									class="fa fa-search"></i></span>
							</div>
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
						<i class="fa fa-table"></i> List of Transaction Log
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th style="padding-bottom: 40px;">Mobile Ref ID (Sent)</th>
								<th style="padding-bottom: 40px;">Core Ref ID (Sent)</th>
								<th style="padding-bottom: 40px;">Remittance Ref ID</th>
								<th style="padding-bottom: 25px;">Sender <strong
									style="font-size: 10px;">(GivenName MiddleName
										SurName)</strong></th>
								<th style="padding-bottom: 25px;">Receiver <strong
									style="font-size: 10px;">(GivenName MiddleName
										SurName)</strong></th>
								<th style="padding-bottom: 45px;">Amount</th>
								<th style="padding-bottom: 35px;">Sender Mobile Number</th>
								<th style="padding-bottom: 40px;">Date&Time Send</th>
								<th style="padding-bottom: 25px;">Source Branch <strong
									style="font-size: 10px;">(Processed By)</strong></th>
								<th style="padding-bottom: 25px;">Processed By Fullname <strong
									style="font-size: 10px;">(Member/Non-member/Agent)</strong></th>
								<th style="padding-bottom: 40px;">Date&Time Receive</th>
								<th style="padding-bottom: 25px;">Target Branch <strong
									style="font-size: 10px;">(Disbursed By)</strong></th>
								<th>Disbursed By FullName <strong style="font-size: 10px;">(Agent/MBO
										Teller/Branch Teller)</strong></th>
								<th style="padding-bottom: 40px;">Date&Time Cancelled</th>
								<th style="padding-bottom: 25px;">Cancelled By FullName <strong
									style="font-size: 10px;">(Member/Non-member/Agent)</strong></th>
								<th style="padding-bottom: 25px;">Status <strong
									style="font-size: 10px;">(Sent/Claimed/Cancelled)</strong></th>
								<th style="padding-bottom: 40px;">Core Ref ID
									(Claimed/Cancelled)</th>
								<th style="padding-bottom: 40px;">Mobile Ref ID
									(Claimed/Cancelled)</th>
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