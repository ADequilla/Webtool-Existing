<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Bank List | Create/Edit</title>
<script type="text/javascript">
		$(document).ready(function() {
			$("form").validate();

			if(! ${isNew}) {
				/* $("#bankCode").attr("readonly","readonly"); */
			}
			
			$("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/utilities/bank-list/save', JSON.stringify(dataJson), function (data) {
                    	$("#isNew").val(false);
                    	/* $("#bankCode").attr("readonly","readonly"); */
                    });
                }
            });
			
			$("#btn-reset").click(function(){
				$('#bankCode').val("");
				$('#bankName').val("");
				$('#shortName').val("");
				$('#bankBic').val("");
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
							href="${pageContext.request.contextPath}/utilities/bank-list/">Bank
								List</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Bank
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
									<i class="fa fa-sitemap fa-fw"></i> Create/Edit Bank
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-group">
									<label class="col-md-3 control-label">Bank Code<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="bankCode" name="bankCode"
											value="${bankList.bankCode}" type="text"
											class="form-control required" placeholder="Bank Code" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Bank Name<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="bankName" name="bankName"
											value="${bankList.bankName}" type="text"
											class="form-control required" placeholder="Bank Name" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Short Name<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="shortName" name="shortName"
											value="${bankList.shortName}" type="text"
											class="form-control required" placeholder="Short Name" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">BIC<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="bankBic" name="bankBic" value="${bankList.bankBic}"
											type="text" class="form-control required" placeholder="BIC" />
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