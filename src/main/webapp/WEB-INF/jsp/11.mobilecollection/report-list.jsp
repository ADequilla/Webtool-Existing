<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>${REPORT_TITLE}| Report</title>
<script type="text/javascript">
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource": "${pageContext.request.contextPath}/mobilecollection/${REPORT_MENU}/search",
                "sServerMethod": "POST",
                "scrollX"		: true,
                "fnServerData": function (sSource, aoData, fnCallback) {
                    var submitedDateStart	= $.trim($("#searchSubmitedDateStart").val());
                    var submitedDateEnd		= $.trim($("#searchSubmitedDateEnd").val());
                    var reportId			= $.trim($("#searchId").val());
                    aoData.push({ "name": "submitedDateStart", "value": submitedDateStart });
                    aoData.push({ "name": "submitedDateEnd", 	"value": submitedDateEnd });
                    aoData.push({ "name": "reportId", 			"value": reportId });
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
                    { "mDataProp": "submitedDate" },
                    { "mDataProp": "completedDate" },
                    { "mDataProp": "status" },
                    { "mDataProp": fnBlank, "bSortable": false }
                ],
                "aoColumnDefs" : [
              		{	
   						className: "text-center",
   						"mRender": ReportStatusFormatter,
   						"aTargets": [4]
              		},
              		{
                    	class: "text-center",
                        "mRender": function(data, type, row){
                        	if(row.filePath == null){
                        		return '';
                        	}else{
                        		return '<a href="${pageContext.request.contextPath}/mobilecollection/${REPORT_MENU}/download/'+ row.id +'"><span class="icon-download"><i class="fa fa-download"></i></span></a>';	
                        	}
                        },
                        "aTargets": [5]
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
				$("#searchStatus").val("");
                $("#searchId").val("");
                
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
						<li>Mobile Collection</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/mobilecollection/${REPORT_MENU}/">${REPORT_TITLE}</a></li>
					</ul>
					<h3>
						<i class="fa fa-clone"></i> ${REPORT_TITLE}
					</h3>
					<em>Mobile Collection</em>
				</div>
			</div>
			<div class="col-lg-4 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/mobilecollection/${REPORT_MENU}/generate">
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
						<i class="fa fa-table"></i> List ${REPORT_TITLE}
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Report ID</th>
								<th style="width: 70pt;">Date Range</th>
								<th>Submitted Date</th>
								<th>Completed Date</th>
								<th>Status</th>
								<th>Download</th>
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