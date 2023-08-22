<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Broadcast Message | Create/Edit</title>
<script type="text/javascript">
		
		function inisiateMultiselect(id) {
	    	$(id).multiselect('destroy');
	    	$(id).multiselect({
	            includeSelectAllOption	: true,
	            enableFiltering			: true,
	            buttonWidth				: '100%',
	            onChange: function(option, checked, select) {
	            	var values = [];
	            	values.push('0');
	                $(id + ' option:selected').each(function() {
	                	values.push($(this).val());
	                });
	            }
	        });
	    	$(id).multiselect('refresh');
		}
	
		$(document).ready(function() {
            $("form").validate();
            inisiateMultiselect('#branch');
            
			 $("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/cs/message/save', JSON.stringify(dataJson), function (data) {
	                	$("input[name='id']").val(data.id);
	                });
                }
            });
			  
			$("#btn-reset").click(function(){
				$('#subject').val($('#default_subject').val());
				$('#desc').val($('#default_desc').val());
				$('#periodStart').val($('#default_periodStart').val());
				$('#periodEnd').val($('#default_periodEnd').val());
				$('#clientType').val($('#default_clientType').val());

				$('#branch option:selected').each(function() {
							            		$(this).prop('selected', false);
							                });
											
							            	inisiateMultiselect('#branch');
			});
			
        });
		
		function resetMultiselect(id) {
	    	$(id + " option").remove();
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
						<li>Customer Service</li>
						<li><a href="${pageContext.request.contextPath}/cs/message/">Broadcast
								Message</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Broadcast Message
					</h3>
					<em>Customer Service</em>
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
		<div class="main-content">
			<form class="form-horizontal" role="form"
				enctype="multipart/form-data">
				<input type="hidden" name="id" value="${inbox.id}"> 
				<input type="hidden" id="default_subject" value="${inbox.subject}">
				<input type="hidden" id="default_desc" value="${inbox.desc}">
				<input type="hidden" id="default_periodStart" value="${inbox.periodStart}"> 
				<input type="hidden" id="default_periodEnd" value="${inbox.periodEnd}">
				<input type="hidden" id="default_clientType" value="${lookup.description}"> 


				<div class="row">
					<div class="col-md-12">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-envelope"></i> Broadcast Message
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-group">
									<label class="col-md-3 control-label">Subject <span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="subject" name="subject" value="${inbox.subject}"
											type="text" class="form-control required"
											placeholder="Subject" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3 control-label">Period <span
										class="required">*</span></label>
									<div class="col-sm-2">
										<div class="input-group">
											<input id="periodStart" name="periodStart"
												value="${inbox.periodStart}" style="cursor: pointer;"
												type="text" class="form-control required startdatepicker"
												placeholder="Date Start"> <span
												class="input-group-addon"><i class="fa fa-calendar"></i></span>
										</div>
									</div>
									<div class="col-sm-2">
										<div class="input-group">
											<input id="periodEnd" name="periodEnd"
												value="${inbox.periodEnd}" style="cursor: pointer;"
												type="text" class="form-control required enddatepicker"
												placeholder="Date End"> <span
												class="input-group-addon"><i class="fa fa-calendar"></i></span>
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3 control-label">Description <span
										class="required">*</span></label>
									<div class="col-md-4">
										<textarea id="desc" name="desc" class="form-control required"
											rows="6">${inbox.desc}</textarea>
									</div>
								</div>
 
								<div class="form-group">
									<label class="col-md-3 control-label">Client Type <span
										class="required">*</span></label>
									<div class="col-md-4">
										<select id="clientType" name="clientType" class="form-control">
											<option value="">-- Client Type --</option>
											<c:forEach items="${listClientType}" var="lookup">
												<option value="${lookup.value}">${lookup.description}</option>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3 control-label">Branch <span
										class="required">*</span></label>
									<div class="col-md-4">
										<div>
											<select id="branch" name="branch[]" multiple="multiple"
												size="1">
												<c:forEach items="${availableBranchList}" var="availBranch">
													<c:set var="selected" value="false" />
													<c:forEach var="selectedBranch"
														items="${selectedBranchList}">
														<c:if
															test="${availBranch.branch.code == selectedBranch.branch.code}">
															<c:set var="selected" value="true" />
														</c:if>
													</c:forEach>
													<option value="${availBranch.branch.code}"
														${selected ? 'selected="selected"' : ''}>${availBranch.branch.description}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>