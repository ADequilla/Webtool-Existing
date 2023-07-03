<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Biller Pay | Create/Edit</title>
<script type="text/javascript">
		$(document).ready(function() {
			$("form").validate();

			if(! ${isNew}) {
				$("#billerCode").attr("readonly","readonly");
			}
			
			$("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/utilities/biller-pay/save', JSON.stringify(dataJson), function (data) {
                    	$("#isNew").val(false);
                    	$("#billerCode").attr("readonly","readonly");
                    });
                }
            });
			
			$("#btn-reset").click(function(){
				$('#billerCode').val("");
				$('#billerName').val("");
				$('#description').val("");
				$('#totalRebates').val("");
				$('#customerRebates').val("");
				$('#bankRebates').val("");
				$('#status').val("");
			});
        });
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
							href="${pageContext.request.contextPath}/utilities/biller-pay/">Biller
								Pay</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Biller Pay
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

				<div class="row">
					<div class="col-md-12">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-sitemap fa-fw"></i> Create/Edit Biller Pay
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-group">
									<label class="col-md-3 control-label">Biller Code<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="billerCode" name="billerCode"
											value="${billerPay.billerCode}" type="text"
											class="form-control required" placeholder="Biller Code" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Biller Name<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="billerName" name="billerName"
											value="${billerPay.billerName}" type="text"
											class="form-control required" placeholder="Biller Name" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Description<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="description" name="description"
											value="${billerPay.description}" type="text"
											class="form-control required" placeholder="Description" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Total Rebates<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="totalRebates" name="totalRebates"
											value="${billerPay.totalRebates}" type="text"
											class="form-control required" placeholder="Total Rebates" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Customer Rebates<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="customerRebates" name="customerRebates"
											value="${billerPay.customerRebates}" type="text"
											class="form-control required" placeholder="Customer Rebates" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Bank Rebates<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="bankRebates" name="bankRebates"
											value="${billerPay.bankRebates}" type="text"
											class="form-control required" placeholder="Bank Rebates" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Status<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="status" name="status" value="${billerPay.status}"
											type="text" class="form-control required"
											placeholder="Status" />
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