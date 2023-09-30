<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>SLF Request | Monitoring</title>
<script type="text/javascript">
        $(document).ready(function() {
        	var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/monitoring/slf/search",
                "sServerMethod"	: "POST",
                "fnServerData"	: function (sSource, aoData, fnCallback) {
                    var cid 		= $.trim($("#searchCid").val());
                    var branch 		= $.trim($("#searchBranchCode").val());
					var transDate 	= $.trim($("#searchRequestDate").val());
                    aoData.push({ "name": "cid", 		"value": cid });
                    aoData.push({ "name": "branch", 	"value": branch });
					aoData.push({ "name": "transDate", 	"value": transDate });
                    jQuery.ajax({
                        "dataType"	: 'json',
                        "type"		: "POST",
                        "url"		: sSource,
                        "data"		: aoData,
                        "success"	: fnCallback
                    });
                },
                "aoColumns": [
                    { "mDataProp": "transDate" },
                    { "mDataProp": "branchDesc"},
                    { "mDataProp": "cid" },
                    { "mDataProp": "clientName"},
					{ "mDataProp": "amount" },
					{ "mDataProp": "amountApproved" },
					{ "mDataProp": "status" },
					{ "mDataProp": fnBlank, "bSortable": false }
                ],
				"aoColumnDefs" : [
					{	
						className	: "text-right",
		            	"aTargets"	: [4]
					},
					{
						className	: "text-right",
						"mRender"	: function(data, type, row){
                        	if(row.status == 'REQUEST'){
                        		return '<input type="number" min="0" class="form-control input-sm text-right"/>';
                        	}
                        	return row.amountApproved;
                        },
		            	"aTargets"	: [5]
					},
					{	
						className	: "text-center",
		            	"mRender"	: TransStatusFormatter,
		            	"aTargets"	: [6]
					},
					{
                    	class: "text-center",
                        "mRender": function(data, type, row){
                        	if(row.status == 'REQUEST'){
                        		return '<input type="checkbox" value="' + row.id + '"/>';
                        	}
                        	
                        	return '';
                        },
                        "aTargets": [7]
                    }   
                ]
            });
        	
        	$("#btn-reset").click(function(){
            	$('#searchCid').val("");
            	$("#searchBranchCode").val("");
				$("#searchBranchDesc").val("");
				$('#searchRequestDate').val("");
            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
            });
            
            $("#btn-reject").click(function(){
            	var data = [];
            	$('#dataTable').find('tr').each(function () {
    				var checkbox = $(this).find('input[type="checkbox"]');
   					if (checkbox.is(':checked')){
        				var row = {"id" : checkbox.val(), "state" : checkbox.attr('class')};
        				data.push(row);
   					}
				});
            	
            	if(data.length > 0){
    				submit('/monitoring/slf/reject', JSON.stringify(data), function (data) {
    					oTable.fnDraw();
    				});
            	} else {
            		BootstrapDialog.show({
                        message: 'Please select at least one data.',
                        type: BootstrapDialog.TYPE_WARNING
                    });
            	}
            });
            
            $("#btn-approve").click(function(){
            	var data	= [];
            	var isError	= false;
            	$('#dataTable').find('tr').each(function () {
    				var checkbox = $(this).find('input[type="checkbox"]');
   					if (checkbox.is(':checked')){
   						var amount	= $(this).find('input[type="number"]').val();
   						if(amount == ''){
   							BootstrapDialog.show({
   		                        message: 'Please enter amount to be approved.',
   		                        type: BootstrapDialog.TYPE_WARNING
   		                    });
   							isError	= true;
   						}else{
   							var row = {"id" : checkbox.val(), "code": amount, "state" : checkbox.attr('class')};
   	        				data.push(row);
   						}
   					}
				});
            	
            	if(!isError){
            		if(data.length > 0){
        				submit('/monitoring/slf/confirm', JSON.stringify(data), function (data) {
        					oTable.fnDraw();
        				});
                	} else {
                		BootstrapDialog.show({
                            message: 'Please select at least one data.',
                            type: BootstrapDialog.TYPE_WARNING
                        });
                	}
            	}
            	
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
							href="${pageContext.request.contextPath}/monitoring/slf/">SLF
								Request</a></li>
					</ul>
					<h3>
						<i class="fa fa-archive"></i> SLF Request
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
						<div class="col-sm-6">
							<input type="text" placeholder="CID" id="searchCid"
								class="form-control">
						</div>
						<div class="col-sm-6">
							<div class="input-group">
								<input id="searchRequestDate" name="requestDate"
									style="cursor: pointer;" placeholder="Request Date" type="text"
									class="form-control datepicker" value=""
									data-date-format="dd-MMM-yyyy" readonly="readonly" /> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<input type="hidden" id="searchBranchCode">
							<div class="input-group">
								<input id="searchBranchDesc" type="text"
									placeholder="Branch Name" style="cursor: pointer;"
									class="form-control showBranchPopup" readonly="readonly" /> <span
									class="input-group-addon"><i class="fa fa-search"></i></span>
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
						<div class="col-sm-6 text-right">
							<a class="btn btn-danger" type="button" data-toggle="modal"
								data-target="#confirm-reject"><i class="fa fa-times"></i>
								Reject</a> <a class="btn btn-success" type="button"
								data-toggle="modal" data-target="#confirm-approve"><i
								class="fa fa-check"></i> Confirm</a>
						</div>
					</div>
				</div>
			</div>
			<!-- END SEARCH DATA TABLE -->

			<!-- SHOW HIDE COLUMNS DATA TABLE -->
			<div class="widget widget-table">
				<div class="widget-header">
					<h3>
						<i class="fa fa-table"></i> List of SLF Request
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Date/Time</th>
								<th>Branch</th>
								<th>Customer ID</th>
								<th>Full Name</th>
								<th width="15%">Amount</th>
								<th width="15%">Amount Approved</th>
								<th>Status</th>
								<th width="5%"></th>
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