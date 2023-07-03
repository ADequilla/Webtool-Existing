<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>List of Used Device ID | Monitoring</title>
<script type="text/javascript">
        $(document).ready(function() {
        	var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/monitoring/list-used-device/search",
                "sServerMethod"	: "GET",
                "bProcessing"	: false,
                "fnServerData"	: function (sSource, aoData, fnCallback) {
					var searchDateStart 	= $.trim($("#searchDateStart").val());
					var searchDateEnd 	= $.trim($("#searchDateEnd").val());
                	var deviceId	= $.trim($("#deviceId").val());
					var cif 	= $.trim($("#cif").val());
					var mobileNumber 	= $.trim($("#mobileNumber").val());
					var clientType 	= $.trim($("#clientType").val());
					var status 	= $.trim($("#status").val());
					
                    aoData.push({ "name": "searchDateStart", "value": searchDateStart});
                    aoData.push({ "name": "searchDateEnd", "value": searchDateEnd });
                    aoData.push({ "name": "deviceId", "value": deviceId });
					aoData.push({ "name": "cif", "value": cif });
					aoData.push({ "name": "mobileNumber", "value": mobileNumber });
					aoData.push({ "name": "clientType", "value": clientType });
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
                    { "mDataProp": "deviceId" },
					{ "mDataProp": "deviceModel" },
					{ "mDataProp": "androidVersion" },
					{ "mDataProp": "cid" },
					{ "mDataProp": "branchCode" },
					{ "mDataProp": "mobileNumber" },
					{ "mDataProp": "clientName" },
					{ "mDataProp": "clientType" },
					{ "mDataProp": "deviceStatus" },
					{ "mDataProp": "id", "bSortable": false }
                ],
				"aoColumnDefs" : [
					{	
						className	: "text-left",
						"aTargets"	: [1],
						"data"		: "deviceId",
		            	"mRender"	: function ( data, type, row, meta ) {
		            	      			return data.substr( 0, data.length-2 );
		            	}
					},
					{	
						className	: "text-center",
		            	"mRender"	: DeviceFormatter,
		            	"aTargets"	: [9]
					},
					{
	            		class		: "text-center",
	                    "mRender"	: EditlinkFormatter,
	                    "aTargets"	: [10]
	                }
                ]
            });
            
        	$("#btn-search").click(function(){
                oTable.fnDraw();
            });
			
            $("#btn-reset").click(function(){
            	$('#deviceId').val("");
				$('#cif').val("");
				$('#mobileNumber').val("")
				$('#searchDateStart').val("")
				$('#searchDateEnd').val("")
				$('#clientType').val("")
				$('#status').val("")
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
						<li>Monitoring</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/monitoring/list-used-device/">List
								of Used Device ID</a></li>
					</ul>
					<h3>
						<i class="fa fa-envelope"></i> List of Used Device ID
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
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchDateStart" style="cursor: pointer;" type="text"
									class="form-control startdatepicker"
									placeholder="Activated Date Start"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchDateEnd" style="cursor: pointer;" type="text"
									class="form-control enddatepicker"
									placeholder="Activated Date End"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>

						<div class="col-sm-6">
							<input type="text" placeholder="Device ID" id="deviceId"
								class="form-control">
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<input type="text" placeholder="CIF Number" id="cif"
								class="form-control">
						</div>

						<div class="col-sm-6">
							<input type="text" placeholder="Mobile Number" id="mobileNumber"
								class="form-control">
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<select id="clientType" class="form-control">
								<option value="">-- Client Type --</option>
								<c:forEach items="${listClientType}" var="lookup">
									<option value="${lookup.value}">${lookup.description}</option>
								</c:forEach>
							</select>
						</div>

						<div class="col-sm-6">
							<select id="status" class="form-control">
								<option value="">-- Status --</option>
								<c:forEach items="${listStatusDevice}" var="lookup">
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
						<i class="fa fa-table"></i> List of Used Device ID
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Date & Time Activated</th>
								<th>Device ID</th>
								<th>Device Model</th>
								<th>Android Version</th>
								<th>CIF</th>
								<th>Branch</th>
								<th>Mobile Number</th>
								<th>Name of Customer</th>
								<th>Client Type (Member/ NonMember)</th>
								<th>Status (Used/ Unused)</th>
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