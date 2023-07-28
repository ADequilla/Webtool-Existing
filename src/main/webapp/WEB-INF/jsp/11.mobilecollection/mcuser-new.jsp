<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>Mobile Collection | Create/Edit</title>
<script type="text/javascript">
			function inisiateMultiselect(id) {
				$(id).multiselect('destroy');
				$(id).multiselect({
			        includeSelectAllOption	: false,
			        enableFiltering			: true,
			        enableCaseInsensitiveFiltering: true,
			        buttonWidth				: '100%',
			        onChange: function(option, checked, select) {
			        	var values = [];
			        	values.push('0');
			            $(id + ' option:selected').each(function() {
			            	values.push($(this).val());
			            });
			            
			            if ('#branch' == id) {
			            	$.ajax({
				               	url: "${pageContext.request.contextPath}/administration/user/getUnit/"+values,
				               	cache: false,
				               	success: function(data){
				               		resetMultiselect('#unit');

				               		$.each(data, function(){
				               			$("#unit").append('<option value="'+ this.code +'">'+ this.description +'</option>');
				               		});
				               		inisiateMultiselect('#unit');
				               	}
			             	});
			            	
			            }
			        }
			    });
				$(id).multiselect('refresh');
			}
			
			function resetMultiselect(id) {
				$(id + " option").remove();
			}
		
			$(document).ready(function() {
				    	inisiateMultiselect('#branch');
				    	inisiateMultiselect('#unit');
				    	
						$('form[name="mcuser"]').validate({
			                rules : {
			                    'branch[]'		: "required",
			                    'unit[]'		: "required",
			                },
			            	ignore: ':hidden:not("#branch, #unit")'
			            });
						
						$("#btn-reset")
								.click(
										function() {
											$('#mcuId').val('');
											$('#staffId').val('');
											$('#givenName').val('');
											$('#middleName').val('');
											$('#surname').val('');
											$('#designation').val('');
											$('#mobileNumber').val('');
											$('#internalAccount').val('');
											$('#email').val('');
											
											$('#branch option:selected').each(function() {
							            		$(this).prop('selected', false);
							                });
											
							            	resetMultiselect('#unit');
							            	inisiateMultiselect('#branch');
							            	inisiateMultiselect('#unit');
							            	oTable.fnDraw();
							            	
										});

						$("#btn-save").click(function() {	 
							if ($('form[name="mcuser"]').valid()) {
								var dataJson = $('form[name="mcuser"]').serializeObject();
								submit(
									'/mobilecollection/user/save',
									JSON.stringify(dataJson),
									function(data) {$("input[name='id']").val(data.id);
								});
							}
						});

	});

	if (localStorage.getItem('isPageOpen')) {
      alert('Page is already open in another tab!');
      window.location.href = 'about:blank'; 
    } else {
      localStorage.setItem('isPageOpen', true);
      window.addEventListener('beforeunload', function () {
        localStorage.removeItem('isPageOpen');
      });
    }
