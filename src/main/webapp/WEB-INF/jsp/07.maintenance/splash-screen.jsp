<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Splash Screen| Utilities</title>
<script type="text/javascript">
		$(document).ready(function() {
	    	var oTable = $("#dataTable").dataTable({
	        	"sAjaxSource"	: "${pageContext.request.contextPath}/utilities/splash-screen/search",
	            "sServerMethod"	: "POST",
	            "scrollX"		: true,
	            "fnServerData"	: function (sSource, aoData, fnCallback) {
	            	var productTypeId		= $.trim($("#productTypeId").val());
	            	var productTypeName		= $.trim($("#productTypeName").val());
	            	var providerName 		= $.trim($("#providerName").val());
					aoData.push({ "name": "productTypeId", "value": productTypeId });
					aoData.push({ "name": "productTypeName", "value": productTypeName });
					aoData.push({ "name": "providerName", "value": providerName });
					
	            	jQuery.ajax({
	                	"type"		: "GET",
	                    "url"		: sSource,
	                    "data"		: aoData,
	                    "success"	: fnCallback
	                });
				},
	            "aoColumns": [
	            	{ "mDataProp": "serviceName" },
					{ "mDataProp": "serviceDescription" },
					{ "mDataProp": "serviceBanner" },
					{ "mDataProp": "createdBy" },
					{ "mDataProp": "createdDate" },
					{ "mDataProp": "updatedBy" },
					{ "mDataProp": "lastUpdatedDate" }
				]
            	
			});
				
			$("#btn-search").click(function(){
	        	oTable.fnDraw();
			});
		    
			$("#btn-reset").click(function(){
				$('#productTypeId').val("");
				$('#productTypeName').val("");
				$('#providerName').val("");
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
	    			submit('/utilities/splash-screen/delete', JSON.stringify(data), function (data) {
	    				oTable.fnDraw();
	    			});
	           	} else {
	           		BootstrapDialog.show({
	                    message: 'Please select at least one data.',
	                    type: BootstrapDialog.TYPE_WARNING
	                });
	           	}
			});
			
			function showConfirmation(msg, style, icon, label, url){
				
				var type = BootstrapDialog.TYPE_DEFAULT;
				
				if (style == 'info'){
					type = BootstrapDialog.TYPE_INFO;
				} else if (style == 'primary'){
					type = BootstrapDialog.TYPE_PRIMARY;
				} else if (style == 'warning'){
					type = BootstrapDialog.TYPE_WARNING;
				} else if (style == 'danger'){
					type = BootstrapDialog.TYPE_DANGER;
				} else if (style == 'success'){
					type = BootstrapDialog.TYPE_SUCCESS;
				}
				
				BootstrapDialog.show({
	            	title	: 'Confirmation',
	                message	: msg,
	                type	: type,
	                buttons	: [
	                       	   {
	                       			label	: '<i class="fa fa-times"></i> Cancel',
	                       			cssClass: 'btn-default',
	                    			action	: function(dialog){
	                    				dialog.close();
	                                }
	                			},
	                			{
	                       			label	: '<i class="fa fa-' + icon + '"></i> ' + label,
	                       			cssClass: 'btn-' + style,
	                    			action	: function(dialog){
	                    				sendAction(url);
	                    				dialog.close();
	                                }
	                			}
	                ]
	            });
			}
	        
			function sendAction(action){
				$.ajax({
					url		: "${pageContext.request.contextPath}/utilities/splash-screen/"+action,
	               	cache	: false,
	               	success	: function(response){
	               		showMessage(response);
	               		/* if (response.success && action != 'viewUsername') {
	               			data = {field:"id", number:id};
	        				retrieve('/monitoring/mobileCollectionUser/get', JSON.stringify(data), function (data) {
	        					populate(data);
	                        });
	               		} */
	               	}
	         	});
			}

		});
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
							href="${pageContext.request.contextPath}/utilities/splash-screen/">Product
								Type</a></li>
					</ul>
					<h3>
						<i class="fa fa-sitemap fa-fw"></i> Product and Services List
					</h3>
					<em>Utilities</em>
				</div>
			</div>
			<div class="col-lg-7 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/utilities/splash-screen/create">
								<div class="quick-access-item bg-color-blue">
									<i class="fa fa-plus"></i>
									<h5>New Product Type</h5>
									<em>add new product type</em>
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
						<input type="text" id="productTypeId" class="form-control"
							placeholder="Product Type Id">
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<input type="text" id="productTypeName" class="form-control"
							placeholder="Product Type Name">
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<select id="providerName" class="form-control">
							<option value="">-- Provider --</option>
							<c:forEach items="${listProvider}" var="provider">
								<option value="${provider.providerName}">${provider.providerName}</option>
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
					<div class="col-sm-6 text-right">
						<div class="form-group">
							<a class="btn btn-danger" type="button" data-toggle="modal"
								data-target="#confirm-delete"><i class="fa fa-trash"></i>
								Delete</a>
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
					<i class="fa fa-table"></i> Product and Services List
				</h3>
			</div>
			<div class="widget-content">
				<table id="dataTable"
					class="table table-striped table-hover table-bordered datatable">
					<thead>
						<tr>
							<th>Service Name</th>
							<th>Service Description</th>
							<th>Service Banner</th>
							<th>Created By</th>
							<th>Created Date</th>
							<th>Updated By</th>
							<th>Last Updated Date</th>
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