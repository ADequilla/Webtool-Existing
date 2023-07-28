<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Dashboard | Customer Service</title>
<script type="text/javascript">
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/cs/dashboard/search",
                "sServerMethod"	: "POST",
                "scrollX"		: true,
                "fnServerData"	: function (sSource, aoData, fnCallback) {
                	var ticketNo	= $.trim($("#searchTicketNo").val());
                	var cid			= $.trim($("#searchCid").val());
                    var submittedBy	= $.trim($("#searchSubmittedBy").val());
                    var startDate	= $.trim($("#searchStartDate").val());
                    var endDate		= $.trim($("#searchEndDate").val());
                    var concern		= $.trim($("#searchConcern").val());
                    var status 		= $.trim($("#searchStatus").val());
                    aoData.push({ "name": "ticketNo",	"value": ticketNo });
                    aoData.push({ "name": "cid",		"value": cid });
                    aoData.push({ "name": "submittedBy","value": submittedBy });
                    aoData.push({ "name": "startDate", 	"value": startDate });
                    aoData.push({ "name": "endDate", 	"value": endDate });
                    aoData.push({ "name": "concern", 	"value": concern });
                    aoData.push({ "name": "status", 	"value": status });
                    jQuery.ajax({
                        "dataType"	: 'json',
                        "type"		: "POST",
                        "url"		: sSource,
                        "data"		: aoData,
                        "success"	: fnCallback
                    });
                },
                "aoColumns": [
                    { "mDataProp": "ticketNo" },
                    { "mDataProp": "createdDate" },
                    { "mDataProp": "cid" },
                    { "mDataProp": "branch" },
                    { "mDataProp": "unit" },
                    { "mDataProp": "center" },
                    { "mDataProp": "clientName" },
                    { "mDataProp": "mobileNo" },
                    { "mDataProp": "submittedBy" },
                    { "mDataProp": "message" },
                    { "mDataProp": "concernName" },
                    { "mDataProp": "level" },
                    { "mDataProp": "concernTime" },
                    { "mDataProp": "transDesc" },
                    { "mDataProp": "assignedTo" },
                    { "mDataProp": "action" },
                    { "mDataProp": "closedDate" },
                    { "mDataProp": "timeElapsed" },
                    { "mDataProp": "statusDesc" },
                    { "mDataProp": fnBlank, "bSortable": false }
                ],
                "aoColumnDefs" : [
					{
						className	: "text-center",
						"mRender"	: ComplexityLevelFormatter,
						"aTargets"	: [11]
					},
					{
						className	: "text-center",
						"mRender"	: CSStatusFormatter,
						"aTargets"	: [18]
					},
					{
                    	class		: "text-center",
                        "mRender"	: function(data, type, row){
                        	if(row.statusCode != 'CLOSED'){
                        		return '<a href="${pageContext.request.contextPath}/cs/dashboard/edit/'+ row.id +'"><span class="icon-edit"><i class="fa fa-pencil"></i></span></a>';
                        	}
                            return '';
                        },
                        "aTargets"	: [19]
                    }
                ]
            });
            
            $("#btn-reset").click(function(){
            	$("#searchTicketNo").val("");
            	$("#searchCid").val("");
            	$("#searchSubmittedBy").val("");
            	$("#searchStartDate").val("");
            	$("#searchEndDate").val("");
            	$("#searchConcern").val("");
            	$("#searchStatus").val("");
            	
            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
            });
            
            
            
            popoverFunction.getUserPopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showUserPopup",
                modalId		: "popupUserTable",
                modalTitle	: "User List",
                hiddenId	: "searchSubmittedBy",
                varValue	: "usrName"
            });
        });
        
        
        
        function closeTicket(id){
        	var data = {"id":id};
			submit('/cs/dashboard/close', JSON.stringify(data), function (data) {
				$("#btn-search").click();
			});
        }

		if (localStorage.getItem('isPageOpen')) {
      alert('Page is already open in another tab!');
      window.location.href = 'about:blank'; 
    } else {
      localStorage.setItem('isPageOpen', true);
      window.addEventListener('beforeunload', function () {
        localStorage.removeItem('isPageOpen');
      });
    }
        
    </script>
</head>
<body>
	<div class="content">
		<div class="row">
			<div class="col-lg-5 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Customer Service</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/cs/dashboard/">Dashboard</a></li>
					</ul>
					<h3>
						<i class="fa fa-phone"></i> Customer Service
					</h3>
					<em>Dashboard</em>
				</div>
			</div>
			<div class="col-lg-7 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/cs/dashboard/create">
								<div class="quick-access-item bg-color-blue">
									<i class="fa fa-plus"></i>
									<h5>New CS Dashboard</h5>
									<em>add new CS Dashboard data</em>
								</div>
						</a></li>
					</ul>
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
							<input type="text" id="searchTicketNo" class="form-control"
								placeholder="Ticket Number">
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchStartDate" style="cursor: pointer;" type="text"
									class="form-control startdatepicker"
									placeholder="Posted Date Start"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchEndDate" style="cursor: pointer;" type="text"
									class="form-control enddatepicker"
									placeholder="Posted Date End"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<input type="text" id="searchCid" class="form-control"
								placeholder="CID">
						</div>
						<div class="col-sm-6">
							<select id="searchConcern" class="form-control">
								<option value="">-- Concern --</option>
								<c:forEach items="${listConcern}" var="concern">
									<option value="${concern.id}">${concern.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<input type="text" id="searchSubmittedBy" class="form-control"
								placeholder="Posted By">
						</div>
						<div class="col-sm-6">
							<select id="searchStatus" class="form-control">
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
						<i class="fa fa-table"></i> List of Customer Service Dashboard
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Ticket No.</th>
								<th>Date&Time Posted</th>
								<th>CID</th>
								<th>Branch</th>
								<th>Unit</th>
								<th>Center</th>
								<th>Client Name</th>
								<th>Mobile</th>
								<th>Posted By</th>
								<th>Details</th>
								<th>Type of Concern</th>
								<th>Complexity Level</th>
								<th>Turn Around Time (minutes)</th>
								<th>Transaction Type</th>
								<th>Assigned To</th>
								<th>Action Taken</th>
								<th>Date&Time Accomplished</th>
								<th>Time Elapsed (minutes)</th>
								<th>Status</th>
								<th></th>
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