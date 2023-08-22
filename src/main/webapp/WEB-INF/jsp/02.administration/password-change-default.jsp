<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en" class="no-js">

<head>
<title>CARDBank Inc,</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />

<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/assets/favicon.ico">

<link
	href="${pageContext.request.contextPath}/assets/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/assets/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/assets/css/main.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/assets/css/custom-style.css"
	rel="stylesheet" type="text/css" />
</head>

<body>
	<!-- WRAPPER -->
	<div class="wrapper">
		<!-- TOP BAR -->
		<div class="top-bar">
			<div class="line"></div>
			<div class="container">
				<div class="row">
					<!-- logo -->
					<div class="col-md-6 logo">
						<a href="${pageContext.request.contextPath}/"><img
							src="${pageContext.request.contextPath}/assets/img/konek2card.png"
							height="75px" alt="logo" /></a>
						<h1 class="sr-only">Card Bank</h1>
					</div>
					<!-- end logo -->
					<div class="col-md-6">
						<div class="row">
							<!-- logged user and the menu -->
							<div class="logged-user header-right">
								<div class="btn-group">
									<a href="#" class="btn btn-link dropdown-toggle"
										data-toggle="dropdown"> <img
										src="${pageContext.request.contextPath}/assets/img/avatar.png"
										class="img-circle" alt="User Avatar"> <span class="name">${loginSecUser.givenName}</span>
										<span class="caret"></span>
									</a>
									<ul class="dropdown-menu" role="menu">
										<li><a href="${pageContext.request.contextPath}/logout">
												<i class="fa fa-power-off"></i> <span class="text">Logout</span>
										</a></li>
									</ul>
								</div>
							</div>
							<!-- end logged user and the menu -->
						</div>
						<!-- /row -->
					</div>
				</div>
				<!-- /row -->
			</div>
			<!-- /container -->
		</div>
		<!-- /top -->

		<!-- top general alert -->
		<div id="klikAlertMessage"></div>
		<!-- end top general alert -->

		<!-- content-wrapper -->
		<div class="col-md-12 content-wrapper">
			<c:if test="${err_msg != null}">
				<div class="alert alert-danger">
					<span> ${err_msg} </span>
				</div>
			</c:if>
			<c:if test="${msg != null}">
				<div class="alert alert-success">
					<span> ${msg} </span>
				</div>
			</c:if>
			<!-- main -->
			<div class="content">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Home</li>
					</ul>
					<h3>
						<i class="fa fa-lock"></i> Change Password
					</h3>
					<em>Administration</em>
				</div>
				<div class="main-content">
					<form class="form-horizontal" role="form" method="post">
						<div class="row">
							<div class="col-md-12">
								<div class="widget">
									<div class="widget-header">
										<h3>
											<i class="fa fa-lock"></i> Change Password
										</h3>
									</div>
									<div class="widget-content form">
										<div class="form-body">
											<div class="form-group">
												<label class="col-md-3 control-label">Login Name</label>
												<div class="col-md-4">
													<div class="input-group">
														<input type="text" class="form-control"
															readonly="readonly" value="${loginSecUser.usrLoginname}" />
														<span class="input-group-addon"><i
															class="fa fa-user"></i></span>
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 control-label">Old Password <span
													class="required">*</span></label>
												<div class="col-md-4">
													<div class="input-group">
														<input type="password" class="form-control required"
															name="oldPassword" placeholder="Old Password" /> <span
															class="input-group-addon"><i class="fa fa-lock"></i></span>
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 control-label">New Password <span
													class="required">*</span></label>
												<div class="col-md-4">
													<div class="input-group">
														<input type="password" id="password"
															class="form-control required" name="newPassword"
															placeholder="New Password"
															pattern="^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[~!@#$%^&*])[a-zA-Z0-9~!@#$%^&*()+=]{8,1000}$"
															title="Password must contain letter(Upper and Lower case), number and symbol, min 8" />
														<span class="input-group-addon"><i
															class="fa fa-lock"></i></span>
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 control-label">Retype New
													Password <span class="required">*</span>
												</label>
												<div class="col-md-4">
													<div class="input-group">
														<input type="password" class="form-control required"
															name="retypePassword" placeholder="Retype New Password" />
														<span class="input-group-addon"><i
															class="fa fa-lock"></i></span>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="col-md-12">
								<div class="form-actions">
									<button type="reset" class="btn btn-default">
										<i class="fa fa-refresh"></i> Reset
									</button>
									<button id="btn-save" type="button" class="btn btn-success">
										<i class="fa fa-save"></i> Save
									</button>
								</div>
							</div>
						</div>
					</form>
				</div>
				<!-- /main-content -->
			</div>
			<!-- /main -->

			<div id="loader"
				style="position: fixed; bottom: 10px; right: 20px; display: none;">
				<img
					src="${pageContext.request.contextPath}/assets/img/loading-cube.gif" />
			</div>
		</div>
		<!-- /content-wrapper -->
	</div>

	<!-- FOOTER -->
	<footer class="footer">
		<div class="copyright">2015 &copy; ValueQuest</div>
		<div class="copyright version">Version 1.0.${buildNumber} build
			20150704.1727</div>
	</footer>
	<!-- END FOOTER -->

	<!-- Javascript -->
	<script
		src="${pageContext.request.contextPath}/assets/js/jquery/jquery-2.1.3.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/assets/plugins/jquery-validation/dist/jquery.validate.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/plugins/jquery.blockui.min.js"
		type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/js/core/app.js"></script>

	<script type="text/javascript">
		var contextPath = '${pageContext.request.contextPath}';
	
		$(document).ready(function() {
	    	
	        $('form').validate({
	            rules : {
	                retypePassword : { equalTo : "#password" }
	            }
	        });
	
	        $("#btn-save").click(function(){
	            if ($('form').valid()) {
	                submit('/password/change', $('form').serialize(), function(data){
	                	document.location.href = contextPath;
	                });
	            }
	        });
	    });
		
		function isJson(str) {
        	try {
            	JSON.parse(str);
        	} catch (e) {
            	return false;
        	}
        	return true;
    	}
		
		function submit(url, data, handler) {
            $.ajax({
                contentType: (isJson(data)? 'application/json':'application/x-www-form-urlencoded'),
                type : 'POST',
                processData: false,
                url: contextPath + url,
                data: data,
                beforeSend: function(){
                    App.blockUI({target:'.wrapper', boxed: true});
                    $("#loader").show();
                },
                success: function(response){
                    if (response.success) {
                        handler(response.data);
                        toastr['success']( response.message || 'Success' );
                    } else {
                        App.alert({type: 'danger', message: response.message, icon: 'warning'});
                    }
                },
                error: function(jqXHR, status, error){
                    console.log(status);
                    console.log(error);
                    App.alert({
                        type: 'danger',
                        message: status.substr(0, 1).toUpperCase() + status.substr(1) + '. ' + error,
                        icon: 'warning'
                    });
                },
                complete: function() {
                    setTimeout(function(){
                        App.unblockUI('.wrapper');
                        $("#loader").hide();
                    }, 1000); // for demo blockUI
                }
            });
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
</body>
</html>