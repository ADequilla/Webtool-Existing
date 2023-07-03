<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Client | Registration</title>
<script type="text/javascript">
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/registration/client/search",
                "sServerMethod"	: "POST",
                "scrollX"		: true,
                "fnServerData"	: function (sSource, aoData, fnCallback) {
                    var cid			= $.trim($("#searchCid").val());
                    var branch		= $.trim($("#searchBranchCode").val());
                    var unit 		= $.trim($("#searchUnitCode").val());
                    var center	 	= $.trim($("#searchCenterCode").val());
                    var accStatus	= $.trim($("#searchAccountStatus").val());
                    var smsStatus	= $.trim($("#searchSmsStatus").val());
                    
                    var mobileNo	= $.trim($("#mobileNo").val());
                    var accountNumber	= $.trim($("#accountNumber").val());
                    var enrolledDateStart	= $.trim($("#enrolledDateStart").val());
                    var enrolledDateEnd	= $.trim($("#enrolledDateEnd").val());
                    var activatedDateStart	= $.trim($("#activatedDateStart").val());
                    var activatedDateEnd	= $.trim($("#activatedDateEnd").val());
                    
                    aoData.push({ "name": "cid",		"value": cid });
                    aoData.push({ "name": "branch", 	"value": branch });
                    aoData.push({ "name": "unit", 		"value": unit });
                    aoData.push({ "name": "center", 	"value": center });
                    aoData.push({ "name": "accStatus", 	"value": accStatus });
                    aoData.push({ "name": "smsStatus", 	"value": smsStatus });
                    
                    aoData.push({ "name": "mobileNo", 			"value": mobileNo });
                    aoData.push({ "name": "accountNumber", 		"value": accountNumber });
                    aoData.push({ "name": "enrolledDateStart", 	"value": enrolledDateStart });
                    aoData.push({ "name": "enrolledDateEnd", 	"value": enrolledDateEnd });
                    aoData.push({ "name": "activatedDateStart", 	"value": activatedDateStart });
                    aoData.push({ "name": "activatedDateEnd", 	"value": activatedDateEnd });
                    
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
                    { "mDataProp": "activationDate" },
                    { "mDataProp": "appExpired" },
                    { "mDataProp": "cid" },
                    { "mDataProp": "accountNumber" },
                    { "mDataProp": "typeAccount" },
                    { "mDataProp": "mobileNo" },
                    { "mDataProp": "fullname" },
                    { "mDataProp": "typeDesc" },
                    { "mDataProp": "branchDesc" },
                    { "mDataProp": "unitDesc" },
                    { "mDataProp": "centerDesc" },
                    { "mDataProp": "accStatusDesc" },
                    { "mDataProp": "smsStatusDesc" },
                    { "mDataProp": fnBlank, "bSortable": false }
                ],
                "aoColumnDefs" : [
					{	
						className	: "text-center",
						"mRender"	: AccountStatusFormatter,
						"aTargets"	: [12]
					},
					{	
						className	: "text-center",
						"mRender"	: SmsStatusFormatter,
						"aTargets"	: [13]
					},
					{	
						className	: "text-center",
						"mRender"	: function(data, type, row) {
							if (row.accStatusCode == 'WAIT_ACTIVATION') {
								return '<button id="btn-resend" class="btn btn-primary btn-sm" data-id="' + row.id + '"><i class="fa fa-send"></i> <b>Re-send</b></button>';
							} else if (row.accStatusCode == 'BLOCKED_ACTCODE') { 
								return '<button id="btn-reset" class="btn btn-success btn-sm" data-id="' + row.id + '"><i class="fa fa-refresh"></i> <b>Reset</b></button>';
							} else {
								return '';
							}
						},
						"aTargets"	: [14]
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
                $("#searchAccountStatus").val("");
                $("#searchSmsStatus").val("");

                $("#mobileNo").val("");
                $("#accountNumber").val("");
                $("#enrolledDateStart").val("");
                $("#enrolledDateEnd").val("");
                $("#activatedDateStart").val("");
                $("#activatedDateEnd").val("");
                
            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
            });
            
            
            
            $(document).on("click", '#btn-resend', function (e) {
    			var id = $(this).attr("data-id");
    			if (id != null) {
    				BootstrapDialog.show({
                    	title	: 'Confirmation',
                        message	: 'Are you sure want to Re-send Activation Code to this Client?',
                        type	: BootstrapDialog.TYPE_PRIMARY,
                        buttons	: [
                               	   {
                               			label	: '<i class="fa fa-times"></i> Cancel',
                               			cssClass: 'btn-default',
                            			action	: function(dialog){
                            				dialog.close();
                                        }
                        			},
                        			{
                               			label	: '<i class="fa fa-envelope"></i> Re-send',
                               			cssClass: 'btn-primary',
                            			action	: function(dialog){
                            				$.ajax({
                            					url		: "${pageContext.request.contextPath}/registration/client/resend/"+id,
                            	               	cache	: false,
                            	               	success	: function(response){
                            	               		showMessage(response);
                            	               		oTable.fnDraw();
                            	               	}
                            	         	});
                            				
                            				dialog.close();
                                        }
                        			}
                        ]
                    });
    			}
    		});
            
            $(document).on("click", '#btn-reset', function (e) {
    			var id = $(this).attr("data-id");
    			if (id != null) {
    				BootstrapDialog.show({
                    	title	: 'Confirmation',
                        message	: 'Are you sure want to Reset this Client?',
                        type	: BootstrapDialog.TYPE_SUCCESS,
                        buttons	: [
                               	   {
                               			label	: '<i class="fa fa-times"></i> Cancel',
                               			cssClass: 'btn-default',
                            			action	: function(dialog){
                            				dialog.close();
                                        }
                        			},
                        			{
                               			label	: '<i class="fa fa-refresh"></i> Reset',
                               			cssClass: 'btn-success',
                            			action	: function(dialog){
                            				$.ajax({
                            					url		: "${pageContext.request.contextPath}/registration/client/reset/"+id,
                            	               	cache	: false,
                            	               	success	: function(response){
                            	               		showMessage(response);
                            	               		oTable.fnDraw();
                            	               	}
                            	         	});
                            				
                            				dialog.close();
                                        }
                        			}
                        ]
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
        
    </script>
</head>
<body>
	<div class="content">
		<div class="row">
			<div class="col-lg-5 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Registration</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/registration/client/">Client</a></li>
					</ul>
					<h3>
						<i class="fa fa-list-ul"></i> Client
					</h3>
					<em>Registration</em>
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
							<input type="hidden" id="searchBranchCode">
							<div class="input-group">
								<input id="searchBranchDesc" type="text"
									style="cursor: pointer;" class="form-control showBranchPopup"
									readonly="readonly" placeholder="Branch" /> <span
									class="input-group-addon"><i class="fa fa-search"></i></span>
							</div>
						</div>
						<div class="col-sm-6">
							<select id="searchAccountStatus" class="form-control">
								<option value="">-- Account Status --</option>
								<c:forEach items="${listAccountStatus}" var="lookup">
									<option value="${lookup.value}">${lookup.description}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<input type="hidden" id="searchUnitCode">
							<div class="input-group">
								<input id="searchUnitDesc" type="text" style="cursor: pointer;"
									class="form-control showUnitPopup" readonly="readonly"
									placeholder="Unit" /> <span class="input-group-addon"><i
									class="fa fa-search"></i></span>
							</div>
						</div>
						<div class="col-sm-6">
							<select id="searchSmsStatus" class="form-control">
								<option value="">-- SMS Status --</option>
								<c:forEach items="${listSmsStatus}" var="lookup">
									<option value="${lookup.value}">${lookup.description}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<input type="text" id="mobileNo" class="form-control"
								placeholder="Mobile Number">
						</div>
						<div class="col-sm-6">
							<input type="text" id="accountNumber" class="form-control"
								placeholder="Account Number">
						</div>
					</div>

					<div class="row">
						<div class="col-sm-3">
							<div class="input-group">
								<input id="enrolledDateStart" style="cursor: pointer;"
									type="text" class="form-control startdatepicker"
									placeholder="Enrolled Date Start"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="enrolledDateEnd" style="cursor: pointer;" type="text"
									class="form-control enddatepicker"
									placeholder="Enrolled Date End"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-3">
							<div class="input-group">
								<input id="activatedDateStart" style="cursor: pointer;"
									type="text" class="form-control startdatepicker"
									placeholder="Activated Date Start"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="activatedDateEnd" style="cursor: pointer;"
									type="text" class="form-control enddatepicker"
									placeholder="Activated Date End"> <span
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
								<th>Date&Time Enrolled</th>
								<th>Date&Time Activated</th>
								<th>Activation Code Expiration</th>
								<th>CID</th>
								<th>Savings Account Number</th>
								<th>Savings Account Type</th>
								<th>Mobile Number</th>
								<th>Full Name</th>
								<th>Client Type</th>
								<th>Branch</th>
								<th>Unit</th>
								<th>Center</th>
								<th>Account Status</th>
								<th>SMS status</th>
								<th></th>
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