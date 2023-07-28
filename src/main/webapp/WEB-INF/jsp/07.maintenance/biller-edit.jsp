<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Biller | Create/Edit</title>
<script type="text/javascript">
        $(document).ready(function() {
            $("form").validate();

			$("#btn-reset").click(function(){
            	$('#name').val($('#default_name').val());
            	$('#account').val($('#default_account').val());
            	$('#enabled').val($('#default_enabled').val());
            });

            $("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                	if ($("#enabled").is(':checked'))
                		dataJson.enabled = 1;
                	else
                		dataJson.enabled = 0;
                	
                    submit('/utilities/biller/save', JSON.stringify(dataJson), function (data) {
	                	$("input[name='id']").val(data.id);
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
						<li>Utilities</li>
						<li><a
							href="${pageContext.request.contextPath}/utilities/biller/">Biller</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Biller
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
			<input type="hidden" name="id" value="${biller.id}"> <input
				type="hidden" id="default_name" value="${biller.name}"> <input
				type="hidden" id="default_account" value="${biller.account}">
			<input type="hidden" id="default_enabled" value="${biller.enabled}">

			<div class="row">
				<div class="col-md-12">
					<div class="widget">
						<div class="widget-header">
							<h3>
								<i class="fa fa-usd"></i> Biller
							</h3>
						</div>
						<div class="widget-content form">
							<div class="form-body">

								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">Name <span
											class="required">*</span></label>
										<div class="col-md-4">
											<input id="name" name="name" type="text"
												class="form-control required" value="${biller.name}" />
										</div>
									</div>
								</div>

								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">Account <span
											class="required">*</span></label>
										<div class="col-md-4">
											<input id="account" name="account" type="text"
												class="form-control required" value="${biller.account}" />
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3 control-label">Type <span
										class="required">*</span></label>
									<div class="col-md-4">
										<select id="type" name="type" class="form-control required">
											<option value="">-- Type --</option>
											<c:forEach items="${billerType}" var="lookup">
												<option value="${lookup.value}"
													${lookup.value == biller.type ? 'selected="selected"' : ''}>${lookup.description}</option>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">Enabled <span
											class="required">*</span></label>
										<div class="col-md-4">
											<input type="checkbox" id="enabled" name="enabled"
												${biller.enabled == 1 ? 'checked' : ''}>
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