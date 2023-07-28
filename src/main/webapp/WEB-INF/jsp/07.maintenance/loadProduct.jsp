<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Load Product | Utilities</title>
<script type="text/javascript">
		$(document).ready(function() {
	    	var oTable = $("#dataTable").dataTable({
	        	"sAjaxSource"	: "${pageContext.request.contextPath}/utilities/load-product/search",
	            "sServerMethod"	: "POST",
	            "scrollX"		: true,
	            "fnServerData"	: function (sSource, aoData, fnCallback) {
	            	var loadProductId		= $.trim($("#loadProductId").val());
	            	var loadProductName    	= $.trim($("#loadProductName").val());
	            	var productCategoryName    	= $.trim($("#productCategoryName").val());
	            	
					aoData.push({ "name": "loadProductId", "value": loadProductId });
	                aoData.push({ "name": "loadProductName", "value": loadProductName });
	                aoData.push({ "name": "productCategoryName", "value": productCategoryName });
	                
	            	jQuery.ajax({
	                    "type"		: "GET",
	                    "url"		: sSource,
	                    "data"		: aoData,
	                    "success"	: fnCallback
	                });
				},
	            "aoColumns": [
	            	{ "mDataProp": "productCategoryName" },
					{ "mDataProp": "loadProductId" },
					{ "mDataProp": "loadProductName" },
					{ "mDataProp": "description" },
					{ "mDataProp": "status" },
					{ "mDataProp": "loadProductId" , "bSortable": false },
					{ "mDataProp": "loadProductId" , "bSortable": false }
				],
            	"aoColumnDefs" : [
            		{	
    					class		: "text-center",
    		            "mRender"	: BillsParameterFormatter,
    		            "aTargets"	: [4]
    				},
					{
	            		class		: "text-center",
	                    "mRender"	: EditlinkFormatter,
	                    "aTargets"	: [5]
	                },
					{
                    	class		: "text-center",
                        "mRender"	: CheckboxFormatter,
                        "aTargets"	: [6]
                    }	
            	]
			});
				
			$("#btn-search").click(function(){
	        	oTable.fnDraw();
			});
		
			$("#btn-reset").click(function(){
				$('#loadProductId').val("");
		        $('#loadProductName').val("");
		        $('#productCategoryName').val("");
		        
				oTable.fnDraw();
			});
		
			$("#btn-delete").click(function(){
	        	var data = [];
	            $('#dataTable').find('tr').each(function () {
	    			var checkbox = $(this).find('input[type="checkbox"]');
	   				if (checkbox.is(':checked')){
	        			var row = {"loadProductId" : checkbox.val(), "state" : checkbox.attr('class')};
	        			data.push(row);
	   				}
				});
	            	
	            if(data.length > 0){
	    			submit('/utilities/load-product/delete', JSON.stringify(data), function (data) {
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
							href="${pageContext.request.contextPath}/utilities/load-product/">Load
								Product</a></li>
					</ul>
					<h3>
						<i class="fa fa-sitemap fa-fw"></i> Load Product
					</h3>
					<em>Utilities</em>
				</div>
			</div>
			<div class="col-lg-7 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/utilities/load-product/create">
								<div class="quick-access-item bg-color-blue">
									<i class="fa fa-plus"></i>
									<h5>New Load Product</h5>
									<em>add new load product</em>
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
						<input type="number" id="loadProductId" class="form-control"
							placeholder="Load Product Id">
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<input type="text" id="loadProductName" class="form-control"
							placeholder="Load Product Name">
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<select id="productCategoryName" class="form-control">
							<option value="">-- Product Category --</option>
							<c:forEach items="${listProductCategoryLoad}"
								var="productCategoryLoad">
								<option value="${productCategoryLoad.productCategoryName}">${productCategoryLoad.productCategoryName}</option>
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
					<i class="fa fa-table"></i> Load Product List
				</h3>
			</div>
			<div class="widget-content">
				<table id="dataTable"
					class="table table-striped table-hover table-bordered datatable">
					<thead>
						<tr>
							<th style="width: 100pt;">Product Category Name</th>
							<th>Load Id</th>
							<th>Load Product Name</th>
							<th>Description</th>
							<th>Status</th>
							<th></th>
							<th></th>
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