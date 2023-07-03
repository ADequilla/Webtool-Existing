<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Product Denomination | Create/Edit</title>
<script type="text/javascript">
		$(document).ready(function() {
			$("form").validate();

			$("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/utilities/product-denom/save', JSON.stringify(dataJson), function (data) {
                    });
                }
            });
			
			$("#btn-reset").click(function(){
				$('#productCode').val("");
				$('#denom').val("");
				$('#description').val("");
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
							href="${pageContext.request.contextPath}/utilities/product-denom/">Product
								Denomination</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Product Denomination
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
				<input type="hidden" id="id" name="id" value="${id}"> <input
					type="hidden" id="isNew" name="isNew" value="${isNew}">

				<div class="row">
					<div class="col-md-12">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-sitemap fa-fw"></i> Create/Edit Product
									Denomination
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-group">
									<label class="col-md-3 control-label">Product Code <span
										class="required">*</span></label>
									<div class="col-md-4">
										<select id="productCode" name="productCode"
											class="form-control">
											<option value="">-- Product Code --</option>
											<c:forEach items="${listProductPay}" var="p">
												<option value="${p.productCode}"
													${p.id == productDenom.productId ? 'selected="selected"' : ''}>${p.productName}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Denom<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="denom" name="denom" value="${productDenom.denom}"
											type="text" class="form-control required" placeholder="Denom" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Description<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="description" name="description"
											value="${productDenom.description}" type="text"
											class="form-control required" placeholder="Description" />
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