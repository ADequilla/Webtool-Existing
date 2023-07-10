<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en" class="no-js">

<head>
<title>konek2CARD PLUS Webtool</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />

<link href="${pageContext.request.contextPath}/assets/img/favicon.ico"
	rel="shortcut icon">

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
	[[${logoutScript}]]
</head>

<body
	data-original="${pageContext.request.contextPath}/assets/img/mobilebg.png"
	style="background: url('assets/img/mobilebg.png') no-repeat; background-size: 1400px 800px;">
	<div class="wrapper full-page-wrapper page-auth page-login text-center">
		<div class="inner-page">

			<div class="login-container">

				<div class="col-md-6 col-md-offset-1">
					<img
						src="${pageContext.request.contextPath}/assets/img/konek2card.png"
						data-src="${pageContext.request.contextPath}/assets/img/konek2card.png" />
					<p class="color-front text-left">

						Use your registered username and password to sign in.<br> To
						have an account or have some problems, please contact our support
						team.
					</p>
				</div>

				<div class="col-md-4">
					<div class="login-box col-md-10">
						<form class="form-horizontal login-form" method="post" role="form"
							data-toggle="validator"
							action="${pageContext.request.contextPath}/j_spring_security_check">

							<c:if test="${param.error}">
								<div class="alert alert-danger">
									<span>
										${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message} </span>
								</div>
							</c:if>

							<div class="form-group">
								<label for="username" class="control-label sr-only">Username</label>
								<div class="col-sm-12">
									<div class="inner-addon left-addon">
										<i class="fa fa-user"></i> <input type="text"
											class="form-control" autocomplete="off"
											placeholder="Username" name="j_username" required>
									</div>
								</div>
							</div>
							<label for="password" class="control-label sr-only">Password</label>
							<div class="form-group">
								<div class="col-sm-12">
									<div class="inner-addon left-addon">
										<i class="fa fa-lock"></i> <input type="password"
											class="form-control" autocomplete="off"
											placeholder="Password" name="j_password" required>
									</div>
								</div>
							</div>
							<button class="btn btn-block btn-lg btn-custom-auth">
								<i class="fa fa-arrow-circle-o-right"></i> Login
							</button>
						</form>
						<br> <span class="links color-front copyright">
							&copy;CARD Bank | Version 1.0.${buildNumber} build 20150704.1727
						</span>
					</div>
				</div>

			</div>

		</div>
	</div>

	<script
		src="${pageContext.request.contextPath}/assets/js/jquery/jquery-2.1.3.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/js/validator.min.js"></script>
</body>
</html>