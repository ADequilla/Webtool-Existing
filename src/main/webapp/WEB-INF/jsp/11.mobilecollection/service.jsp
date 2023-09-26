<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>MC Service DownTime | Mobile Collection</title>
<script type="text/javascript">
		$(document).ready(function() {
	    	var oTable = $("#dataTable").dataTable({
	        	"sAjaxSource"	: "${pageContext.request.contextPath}/mobilecollection/service/search",
	            "sServerMethod"	: "POST",
	            "fnServerData"	: function (sSource, aoData, fnCallback) {
	            	 var startDate	= $.trim($("#searchStartDate").val());
					 var endDate	= $.trim($("#searchEndDate").val());
					 var desc		= $.trim($("#searchDesc").val());
	                 aoData.push({ "name": "startDate", "value": startDate });
	                 aoData.push({ "name": "endDate", 	"value": endDate });
					 aoData.push({ "name": "desc",		"value": desc });
	            	jQuery.ajax({
	                	"type"		: "GET",
	                    "url"		: sSource,
	                    "data"		: aoData,
	                    "success"	: fnCallback
	                });
				},
	            "aoColumns": [
					{ "mDataProp": "desc", },
					{ "mDataProp": "startDate" },
					{ "mDataProp": "endDate" },
					{ "mDataProp": "id", "bSortable": false },
					{ "mDataProp": "id", "bSortable": false }
				],
	            "aoColumnDefs" : [
					{
	            		class		: "text-center",
	                    "mRender"	: EditlinkFormatter,
	                    "aTargets"	: [3]
	                },
					{
	                	class		: "text-center",
	                    "mRender"	: CheckboxFormatter,
	                    "aTargets"	: [4]
	                }				
	            ]
			});
			
			$("#btn-search").click(function(){
	        	oTable.fnDraw();
			});
			
			$("#btn-reset").click(function(){
		        $('#searchStartDate').val("");
				$('#searchEndDate').val("");
				$('#searchDesc').val("");
	
				oTable.fnDraw();
			});
			
			
			
			$("#btn-delete").click(function(){
	        	var data = [];
	            $('#dataTable').find('tr').each(function () {
	    			var checkbox = $(this).find('input[type="checkbox"]');
	   				if (checkbox.is(':checked')){
	        			var row = {"id" : checkbox.val(), "state" : checkbox.attr('class')};
	        			data.push(row);
	   				}
				});
	            	
	            if(data.length > 0){
	    			submit('/mobilecollection/service/delete', JSON.stringify(data), function (data) {
	    				oTable.fnDraw();
	    			});
	           	} else {
	           		BootstrapDialog.show({
	                    message: 'Please select at least one data.',
	                    type: BootstrapDialog.TYPE_WARNING
                	});
	           	}
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
						<li>Mobile Collection</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/mobilecollection/service/">MC
								Service DownTime</a></li>
					</ul>
					<h3>
						<i class="fa fa-server fa-fw"></i>MC Service DownTime
					</h3>
					<em>Mobile Collection</em>
				</div>
			</div>
			<div class="col-lg-7 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/mobilecollection/service/create">
								<div class="quick-access-item bg-color-blue">
									<i class="fa fa-plus"></i>
									<h5>New MC Service DownTime</h5>
									<em>add new MC service downtime</em>
								</div>
						</a></li>
					</ul>
				</div>
			</div>
		</div>

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
						<input type="text" id="searchDesc" class="form-control"
							placeholder="Description">
					</div>
				</div>
				<div class="row">
					<div class="col-sm-3">
						<div class="input-group">
							<input id="searchStartDate" style="cursor: pointer;" type="text"
								class="form-control startdatepicker" placeholder="Date Start">
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
					<div class="col-sm-3">
						<div class="input-group">
							<input id="searchEndDate" style="cursor: pointer;" type="text"
								class="form-control enddatepicker" placeholder="Date End">
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
					<div class="col-md-6 text-right">
						<div class="controls form-inline">
							<div class="form-group">
								<a class="btn btn-danger" type="button" data-toggle="modal"
									data-target="#confirm-delete"><i class="fa fa-trash"></i>
									Delete</a>
							</div>
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
					<i class="fa fa-table"></i>MC Service DownTime List
				</h3>
			</div>
			<div class="widget-content">
				<table id="dataTable"
					class="table table-striped table-hover table-bordered datatable">
					<thead>
						<tr>
							<th>Description</th>
							<th>Start Date</th>
							<th>End Date</th>
							<th width="5%"></th>
							<th width="5%"></th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<!-- END SHOW HIDE COLUMNS DATA TABLE -->
	</div>
	<!-- /main-content -->
</body>
</html>