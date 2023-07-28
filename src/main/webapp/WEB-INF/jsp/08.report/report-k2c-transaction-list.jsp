<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>${REPORT_TITLE}| Report</title>
<script type="text/javascript">
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource": "${pageContext.request.contextPath}/report/${REPORT_MENU}/search-report-list",
                "sServerMethod": "POST",
                "scrollX"		: true,
                "fnServerData": function (sSource, aoData, fnCallback) {
                    var submitedDateStart	= $.trim($("#searchSubmitedDateStart").val());
                    var submitedDateEnd		= $.trim($("#searchSubmitedDateEnd").val());
                    var completedDateStart	= $.trim($("#searchCompletedDateStart").val());
                    var completedDateEnd	= $.trim($("#searchCompletedDateEnd").val());
                    var status				= $.trim($("#searchStatus").val());
                    var reportId			= $.trim($("#searchId").val());
                    var branch				= $.trim($("#searchBranchCode").val());
                    var username			= $.trim($("#searchUsername").val());
                    aoData.push({ "name": "submitedDateStart", "value": submitedDateStart });
                    aoData.push({ "name": "submitedDateEnd", 	"value": submitedDateEnd });
					aoData.push({ "name": "completedDateStart", "value": completedDateStart });
                    aoData.push({ "name": "completedDateEnd", 	"value": completedDateEnd });
					aoData.push({ "name": "status", 			"value": status });
                    aoData.push({ "name": "reportId", 			"value": reportId });
					aoData.push({ "name": "branch", 			"value": branch });
                    aoData.push({ "name": "username", 			"value": username });
                    jQuery.ajax({
                        "dataType": 'json',
                        "type": "POST",
                        "url": sSource,
                        "data": aoData,
                        "success": fnCallback
                    });
                },
                "aoColumns": [
                    { "mDataProp": "id" },
                    { "mDataProp": "criteria" },
                    { "mDataProp": "username"},
                    { "mDataProp": "branches"},
                    { "mDataProp": "roles"},
                    { "mDataProp": "submitedDate" },
                    { "mDataProp": "completedDate" },
                    { "mDataProp": "status" },
                    { "mDataProp": fnBlank, "bSortable": false },
                    { "mDataProp": "remark"}
                ],
                "aoColumnDefs" : [
              		{	
   						className: "text-center",
   						"mRender": ReportStatusFormatter,
   						"aTargets": [7]
              		},
              		{
                    	class: "text-center",
                        "mRender": function(data, type, row){
                        	if(row.filePath == null){
                        		return '';
                        	}else{
                        		return '<a href="${pageContext.request.contextPath}/report/${REPORT_MENU}/download/'+ row.id +'"><span class="icon-download"><i class="fa fa-download"></i></span></a>';	
                        	}
                        },
                        "aTargets": [8]
                    }
            	]
            });

			$("#searchId").on("input", function(e) {
            var value = $(this).val();
            $(this).val(value.replace(/[^0-9]/g, ''));
        });
            
            $("#btn-reset").click(function(){
                $("#searchSubmitedDateStart").val("");
                $("#searchSubmitedDateEnd").val("");
				$("#searchCompletedDateStart").val("");
                $("#searchCompletedDateEnd").val("");
				$("#searchStatus").val("");
                $("#searchId").val("");
				$("#searchBranchCode").val("");
				$("#searchBranchDesc").val("");
                $("#searchUsername").val("");
                
            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
            });
            
            popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showBranchPopup",
                modalTitle	: "Branch List",
                hiddenId	: "searchBranchCode",
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
			<div class="col-lg-8 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Report</li>
						<li><a
							href="${pageContext.request.contextPath}/report/${REPORT_MENU}/">${REPORT_TITLE}</a></li>
						<li class="active">Report List</li>
					</ul>
					<h3>
						<i class="fa fa-clone"></i> ${REPORT_TITLE}
					</h3>
					<em>Report</em>
				</div>
			</div>
			<div class="col-lg-4 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/report/${REPORT_MENU}/generate">
								<div class="quick-access-item bg-color-green">
									<i class="fa fa-file-pdf-o"></i>
									<h5>New Report</h5>
									<em>Generate New Report</em>
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
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchSubmitedDateStart" style="cursor: pointer;"
									type="text" class="form-control datepicker"
									placeholder="Submited Date Start"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchSubmitedDateEnd" style="cursor: pointer;"
									type="text" class="form-control datepicker"
									placeholder="Submited Date End"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-6">
							<input type="number" id="searchId" class="form-control"
								placeholder="Report ID">
						</div>
					</div>
					<div class="row">
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchCompletedDateStart" style="cursor: pointer;"
									type="text" class="form-control datepicker"
									placeholder="Completed Date Start"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchCompletedDateEnd" style="cursor: pointer;"
									type="text" class="form-control datepicker"
									placeholder="Completed Date End"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<!-- <div class="col-sm-6">
							<input type="hidden" id="searchBranchCode">
							<div class="input-group">
								<input id="searchBranchDesc" type="text"
									style="cursor: pointer;" class="form-control showBranchPopup"
									readonly="readonly" placeholder="Branch" /> <span
									class="input-group-addon"><i class="fa fa-search"></i></span>
							</div>
						</div> -->

						<div class="col-sm-6">
							<div class="input-group">
								<div class="col-sm-6">
									<input type="text" id="searchUsername" class="form-control"
										placeholder="Username">
								</div>
							</div>
						</div>

					</div>
					<div class="row">
						<div class="col-sm-6">
							<select id="searchStatus" class="form-control">
								<option value="">-- Status --</option>
								<c:forEach items="${listReportStatus}" var="lookup">
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
						<i class="fa fa-table"></i> List of K2C Transaction Reports
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Report ID</th>
								<th style="width: 70pt;">Date Range</th>
								<th>Username</th>
								<th>Branch</th>
								<th>Role</th>
								<th>Submitted Date</th>
								<th>Completed Date</th>
								<th>Status</th>
								<th>Download</th>
								<th>Remark</th>
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