<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Product Category | Utilities</title>
<script type="text/javascript">
		$(document).ready(function() {
	    	var oTable = $("#dataTable").dataTable({
	        	"sAjaxSource"	: "${pageContext.request.contextPath}/utilities/product-category/search",
	            "sServerMethod"	: "POST",
	            "scrollX"		: true,
	            "fnServerData"	: function (sSource, aoData, fnCallback) {
	            	var productCategoryId		= $.trim($("#productCategoryId").val());
	            	var productCategoryName		= $.trim($("#productCategoryName").val());
	            	var productTypeName			= $.trim($("#productTypeName").val());
	            	var providerName 		= $.trim($("#providerName").val());
	            	
					aoData.push({ "name": "productCategoryId", "value": productCategoryId });
					aoData.push({ "name": "productCategoryName", "value": productCategoryName });
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
	            	{ "mDataProp": "providerName" },
	            	{ "mDataProp": "productTypeName" },
					{ "mDataProp": "productCategoryId" },
					{ "mDataProp": "productCategoryName" },
					{ "mDataProp": "status" },
					{ "mDataProp": "productCategoryId", "bSortable": false },
					{ "mDataProp": "productCategoryId", "bSortable": false }
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
				$('#productCategoryId').val("");
				$('#productCategoryName').val("");
				$('#productTypeName').val("");
				$('#providerName').val("");
				oTable.fnDraw();
			});
		
			
			
			$("#btn-delete").click(function(){
	        	var data = [];
	            $('#dataTable').find('tr').each(function () {
	    			var checkbox = $(this).find('input[type="checkbox"]');
	   				if (checkbox.is(':checked')){
	        			var row = {"productCategoryId" : checkbox.val(), "state" : checkbox.attr('class')};
	        			data.push(row);
	   				}
				});
	            	
	            if(data.length > 0){
	    			submit('/utilities/product-category/delete', JSON.stringify(data), function (data) {
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
						<li>Utilities</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/utilities/product-category/">Product
								Category</a></li>
					</ul>
					<h3>
						<i class="fa fa-sitemap fa-fw"></i> Product Category
					</h3>
					<em>Utilities</em>
				</div>
			</div>
			<div class="col-lg-7 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/utilities/product-category/create">
								<div class="quick-access-item bg-color-blue">
									<i class="fa fa-plus"></i>
									<h5>New Product Category</h5>
									<em>add new product category</em>
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
						<input type="number" id="productCategoryId" class="form-control"
							placeholder="Product Category Id">
					</div>

					<div class="col-sm-6">
						<select id="providerName" class="form-control">
							<option value="">-- Provider --</option>
							<c:forEach items="${listProvider}" var="provider">
								<option value="${provider.providerName}">${provider.providerName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<input type="text" id="productCategoryName" class="form-control"
							placeholder="Product Category Name">
					</div>

					<div class="col-sm-6">
						<select id="productTypeName" class="form-control">
							<option value="">-- Product Type --</option>
							<c:forEach items="${listProductType}" var="productType">
								<option value="${productType.productTypeName}">${productType.productTypeName}</option>
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
					<i class="fa fa-table"></i> Product Category List
				</h3>
			</div>
			<div class="widget-content">
				<table id="dataTable"
					class="table table-striped table-hover table-bordered datatable">
					<thead>
						<tr>
							<th>Provider Name</th>
							<th>Product Type Name</th>
							<th>Product Category Id</th>
							<th>Product Category Name</th>
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