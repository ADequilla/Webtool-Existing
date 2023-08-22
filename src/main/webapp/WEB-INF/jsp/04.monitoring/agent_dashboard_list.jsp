<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Agent Dashboard| Monitoring</title>
<script type="text/javascript">
		$(document).ready(function() {
	    	var oTable = $("#dataTable").dataTable({
	        	"sAjaxSource"	: "${pageContext.request.contextPath}/monitoring/agent-dashboard/search",
	            "sServerMethod"	: "POST",
	            "fnServerData"	: function (sSource, aoData, fnCallback) {
	            	var branch    = $.trim($("#searchBranchCode").val());
	            	
	                aoData.push({ "name": "branch", "value": branch });
	            	jQuery.ajax({
	                	"type"		: "GET",
	                    "url"		: sSource,
	                    "data"		: aoData,
	                    "success"	: fnCallback
	                });
				},
	            "aoColumns": [
					{ "mDataProp": "branchDesc" },
					{ "mDataProp": "cashIn" },
					{ "mDataProp": "cashOut" },
					{ "mDataProp": "agentAssistedPayment" },
					{ "mDataProp": "billPayment" },
					{ "mDataProp": "total" },
					{ "mDataProp": "totalIncome" },
				]
			});

	    	var oTableAgent = $("#dataTable-agent").dataTable({
	        	"sAjaxSource"	: "${pageContext.request.contextPath}/monitoring/agent-dashboard/search-agent",
	            "sServerMethod"	: "POST",
	            "fnServerData"	: function (sSource, aoData, fnCallback) {
	            	var branch    = $.trim($("#searchBranchCode").val());
	            	
	                aoData.push({ "name": "branch", "value": branch });
	            	jQuery.ajax({
	                	"type"		: "GET",
	                    "url"		: sSource,
	                    "data"		: aoData,
	                    "success"	: fnCallback
	                });
				},
	            "aoColumns": [
					{ "mDataProp": "clientName" },
					{ "mDataProp": "cashIn" },
					{ "mDataProp": "cashOut" },
					{ "mDataProp": "agentAssistedPayment" },
					{ "mDataProp": "billPayment" },
					{ "mDataProp": "total" },
					{ "mDataProp": "totalIncome" },
				]
			});
				
			$("#btn-search").click(function(){
	        	oTable.fnDraw();
	        	oTableAgent.fnDraw();
			});
		
			$("#btn-reset").click(function(){
		        $('#searchBranchCode').val("");
		        $('#branch').val("");
				oTable.fnDraw();
				oTableAgent.fnDraw();
			});
			
			popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showBranchPopup",
                modalId		: "popupBranchTable",
                modalTitle	: "Branch List",
                hiddenId	: "searchBranchCode",
                varValue	: "description",
               	ajax_data	: [
       					{
       						fieldVar	: "type",
       						fieldValue	: "BRANCH"
       					},
       					{
    	                	fieldVar	: "filter",
    						fieldValue	: "Y"
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
			<div class="col-lg-5 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Monitoring</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/monitoring/agent-dashboard/">Agent
								Dashboard</a></li>
					</ul>
					<h3>
						<i class="fa fa-list-alt"></i> Agent Dashboard
					</h3>
					<em>Monitoring</em>
				</div>
			</div>
		</div>

		<div class="main-content">
			<div class="widget widget-table">
				<div class="widget-header">
					<h3>
						<i class="fa fa-search"></i>
					</h3>
				</div>
				<div class="widget-content-search">

					<div class="row">
						<div class="col-sm-6">
							<input type="hidden" id="searchBranchCode">
							<div class="input-group">
								<input id="branch" type="text" style="cursor: pointer;"
									class="form-control showBranchPopup" readonly="readonly"
									placeholder="Branch" /> <span class="input-group-addon"><i
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
						<i class="fa fa-table"></i>List of Branch
					</h3>
				</div>

				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Branch</th>
								<th>Cash In</th>
								<th>Cash Out</th>
								<th>Agent Assisted Payment</th>
								<th>Bill Payment</th>
								<th>Total</th>
								<th>Total Income</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<!-- END SHOW HIDE COLUMNS DATA TABLE -->
		</div>
		<!-- /main-content -->


		<!-- SHOW HIDE COLUMNS DATA TABLE -->
		<div class="widget widget-table">
			<div class="widget-header">
				<h3>
					<i class="fa fa-table"></i>List of Agent
				</h3>
			</div>

			<div class="widget-content">
				<table id="dataTable-agent"
					class="table table-striped table-hover table-bordered datatable">
					<thead>
						<tr>
							<th>Branch</th>
							<th>Cash In</th>
							<th>Cash Out</th>
							<th>Agent Assisted Payment</th>
							<th>Bill Payment</th>
							<th>Total</th>
							<th>Total Income</th>
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