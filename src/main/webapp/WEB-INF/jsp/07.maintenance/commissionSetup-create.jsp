<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Commission Setup | Create/Edit</title>
<script type="text/javascript">
		$(document).ready(function() {
			$("form").validate();
			
			$("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/utilities/commission-setup/save', JSON.stringify(dataJson), function (data) {
                    	$("#isNew").val(false);
                    });
                }
            });
			
			$("#btn-reset").click(function(){
				$('#transType').val("");
				$('#commissionType').val("");
				$('#customerIncome').val("");
				$('#bankIncome').val("");
				$('#agentIncome').val("");
				$('#bankPartnerIncome').val("");
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
							href="${pageContext.request.contextPath}/utilities/commission-setup/">Commission
								Setup</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Commission Setup
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

				<div class="row">
					<div class="col-md-12">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-sitemap fa-fw"></i> Create/Edit Commission
									Setup
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-group">
									<label class="col-md-3 control-label">Transaction Type<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="transType" name="transType"
											value="${commissionSetup.transType}" type="text"
											class="form-control required" placeholder="Transaction Type" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Commission Type<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="commissionType" name="commissionType"
											value="${commissionSetup.commissionType}" type="text"
											class="form-control required" placeholder="Commission Type" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Customer Income<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="customerIncome" name="customerIncome"
											value="${commissionSetup.customerIncome}" type="text"
											class="form-control required" placeholder="Customer Income" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Agent Income<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="agentIncome" name="agentIncome"
											value="${commissionSetup.agentIncome}" type="text"
											class="form-control required" placeholder="Agent Income" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Bank Income<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="bankIncome" name="bankIncome"
											value="${commissionSetup.bankIncome}" type="text"
											class="form-control required" placeholder="Bank Income" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Bank Partner
										Income<span class="required">*</span>
									</label>
									<div class="col-md-4">
										<input id="bankPartnerIncome" name="bankPartnerIncome"
											value="${commissionSetup.bankPartnerIncome}" type="text"
											class="form-control required"
											placeholder="Bank Partner Income" />
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