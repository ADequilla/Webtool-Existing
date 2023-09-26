<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Biller Product | Create/Edit</title>
<script type="text/javascript">
		$(document).ready(function() {
			$("form").validate();

			if(! ${isNew}) {
				$("#billerProductId").attr("readonly","readonly");
				$("#productCategoryId").attr("readonly","readonly");
			}
			
			$("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/utilities/biller-product/save', JSON.stringify(dataJson), function (data) {
                    	$("#isNew").val(false);
                    	$("#billerProductId").attr("readonly","readonly");
                    	$("#productCategoryId").attr("readonly","readonly");
                    });
                }
            });

			$("#btn-reset").click(function(){
            	$('#productCategoryId').val($('#default_productCategoryId').val());
				$('#billerProductId').val($('#default_billerProductId').val());
				$('#billerProductName').val($('#default_billerProductName').val());
				$('#description').val($('#default_description').val());
            	$('#bankCommission').val($('#default_bankCommission').val());
				$('#serviceFee').val($('#default_serviceFee').val());
				$('#status').val($('#default_status').val());
				
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
							href="${pageContext.request.contextPath}/utilities/biller-product/">Biller
								Product</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Biller Product
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
				<input type="hidden" id="default_productCategoryId" value="${billerProduct.productCategoryId}"> 
				<input type="hidden" id="default_billerProductId" value="${billerProduct.billerProductId}">
				<input type="hidden" id="default_billerProductName" value="${billerProduct.billerProductName}">
				<input type="hidden" id="default_description" value="${billerProduct.description}">
				<input type="hidden" id="default_bankCommission" value="${billerProduct.bankCommission}">
				<input type="hidden" id="default_serviceFee" value="${billerProduct.serviceFee}">
				<input type="hidden" id="default_status" value="${billerProduct.status}">

				<div class="row">
					<div class="col-md-12">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-sitemap fa-fw"></i> Create/Edit Biller Product
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-group">
									<label class="col-md-3 control-label">Product Category<span
										class="required">*</span></label>
									<div class="col-md-4">
										<select id="productCategoryId" name="productCategoryId"
											class="form-control required">
											<option value="">-- Product Category --</option>
											<c:forEach items="${listProductCategoryBiller}"
												var="productCategoryBiller">
												<option value="${productCategoryBiller.productCategoryId}"
													${productCategoryBiller.productCategoryId == billerProduct.productCategoryId ? 'selected="selected"' : ''}>${productCategoryBiller.productCategoryName}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Biller Product Id<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="billerProductId" name="billerProductId"
											value="${billerProduct.billerProductId}" type="text"
											class="form-control required" placeholder="Biller Product Id" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Biller Product
										Name<span class="required">*</span>
									</label>
									<div class="col-md-4">
										<input id="billerProductName" name="billerProductName"
											value="${billerProduct.billerProductName}" type="text"
											class="form-control required"
											placeholder="Biller Product Name" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Description<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="description" name="description"
											value="${billerProduct.description}" type="text"
											class="form-control required" placeholder="Description" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Bank Commission<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="bankCommission" name="bankCommission"
											value="${billerProduct.bankCommission}" type="number"
											class="form-control required" placeholder="Bank Commission" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Service Fee<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="serviceFee" name="serviceFee"
											value="${billerProduct.serviceFee}" type="number"
											class="form-control required" placeholder="Service Fee" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Status<span
										class="required">*</span></label>
									<div class="col-md-4">
										<select id="status" name="status"
											class="form-control required">
											<option value="">-- Status --</option>
											<c:forEach items="${listBillsParameter}" var="lookup">
												<option value="${lookup.value}"
													${lookup.value == billerProduct.status ? 'selected="selected"' : ''}>${lookup.description}</option>
											</c:forEach>
										</select>
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