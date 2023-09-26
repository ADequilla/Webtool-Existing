<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Ao Account For Registration | Registration</title>
<script type="text/javascript">
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/registration/ao-account-registration/search",
                "sServerMethod"	: "POST",
                "scrollX"		: true,
                "fnServerData"	: function (sSource, aoData, fnCallback) {
                    var cid			= $.trim($("#searchCid").val());
                    var branch		= $.trim($("#searchBranchCode").val());
                    var unit 		= $.trim($("#searchUnitCode").val());
                    var center	 	= $.trim($("#searchCenterCode").val());
                    var accStatus	= $.trim($("#searchAccountStatus").val());
                    var smsStatus	= $.trim($("#searchSmsStatus").val());
                    aoData.push({ "name": "cid",		"value": cid });
                    aoData.push({ "name": "branch", 	"value": branch });
                    aoData.push({ "name": "unit", 		"value": unit });
                    aoData.push({ "name": "center", 	"value": center });
                    aoData.push({ "name": "accStatus", 	"value": accStatus });
                    aoData.push({ "name": "smsStatus", 	"value": smsStatus });
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
                    { "mDataProp": "approved" },
                    { "mDataProp": "approver.usrLoginname", "defaultContent": "" },
                    { "mDataProp": "registered" },
                    { "mDataProp": "reEnrolled" },
                    { "mDataProp": "reApproved" },
                    { "mDataProp": "reApprover.usrLoginname", "defaultContent": "" },
                    { "mDataProp": "actCodeExpired" },
                    { "mDataProp": "cid" },
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
						"aTargets"	: [15]
					},
					{	
						className	: "text-center",
						"mRender"	: SmsStatusFormatter,
						"aTargets"	: [16]
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
						"aTargets"	: [17]
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
                            					url		: "${pageContext.request.contextPath}/registration/ao-account-registration/resend/"+id,
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
                            					url		: "${pageContext.request.contextPath}/registration/ao-account-registration/reset/"+id,
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
						<li>Registration</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/registration/ao-account-registration/">Ao
								Account For Registration</a></li>
					</ul>
					<h3>
						<i class="fa fa-list-ul"></i> Ao Account For Registration
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
						<i class="fa fa-table"></i> Ao Account For Registration
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Date Enrolled</th>
								<th>Date Approved</th>
								<th>Approved By</th>
								<th>Date Registered</th>
								<th>Date Re-enrolled</th>
								<th>Date Re-approved</th>
								<th>Re-approved By</th>
								<th>Activation Code Expiration</th>
								<th>CID</th>
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