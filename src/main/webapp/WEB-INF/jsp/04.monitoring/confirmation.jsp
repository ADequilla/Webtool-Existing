<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Transaction for Confirmation | Monitoring</title>
<script type="text/javascript">
	    $(document).ready(function() {	    	
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/monitoring/confirmation/search",
                "sServerMethod"	: "POST",
                "bProcessing"	: false,
                "scrollX"		: true,
                "fnServerData"	: function (sSource, aoData, fnCallback) {
					var cid 		= $.trim($("#searchCID").val());
                    var transType 	= $.trim($("#searchTransType").val());
					var status 		= $.trim($("#searchStatus").val());
					var branch 		= $.trim($("#searchBranch").val());
					var dateStart 	= $.trim($("#searchDateStart").val());
					var dateEnd		= $.trim($("#searchDateEnd").val());
					aoData.push({ "name": "cid", 		"value": cid });
					aoData.push({ "name": "transType",	"value": transType });
                    aoData.push({ "name": "status", 	"value": status });
					aoData.push({ "name": "branch", 	"value": branch });
					aoData.push({ "name": "dateStart", 	"value": dateStart });
					aoData.push({ "name": "dateEnd", 	"value": dateEnd });
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
                    { "mDataProp": "transDesc" },
                    { "mDataProp": "clientMobile", "defaultContent": ""},
                    { "mDataProp": "clientCid", "defaultContent": ""},
					{ "mDataProp": "clientName", "defaultContent": ""},
					{ "mDataProp": "branchDesc", "defaultContent": ""},
					{ "mDataProp": "unitDesc", "defaultContent": ""},
					{ "mDataProp": "centerDesc", "defaultContent": ""},
					{ "mDataProp": "note" },
					{ "mDataProp": "status" },
					{ "mDataProp": fnBlank, "bSortable": false }
                ],
                "aoColumnDefs" : [
					{	
						className	: "text-center",
						"mRender"	: SuspiciousStatusFormatter,
						"aTargets"	: [9]
					},
					{
						class		: "text-center",
					    "mRender"	: function(data, type, row){
					    	if(row.status == null){
					    		return '<a class="btn btn-warning btn-sm" type="button" onclick="confirmDialog(\''+row.id+'\');"><i class="fa fa-warning"></i></a>';
					    	}else{
					    		return '';
					    	}
					    	
					    },
					    "aTargets"	: [10]
					},
                ]
            });
			
            $("#btn-search").click(function(){
                oTable.fnDraw();
            });
			
			$("#btn-reset").click(function(){
                $('#searchCID').val("");
				$('#searchTransType').val("");
				$("#searchStatus").val("");
				$('#searchBranch').val("");
				$('#searchBranchDesc').val("");
				$('#searchDateStart').val("");
				$('#searchDateEnd').val("");

				oTable.fnDraw();
            });
			
			
			
			popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showBranchPopup",
                modalTitle	: "Branch List",
                hiddenId	: "searchBranch",
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
	    
	    function confirmDialog(id) {
			BootstrapDialog.show({
            	title	: 'Please enter detail',
                message	: '<textarea id="dialog_detail_note" class="form-control" rows="5"></textarea>',
                type	: BootstrapDialog.TYPE_SUCCESS,
                buttons: [
                	{
	                    label: '<i class="fa fa-arrow-left"></i> Back',
	                    cssClass: 'btn-default',
	                    action: function(dialog) {
	                    	dialog.close();
	                    }
                	},
                	{
	                    label: '<i class="fa fa-check"></i> Valid',
	                    cssClass: 'btn-success',
	                    action: function(dialog) {
	                    	var note	= $.trim($("#dialog_detail_note").val());
	                        if(note == ''){
	                        	dialog.setTitle('* Detail is required');
	                        	dialog.setType(BootstrapDialog.TYPE_DANGER);
	                        }else{
	                        	confirm(id, note, 'VALID');
	                        	dialog.close();
	                        }
	                    }
                	},
                	{
	                    label: '<i class="fa fa-flag"></i> Suspicious',
	                    cssClass: 'btn-danger',
	                    action: function(dialog) {
	                    	var note	= $.trim($("#dialog_detail_note").val());
	                        if(note == ''){
	                        	dialog.setTitle('* Detail is required');
	                        	dialog.setType(BootstrapDialog.TYPE_DANGER);
	                        }else{
	                        	confirm(id, note, 'SUSPICIOUS');
	                        	dialog.close();
	                        }
	                    }
                	}
               	]
            });
		}
	    
	    function confirm(id, note, status){
        	var data = {"id": id, "note":note, "status":status};
        	submit('/monitoring/confirmation/confirm', JSON.stringify(data), function (data) {
				$("#btn-search").click();
				return true;
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
			<div class="col-lg-12">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Monitoring</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/monitoring/confirmation/">Transaction
								for Confirmation</a></li>
					</ul>
					<h3>
						<i class="fa fa-mobile"></i> Transaction for Confirmation
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
							<input type="text" placeholder="CID" id="searchCID"
								class="form-control">
						</div>
						<div class="col-sm-6">
							<input type="hidden" id="searchBranch">
							<div class="input-group">
								<input id="searchBranchDesc" type="text"
									style="cursor: pointer;" class="form-control showBranchPopup"
									readonly="readonly" placeholder="Branch" /> <span
									class="input-group-addon"><i class="fa fa-search"></i></span>
							</div>
						</div>
					</div>
 
					<div class="row">
						<div class="col-sm-6">
							<select id="searchTransType" class="form-control">
								<option value="">-- Transaction --</option>
								<c:forEach items="${listSuspiciousType}" var="lookup">
									<option value="${lookup.description}">${lookup.description}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchDateStart" style="cursor: pointer;" type="text"
									class="form-control startdatepicker" placeholder="Date Start">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchDateEnd" style="cursor: pointer;" type="text"
									class="form-control enddatepicker" placeholder="Date End">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<select id="searchStatus" class="form-control">
								<option value="">-- Status --</option>
								<c:forEach items="${listSuspiciousStatus}" var="lookup">
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
						<i class="fa fa-table"></i> Transaction for Confirmation List
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Date</th>
								<th>Transaction</th>
								<th>Mobile No</th>
								<th>CID</th>
								<th>Client Name</th>
								<th>Branch</th>
								<th>Unit</th>
								<th>Center</th>
								<th>Details</th>
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