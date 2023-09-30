<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Product Pay | Create/Edit</title>
<script type="text/javascript">
		$(document).ready(function() {
			$("form").validate();

			if(! ${isNew}) {
				$("#productCode").attr("readonly","readonly");
			}
			
			$("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/utilities/product-pay/save', JSON.stringify(dataJson), function (data) {
                    	$("#isNew").val(false);
                    	$("#productCode").attr("readonly","readonly");
                    });
                }
            });
			
			$("#btn-reset").click(function(){
				$('#billerCode').val("");
				$('#productCode').val("");
				$('#productName').val("");
				$('#description').val("");
				$('#status').val("");
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
							href="${pageContext.request.contextPath}/utilities/product-pay/">Product
								Pay</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Product Pay
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
									<i class="fa fa-sitemap fa-fw"></i> Create/Edit Product Pay
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-group">
									<label class="col-md-3 control-label">Biller Code <span
										class="required">*</span></label>
									<div class="col-md-4">
										<select id="billerCode" name="billerCode" class="form-control">
											<option value="">-- Biller Code --</option>
											<c:forEach items="${listBillerPay}" var="p">
												<option value="${p.billerCode}"
													${p.id == productPay.billerId ? 'selected="selected"' : ''}>${p.billerName}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Product Code<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="productCode" name="productCode"
											value="${productPay.productCode}" type="text"
											class="form-control required" placeholder="Product Code" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Product Name<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="productName" name="productName"
											value="${productPay.productName}" type="text"
											class="form-control required" placeholder="Product Name" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Description<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="description" name="description"
											value="${productPay.description}" type="text"
											class="form-control required" placeholder="Description" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Status<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="status" name="status" value="${productPay.status}"
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