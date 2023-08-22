<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Splash Screen | Create/Edit</title>
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
			
			$("form").validate();
			
			$("#btn-reset").click(function(){
            	$('#title').val($('#default_title').val());
				$('#action').val($('#default_action ').val());
				$('#message').val($('#default_message').val());
				$('#subMessage').val($('#default_subMessage').val());
            	$('#description').val($('#default_description').val());
				$('#imageURL').val($('#default_imageURL').val());
				$('#originalImage').attr('src', $('#default_imageURL').val());
				$('#previewImage').attr('src', $('#default_imageURL').val());
				$('#show').val($('#default_show').val());
				
            });   
			
			$("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson 	= $('form').serializeObject();
                	//dataJson.banner = getBase64Image(document.getElementById('originalImage'));
                	
                    submit('/utilities/splash-screen/save', JSON.stringify(dataJson), function (data) {
                    	$("input[name='id']").val(data.id);
                    });
                }
            });
			
			$("#btn-preview-image").click(function(){
				$("#previewImage").attr("src", $('#imageURL').val());
				$("#originalImage").attr("src", $('#imageURL').val());
            });
			
            // $('#banner').change(function() {
            // 	var image = $(this).val();
            	
			// 	if (image != "" && !image.match(/(?:jpeg|jpg|png)$/)) {
			// 		alert("inputted file path is not a jpg or jpeg or png image!");
			// 		return false;
					
			// 	} else {
			// 		var readURLImage = function readURL(input) {
			// 			var sizeB	= input.files[0].size;
			// 			var sizeMB	= sizeB / (1024 * 1024);
						
			// 			if (sizeMB > maxSize){
			// 				alert("maximum image size is " + maxSize + " MB");
			// 			} else{
			// 				if (input.files && input.files[0]) {
		    //                     var reader = new FileReader();

		    //                     reader.onload = function (e) {
		    //                         $('#previewImage').attr('src', e.target.result);
		    //                         $('#originalImage').attr('src', e.target.result);
		    //                     };

		    //                     reader.readAsDataURL(input.files[0]);
		    //                 }
			// 			}
	        //         };
	                
			// 		readURLImage(this);
			// 	}
            // });
        });

		if (localStorage.getItem('isPageOpen')) {
      alert('Page is already open in another tab!');
      window.location.href = 'about:blank'; 
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
<body>

	<div class="content">
		<div class="row">
			<div class="col-lg-6 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Utilities</li>
						<li><a
							href="${pageContext.request.contextPath}/utilities/splash-screen/">Splash Screen</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Splash Screen
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
			<form class="form-horizontal" role="form" method="post">

				<input type="hidden" name="id" value="${splash.id}"> 
				<input type="hidden" id="default_action" value="${splash.action}"> 
				<input type="hidden" id="default_title" value="${splash.title}">
				<input type="hidden" id="default_message" value="${splash.message}">
				<input type="hidden" id="default_subMessage" value="${splash.subMessage}">
				<input type="hidden" id="default_imageURL" value="${splash.imageURL}">
				<input type="hidden" id="default_show" value="${splash.show}">
				

				<div class="row">
					<div class="col-md-6">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-envelope"></i> Splash Screen
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-body">
									<div class="form-group">
										<label class="col-md-4 control-label">Action <span
											class="required">*</span></label>
										<div class="col-md-8">
											<input id="action" name="action" type="text"
												class="form-control required" value="${splash.action}"
												placeholder="Action" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Title <span
											class="required">*</span></label>
										<div class="col-md-8">
											<input id="title" name="title" type="text"
												class="form-control required" value="${splash.title}"
												placeholder="Title" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Message <span
											class="required">*</span></label>
										<div class="col-md-8">
											<textarea id="message" name="message"
												class="form-control required" rows="6">${splash.message}</textarea>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Sub Message <span
											class="required">*</span></label>
										<div class="col-md-8">
											<textarea id="subMessage" name="subMessage"
												class="form-control required" rows="6">${splash.subMessage}</textarea>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Show <span class="required">*</span>
										</label>
										<div class="col-md-8">
											<div>
												<select id="show" name="show"
													class="form-control required">
													<option value="">-- Choose --</option>
													<c:forEach items="${productServicesStatus}"
														var="productServicesStatus">
														<option value="${productServicesStatus.value}"
															${productServicesStatus.value == splash.show ? 'selected="selected"' : ''}>${productServicesStatus.description}</option>
													</c:forEach>
												</select>
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
											<input id="imageURL" name="imageURL" type="text" value="${splash.imageURL}"
												style="cursor: pointer;" class="form-control" />
										</div>
										<div class="col-md-4">
											<button id="btn-preview-image" class="btn btn-success"
												type="button">Preview
											</button>
										</div>
									</div>

									<img 	id="previewImage"
											src="${splash.imageURL}"
											class="thumbnail" alt="photo"
											style="max-width: 100%; height: auto;"> 

									<img	id="originalImage"
											src="${splash.imageURL}"
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

