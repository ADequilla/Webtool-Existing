<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Remittance | Sent</title>
<script type="text/javascript">
	    $(document).ready(function() {
	        var oTable = $("#dataTable").dataTable({
	            "sAjaxSource": "${pageContext.request.contextPath}/monitoring/remittance-dashboard/searchAll",
	            "sServerMethod": "POST",
	            "scrollX"		: true,
	            "fnServerData": function (sSource, aoData, fnCallback) {
	                var startSendDate	= $.trim($("#startSendDate").val());
	                var endSendDate		= $.trim($("#endSendDate").val());
	                aoData.push({ "name": "startSendDate", "value": startSendDate });
	                aoData.push({ "name": "endSendDate", 	"value": endSendDate });
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
	                { "mDataProp": "processedByFullname" }
	            ]
	        });
        
            $("#btn-reset").click(function(){
                $("#startSendDate").val("");
                $("#endSendDate").val("");
                
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
	function DisableBackButton(){
		window.history.forward()
	}
	DisableBackButton();
	window.onload = DisableBackButton;
	window.onpageshow = function(evt) {if (evt.persisted) DisableBackButton}
	window.onunload = function(){ void (0)} 
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
						<li class="active">Sent</li>
					</ul>
					<h3>
						<i class="fa fa-clone"></i> Remittance
					</h3>
					<em>Sent</em>
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
						<i class="fa fa-table"></i> List of Sent Mobile Remittance
						Transaction
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th style="padding-bottom: 30px;">Mobile ID</th>
								<th style="padding-bottom: 30px;">Core ID</th>
								<th style="padding-bottom: 30px;">Remittance Reference
									Number</th>
								<th style="padding-bottom: 20px;">Sender <strong
									style="font-size: 10px;">(GivenName MiddleName
										SurName)</strong></th>
								<th style="padding-bottom: 20px;">Receiver <strong
									style="font-size: 10px;">(GivenName MiddleName
										SurName)</strong></th>
								<th style="padding-bottom: 35px;">Amount</th>
								<th style="padding-bottom: 25px;">Sender Mobile Number</th>
								<th style="padding-bottom: 30px;">Date&Time Send</th>
								<th style="padding-bottom: 20px;">Source Branch <strong
									style="font-size: 10px;">(Processed By)</strong></th>
								<th style="padding-bottom: 20px;">Processed By Fullname <strong
									style="font-size: 10px;">(Member/Non-member/Agent)</strong></th>
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