<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Products and Services | Utilities</title>
<script type="text/javascript">
	$(document).ready(function() {
    	var oTable = $("#dataTable").dataTable({
        	"sAjaxSource"	: "${pageContext.request.contextPath}/utilities/product-service/search",
            "sServerMethod"	: "POST",
            "fnServerData"	: function (sSource, aoData, fnCallback) {
            	var name	= $.trim($("#searchName").val());
                aoData.push({ "name": "name", "value": name });
            	jQuery.ajax({
                	"type"		: "GET",
                    "url"		: sSource,
                    "data"		: aoData,
                    "success"	: fnCallback
                });
			},
            "aoColumns": [
				{ "mDataProp": "name" },
				{ "mDataProp": "description" },
				{ "mDataProp": "show" },
				{ "mDataProp": "id", "bSortable": false },
				{ "mDataProp": "id", "bSortable": false }
			],
            "aoColumnDefs" : [
				{	
            		class		: "text-center",
                    "mRender"	: function ( data, type, full ) {
						if(data == false){
							return '<span class="label label-default"> false </span>';
						}

						if(data == true){
							return '<span class="label label-default"> true </span>';
						}


					},
                    "aTargets"	: [2]
                },
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
	        $('#searchName').val("");

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
    			submit('/utilities/product-service/delete', JSON.stringify(data), function (data) {
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
			<div class="col-lg-6">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Utilities</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/utilities/product-service/">Products
								and Services</a></li>
					</ul>
					<h3>
						<i class="fa fa-cube"></i> Products and Services
					</h3>
					<em>Utilities</em>
				</div>
			</div>
			<div class="col-lg-6">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/utilities/product-service/create">
								<div class="quick-access-item bg-color-blue">
									<i class="fa fa-plus"></i>
									<h5>New Products and Services</h5>
									<em>add new Products and Services</em>
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
						<input type="text" id="searchName" class="form-control"
							placeholder="Name">
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
					<i class="fa fa-table"></i> List of Products and Services
				</h3>
			</div>
			<div class="widget-content">
				<table id="dataTable"
					class="table table-striped table-hover table-bordered datatable">
					<thead>
						<tr>
							<th>Name</th>
							<th>Description</th>
							<th>Show</th>
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