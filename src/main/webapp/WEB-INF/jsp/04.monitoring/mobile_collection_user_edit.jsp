<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>Client Profile | Monitoring</title>
<script type="text/javascript">
        $(document).ready(function() {
        	
        	$("#btn-update").click(function(){
				showConfirmation("Are you sure want to Update data ?", "warning", "refresh", "Update", "updateData");
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
			
			$("#btn-sync").click(function(){
				showConfirmation("Are you sure want to Synchronize?", "warning", "refresh", "Update", "synchronize");
            });
			
			$("#btn-view-username").click(function(){
				sendAction("viewUsername");
            });
			
        });
        
        
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
			var id = $('#id').val() + "|" + $('#searchClientType').val() +"|"+ $('#accountNumber').val() +"|"+ $('#cid').val();
			$.ajax({
				url		: "${pageContext.request.contextPath}/monitoring/mobileCollectionUser/" + action + '/' + id,
               	cache	: false,
               	success	: function(response){
               		showMessage(response);
               		/* if (response.success && action != 'viewUsername') {
               			data = {field:"id", number:id};
        				retrieve('/monitoring/mobileCollectionUser/get', JSON.stringify(data), function (data) {
        					populate(data);
                        });
               		} */
               	}
         	});
		}
		
        
        function populate(data) {
       		$('#id').val(data.id);
       		$('#cid').val(data.cid);
       		$("#fullname").val(data.fullname);
           	$('#mobileno').val(data.mobileNo);
           	$('#searchClientType').val(data.typeDesc);
           	$('#status').val(data.accStatusDesc);
           	$('#branch').val(data.branchDesc);
           	$('#unit').val(data.unitDesc);
           	$('#center').val(data.centerDesc);
           	$('#birthday').val(data.dob);
           	$('#address').val(data.address);
           	$('#accountNumber').val(data.account);
           	
        }

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
			<div class="col-lg-6 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Monitoring</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/monitoring/mobileCollectionUser/">Mobile
								Collection User</a></li>
					</ul>
					<h3>
						<i class="fa fa-user-secret"></i> Mobile Collection User
					</h3>
					<em>Monitoring</em>
				</div>
			</div>
		</div>

		<form class="form-horizontal" role="form" method="post" name="view">
			<div class="row">
				<div class="col-md-12">
					<div class="widget">
						<div class="widget-header">
							<h3>
								<i class="fa fa-user-secret"></i> Mobile Collection User
							</h3>
						</div>

						<input type="hidden" id="id" value="${view.id}" />

						<div class="widget-content form">
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Customer I.D.</label>
									<div class="col-md-4">
										<input id="cid" type="text" value="${view.cid}"
											class="form-control" placeholder="Customer I.D."
											readonly="readonly" />
									</div>

									<div class="col-sm-offset-1 col-md-3">
										<button id="btn-update" type="button"
											class="btn btn-warning btn-block">
											<i class="fa fa-save"></i> Update Data
										</button>
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Client Type<span
										class="required">*</span></label>
									<div class="col-md-4">
										<select id="searchClientType" class="form-control required">
											<option value="">-- Client Type --</option>
											<c:forEach items="${listClientType}" var="lookup">
												<option value="${lookup.value}"
													${lookup.value == view.typeCode ? 'selected="${lookup.description}"' : ''}>${lookup.description}</option>
											</c:forEach>
										</select>
									</div>

									<div class="col-sm-offset-1 col-md-3">
										<button id="btn-reset-password" type="button"
											class="btn btn-warning btn-block">
											<i class="fa fa-refresh"></i> Reset Password
										</button>
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Account Number<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="accountNumber" type="text" value="${view.account}"
											class="form-control required" placeholder="Account Number" />
									</div>

									<div class="col-sm-offset-1 col-md-3">
										<button id="btn-reset-mpin" type="button"
											class="btn btn-warning btn-block">
											<i class="fa fa-refresh"></i> Reset MPIN
										</button>
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Full Name</label>
									<div class="col-md-4">
										<input id="fullname" value="${view.fullname}" type="text"
											class="form-control" placeholder="Full Name"
											readonly="readonly" />
									</div>
									<div class="col-sm-offset-1 col-md-3">
										<button id="btn-deactivate" type="button"
											class="btn btn-danger btn-block">
											<i class="fa fa-times-circle"></i> Deactivate
										</button>
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Mobile Number</label>
									<div class="col-md-4">
										<input id="mobileno" type="text" value="${view.mobileNo}"
											class="form-control" placeholder="Mobile Number"
											readonly="readonly" />
									</div>
									<div class="col-sm-offset-1 col-md-3">
										<button id="btn-sync" type="button"
											class="btn btn-warning btn-block">
											<i class="fa fa-refresh"></i> Synchronize
										</button>
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Account Status</label>
									<div class="col-md-4">
										<input id="status" type="text" value="${view.accStatusDesc}"
											class="form-control" placeholder="Account Status"
											readonly="readonly" />
									</div>
									<div class="col-sm-offset-1 col-md-3">
										<button id="btn-view-username" type="button"
											class="btn btn-info btn-block">
											<i class="fa fa-search-plus"></i> View User Name
										</button>
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Branch</label>
									<div class="col-md-4">
										<input id="branch" type="text" value="${view.branchDesc}"
											class="form-control" placeholder="Branch" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Unit</label>
									<div class="col-md-4">
										<input id="unit" type="text" value="${view.unitDesc}"
											class="form-control" placeholder="Unit" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Center</label>
									<div class="col-md-4">
										<input id="center" type="text" value="${view.centerDesc}"
											class="form-control" placeholder="Center" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Birthday</label>
									<div class="col-md-4">
										<input id="birthday" type="text" value="${view.birthDateStr}"
											class="form-control" placeholder="Birthday"
											readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Address</label>
									<div class="col-md-4">
										<textarea id="address" class="form-control" rows="3"
											readonly="readonly">${view.address}</textarea>
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