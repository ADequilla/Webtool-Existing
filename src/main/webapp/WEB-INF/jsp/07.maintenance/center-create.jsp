<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Center | Create/Edit</title>
<script type="text/javascript">
		$(document).ready(function() {
			$("form").validate();
			
			if(! ${isNew}) {
				$("#code").attr("readonly","readonly");
			}
			
			$("#btn-save").click(function(){
            	if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/utilities/center/save', JSON.stringify(dataJson), function (data) {
                    	$("#isNew").val(false);
                    	$("#code").attr("readonly","readonly");
	                });
                }
            });
			
			$("#btn-reset").click(function(){
				$('#code').val($('#default_code').val());
				$('#description').val($('#default_description').val());
			});
        });

	// 	if (localStorage.getItem('isPageOpen')) {
    //   alert('Page is already open in another tab!');
	//   window.location.href = '${pageContext.request.contextPath}/logout';  
    // } else {
    //   localStorage.setItem('isPageOpen', true);
    //   window.addEventListener('beforeunload', function () {
    //     localStorage.removeItem('isPageOpen');
    //   });
    // }
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
						<li>Utilities</li>
						<li><a
							href="${pageContext.request.contextPath}/utilities/center/">Center</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Center
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
				<input type="hidden" id="default_code" value="${center.code}">
				<input type="hidden" id="default_description"
					value="${center.description}"> <input type="hidden"
					id="isNew" name="isNew" value="${isNew}">

				<div class="row">
					<div class="col-md-12">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-sitemap fa-fw"></i> Create/Edit Center
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-group">
									<label class="col-md-3 control-label">Center Code <span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="code" name="code" value="${center.code}"
											type="text" class="form-control required"
											placeholder="Center Code" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Center
										Description <span class="required">*</span>
									</label>
									<div class="col-md-4">
										<input id="description" name="description"
											value="${center.description}" type="text"
											class="form-control required" placeholder="Description" />
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