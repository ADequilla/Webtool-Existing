<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Failed MC User's Information | Mobile Collection</title>
<script type="text/javascript">
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource": "${pageContext.request.contextPath}/mobilecollection/failedinformation/search",
                "sServerMethod": "POST",
                "scrollX"		: true,
                "fnServerData": function (sSource, aoData, fnCallback) {
                    var submitedDateStart	= $.trim($("#searchSubmitedDateStart").val());
                    var submitedDateEnd		= $.trim($("#searchSubmitedDateEnd").val());
                    var reportId			= $.trim($("#searchId").val());
                    var userUploader	= $.trim($("#searchUserUploader").val());
                    aoData.push({ "name": "submitedDateStart", "value": submitedDateStart });
                    aoData.push({ "name": "submitedDateEnd", 	"value": submitedDateEnd });
                    aoData.push({ "name": "reportId", 			"value": reportId });
                    aoData.push({ "name": "userUploader", "value": userUploader });
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
                    { "mDataProp": "uploadDate" },
                    { "mDataProp": "fileName" },
                    { "mDataProp": "uploadBy"},
                    { "mDataProp": fnBlank, "bSortable": false },
                ],
                "aoColumnDefs" : [
                	{
                    	class: "text-center",
                        "mRender": function(data, type, row){
                        		return row.uploadBy.givenName+' '+row.uploadBy.middleName+' '+row.uploadBy.lastName;
                        },
                        "aTargets": [3]
                    },
              		{
                    	class: "text-center",
                        "mRender": function(data, type, row){
                        		return '<button onclick="download('+row.id+')"><span class="icon-download"><i class="fa fa-download"></i></span></button>';	
                        },
                        "aTargets": [4]
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
                $("#searchId").val("");
                $("#searchUserUploader").val("");
                
            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
            });
            
        });
        
        function download(id) {
        	window.location = "${pageContext.request.contextPath}/mobilecollection/failedinformation/download/" + id;
        }

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
						<li>Mobile Collection</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/mobilecollection/failedinformation/">Failed
								MC User's Information</a></li>
					</ul>
					<h3>
						<i class="fa fa-clone"></i> Failed MC User's Information
					</h3>
					<em>Mobile Collection</em>
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
							<input type="number" id="searchId" class="form-control"
								placeholder="Report ID">
						</div>
						<div class="col-sm-6">
							<input type="text" id="searchUserUploader" class="form-control"
								placeholder="User Uploader">
						</div>
					</div>
					<div class="row">
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchSubmitedDateStart" style="cursor: pointer;"
									type="text" class="form-control datepicker"
									placeholder="Start Date Uploaded"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchSubmitedDateEnd" style="cursor: pointer;"
									type="text" class="form-control datepicker"
									placeholder="End Date Uploaded"> <span
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
						<i class="fa fa-table"></i> Failed MC user's information
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Report ID</th>
								<th>Date&Time File Upload</th>
								<th>File Name</th>
								<th>Web User Uploader</th>
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