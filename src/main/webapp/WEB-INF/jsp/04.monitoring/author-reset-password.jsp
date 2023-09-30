<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Authorization Reset Password | Monitoring</title>
<script type="text/javascript">
		$(document).ready(function() {
	    	var oTable = $("#dataTable").dataTable({
	        	"sAjaxSource"	: "${pageContext.request.contextPath}/monitoring/author-reset-password/search",
	            "sServerMethod"	: "POST",
	            "fnServerData"	: function (sSource, aoData, fnCallback) {
	            	var cid		= $.trim($("#cid").val());
	            	var status    = $.trim($("#status").val()); 
					aoData.push({ "name": "cid", "value": cid });
	                aoData.push({ "name": "status", "value": status });
	            	jQuery.ajax({
	                	"type"		: "GET",
	                    "url"		: sSource,
	                    "data"		: aoData,
	                    "success"	: fnCallback
	                });
				},
	            "aoColumns": [
					{ "mDataProp": "createdDate" },
					{ "mDataProp": "cid" },
					{ "mDataProp": "clientName" },
					{ "mDataProp": "clientMobileNo" },
					{ "mDataProp": "branchCode" },
					{ "mDataProp": "unitCode" },
					{ "mDataProp": "centerCode" },
					{ "mDataProp": "type" },
					{ "mDataProp": "lastUpdatedByName" },
					{ "mDataProp": "id", "bSortable": false }
					/* { "mDataProp": "id", "bSortable": false } */
				],
            	"aoColumnDefs" : [
					{
	            		class		: "text-center",
	                    "mRender"	: EditlinkFormatter,
	                    "aTargets"	: [9]
	                }
					/* {
                    	class		: "text-center",
                        "mRender"	: CheckboxFormatter,
                        "aTargets"	: [10]
                    }	 */	
            	]
			});
				
			$("#btn-search").click(function(){
	        	oTable.fnDraw();
			});
		
			$("#btn-reset").click(function(){
				$('#cid').val("");
		        $('#status').val("");
				
				oTable.fnDraw();
			});
		
			
			
			/* $("#btn-delete").click(function(){
	        	var data = [];
	            $('#dataTable').find('tr').each(function () {
	    			var checkbox = $(this).find('input[type="checkbox"]');
	   				if (checkbox.is(':checked')){
	        			var row = {"id" : checkbox.val(), "state" : checkbox.attr('class')};
	        			data.push(row);
	   				}
				});
	            	
	            if(data.length > 0){
	    			submit('/monitoring/author-reset-password/delete', JSON.stringify(data), function (data) {
	    				oTable.fnDraw();
	    			});
	           	} else {
	           		BootstrapDialog.show({
	                    message: 'Please select at least one data.',
	                    type: BootstrapDialog.TYPE_WARNING
	                });
	           	}
			}); */
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
							href="${pageContext.request.contextPath}/monitoring/author-reset-password/">Author
								Reset Password</a></li>
					</ul>
					<h3>
						<i class="fa fa-sitemap fa-fw"></i> Author Reset Password
					</h3>
					<em>Monitoring</em>
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
						<input type="text" id="cid" class="form-control" placeholder="CID">
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<input type="text" id="status" class="form-control"
							placeholder="Status">
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
					<!-- <div class="col-sm-6 text-right">
					<div class="form-group">
						<a class="btn btn-danger" type="button" data-toggle="modal" data-target="#confirm-delete"><i class="fa fa-trash"></i> Delete</a>
					</div>
				</div> -->
				</div>
			</div>
		</div>
		<!-- END SEARCH DATA TABLE -->

		<!-- SHOW HIDE COLUMNS DATA TABLE -->
		<div class="widget widget-table">
			<div class="widget-header">
				<h3>
					<i class="fa fa-table"></i> Author Reset Password List
				</h3>
			</div>
			<div class="widget-content">
				<table id="dataTable"
					class="table table-striped table-hover table-bordered datatable">
					<thead>
						<tr>
							<th>Date & Time</th>
							<th>CID</th>
							<th>Full Name</th>
							<th>Mobile Number</th>
							<th>Branch</th>
							<th>Unit</th>
							<th>Center</th>
							<th>Type of Credential</th>
							<th>Reset by</th>
							<th width="5%"></th>
							<!-- <th width="5%"></th> -->
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