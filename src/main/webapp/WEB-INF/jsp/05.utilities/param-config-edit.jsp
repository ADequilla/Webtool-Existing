<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Parameter Configuration | Edit</title>
<script type="text/javascript">
        $(document).ready(function() {
            $("form").validate();

			$("#btn-reset").click(function(){
            	$('#type').val($('#default_type').val());
            	$('#name').val($('#default_name').val());
				$('#configValue').val($('#default_value').val());
            	$('#configDescription').val($('#default_description').val());
            });

            $("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/utilities/config/save', JSON.stringify(dataJson), function (data) {
	                	$("input[name='id']").val(data.id);
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
						<li>Utilities</li>
						<li><a
							href="${pageContext.request.contextPath}/utilities/config/">Parameter
								Configuration</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Edit Parameter Configuration
					</h3>
					<em>Utilities</em>
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

		<form class="form-horizontal" role="form" method="post">
			<input type="hidden" name="id" value="${config.id}"> 
			<input type="hidden" id="default_type" value="${paramConfig.type}"> 
			<input type="hidden" id="default_name" value="${config.name}">
			<input type="hidden" id="default_value" value="${config.value}"> 
			<input type="hidden" id="default_description" value="${config.description}">

			<div class="row">
				<div class="col-md-12">
					<div class="widget">
						<div class="widget-header">
							<h3>
								<i class="fa fa-wrench"></i> Parameter Configuration
							</h3>
						</div>
						<div class="widget-content form">
							<div class="form-body">

								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">Parameter Type <span
											class="required">*</span></label>
										<div class="col-md-4">
											<select id="type" name="type" class="form-control required">
												<option value="">--Parameter Type--</option>
												<c:forEach items="${listConfigType}" var="lookup">
													<option value="${lookup.value}"
														${lookup.value == paramConfig.type ? 'selected="selected"':''}>${lookup.description}</option>

												</c:forEach>
											</select>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Parameter Name <span
											class="required">*</span></label>
										<div class="col-md-4">
											<input id="name" name="name" value="${config.name}"
												type="text" class="form-control required" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Parameter Value
											<span class="required">*</span>
										</label>
										<div class="col-md-4">
											<c:choose>
												<c:when test="${config.valueType=='LIST'}">
													<select id="configValue" name="value"
														class="form-control required">
														<c:forEach items="${configValueList}" var="lookup">
															<option value="${lookup.value}"
																${lookup.value == config.value ? 'selected="selected"' : ''}>${lookup.description}</option>
														</c:forEach>
													</select>
												</c:when>
												<c:otherwise>
													<input id="configValue" name="value"
														value="${config.value}" type="${config.valueType}"
														class="form-control required"
														placeholder="Parameter Value" />
												</c:otherwise>
											</c:choose>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Description <span
											class="required">*</span></label>
										<div class="col-md-4">
											<textarea id="configDescription" name="description" rows="5"
												class="form-control required" placeholder="Description">${config.description}</textarea>
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