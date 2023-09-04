<!DOCTYPE html>
<html>
<head>
<script>
        $(document).ready(function() {
        	
            $('form[name="change-passwd"]').validate({
                rules : {
                    retypePassword : { equalTo : "#password" }
                }
            });

            $("#btn-save").click(function(){
                if ($('form[name="change-passwd"]').valid()) {
                    submit('/password/change', $('form[name="change-passwd"]').serialize(), function(data){});
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
<body class="page-header-fixed">

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
			<form class="form-horizontal" role="form" method="post"
				name="change-passwd">
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
												<input type="text" class="form-control" readonly="readonly"
													value="${loginSecUser.usrLoginname}" /> <span
													class="input-group-addon"><i class="fa fa-user"></i></span>
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
												<span class="input-group-addon"><i class="fa fa-lock"></i></span>
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
												<span class="input-group-addon"><i class="fa fa-lock"></i></span>
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

</body>
</html>