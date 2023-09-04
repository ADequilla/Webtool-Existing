<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Mobile Prefix | Utilities</title>
<script type="text/javascript">
		$(document).ready(function() {
	    	var oTable = $("#dataTable").dataTable({
	        	"sAjaxSource"	: "${pageContext.request.contextPath}/utilities/mobile-prefix/search",
	            "sServerMethod"	: "POST",
	            "fnServerData"	: function (sSource, aoData, fnCallback) {
	            	var prefixValue				= $.trim($("#prefixValue").val());
	            	var productName		= $.trim($("#productName").val());
					aoData.push({ "name": "prefixValue", "value": prefixValue });
					aoData.push({ "name": "productName", "value": productName });
	            	jQuery.ajax({
	                	"type"		: "GET",
	                    "url"		: sSource,
	                    "data"		: aoData,
	                    "success"	: fnCallback
	                });
				},
	            "aoColumns": [
					{ "mDataProp": "prefixValue" },
					{ "mDataProp": "productName" },
					{ "mDataProp": "id", "bSortable": false },
					{ "mDataProp": "id", "bSortable": false }
				],
            	"aoColumnDefs" : [
					{
	            		class		: "text-center",
	                    "mRender"	: EditlinkFormatter,
	                    "aTargets"	: [2]
	                },
					{
                    	class		: "text-center",
                        "mRender"	: CheckboxFormatter,
                        "aTargets"	: [3]
                    }		
            	]
			});
				
			$("#btn-search").click(function(){
	        	oTable.fnDraw();
			});
		
			$("#btn-reset").click(function(){
				$('#prefixValue').val("");
				$('#productName').val("");
				
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
	    			submit('/utilities/mobile-prefix/delete', JSON.stringify(data), function (data) {
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
							href="${pageContext.request.contextPath}/utilities/mobile-prefix/">Mobile
								Prefix</a></li>
					</ul>
					<h3>
						<i class="fa fa-sitemap fa-fw"></i> Mobile Prefix
					</h3>
					<em>Utilities</em>
				</div>
			</div>
			<div class="col-lg-7 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/utilities/mobile-prefix/create">
								<div class="quick-access-item bg-color-blue">
									<i class="fa fa-plus"></i>
									<h5>New Mobile Prefix</h5>
									<em>add new mobile prefix</em>
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
						<input type="text" id="prefixValue" class="form-control"
							placeholder="Prefix Value">
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<input type="text" id="productName" class="form-control"
							placeholder="Product Name">
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
					<i class="fa fa-table"></i> Mobile Prefix List
				</h3>
			</div>
			<div class="widget-content">
				<table id="dataTable"
					class="table table-striped table-hover table-bordered datatable">
					<thead>
						<tr>
							<th>Prefix Value</th>
							<th>Product Name</th>
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