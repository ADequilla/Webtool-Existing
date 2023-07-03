<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Bill Payment | Mobile Prefix</title>
<script type="text/javascript">
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/monitoring/bill-payment-biller-product/search",
                "sServerMethod"	: "POST",
                "scrollX"		: true,
                "fnServerData"	: function (sSource, aoData, fnCallback) {
                    var name 	= $.trim($("#searchName").val());
                    aoData.push({ "name": "name", 	"value": name });
                    jQuery.ajax({
                        "dataType"	: 'json',
                        "type"		: "POST",
                        "url"		: sSource,
                        "data"		: aoData,
                        "success"	: fnCallback
                    });
                },
                "aoColumns": [
                    { "mDataProp": "product_category" },
                    { "mDataProp": "biller_product" },
                    { "mDataProp": "prefix_value" },
                    { "mDataProp": "product_name" }
               
                    
                ],
                "aoColumnDefs" : [
                    {
                    	class		: "text-center",
                        "mRender"	: EditlinkFormatter,
                        "aTargets"	: [5]
                    }
                ]
            });
            
            $("#btn-reset").click(function(){
            	$("#searchName").val("");

            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
            });
            
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
						<li>Bill Payment</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/monitoring/bill-payment-biller-product/">List</a></li>
					</ul>
					<h3>
						<i class="fa fa-user-o"></i> List
					</h3>
					<em>Mobile Prefix</em>
				</div>
			</div>
			<div class="col-lg-7 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/monitoring/bill-payment-biller-product/create">
								<div class="quick-access-item bg-color-blue">
									<i class="fa fa-plus"></i>
									<h5>Mobile Prefix</h5>
									<em>add new mobile prefix data</em>
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
						<i class="fa fa-table"></i> Mobile Prefix List
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Product Category</th>
								<th>Biller Product</th>
								<th>Product Name</th>
								<th>Prefix Value</th>
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