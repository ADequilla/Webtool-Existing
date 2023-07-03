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
										<li><a
											href="${pageContext.request.contextPath}/password/change">
												<i class="fa fa-lock"></i> <span class="text">Change
													Password</span>
										</a></li>
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

		<div class="wrapper full-page-wrapper page-error text-center">
			<div class="inner-page">
				<h1>
					<span class="clearfix title"> <span class="number">404</span>
						<span class="text">Oops! <br />Page Not Found
					</span>
					</span>
				</h1>

				<div>
					<a href="javascript:history.go(-1)" class="btn btn-custom-primary"><i
						class="fa fa-arrow-left"></i> Go Back</a> <a
						href="${pageContext.request.contextPath}/" class="btn btn-primary"><i
						class="fa fa-home"></i> Home</a>
				</div>
			</div>
		</div>

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
		src="${pageContext.request.contextPath}/assets/js/jquery/jquery-2.1.0.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
</body>
</html>