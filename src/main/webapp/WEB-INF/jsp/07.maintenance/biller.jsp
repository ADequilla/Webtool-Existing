<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Biller | Utilities</title>
<script type="text/javascript">
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/utilities/biller/search",
                "sServerMethod"	: "POST",
                "fnServerData"	: function (sSource, aoData, fnCallback) {
                    var name	= $.trim($("#searchName").val());
                    var account	= $.trim($("#searchAccount").val());
                    aoData.push({ "name": "name", "value": name });
                    aoData.push({ "name": "account", "value": account });
                    jQuery.ajax({
                        "dataType"	: 'json',
                        "type"		: "POST",
                        "url"		: sSource,
                        "data"		: aoData,
                        "success"	: fnCallback
                    });
                },
                "aoColumns": [
                    { "mDataProp": "name" },
                    { "mDataProp": "account" },
                    { "mDataProp": "enabled" },
                    { "mDataProp": "status" },
                    { "mDataProp": "approveBy.usrName", "defaultContent": "" },
                    { "mDataProp": "id", "bSortable": false },
                    { "mDataProp": "status", "bSortable": false }
                ],
                "aoColumnDefs" : [
     					{
   						class		: "text-center",
   					    "mRender"	: EnabledStatusFormatter,
   					    "aTargets"	: [2]
   					},
   					{	
 						className	: "text-center",
 						"mRender"	: ApprovalStatusFormatter,
 						"aTargets"	: [3]
 					},
                    {
                    	class		: "text-center",
                        "mRender"	: EditlinkFormatter,
                        "aTargets"	: [5]
                    },
					{
                    	class		: "text-center",
                        "mRender"	: ApprovalCheckboxFormatter,
                        "aTargets"	: [6]
                    }
                ]
            });
            
            $("#btn-reset").click(function(){
            	$("#searchName").val("");
            	$("#searchAccount").val("");
                
            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
            });
            
            
            
            $("#check_all").click(function(){
                $('input:checkbox').not(this).prop('checked', this.checked);
            });
            
            $("#btn-reject").click(function(){
            	var data = [];
            	$('#dataTable').find('tr').each(function () {
    				var checkbox = $(this).find('input[type="checkbox"]');
    				if(checkbox.attr('id') != 'check_all'){
	   					if (checkbox.is(':checked')){
	        				var row = {"id" : checkbox.val(), "state" : checkbox.attr('class')};
	        				data.push(row);
	   					}
    				}
				});
            	
            	if(data.length > 0){
    				submit('/utilities/biller/reject', JSON.stringify(data), function (data) {
    					oTable.fnDraw();
    				});
            	} else{
            		BootstrapDialog.show({
                        message: 'Please select at least one data.',
                        type: BootstrapDialog.TYPE_WARNING
                    });
            	}
            });
            
            $("#btn-approve").click(function(){
            	var data = [];
            	$('#dataTable').find('tr').each(function () {
    				var checkbox = $(this).find('input[type="checkbox"]');
    				if(checkbox.attr('id') != 'check_all'){
	   					if (checkbox.is(':checked')){
	        				var row = {"id" : checkbox.val(), "state" : checkbox.attr('class')};
	        				data.push(row);
	   					}
    				}
				});
            	
            	if(data.length > 0){
    				submit('/utilities/biller/approve', JSON.stringify(data), function (data) {
    					oTable.fnDraw();
    				});
            	} else{
            		BootstrapDialog.show({
                        message: 'Please select at least one data.',
                        type: BootstrapDialog.TYPE_WARNING
                    });
            	}
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
			<div class="col-lg-5 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Utilities</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/utilities/biller/">Biller</a></li>
					</ul>
					<h3>
						<i class="fa fa-usd"></i> Biller
					</h3>
					<em>Utilities</em>
				</div>
			</div>
			<div class="col-lg-7 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/utilities/biller/create">
								<div class="quick-access-item bg-color-blue">
									<i class="fa fa-plus"></i>
									<h5>New Biller</h5>
									<em>add new Biller data</em>
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
							<input type="text" id="searchName" class="form-control"
								placeholder="Name">
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<input type="text" id="searchAccount" class="form-control"
								placeholder="Account">
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
						<i class="fa fa-table"></i> List of Biller
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Name</th>
								<th>Account</th>
								<th width="10%">Enabled</th>
								<th>Status</th>
								<th>Approved By</th>
								<th width="5%"></th>
								<th width="5%"><input type="checkbox" id="check_all" /></th>
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