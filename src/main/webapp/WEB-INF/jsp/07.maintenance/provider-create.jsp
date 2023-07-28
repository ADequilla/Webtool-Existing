<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Provider | Create/Edit</title>
<script type="text/javascript">
		$(document).ready(function() {
			$("form").validate();
			
			$("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/utilities/provider/save', JSON.stringify(dataJson), function (data) {
                    	$("#isNew").val(false);
                    });
                }
            });
			
			$("#btn-reset").click(function(){
				$('#providerName').val($('#default_providerName').val());
				$('#description').val($('#default_description').val());
            	$('#providerAlias').val($('#default_providerAlias').val());
				$('#status').val($('#default_status').val());
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
							href="${pageContext.request.contextPath}/utilities/provider/">Provider</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Provider
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
								<i class="fa fa-save"></i>Save Data
							</button>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="main-content">
			<form class="form-horizontal" role="form"
				enctype="multipart/form-data">
				<input type="hidden" id="isNew" name="isNew" value="${isNew}">
				<input type="hidden" id="id" name="id" value="${id}">
				<input type="hidden" id="default_providerName" value="${provider.providerName}">
				<input type="hidden" id="default_description" value="${provider.description}"> 
				<input type="hidden" id="default_providerAlias" value="${provider.providerAlias}"> 
				<input type="hidden" id="default_status" value="${provider.status}">

				<div class="row">
					<div class="col-md-12">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-sitemap fa-fw"></i> Create/Edit Provider
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-group">
									<label class="col-md-3 control-label">Provider Name<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="providerName" name="providerName"
											value="${provider.providerName}" type="text"
											class="form-control required" placeholder="Provider Name" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Description<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="description" name="description"
											value="${provider.description}" type="text"
											class="form-control required" placeholder="Description" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Provider Alias<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="providerAlias" name="providerAlias"
											value="${provider.providerAlias}" type="text"
											class="form-control required" placeholder="Provider Alias" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Status<span
										class="required">*</span></label>
									<div class="col-md-4">
										<select id="status" name="status"
											class="form-control required">
											<option value="">-- Status --</option>
											<c:forEach items="${listBillsParameter}" var="lookup">
												<option value="${lookup.value}"
													${lookup.value == provider.status ? 'selected="selected"' : ''}>${lookup.description}</option>
											</c:forEach>
										</select>
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