<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Mobile Collection User | Monitoring</title>
<script type="text/javascript">
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/monitoring/mobileCollectionUser/search",
                "sServerMethod"	: "POST",
                "bProcessing"	: false,
                "scrollX"		: true,
                "fnServerData"	: function (sSource, aoData, fnCallback) {
                    var customerId			= $.trim($("#customerId").val());
                    var mobileNo			= $.trim($("#mobileNo").val());
                   
                    
                    aoData.push({ "name": "customerId",			"value": customerId });
                    aoData.push({ "name": "mobileNo", 		"value": mobileNo });
                    jQuery.ajax({
                        "dataType"	: 'json',
                        "type"		: "POST",
                        "url"		: sSource,
                        "data"		: aoData,
                        "success"	: fnCallback
                    });
                },
                "order"		: [ [ 1, "desc" ] ],  
                "aoColumns"	: [
                    { "mDataProp": "cid"},
                    { "mDataProp": "fullname" },
                    { "mDataProp": "mobileNo" },
					{ "mDataProp": "typeDesc" },
	            	{ "mDataProp": "accStatusDesc" },
                    { "mDataProp": "branchDesc"},
                    { "mDataProp": "unitDesc"},
                    { "mDataProp": "id", "bSortable": false }
                ],
                
                "aoColumnDefs" : [
                   {
                   	class		: "text-center",
                       "mRender"	: EditlinkFormatter,
                       "aTargets"	: [7]
                   }
               ]
            });
            
            $("#btn-reset").click(function(){
            	$("#customerId").val("");
            	$("#mobileNo").val("");
            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
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
							href="${pageContext.request.contextPath}/monitoring/mobileCollectionUser/">Mobile
								Collection User</a></li>
					</ul>
					<h3>
						<i class="fa fa-list-alt"></i> Mobile Collection User
					</h3>
					<em>Monitoring</em>
				</div>
			</div>
			<div class="col-lg-7 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/monitoring/mobileCollectionUser/create">
								<div class="quick-access-item bg-color-blue">
									<i class="fa fa-plus"></i>
									<h5>New User</h5>
									<em>add new data</em>
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
							<input type="text" id="customerId" class="form-control"
								placeholder="Customer Id">
						</div>
						<div class="col-sm-6">
							<input type="text" id="mobileNo" class="form-control"
								placeholder="Mobile No">
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
						<i class="fa fa-table"></i>Mobile Collection User
					</h3>
				</div>

				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Customer Id</th>
								<th>Full Name</th>
								<th>Mobile No</th>
								<th>Client Type</th>
								<th>Account Status</th>
								<th>Branch</th>
								<th>Unit</th>
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