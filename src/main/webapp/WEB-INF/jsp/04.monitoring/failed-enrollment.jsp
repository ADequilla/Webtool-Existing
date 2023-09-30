<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Failed Enrollment List | Monitoring</title>
<script type="text/javascript">
        $(document).ready(function() {
        	var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/monitoring/failed-enrollment-list/search",
                "sServerMethod"	: "GET",
                "bProcessing"	: false,
                "fnServerData"	: function (sSource, aoData, fnCallback) {
					var searchDateStart 	= $.trim($("#searchDateStart").val());
					var searchDateEnd 	= $.trim($("#searchDateEnd").val());
                	var accountNumber	= $.trim($("#accountNumber").val());
					var dateBirth 	= $.trim($("#dateBirth").val());
					var mobileNumber 	= $.trim($("#mobileNumber").val());
					var clientType 	= $.trim($("#clientType").val());
					var errorMessage 	= $.trim($("#errorMessage").val());
					
                    aoData.push({ "name": "searchDateStart", "value": searchDateStart});
                    aoData.push({ "name": "searchDateEnd", "value": searchDateEnd });
                    aoData.push({ "name": "accountNumber", "value": accountNumber });
					aoData.push({ "name": "dateBirth", "value": dateBirth });
					aoData.push({ "name": "mobileNumber", "value": mobileNumber });
					aoData.push({ "name": "clientType", "value": clientType });
					aoData.push({ "name": "errorMessage", "value": errorMessage });
                    jQuery.ajax({
                        "type"		: "GET",
                        "url"		: sSource,
                        "data"		: aoData,
                        "success"	: fnCallback
                    });
                }, 
                "aoColumns": [
                    { "mDataProp": "createdDate" },
                    { "mDataProp": "accountNumber" },
					{ "mDataProp": "dateBirth" },
					{ "mDataProp": "mobileNumber" },
					{ "mDataProp": "clientType" },
					{ "mDataProp": "deviceId" },
					{ "mDataProp": "deviceModel" },
					{ "mDataProp": "errorMessage" }
                ]
            });
            
        	$("#btn-search").click(function(){
                oTable.fnDraw();
            });
			
            $("#btn-reset").click(function(){
            	$('#accountNumber').val("");
				$('#dateBirth').val("");
				$('#mobileNumber').val("")
				$('#searchDateStart').val("")
				$('#searchDateEnd').val("")
				$('#clientType').val("")
				$('#errorMessage').val("")
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
			<div class="col-lg-5 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Monitoring</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/monitoring/failed-enrollment-list/">Failed
								Enrollment List</a></li>
					</ul>
					<h3>
						<i class="fa fa-envelope"></i> Failed Enrollment List
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
								<input id="searchDateStart" style="cursor: pointer;" type="text"
									class="form-control startdatepicker"
									placeholder="Enrolled Date Start"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchDateEnd" style="cursor: pointer;" type="text"
									class="form-control enddatepicker"
									placeholder="Enrolled Date End"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>

						<div class="col-sm-6">
							<input type="text" placeholder="Account Number"
								id="accountNumber" class="form-control">
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="input-group">
								<input id="dateBirth" style="cursor: pointer;" type="text"
									class="form-control startdatepicker" placeholder="Date Birth">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>

						<div class="col-sm-6">
							<input type="text" placeholder="Mobile Number" id="mobileNumber"
								class="form-control">
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<select id="clientType" class="form-control">
								<option value="">-- Client Type --</option>
								<c:forEach items="${listClientType}" var="lookup">
									<option value="${lookup.value}">${lookup.description}</option>
								</c:forEach>
							</select>
						</div>

						<div class="col-sm-6">
							<input type="text" placeholder="Error Message" id="errorMessage"
								class="form-control">
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
						<i class="fa fa-table"></i> Failed Enrollment List
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Date & Time</th>
								<th>Account Number</th>
								<th>Date of Birth</th>
								<th>Mobile Number</th>
								<th>Client Type</th>
								<th>Device ID</th>
								<th>Device Model</th>
								<th>Error Message</th>
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