<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Provider | Create/Edit</title>
<script type="text/javascript">
        $(document).ready(function() {
            $("form").validate();

			$("#btn-reset").click(function(){
				$('#providerName]').val($('#default_name').val());
            	$('#providerDescription').val($('#default_description').val());
            	$('#providerStatus').val($('#default_status').val());

            $("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/utilities/provider/save', JSON.stringify(dataJson), function (data) {
	                	$("input[name='id']").val(data.id);
	                });
                }
            });
            
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
								<i class="fa fa-save"></i> Save Data
							</button>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<form class="form-horizontal" role="form" method="post">
			<input type="hidden" name="id" value="${provider.id}"> <input
				type="hidden" id="default_name" value="${provider.name}"> <input
				type="hidden" id="default_description"
				value="${provider.description}"> <input type="hidden"
				id="default_status" value="${provider.status}">

			<div class="row">
				<div class="col-md-12">
					<div class="widget">
						<div class="widget-header">
							<h3>
								<i class="fa fa-money"></i> Provider
							</h3>
						</div>
						<div class="widget-content form">

							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Name <span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="providerName" name="providerName"
											class="form-control required" value="${provider.name}" />
									</div>
								</div>
							</div>

							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Description<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="providerDescription" name="providerDescription"
											class="form-control required" value="${provider.description}" />
									</div>
								</div>
							</div>

							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Status<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="providerStatus" name="providerStatus" type="number"
											class="form-control required" value="${provider.status}" />
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