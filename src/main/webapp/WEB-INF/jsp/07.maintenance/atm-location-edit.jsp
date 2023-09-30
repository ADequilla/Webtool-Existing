<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>ATM Location | Create/Edit</title>
<script type="text/javascript">
        $(document).ready(function() {
            $("form").validate();

			$("#btn-reset").click(function(){
            	$('#description').val($('#default_description').val());
            	$('#address').val($('#default_address').val());
            	$('#city').val($('#default_city').val());
            	$('#longitude').val($('#default_longitude').val());
            	$('#latitude').val($('#default_latitude').val());
            });

            $("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/utilities/atm/save', JSON.stringify(dataJson), function (data) {
	                	$("input[name='id']").val(data.id);
	                });
                }
            });
            
            
            
            $("#latitude").keydown(function (e) {
            	validateNumber(e);
            });
            
            $("#longitude").keydown(function (e) {
            	validateNumber(e);
            });
        });
        
        function validateNumber(e){
        	if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190, 189]) !== -1 ||
                    // Allow: Ctrl+A, Command+A
                   (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || 
                    // Allow: home, end, left, right, down, up
                   (e.keyCode >= 35 && e.keyCode <= 40)) {
                        // let it happen, don't do anything
                        return;
        	}
        	
	        // Ensure that it is a number and stop the keypress
	        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
	            e.preventDefault();
	        }
        }

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
							href="${pageContext.request.contextPath}/utilities/atm/">ATM
								Location</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit ATM Location
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

		<form class="form-horizontal" role="form" method="post">
			<input type="hidden" name="id" value="${atm.id}"> <input
				type="hidden" id="default_description" value="${atm.description}">
			<input type="hidden" id="default_address" value="${atm.address}">
			<input type="hidden" id="default_city" value="${atm.city}"> <input
				type="hidden" id="default_longitude" value="${atm.longitude}">
			<input type="hidden" id="default_latitude" value="${atm.latitude}">

			<div class="row">
				<div class="col-md-12">
					<div class="widget">
						<div class="widget-header">
							<h3>
								<i class="fa fa-map-marker"></i> ATM Location
							</h3>
						</div>
						<div class="widget-content form">
							<div class="form-body">

								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">Description <span
											class="required">*</span></label>
										<div class="col-md-4">
											<textarea id="description" name="description" type="text"
												class="form-control required" rows="3">${atm.description}</textarea>
										</div>
									</div>
								</div>

								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">Street / Brgy <span
											class="required">*</span></label>
										<div class="col-md-4">
											<input id="address" name="address" type="text"
												class="form-control required" value="${atm.address}" />
										</div>
									</div>
								</div>

								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">City / Province
											<span class="required">*</span>
										</label>
										<div class="col-md-4">
											<input id="city" name="city" type="text"
												class="form-control required" value="${atm.city}" />
										</div>
									</div>
								</div>

								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">Longitude</label>
										<div class="col-md-4">
											<input id="longitude" name="longitude" type="text"
												class="form-control" value="${atm.longitude}" />
										</div>
									</div>
								</div>

								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">Latitude</label>
										<div class="col-md-4">
											<input id="latitude" name="latitude" type="text"
												class="form-control" value="${atm.latitude}" />
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