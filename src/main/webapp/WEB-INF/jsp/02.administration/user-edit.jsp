<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>User | Create/Edit</title>
<script type="text/javascript">
	    function inisiateMultiselect(id) {
	    	$(id).multiselect('destroy');
	    	$(id).multiselect({
                includeSelectAllOption	: true,
                enableFiltering			: true,
                enableCaseInsensitiveFiltering: true,
                buttonWidth				: '100%',
                onChange: function(option, checked, select) {
                	var values = [];
                	values.push('0');
                    $(id + ' option:selected').each(function() {
                    	values.push($(this).val());
                    });
                     
                    if ('#institution' == id){
                    	$.ajax({
        	            	url: "${pageContext.request.contextPath}/administration/user/getBranch/"+values,
        	                cache: false,
        	                success: function(data){
        	                	resetMultiselect('#branch');
        	                	/* resetMultiselect('#unit'); */
        	                	/* resetMultiselect('#center'); */
        	                	$.each(data, function(){
        	                		$("#branch").append('<option value="'+ this.code +'">'+ this.description +'</option>');
        	                	});
        	                	inisiateMultiselect('#branch');
        	                	/* inisiateMultiselect('#unit'); */
        	                	/* inisiateMultiselect('#center'); */
        	                }
                     	});
                    	   
                    } 
                    /* else if ('#branch' == id) {
                    	$.ajax({
        	               	url: "${pageContext.request.contextPath}/administration/user/getUnit/"+values,
        	               	cache: false,
        	               	success: function(data){
        	               		resetMultiselect('#unit');
        	               		resetMultiselect('#center');
        	               		$.each(data, function(){
        	               			$("#unit").append('<option value="'+ this.code +'">'+ this.description +'</option>');
        	               		});
        	               		inisiateMultiselect('#unit');
        	                	inisiateMultiselect('#center');
        	               	}
                     	});
                    	
                    } else if ('#unit' == id) {
                    	$.ajax({
        	               	url: "${pageContext.request.contextPath}/administration/user/getCenter/"+values,
        	               	cache: false,
        	               	success: function(data){
        	               		resetMultiselect('#center');
        	               		$.each(data, function(){
        	               			$("#center").append('<option value="'+ this.code +'">'+ this.description +'</option>');
        	               		});
        	               		inisiateMultiselect('#center');
        	               	}
                     	});
                    } */
                }
            });
	    	$(id).multiselect('refresh');
		}
	    
	    function resetMultiselect(id) {
	    	$(id + " option").remove();
	    }
	    
        $(document).ready(function() {
        	inisiateMultiselect('#institution');
        	inisiateMultiselect('#branch');
        	/* inisiateMultiselect('#unit');
        	inisiateMultiselect('#center'); */
        	
            $('form[name="user"]').validate({
                rules : {
                    roles			: "required",
                    'institution[]'	: "required",
                    'branch[]'		: "required",
                    /* 'unit[]'		: "required",
                    'center[]'		: "required", */
                },
                messages: {
                	roles: {
                    	required: "Please select roles."
                	}
                },
                errorPlacement: function(error, element) {
                	if (element.attr('name') == 'roles') {
                    	error.insertAfter('#checkboxRoles');
                    }else{
                    	error.insertAfter(element.parent());
                    }
            	},
            	/* ignore: ':hidden:not("#institution, #branch, #unit, #center")' */
            	ignore: ':hidden:not("#institution, #branch")'
            });

			$("#btn-reset").click(function(){
            	$('#loginname').val($('#default_loginname').val());
				$('#givenname').val($('#default_givenname').val());
				$('#middlename').val($('#default_middlename').val());
            	$('#lastname').val($('#default_lastname').val());
				$('#email').val($('#default_email').val());
				$('#phone').val($('#default_phone').val());
				$('#status').val($('#default_status').val());
				$('#checkStatus').val($('#default_checkStatus').val());
				$('input[name="roles"]').prop('checked', false);
				
				$('#institution option:selected').each(function() {
            		$(this).prop('selected', false);
                });

            	resetMultiselect('#branch');
            	/* resetMultiselect('#unit');
            	resetMultiselect('#center'); */
            	inisiateMultiselect('#institution');
            	inisiateMultiselect('#branch');
            	/* inisiateMultiselect('#unit');
            	inisiateMultiselect('#center'); */
            	oTable.fnDraw();
            });
            
            $("#btn-save").click(function(){
                if ($('form[name="user"]').valid()) {
                	var dataJson = $('form[name="user"]').serializeObject();
                	var roles = [];
                	
                	$('#dataTable').find('tr').each(function () {
        				var state = "0";
        				var checkbox = $(this).find('input[type="radio"]');
       					if (checkbox.is(':checked')){
            				state = "1";
       					}
       					if (checkbox.val()){
       						var row = {"id" : checkbox.val(), "state" : state};
       						roles.push(row);
       					}
    				});

                	dataJson.roles = roles;
    				submit('/administration/user/save', JSON.stringify(dataJson), function (data) {
                        $("input[name='id']").val(data.id);
                    });
                }
            });
            
            $("#btn-reset-password").click(function(){
            	var id	= $.trim( $("input[name='id']").val());
            	if(id != ''){
            		var data	= {"id" : id};
                	submit('/administration/user/resetPassword', JSON.stringify(data), function (status) {
                		$("#status").val(status);
                	});
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
						<li>Administrator</li>
						<li><a
							href="${pageContext.request.contextPath}/administration/user/">Users</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit User
					</h3>
					<em>Administration</em>
				</div>
			</div>
			<div class="col-lg-6">
				<div class="top-content-button">
					<ul class="list-inline quick-access">
						<c:if test="${user.id != null}">
							<li>
								<button class="btn btn-danger" type="button" data-toggle="modal"
									data-target="#confirm-reset">
									<i class="fa fa-minus-circle"></i> Reset Password
								</button>
							</li>
						</c:if>
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
			<form class="form-horizontal" role="form" method="post" name="user">
				<input type="hidden" name="id" value="${user.id}">
				<input type="hidden" id="default_loginname" value="${user.usrLoginname}"> 
				<input type="hidden" id="default_givenname" value="${user.givenName}">
				<input type="hidden" id="default_middlename" value="${user.middleName}">
				<input type="hidden" id="default_lastname" value="${user.lastName}">
				<input type="hidden" id="default_email" value="${user.usrEmail}">
				<input type="hidden" id="default_phone" value="${user.usrPhone}">
				<input type="hidden" id="default_status" value="${user.usrStatus}">
				<input type="hidden" id="default_checkStatus" value="${user.checkStatus}">
				<!-- <input type="hidden" id="default_institution" value="${availInst.description}">
				<input type="hidden" id="default_branch" value="${availBranch.description}"> -->

				<div class="row">
					<div class="col-md-6">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-user"></i> Account Details
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-body">
									<div class="form-group">
										<label class="col-md-4 control-label">User Name <span
											class="required">*</span></label>
										<div class="col-md-8">
											<div>
												<input id="loginname" name="login"
													value="${user.usrLoginname}" type="text"
													class="form-control required" placeholder="User Name"
													pattern="^[a-zA-Z0-9]{6,20}$"
													title="Login Name no allow blank space and special character, min 6 max 20" />
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-4 control-label">Given Name <span
											class="required">*</span></label>
										<div class="col-md-8">
											<div>
												<input id="givenname" name="givenName"
													value="${user.givenName}" type="text"
													class="form-control required" placeholder="Given Name" />
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-4 control-label">Middle Name <span
											class="required">*</span></label>
										<div class="col-md-8">
											<div>
												<input id="middlename" name="middleName"
													value="${user.middleName}" type="text"
													class="form-control required" placeholder="Middle Name" />
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-4 control-label">Last Name <span
											class="required">*</span></label>
										<div class="col-md-8">
											<div>
												<input id="lastname" name="lastName"
													value="${user.lastName}" type="text"
													class="form-control required" placeholder="Last Name" />
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-4 control-label">Email <span
											class="required">*</span></label>
										<div class="col-md-8">
											<div class="input-group">
												<input id="email" name="email" value="${user.usrEmail}"
													type="email" class="form-control required"
													placeholder="Email" /> <span class="input-group-addon"><i
													class="fa fa-envelope"></i></span>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-4 control-label">Mobile Number <span
											class="required">*</span></label>
										<div class="col-md-8">
											<div class="input-group">
												<input id="phone" name="phone" value="${user.usrPhone}"
													type="tel" class="form-control required phoneUS"
													placeholder="Mobile Number" /> <span
													class="input-group-addon"><i class="fa fa-phone"></i></span>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-4 control-label">User Status <span
											class="required">*</span></label>
										<div class="col-md-8">
											<div>
												<select id="status" name="status"
													class="form-control required">
													<option value="">-- Choose --</option>
													<c:forEach items="${listUserStatus}" var="lookup">
														<option value="${lookup.value}"
															${lookup.value == user.usrStatus ? 'selected="selected"' : ''}>${lookup.description}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-4 control-label">Concurrent User
											Status <span class="required">*</span>
										</label>
										<div class="col-md-8">
											<div>
												<select id="checkStatus" name="checkStatus"
													class="form-control required">
													<option value="">-- Choose --</option>
													<c:forEach items="${listConcurrent}"
														var="lookupConcurrentUser">
														<option value="${lookupConcurrentUser.value}"
															${lookupConcurrentUser.value == user.checkStatus ? 'selected="selected"' : ''}>${lookupConcurrentUser.description}</option>
													</c:forEach>
												</select>
											</div>
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
									<i class="fa fa-sitemap"></i> Granted Roles
								</h3>
							</div>
							<div class="widget-content">
								<table id="dataTable"
									class="table table-striped table-bordered table-hover table-full-width">
									<thead>
										<tr>
											<th>Granted ?</th>
											<th>Role Name</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="role" items="${listRole}">
											<tr>
												<td><c:set var="checked" value="false" /> 
													<c:forEach	var="userrole" items="${listUserrole}">
														<c:if test="${userrole.role.id == role.id}">
															<c:set var="checked" value="true" />
														</c:if>
													</c:forEach> <input type="radio"  id="roles" name="roles" value="${role.id}"
													${checked ? 'checked="checked"' : ''} >${role.name}</input></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-sitemap"></i> Structure
								</h3>
							</div>
							<div class="widget-content">

								<div class="form-group">
									<label class="col-md-4 control-label">Institution <span
										class="required">*</span></label>
									<div class="col-md-8">
										<div>
											<select id="institution" name="institution[]"
												multiple="multiple" size="1">
												<c:forEach items="${availableInstitutionList}"
													var="availInst">
													<c:set var="selected" value="false" />
													<c:forEach var="selectedInst"
														items="${selectedInstitutionList}">
														<c:if test="${availInst.code == selectedInst.code}">
															<c:set var="selected" value="true" />
														</c:if>
													</c:forEach>
													<option value="${availInst.code}"
														${selected ? 'selected="selected"' : ''}>${availInst.description}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-4 control-label">Branch <span
										class="required">*</span></label>
									<div class="col-md-8">
										<div>
											<select id="branch" name="branch[]" multiple="multiple"
												size="1">
												<c:forEach items="${availableBranchList}" var="availBranch">
													<c:set var="selected" value="false" />
													<c:forEach var="selectedBranch"
														items="${selectedBranchList}">
														<c:if test="${availBranch.code == selectedBranch.code}">
															<c:set var="selected" value="true" />
														</c:if>
													</c:forEach>
													<option value="${availBranch.code}"
														${selected ? 'selected="selected"' : ''}>${availBranch.description}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>

								<!-- <%-- <div class="form-group">
                                <label class="col-md-4 control-label">Unit <span class="required">*</span></label>
                                <div class="col-md-8">
                                	<div>
	                                    <select id="unit" name="unit[]" multiple="multiple" size="1">
	                                    <c:forEach items="${availableUnitList}" var="availUnit">
	                                    	<c:set var="selected" value="false"/>
	                                        <c:forEach var="selectedUnit" items="${selectedUnitList}">
	                                            <c:if test="${availUnit.code == selectedUnit.code}">
	                                                <c:set var="selected" value="true"/>
	                                            </c:if>
	                                        </c:forEach>
	                                        <option value="${availUnit.code}" ${selected ? 'selected="selected"' : ''}>${availUnit.description}</option>
	                                    </c:forEach>
	                                	</select>
                                	</div>
                                </div>
                            </div> --%>

								<%-- <div class="form-group">
                                <label class="col-md-4 control-label">Center Management <span class="required">*</span></label>
                                <div class="col-md-8">
                                	<div>
                                		<select id="center" name="center[]" multiple="multiple" size="1">
	                                	<c:forEach items="${availableCenterList}" var="availCenter">
	                                    	<c:set var="selected" value="false"/>
	                                        <c:forEach var="selectedCenter" items="${selectedCenterList}">
	                                            <c:if test="${availCenter.code == selectedCenter.code}">
	                                                <c:set var="selected" value="true"/>
	                                            </c:if>
	                                        </c:forEach>
	                                        <option value="${availCenter.code}" ${selected ? 'selected="selected"' : ''}>${availCenter.description}</option>
	                                    </c:forEach>
	                                	</select>
                                	</div>
                                </div>
                            </div> --%> -->

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