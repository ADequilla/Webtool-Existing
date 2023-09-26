<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Biller Product | Generate</title>
<script type="text/javascript">
        $(document).ready(function() {
            $("form").validate();
			
			$("#btn-reset").click(function(){
            	$('#billerProductId').val('');
            	$('#billerProductName').val('');
            	$('#productCategoryName').val('');
            	$('#providerName').val('');
            });

            $("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                	var params = {}
                	let url = "${pageContext.request.contextPath}/utilities/biller-product/generate?";
                	let urlParameters = Object.entries(dataJson).map(e => e.join('=')).join('&');
                	window.location = url + urlParameters;
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
			<div class="col-lg-8 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Utilities</li>
						<li><a
							href="${pageContext.request.contextPath}/utilities/biller-product/">Biller
								Product</a></li>
						<li class="active">Generate</li>
					</ul>
					<h3>
						<i class="fa fa-file-pdf-o"></i> Biller Product
					</h3>
					<em>Utilities</em>
				</div>
			</div>
			<div class="col-lg-4 ">
				<div class="top-content-button">
					<ul class="list-inline quick-access">
						<li>
							<button id="btn-reset" class="btn btn-default" type="button">
								<i class="fa fa-refresh"></i> Reset Form
							</button>
						</li>
						<li>
							<button id="btn-save" class="btn btn-success" type="button">
								<i class="fa fa-save"></i> Generate Report
							</button>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<form class="form-horizontal" role="form" method="post">
			<div class="row">
				<div class="col-md-12">
					<div class="widget">
						<div class="widget-header">
							<h3>
								<i class="fa fa-clone"></i> Parameter Biller Product
							</h3>
						</div>
						<div class="widget-content form">
							<div class="form-body">
								<div class="form-body">
									<div class="form-group">
										<label class="col-md-2 control-label">Biller Product
											Id<span
											class="required">*</span> </label>
										<div class="col-sm-4">
											<input type="number" id="billerProductId"
												name="billerProductId" class="form-control"
												placeholder="Biller Product Id">
										</div>

										<label class="col-md-2 control-label">Provider </label>
										<div class="col-sm-4">
											<select id="providerName" name="providerName"
												class="form-control">
												<option value="">-- Provider --</option>
												<c:forEach items="${listProvider}" var="provider">
													<option value="${provider.providerName}">${provider.providerName}</option>
												</c:forEach>
											</select>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-2 control-label">Biller Product
											Name <span class="required">*</span> </label>
										<div class="col-sm-4">
											<input type="text" id="billerProductName"
												name="billerProductName" class="form-control"
												placeholder="Biller Product Name">
										</div>

										<label class="col-md-2 control-label">Product Category
										</label>
										<div class="col-sm-4">
											<select id="productCategoryName" name="productCategoryName"
												class="form-control">
												<option value="">-- Product Category --</option>
												<c:forEach items="${listProductCategoryBiller}"
													var="productCategoryBiller">
													<option
														value="${productCategoryBiller.productCategoryName}">${productCategoryBiller.productCategoryName}</option>
												</c:forEach>
											</select>
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