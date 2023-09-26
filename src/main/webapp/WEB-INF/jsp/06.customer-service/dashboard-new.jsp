<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Dashboard | Create</title>
<script type="text/javascript">
    	var userLogin = ${loginSecUser.id}
    	
        $(document).ready(function() {
            $("form").validate();
            
            $("#clientCid").change(function(){
            	var cid = $(this).val();
            	retrieve('/cs/dashboard/getClient', JSON.stringify(cid), function (data) {
            		if(data == null){
            			$('#clientCid').val("");
                		$('#clientType').val("");
                		$('#clientName').val("");
                		$('#familyName').val("");
                		$('#middleName').val("");
                		$('#mobileNo').val("");
                		$('#branch').val("");
                		$('#unit').val("");
                		$('#center').val("");
                		$('#birthday').val("");
                	}else{
                		$('#clientType').val(data.typeDesc);
                		$('#clientName').val(data.firstname);
                		$('#familyName').val(data.lastname);
                		$('#middleName').val(data.middlename);
                		$('#mobileNo').val(data.mobileNo);
                		$('#branch').val(data.branchDesc);
                		$('#unit').val(data.unitDesc);
                		$('#center').val(data.centerDesc);
                		$('#birthday').val(data.dob);
                	}
                });
            });
            
            $("#btn-reset").click(function(){
            	$('#clientCid').val("");
        		$('#clientType').val("");
        		$('#clientName').val("");
        		$('#familyName').val("");
        		$('#middleName').val("");
        		$('#mobileNo').val("");
        		$('#branch').val("");
        		$('#unit').val("");
        		$('#center').val("");
        		$('#birthday').val("");
            	$('#transType').val("");
            	$('#concernId').val("");
            	$('#assignedToId').val("");
            	$('#assignedToName').val("");
            	$('#action').val("");
            	$('#detail').val("");
            });
            
            $("#btn-save").click(function(){
                if ($("form").valid()) {
                	var data = $('form').serializeObject();
    				submit('/cs/dashboard/save', JSON.stringify(data), function (data) {
                        $("input[name='id']").val(data.id);
                    });
                }
            });

            popoverFunction.getUserPopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showAssignedToPopup",
                modalId		: "popupAssignedToTable",
                modalTitle	: "User List",
                hiddenId	: "assignedToId",
                varValue	: "name",
                callback	: function(){
                	var userId = $('#assignedToId').val();
                	if (userLogin == userId) {
                		$("#action").prop('disabled', false);
                	} else {
                		$('#action').val("");
                		$("#action").prop('disabled', true);
                	}
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
			<div class="col-lg-6 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Customer Service</li>
						<li><a
							href="${pageContext.request.contextPath}/cs/dashboard/">Dashboard</a></li>
						<li class="active">Create</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create CS Dashboard
					</h3>
					<em>Customer Service</em>
				</div>
			</div>
			<div class="col-lg-6">
				<div class="top-content-button">
					<ul class="list-inline quick-access">
						<li>
							<button id="btn-reset" class="btn btn-default" type="button">
								<i class="fa fa-refresh"></i> Reset Form
							</button>
						</li>
						<li>
							<button id="btn-save" class="btn btn-custom-secondary"
								type="button">
								<i class="fa fa-save"></i> Save Data
							</button>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<div class="main-content">
			<form class="form-horizontal" role="form" method="post"
				enctype="multipart/form-data">
				<input type="hidden" name="id">

				<div class="row">
					<div class="col-md-6">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-user"></i> Client Information
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-body">
									<div class="form-group">
										<label class="col-md-4 control-label">CID <span
											class="required">*</span></label>
										<div class="col-md-8">
											<input id="clientCid" name="clientCid" type="text"
												class="form-control required" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-4 control-label">Type of Client</label>
										<div class="col-md-8">
											<input id="clientType" name="clientType" type="text"
												class="form-control required" readonly="readonly" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Family Name</label>
										<div class="col-md-8">
											<input id="familyName" type="text" class="form-control"
												readonly="readonly" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Given Name</label>
										<div class="col-md-8">
											<input id="clientName" name="clientName" type="text"
												class="form-control" readonly="readonly" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Middle Name</label>
										<div class="col-md-8">
											<input id="middleName" type="text" class="form-control"
												readonly="readonly" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Mobile Number</label>
										<div class="col-md-8">
											<input id="mobileNo" type="text" class="form-control"
												readonly="readonly" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Branch Name</label>
										<div class="col-md-8">
											<input id="branch" type="text" class="form-control"
												readonly="readonly" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Unit Name</label>
										<div class="col-md-8">
											<input id="unit" type="text" class="form-control"
												readonly="readonly" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Center Name</label>
										<div class="col-md-8">
											<input id="center" type="text" class="form-control"
												readonly="readonly" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Birthday</label>
										<div class="col-md-8">
											<input id="birthday" type="text" class="form-control"
												readonly="readonly" />
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>

					<div class="col-md-6">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-phone"></i> Detail Information
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-body">
									<div class="form-group">
										<label class="col-md-4 control-label">Type of Concern
											<span class="required">*</span>
										</label>
										<div class="col-md-8">
											<select id="concernId" name="concernId"
												class="form-control required">
												<option value="">-- Choose --</option>
												<c:forEach items="${listConcern}" var="concern">
													<option value="${concern.id}">${concern.name}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Type of
											Transaction <span class="required">*</span>
										</label>
										<div class="col-md-8">
											<select id="transType" name="transType"
												class="form-control required">
												<option value="">-- Choose --</option>
												<c:forEach items="${listTransaction}" var="lookup">
													<option value="${lookup.value}">${lookup.description}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Assigned To <span
											class="required">*</span></label>
										<div class="col-md-8">
											<input type="hidden" id="assignedToId" name="assignedToId">
											<div class="input-group">
												<input id="assignedToName" name="assignedToName" type="text"
													style="cursor: pointer;"
													class="form-control showAssignedToPopup required"
													readonly="readonly" /> <span class="input-group-addon"><i
													class="fa fa-search"></i></span>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Action Taken <span
											class="required">*</span></label>
										<div class="col-md-8">
											<textarea id="action" name="action"
												class="form-control required" rows="6"></textarea>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Detail <span
											class="required">*</span></label>
										<div class="col-md-8">
											<textarea id="detail" name="detail"
												class="form-control required" rows="6"></textarea>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

			</form>
		</div>
		<!-- /main-content -->
	</div>
	<!-- /main -->
</body>
</html>