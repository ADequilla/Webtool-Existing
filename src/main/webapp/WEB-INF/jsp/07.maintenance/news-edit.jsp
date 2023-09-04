<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Bank News | Create/Edit</title>
<script type="text/javascript">
	
		function getBase64Image(img) {
			var canvas 		= document.createElement("canvas");
			var width 		= img.width;
		  	var height 		= img.height;
		  	canvas.width	= width;
		  	canvas.height 	= height;
		  	canvas.getContext('2d').drawImage(img, 0, 0, width, height);
		  	var dataURL = canvas.toDataURL("image/jpeg");
		  	
		  	return dataURL;
		}
		
		$(document).ready(function() {
			var maxSize = ${ImageMaxSize};
			
			$("form").validate();
			
			$("#btn-reset").click(function(){
            	$('#productName').val($('#default_name').val());
            	$('#productDesc').val($('#default_desc').val());
				$('#productPeriodStart').val($('#default_periodStart').val());
				$('#productPeriodEnd').val($('#default_periodEnd').val());
				$('#productImg').val($('#default_productImg').val());
				$('#previewImage').attr('src', document.getElementById('default_image').src);
				$('#originalImage').attr('src', document.getElementById('default_image').src);
            });
			
			$("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson 		= $('form').serializeObject();
                	// dataJson.productImg = getBase64Image(document.getElementById('originalImage'));
                	
                    submit('/utilities/news/save', JSON.stringify(dataJson), function (data) {
                    	$("input[name='id']").val(data.id);
                    });
                }
            });
			
			$("#btn-remove-image").click(function(){
				$('#productImg').val($('#default_productImg').val());
				$("#previewImage").attr("src", "${pageContext.request.contextPath}/assets/img/placeholder.jpg");
				$("#originalImage").attr("src", "${pageContext.request.contextPath}/assets/img/placeholder.jpg");
            });
			
            $('#productImg').change(function() {
            	var image = $(this).val();
            	
				if (image != "" && !image.match(/(?:jpeg|jpg|png)$/)) {
					alert("inputted file path is not a jpg or jpeg or png image!");
					return false;
					
				} else {
					var readURLImage = function readURL(input) {
						var sizeB	= input.files[0].size;
						var sizeMB	= sizeB / (1024 * 1024);
						
						if (sizeMB > maxSize){
							alert("maximum image size is " + maxSize + " MB");
						} else{
							if (input.files && input.files[0]) {
		                        var reader = new FileReader();

		                        reader.onload = function (e) {
		                            $('#previewImage').attr('src', e.target.result);
		                            $('#originalImage').attr('src', e.target.result);
		                        };

		                        reader.readAsDataURL(input.files[0]);
		                    }
						}
	                };
	                
					readURLImage(this);
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
							href="${pageContext.request.contextPath}/utilities/news/">Bank
								News</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Bank News
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
								<i class="fa fa-save"></i> Save Data
							</button>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<div class="main-content">
			<form class="form-horizontal" role="form" enctype="multipart/form-data">
				<input type="hidden" name="id" value="${product.id}"> 
				<input type="hidden" id="default_name" value="${product.productName}">
				<input type="hidden" id="default_desc" value="${product.productDesc}"> 
				<input type="hidden" id="default_periodStart" value="<fmt:formatDate value='${product.productPeriodStart}' type='date' pattern='dd-MMM-yyyy'/>">
				<input type="hidden" id="default_periodEnd" value="<fmt:formatDate value='${product.productPeriodEnd}' type='date' pattern='dd-MMM-yyyy'/>">
				<input type="hidden" id="default_productImg" value="${product.productImg}"> 
				<input type="hidden" id="default_productImg" value="${product.productImg}"> 
				<img type="hidden" id="default_image" src="${pageContext.request.contextPath}"/>

				<div class="row">
					<div class="col-md-6">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-envelope"></i> Bank News Info
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-body">
									<div class="form-group">
										<label class="col-md-4 control-label">Topic <span
											class="required">*</span></label>
										<div class="col-md-8">
											<input id="productName" name="productName" type="text"
												class="form-control required" value="${product.productName}"
												placeholder="Topic" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Description <span
											class="required">*</span></label>
										<div class="col-md-8">
											<textarea id="productDesc" name="productDesc"
												class="form-control required" rows="6">${product.productDesc}</textarea>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Period <span
											class="required">*</span></label>
										<div class="col-md-4">
											<div class="input-group">
												<input id="productPeriodStart" name="productPeriodStart"
													type="text" style="cursor: pointer;"
													class="form-control required startdatepicker"
													value="<fmt:formatDate value='${product.productPeriodStart}' type='date' pattern='dd-MMM-yyyy'/>"
													placeholder="Start Period"> <span
													class="input-group-addon"><i class="fa fa-calendar"></i></span>
											</div>
										</div>
										<div class="col-md-4">
											<div class="input-group">
												<input id="productPeriodEnd" name="productPeriodEnd"
													type="text" style="cursor: pointer;"
													class="form-control required enddatepicker"
													value="<fmt:formatDate value='${product.productPeriodEnd}' type='date' pattern='dd-MMM-yyyy'/>"
													placeholder=" End Period"> <span
													class="input-group-addon"><i class="fa fa-calendar"></i></span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="col-md-6">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-image"></i> Banner
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-body">
									<div class="form-group">
										<div class="col-md-8">
											<input id="productImg" name="productImg" type="file" value="${product.productImg}"
												style="cursor: pointer;" class="form-control" />
										</div>
										<div class="col-md-4">
											<button id="btn-remove-image" class="btn btn-danger"
												type="button">
												<i class="fa fa-trash"></i> Remove
											</button>
										</div>
									</div>
									<img id="previewImage"
										src="${pageContext.request.contextPath}"
										class="thumbnail" alt="photo"
										style="max-width: 100%; height: auto;"> 
									<img id="originalImage"
										src="${pageContext.request.contextPath}${(product.id == null) ? '/assets/img/placeholder.jpg' : '/utilities/news/banner/'}${product.id}"
										class="thumbnail" alt="photo" style="display: none;">
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