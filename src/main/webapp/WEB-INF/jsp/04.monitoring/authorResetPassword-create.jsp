<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Author Reset Password | Create/Edit</title>
<script type="text/javascript">
		$(document).ready(function() {
			$("form").validate();

			$("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/monitoring/author-reset-password/save', JSON.stringify(dataJson), function (data) {
                    	$("#cid").attr("readonly","readonly");
                    	$("#clientName").attr("readonly","readonly");
                    	$("#status").attr("readonly","readonly");
                    	$("#type").attr("readonly","readonly");
                    	$("#status").attr("value",data.status);
                    });
                }
            });
			
			$("#btn-rejected").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/monitoring/author-reset-password/rejected', JSON.stringify(dataJson), function (data) {
                    	$("#cid").attr("readonly","readonly");
                    	$("#clientName").attr("readonly","readonly");
                    	$("#status").attr("readonly","readonly");
                    	$("#type").attr("readonly","readonly");
                    	$("#status").attr("value",data.status);
                    });
                }
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
						<li>Monitoring</li>
						<li><a
							href="${pageContext.request.contextPath}/monitoring/author-reset-password/">Author
								Reset Password</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Provider
					</h3>
					<em>Monitoring</em>
				</div>
			</div>
			<div class="col-lg-6 ">
				<div class="top-content-button">
					<ul class="list-inline quick-access">
						<li></li>
						<li>
							<button id="btn-save" class="btn btn-custom-secondary"
								type="button">
								<i class="fa fa-save"></i>Approved
							</button>
							<button id="btn-rejected" class="btn btn-custom-secondary"
								type="button">
								<i class="fa fa-save"></i>Rejected
							</button>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="main-content">
			<form class="form-horizontal" role="form"
				enctype="multipart/form-data">
				<input type="hidden" id="default_code"
					value="${authorResetPassword.clientId}">
				<div class="row">
					<div class="col-md-12">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-sitemap fa-fw"></i> Create/Edit Author Reset
									Password
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-group">
									<label class="col-md-3 control-label">CID<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="cid" name="cid" value="${authorResetPassword.cid}"
											type="text" class="form-control required" placeholder="CID" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Client Name<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="clientName" name="clientName"
											value="${authorResetPassword.clientName}" type="text"
											class="form-control required" placeholder="Client Name" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Type<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="type" name="type"
											value="${authorResetPassword.type}" type="text"
											class="form-control required" placeholder="Type" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Status<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="status" name="status"
											value="${authorResetPassword.status}" type="text"
											class="form-control required" placeholder="Status" />
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