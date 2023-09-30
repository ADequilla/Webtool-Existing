<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Mobile Collection Enrollment | Monitoring</title>
<script type="text/javascript">
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/monitoring/task/search",
                "sServerMethod"	: "POST",
                "scrollX"		: true,
                "fnServerData"	: function (sSource, aoData, fnCallback) {
                    var cid				= $.trim($("#searchCid").val());
                    var name			= $.trim($("#searchName").val());
                    var mobileNo		= $.trim($("#searchMobile").val());
                    var clientType		= $.trim($("#searchClientType").val());
                    var enrolDateStart	= $.trim($("#searchDateStart").val());
                    var enrolDateEnd	= $.trim($("#searchDateEnd").val());
                    var branch			= $.trim($("#searchBranchCode").val());
                    var unit 			= $.trim($("#searchUnitCode").val());
                    var center	 		= $.trim($("#searchCenterCode").val());
                    var approvalStatus	= $.trim($("#searchApprovalStatus").val());
                    
                    aoData.push({ "name": "cid",			"value": cid });
                    aoData.push({ "name": "name",			"value": name });
                    aoData.push({ "name": "mobileNo",		"value": mobileNo });
                    aoData.push({ "name": "clientType",		"value": clientType });
                    aoData.push({ "name": "enrolDateStart",	"value": enrolDateStart });
                    aoData.push({ "name": "enrolDateEnd",	"value": enrolDateEnd });
                    aoData.push({ "name": "branch", 		"value": branch });
                    aoData.push({ "name": "unit", 			"value": unit });
                    aoData.push({ "name": "center", 		"value": center });
                    aoData.push({ "name": "approvalStatus", "value": approvalStatus });
                    jQuery.ajax({
                        "dataType"	: 'json',
                        "type"		: "POST",
                        "url"		: sSource,
                        "data"		: aoData,
                        "success"	: fnCallback
                    });
                },
                "order"		: [ [ 0, "desc" ] ],               
                "aoColumns"	: [
                    { "mDataProp": "enrolled" },
                    { "mDataProp": "reEnrolled" },
                    { "mDataProp": "cid" },
                    { "mDataProp": "mobileNo" },
                    { "mDataProp": "fullname" },
                    { "mDataProp": "maidenname" },
                    { "mDataProp": "dob" },
                    { "mDataProp": "typeDesc" },
                    { "mDataProp": "branchDesc" },
                    { "mDataProp": "unitDesc" },
                    { "mDataProp": "centerDesc" },
                    { "mDataProp": "address" },
                    { "mDataProp": "approveStatusCode" },
                    { "mDataProp": "approveStatusCode", "bSortable": false }
                ],
                "aoColumnDefs" : [
					{	
						className	: "text-center",
						"mRender"	: ApprovalStatusFormatter,
						"aTargets"	: [12]
					},
                    {
                    	class		: "text-center",
                        "mRender"	: ApprovalCheckboxFormatter,
                        "aTargets"	: [13]
                    }
                ]
            });
            
            $("#btn-reset").click(function(){
            	$("#searchBranchCode").val("");
            	$("#searchBranchDesc").val("");
            	$("#searchUnitCode").val("");
                $("#searchUnitDesc").val("");
                $("#searchCenterCode").val("");
                $("#searchCenterDesc").val("");
                $("#searchCid").val("");
                $("#searchName").val("");
                $("#searchMobile").val("");
                $("#searchClientType").val("");
                $("#searchDateStart").val("");
                $("#searchDateEnd").val("");
                $("#searchApprovalStatus").val("");
                
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
    				submit('/monitoring/task/reject', JSON.stringify(data), function (data) {
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
    				submit('/monitoring/task/approve', JSON.stringify(data), function (data) {
    					oTable.fnDraw();
    				});
            	} else{
            		BootstrapDialog.show({
                        message: 'Please select at least one data.',
                        type: BootstrapDialog.TYPE_WARNING
                    });
            	}
            });
            
            popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showBranchPopup",
                modalId		: "popupBranchTable",
                modalTitle	: "Branch List",
                hiddenId	: "searchBranchCode",
                varValue	: "description",
                callback	: function callback(){
                	$("#searchUnitCode").val("");
                    $("#searchUnitDesc").val("");
                    $("#searchCenterCode").val("");
                    $("#searchCenterDesc").val("");
    			},
               	ajax_data	: [
       					{
       						fieldVar	: "type",
       						fieldValue	: "BRANCH"
       					},
       					{
    	                	fieldVar	: "filter",
    						fieldValue	: "Y"
    	                }
            		]
            });
            
            popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showUnitPopup",
                modalId		: "popupUnitTable",
                modalTitle	: "Unit List",
                hiddenId	: "searchUnitCode",
                varValue	: "description",
                callback	: function callback(){
                	$("#searchCenterCode").val("");
                    $("#searchCenterDesc").val("");
    			},
               	ajax_data	: [
       					{
       						fieldVar	: "type",
       						fieldValue	: "UNIT"
       					},
       					{
    	                	fieldVar	: "filter",
    						fieldValue	: "Y"
    	                },
    	                {
    	                    fieldVar	: "branchCode",
    	                    fieldValue	: function getBranch() {
    	                        			  return $.trim($("#searchBranchCode").val())
    	                    			  }
    	                }
            		]
            });
            
            popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showCenterPopup",
                modalId		: "popupCenterTable",
                modalTitle	: "Center List",
                hiddenId	: "searchCenterCode",
                varValue	: "description",
               	ajax_data	: [
       					{
       						fieldVar	: "type",
       						fieldValue	: "CENTER"
       					},
       					{
    	                	fieldVar	: "filter",
    						fieldValue	: "Y"
    	                },
    	                {
    	                    fieldVar	: "branchCode",
    	                    fieldValue	: function getBranch() {
    	                        			  return $.trim($("#searchBranchCode").val())
    	                    			  }
    	                },
    	                {
    	                    fieldVar	: "unitCode",
    	                    fieldValue	: function getUnit() {
    	                        			  return $.trim($("#searchUnitCode").val())
    	                    			  }
    	                }
            		]
            });
            
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
							href="${pageContext.request.contextPath}/monitoring/task/">Mobile
								Collection Enrollment</a></li>
					</ul>
					<h3>
						<i class="fa fa-list-alt"></i> Mobile Collection Enrollment
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
						<div class="col-sm-6">
							<input type="text" id="searchCid" class="form-control"
								placeholder="CID">
						</div>
						<div class="col-sm-6">
							<input type="hidden" id="searchBranchCode">
							<div class="input-group">
								<input id="searchBranchDesc" type="text"
									style="cursor: pointer;" class="form-control showBranchPopup"
									readonly="readonly" placeholder="Branch" /> <span
									class="input-group-addon"><i class="fa fa-search"></i></span>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<input type="text" id="searchName" class="form-control"
								placeholder="Name">
						</div>
						<div class="col-sm-6">
							<input type="hidden" id="searchUnitCode">
							<div class="input-group">
								<input id="searchUnitDesc" type="text" style="cursor: pointer;"
									class="form-control showUnitPopup" readonly="readonly"
									placeholder="Unit" /> <span class="input-group-addon"><i
									class="fa fa-search"></i></span>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<input type="text" id="searchMobile" class="form-control"
								placeholder="Mobile No">
						</div>
						<div class="col-sm-6">
							<input type="hidden" id="searchCenterCode">
							<div class="input-group">
								<input id="searchCenterDesc" type="text"
									style="cursor: pointer;" class="form-control showCenterPopup"
									readonly="readonly" placeholder="Center" /> <span
									class="input-group-addon"><i class="fa fa-search"></i></span>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<select id="searchClientType" class="form-control">
								<option value="">-- Client Type --</option>
								<c:forEach items="${listClientType}" var="lookup">
									<option value="${lookup.value}">${lookup.description}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-6">
							<select id="searchApprovalStatus" class="form-control">
								<option value="">-- Approval Status --</option>
								<c:forEach items="${listApprovalStatus}" var="lookup">
									<option value="${lookup.value}">${lookup.description}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchDateStart" style="cursor: pointer;" type="text"
									class="form-control startdatepicker"
									placeholder="Enrolled Date Start"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchDateEnd" style="cursor: pointer;" type="text"
									class="form-control enddatepicker"
									placeholder="Enrolled Date End"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
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
						<i class="fa fa-table"></i> Client List for Registration
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Date Enrolled</th>
								<th>Date Re-enrolled</th>
								<th>CID</th>
								<th>Mobile Number</th>
								<th>Full Name</th>
								<th>Maiden Name</th>
								<th>Birthdate</th>
								<th>Client Type</th>
								<th>Branch</th>
								<th>Unit</th>
								<th>Center</th>
								<th>Address</th>
								<th>Approval Status</th>
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