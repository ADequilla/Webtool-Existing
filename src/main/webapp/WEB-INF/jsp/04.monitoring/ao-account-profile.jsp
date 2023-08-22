<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>AO Account Profile | Monitoring</title>
<script type="text/javascript">
        $(document).ready(function() {
        	
        	$("#cid").change(function(){
				var cid	= $(this).val();
				if (cid != '') {
					data = {field:"cid", string:cid};
					retrieve('/monitoring/ao-account-profile/get', JSON.stringify(data), function (data) {
    					populate(data);
                    });
				}
        	});
        	
        	$("#fullname").change(function(){
				var id	= $(this).val();
				if (id != null) {
					data = {field:"id", number:id};
					retrieve('/monitoring/ao-account-profile/get', JSON.stringify(data), function (data) {
    					populate(data);
                    });
				}
        	});
        	
			$("#mobileno").change(function(){
				var mobile	= $(this).val();
				if (mobile != '') {
					data = {field:"mobileNo", string:mobile};
					retrieve('/monitoring/ao-account-profile/get', JSON.stringify(data), function (data) {
    					populate(data);
                    });
				}
        	});
			
			$("#accountNumber").change(function(){
				var accountNumber	= $(this).val();
				if (accountNumber != '') {
					data = {field:"accountNumber", string:accountNumber};
					retrieve('/monitoring/ao-account-profile/get', JSON.stringify(data), function (data) {
    					populate(data);
                    });
				}
        	});
			
			$("#fullname").select2({
				allowClear			: true,
				placeholder			: "Full Name",
				minimumInputLength	: 3,
				ajax				: {
			        url			: "${pageContext.request.contextPath}/monitoring/ao-account-profile/names",
			        dataType	: 'json',
			        type		: "POST",
			        quietMillis	: 500,
			        data		: function (params) {
			        	return {
			            	name: params.term,
			            	size: 10
			            };
			       	},
			        processResults: function (data) {
			            return {
			                results: $.map(data, function (item) {
			                    return {
			                    	id	: item.id,
			                    	text: item.fullname
			                    }
			                })
			            };
			        }
			    }
			});
			
			$("#btn-reset-password").click(function(){
				showConfirmation("Are you sure want to Reset Client Password?", "warning", "refresh", "Reset", "resetPassword");
            });
			
			$("#btn-reset-mpin").click(function(){
				showConfirmation("Are you sure want to Reset Client MPIN?", "warning", "refresh", "Reset", "resetMpin");
            });
			
			$("#btn-deactivate").click(function(){
				showConfirmation("Are you sure want to Deactivate this Client?", "danger", "times-circle", "Deactivate", "deactivate");
            });
			
			$("#btn-restrict").click(function(){
				var restrict = $("#restrict").val();
				if (restrict == '1') {
					showConfirmation("Are you sure want to Unrestrict this Client?", "primary", "unlock", "Unrestrict", "unrestrict");
				} else {
					showConfirmation("Are you sure want to Restrict this Client?", "danger", "lock", "Restrict", "restricted");
				}
			});
			
			$("#btn-view-username").click(function(){
				sendAction("viewUsername");
            });
			
			$("#btn-agentFeature-enabled").click(function(){
				var agentFeature = $("#agentFeature").val();
				if (agentFeature == '1') {
					showConfirmation("Are you sure want to Disable Agent Feature?", "link", "close", "Disabled", "disableAgentFeature");
				} else {
					showConfirmation("Are you sure want to Enable Agent Feature?", "success", "check", "Enabled", "enableAgentFeature");
				}
            });
        });
        
        
        
        function populate(data) {
        	if (data == null) {
        		$('#id').val('');
        		$('#agentFeature').val('');
        		$('#restrict').val('');
        		$('#cid').val('');
            	$("#fullname").html('');
            	$('#mobileno').val('');
            	$('#accountNumber').val('');
            	$('#clientType').val('');
            	$('#status').val('');
            	$('#branch').val('');
            	$('#unit').val('');
            	$('#center').val('');
            	$('#birthday').val('');
            	$('#address').val('');
            	$('#enrolled').val('');
            	$('#registered').val('');
            	$('#imei').val('');
            	$('#deviceModelNumber').val('');
            	
            	
            	$("#btn-reset-password").disable(true);
            	$("#btn-reset-mpin").disable(true);
            	$("#btn-deactivate").disable(true);
            	$("#btn-restrict").disable(true);
            	$("#btn-view-username").disable(true);
            	$("#btn-agentFeature-enabled").disable(true);
        	} else {
        		$('#id').val(data.id);
        		$('#agentFeature').val(data.agentFeature);
        		$('#restrict').val(data.restrict);
        		$('#cid').val(data.cid);
        		$("#fullname").html((data.fullname == null)?'':'<option value="'+ data.id +'" selected="selected">'+ data.fullname +'</option>');
            	$('#mobileno').val(data.mobileNo);
            	$('#accountNumber').val(data.accountNumber);
            	$('#clientType').val(data.typeDesc);
            	$('#status').val(data.accStatusDesc);
            	$('#branch').val(data.branchDesc);
            	$('#unit').val(data.unitDesc);
            	$('#center').val(data.centerDesc);
            	$('#birthday').val(data.dob);
            	$('#address').val(data.address);
            	$('#registered').val(data.registered);
            	$('#imei').val(data.clientImei);
            	$('#deviceModelNumber').val(data.deviceModel);
            	
            	$("#btn-reset-password").disable(false);
            	$("#btn-reset-mpin").disable(false);
            	$("#btn-deactivate").disable(false);
            	$("#btn-restrict").disable(false);
            	$("#btn-view-username").disable(false);
            	$("#btn-agentFeature-enabled").disable(false);
            	
            	if (data.restrict == '1') {
            		$("#btn-restrict").html('<i class="fa fa-lock"></i> Restricted</a>');
            		$("#btn-restrict").removeClass("btn-primary").addClass("btn-danger");
        		} else {
        			$("#btn-restrict").html('<i class="fa fa-unlock"></i> Unrestricted</a>');
        			$("#btn-restrict").removeClass("btn-danger").addClass("btn-primary");
        		}
            	
            	if (data.agentFeature == '1') {
            		$("#btn-agentFeature-enabled").html('<i class="fa fa-check"></i> Enable</a>');
            		$("#btn-agentFeature-enabled").removeClass("btn-link").addClass("btn-success");
        		} else {
        			$("#btn-agentFeature-enabled").html('<i class="fa fa-close"></i> Enabling</a>');
        			$("#btn-agentFeature-enabled").removeClass("btn-success").addClass("btn-link");
        		}
        	}
        }
        
		function showConfirmation(msg, style, icon, label, url){
			
			var type = BootstrapDialog.TYPE_DEFAULT;
			
			if (style == 'info'){
				type = BootstrapDialog.TYPE_INFO;
			} else if (style == 'primary'){
				type = BootstrapDialog.TYPE_PRIMARY;
			} else if (style == 'warning'){
				type = BootstrapDialog.TYPE_WARNING;
			} else if (style == 'danger'){
				type = BootstrapDialog.TYPE_DANGER;
			} else if (style == 'success'){
				type = BootstrapDialog.TYPE_SUCCESS;
			}
			
			BootstrapDialog.show({
            	title	: 'Confirmation',
                message	: msg,
                type	: type,
                buttons	: [
                       	   {
                       			label	: '<i class="fa fa-times"></i> Cancel',
                       			cssClass: 'btn-default',
                    			action	: function(dialog){
                    				dialog.close();
                                }
                			},
                			{
                       			label	: '<i class="fa fa-' + icon + '"></i> ' + label,
                       			cssClass: 'btn-' + style,
                    			action	: function(dialog){
                    				sendAction(url);
                    				dialog.close();
                                }
                			}
                ]
            });
		}
		
		function sendAction(action){
			var id = $('#id').val();
			$.ajax({
				url		: "${pageContext.request.contextPath}/monitoring/ao-account-profile/" + action + '/' + id,
               	cache	: false,
               	success	: function(response){
               		showMessage(response);
               		if (response.success && action != 'viewUsername') {
               			data = {field:"id", number:id};
        				retrieve('/monitoring/ao-account-profile/get', JSON.stringify(data), function (data) {
        					populate(data);
                        });
               		}
               	}
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
			<div class="col-lg-6 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Monitoring</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/monitoring/ao-account-profile/">AO
								Account Profile </a></li>
					</ul>
					<h3>
						<i class="fa fa-user-secret"></i> AO Account Profile
					</h3>
					<em>Monitoring</em>
				</div>
			</div>
		</div>

		<form class="form-horizontal" role="form" method="post" name="profile">
			<div class="row">
				<div class="col-md-12">
					<div class="widget">
						<div class="widget-header">
							<h3>
								<i class="fa fa-user-secret"></i> AO Account Profile
							</h3>
						</div>

						<input type="hidden" id="id"> <input type="hidden"
							id="restrict"> <input type="hidden" id="agentFeature">

						<div class="widget-content form">
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Customer I.D.</label>
									<div class="col-md-4">
										<input id="cid" type="text" class="form-control"
											placeholder="Customer I.D." />
									</div>

									<div class="col-sm-offset-1 col-md-3">
										<button id="btn-reset-password" type="button"
											class="btn btn-warning btn-block" disabled>
											<i class="fa fa-refresh"></i> Reset Password
										</button>
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Full Name</label>
									<div class="col-md-4">
										<select id="fullname" class="form-control">
										</select>
									</div>

									<div class="col-sm-offset-1 col-md-3">
										<button id="btn-reset-mpin" type="button"
											class="btn btn-warning btn-block" disabled>
											<i class="fa fa-refresh"></i> Reset MPIN
										</button>
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Mobile Number</label>
									<div class="col-md-4">
										<input id="mobileno" type="text" class="form-control"
											placeholder="Mobile Number" />
									</div>

									<div class="col-sm-offset-1 col-md-3">
										<button id="btn-deactivate" type="button"
											class="btn btn-danger btn-block" disabled>
											<i class="fa fa-times-circle"></i> Deactivate
										</button>
									</div>
								</div>
							</div>

							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Enrolled Account
										Number</label>
									<div class="col-md-4">
										<input id="accountNumber" type="text" class="form-control"
											placeholder="Enrolled Account Number" />
									</div>

									<div class="col-sm-offset-1 col-md-3">
										<button id="btn-restrict" type="button"
											class="btn btn-primary btn-block" disabled>
											<i class="fa fa-lock"></i> Restrict/Unrestrict
										</button>
									</div>
								</div>
							</div>

							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Type of enrolled
										savings account</label>
									<div class="col-md-4">
										<input id="accountType" type="text" class="form-control"
											placeholder="Type of enrolled savings account"
											readonly="readonly" />
									</div>
									<div class="col-sm-offset-1 col-md-3">
										<button id="btn-view-username" type="button"
											class="btn btn-info btn-block" disabled>
											<i class="fa fa-search-plus"></i> View User Name
										</button>
									</div>
								</div>
							</div>

							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Client Type</label>
									<div class="col-md-4">
										<input id="clientType" type="text" class="form-control"
											placeholder="Client Type" readonly="readonly" />
									</div>
									<div class="col-sm-offset-1 col-md-3">
										<button id="btn-agentFeature-enabled" type="button"
											class="btn btn-success btn-block" disabled>
											<i class="fa fa-check"></i> Agent Feature (Enabled/Disabled)
										</button>
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Account Status</label>
									<div class="col-md-4">
										<input id="status" type="text" class="form-control"
											placeholder="Account Status" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Branch</label>
									<div class="col-md-4">
										<input id="branch" type="text" class="form-control"
											placeholder="Branch" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Unit</label>
									<div class="col-md-4">
										<input id="unit" type="text" class="form-control"
											placeholder="Unit" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Center</label>
									<div class="col-md-4">
										<input id="center" type="text" class="form-control"
											placeholder="Center" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Birthday</label>
									<div class="col-md-4">
										<input id="birthday" type="text" class="form-control"
											placeholder="Birthday" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Address</label>
									<div class="col-md-4">
										<textarea id="address" class="form-control" rows="3"
											readonly="readonly"></textarea>
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Date Registered</label>
									<div class="col-md-4">
										<input id="registered" type="text" class="form-control"
											placeholder="Date Registered" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">IMEI (Device I.D)</label>
									<div class="col-md-4">
										<input id="imei" type="text" class="form-control"
											placeholder="Imei (Device I.D)" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Device Model
										Number</label>
									<div class="col-md-4">
										<input id="deviceModelNumber" type="text" class="form-control"
											placeholder="Device Model Number" readonly="readonly" />
									</div>
								</div>
							</div>

						</div>

					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>