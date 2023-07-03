<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Product | Create/Edit</title>
<script type="text/javascript">
        $(document).ready(function() {
            $("form").validate();

			$("#btn-reset").click(function(){
				$('#productName]').val($('#name_default').val());
            	$('#productDescription').val($('#description_default').val());
            	$('#productStatus').val($('#status_default').val());

            $("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/monitoring/bill-payment-product/save', JSON.stringify(dataJson), function (data) {
	                	$("input[name='id']").val(data.id);
	                });
                }
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
						<li>Bill Payment</li>
						<li><a
							href="${pageContext.request.contextPath}/utilities/fee/">Product</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Product
					</h3>
					<em>Bill Payment</em>
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
								<i class="fa fa-save"></i> Save Data
							</button>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<form class="form-horizontal" role="form" method="post">
			<input type="hidden" name="id" value="${product.id}"> <input
				type="hidden" id="name_default" value="${product.name}"> <input
				type="hidden" id="description_default"
				value="${product.description}"> <input type="hidden"
				id="status_default" value="${product.status}">

			<div class="row">
				<div class="col-md-12">
					<div class="widget">
						<div class="widget-header">
							<h3>
								<i class="fa fa-money"></i> Product
							</h3>
						</div>
						<div class="widget-content form">

							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Name <span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="productName" name="productName"
											class="form-control required" value="${product.name}" />
									</div>
								</div>
							</div>

							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Description<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="productDescription" name="productDescription"
											class="form-control required" value="${product.description}" />
									</div>
								</div>
							</div>

							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Status<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="productStatus" name="productStatus" type="number"
											class="form-control required" value="${product.status}" />
									</div>
								</div>
							</div>


						</div>
					</div>
				</div>
			</div>
	</div>
	</form>

	</div>
</body>
</html>