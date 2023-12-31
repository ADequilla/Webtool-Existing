<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Remittance | Cancelled</title>
<script type="text/javascript">
	    $(document).ready(function() {
	        var oTable = $("#dataTable").dataTable({
	            "sAjaxSource": "${pageContext.request.contextPath}/monitoring/remittance-dashboard/searchCancelled",
	            "sServerMethod": "POST",
	            "scrollX"		: true,
	            "fnServerData": function (sSource, aoData, fnCallback) {
	                var startCancelledDate	= $.trim($("#startCancelledDate").val());
	                var endCancelledDate		= $.trim($("#endCancelledDate").val());
	                aoData.push({ "name": "startCancelledDate", "value": startCancelledDate });
	                aoData.push({ "name": "endCancelledDate", 	"value": endCancelledDate });
	                aoData.push({ "name": "status", 	"value": 2 });
	                jQuery.ajax({
	                    "dataType": 'json',
	                    "type": "POST",
	                    "url": sSource,
	                    "data": aoData,
	                    "success": fnCallback
	                });
	            },
	            "aoColumns": [
	            	{ "mDataProp": "claimedMobileRefId" },
	            	{ "mDataProp": "claimedCoreRefId" },
	            	{ "mDataProp": "referenceNumber" },
	                { "mDataProp": "senderName" },
	                { "mDataProp": "receiverName"},
	                { "mDataProp": "amount"},
	                { "mDataProp": "senderMobileNumber"},
	                { "mDataProp": "cancelledDate" },
	                { "mDataProp": "sourceBranch" },
	                { "mDataProp": "cancelledByFullname" },
	            ]
	        });
	        
            $("#btn-reset").click(function(){
                $("#startCancelledDate").val("");
                $("#endCancelledDate").val("");
                
            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
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
						<li>Remittance</li>
						<li><a
							href="${pageContext.request.contextPath}/monitoring/remittance-dashboard/">Dashboard</a></li>
						<li class="active">Cancelled</li>
					</ul>
					<h3>
						<i class="fa fa-clone"></i> Remittance
					</h3>
					<em>Cancelled</em>
				</div>
			</div>
		</div>

		<!-- main -->
		<div class="main-content">
			<!-- SEARCH DATA TABLE -->
			<div class="widget widget-table">
				<div class="widget-header">
					<h3>
						<i class="fa fa-search"></i>Remittance
					</h3>
				</div>
				<div class="widget-content-search">

					<div class="row">
						<div class="col-sm-3">
							<div class="input-group">
								<input id="startCancelledDate" style="cursor: pointer;"
									type="text" class="form-control datepicker"
									placeholder="Start Cancelled Date"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="endCancelledDate" style="cursor: pointer;"
									type="text" class="form-control datepicker"
									placeholder="End Cancelled Date"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
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
						<i class="fa fa-table"></i> List of Cancelled Mobile Remittance
						Transaction
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Mobile ID</th>
								<th>Core ID</th>
								<th>Remittance Reference Number</th>
								<th>Sender <strong style="font-size: 10px;">(GivenName
										MiddleName SurName)</strong></th>
								<th>Receiver <strong style="font-size: 10px;">(GivenName
										MiddleName SurName)</strong></th>
								<th>Amount</th>
								<th>Sender Mobile Number</th>
								<th>Date&Time Cancelled</th>
								<th>Source Branch <strong style="font-size: 10px;">(Cancelled
										By)</strong></th>
								<th>Cancelled By Fullname <strong style="font-size: 10px;">(Member/Non-member/Agent)</strong></th>
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