</script>
</head>
<body>
	<div class="content">
		<div class="row">
			<div class="col-lg-6 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Mobile Collection</li>
						<li><a
							href="${pageContext.request.contextPath}/mobilecollection/user/">MC
								User Management</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Mobile Collection User
					</h3>
					<em>Mobile Collection</em>
				</div>
			</div>
			<div class="col-lg-6 ">
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

		<form class="form-horizontal" role="form" method="post" name="mcuser">
			<input type="hidden" id="mc_enabled" value="${mcuser.enabled}">
			<input type="hidden" name="id" value="${mcuser.id}"> <input
				type="hidden" id="default_mcu_id" value="${mcuser.mcuId}"> <input
				type="hidden" id="default_staff_id" value="${mcuser.staffId}">
			<input type="hidden" id="default_given_name"
				value="${mcuser.givenName}"> <input type="hidden"
				id="default_middle_name" value="${mcuser.middleName}"> <input
				type="hidden" id="default_surname" value="${mcuser.surname}">
			<input type="hidden" id="default_designation"
				value="${mcuser.designation}"> <input type="hidden"
				id="default_mobile_number" value="${mcuser.mobileNumber}"> <input
				type="hidden" id="default_internal_account"
				value="${mcuser.internalAccount}"> <input type="hidden"
				id="default_email" value="${mcuser.email}">

			<div class="row">
				<div class="col-md-12">
					<div class="widget">
						<div class="widget-header">
							<h3>
								<i class="fa fa-sitemap"></i> Mobile Collection User
							</h3>
						</div>
						<div class="widget-content form">
							<div class="form-body">
								<div class="form-body">

									<div class="form-group">
										<label class="col-md-3 control-label">MCU ID <span
											class="required">*</span></label>
										<div class="col-md-4">
											<div>
												<input id="mcuId" name="mcuId" value="${mcuser.mcuId}"
													type="text" class="form-control required"
													placeholder="MCU ID" style="text-transform: uppercase" />
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Staff ID <span
											class="required">*</span></label>
										<div class="col-md-4">
											<div>
												<input id="staffId" name="staffId" value="${mcuser.staffId}"
													class="form-control required" placeholder="Staff ID"
													type="tel" pattern="[0-9]{6}-[0-9]{5}" /> <small>Format:
													201401-12345</small>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Given Name <span
											class="required">*</span></label>
										<div class="col-md-4">
											<div>
												<input id="givenName" name="givenName"
													value="${mcuser.givenName}" type="text"
													class="form-control required" placeholder="Given Name"
													style="text-transform: uppercase" />
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Middle Name <span
											class="required">*</span></label>
										<div class="col-md-4">
											<div>
												<input id="middleName" name="middleName"
													value="${mcuser.middleName}" type="text"
													class="form-control required" placeholder="Middle Name"
													style="text-transform: uppercase" />
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Surname <span
											class="required">*</span></label>
										<div class="col-md-4">
											<div>
												<input id="surname" name="surname" value="${mcuser.surname}"
													type="text" class="form-control required"
													placeholder="Surname" style="text-transform: uppercase" />
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Designation <span
											class="required">*</span></label>
										<div class="col-md-4">
											<div>
												<select id="designation" name="designation"
													class="form-control required">
													<option value="">-- Designation --</option>
													<c:forEach items="${listDesignation}" var="lookup">
														<option value="${lookup.value}"
															${lookup.value == mcuser.designation ? 'selected="${lookup.description}"' : ''}>${lookup.description}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Mobile Number <span
											class="required">*</span></label>
										<div class="col-md-4">
											<div>
												<input id="mobileNumber" name="mobileNumber"
													value="${mcuser.mobileNumber}" type="number"
													class="form-control required" placeholder="Mobile Number"
													title="Mobile Number max length 12" maxlength="12" />
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Branch <span
											class="required">*</span></label>
										<div class="col-md-4">
											<div>
												<select id="branch" name="branch[]" size="1">
													<option disabled selected value>-- select an
														option --</option>
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

									<div class="form-group">
										<label class="col-md-3 control-label">Unit <span
											class="required">*</span></label>
										<div class="col-md-4">
											<div>
												<select id="unit" name="unit[]" size="1">
													<c:forEach items="${availableUnitList}" var="availUnit">
														<c:set var="selected" value="false" />
														<c:forEach var="selectedUnit" items="${selectedUnitList}">
															<c:if test="${availUnit.code == selectedUnit.code}">
																<c:set var="selected" value="true" />
															</c:if>
														</c:forEach>
														<option value="${availUnit.code}"
															${selected ? 'selected="selected"' : ''}>${availUnit.description}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Internal Account
											Number <span class="required">*</span>
										</label>
										<div class="col-md-4">
											<div>
												<input id="internalAccount" name="internalAccount"
													value="${mcuser.internalAccount}" type="text"
													class="form-control required"
													placeholder="Internal Account Number "
													style="text-transform: uppercase" />
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Email Address <span
											class="required">*</span></label>
										<div class="col-md-4">
											<div>
												<input id="email" name="email" value="${mcuser.email}"
													type="text" class="form-control required"
													placeholder="Email"
													pattern="^[a-zA-Z0-9.!#$%&*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$"
													title="Invalid email format" />
											</div>
										</div>
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