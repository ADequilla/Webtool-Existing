<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>CSR Hotline | Edit</title>
<script type="text/javascript">
		$(document).ready(function() {
			$("form").validate();
			
			$("#btn-edit").click(function(){
				$('#instDesc').prop('disabled', true)
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                   	 submit('/cs/csr-hotlain/save', JSON.stringify(dataJson), function (data) {
						$("input[name='id']").val(data.id);
                    });
                }
				$('#instDesc').prop('disabled', false)
            });

			popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showInstitutionPopup",
                modalId		: "popupInstitutionTable",
                modalTitle	: "Institution List",
                hiddenId	: "instCode",
                varValue	: "description",
                callback	: function callback(){
                	$("#branchCode").val("");
                    $("#branchDesc").val("");
                	$("#unitCode").val("");
                    $("#unitDesc").val("");
                    $("#centerCode").val("");
                    $("#centerDesc").val("");
                    
    			},
               	ajax_data	: [
       					{
       						fieldVar	: "type",
       						fieldValue	: "INSTITUTION"
       					},
       					{
    	                	fieldVar	: "filter",
    						fieldValue	: "Y"
    	                }
            		]
            });
			
			$("#btn-reset").click(function(){
				$('#contactNumber').val($('#default_code').val());
				$('#networkProvider').val($('#default_description').val());
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
						<li>Customer Service</li>
						<li><a
							href="${pageContext.request.contextPath}/cs/csr-hotlain/">CSR
								Hotline</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Edit CSR Hotline
					</h3>
					<em>Customer Service</em>
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
							<button id="btn-edit" class="btn btn-custom-secondary"
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
				<input type="hidden" id="default_code"
					value="${CSRHotline.contactNumber}"> <input type="hidden"
					id="default_description" value="${CSRHotline.networkProvider}">
				<input type="hidden" id="default_insti"
					value="${CSRHotline.instCode}"> <input type="hidden"
					name="id" value="${CSRHotline.id}">


				<div class="row">
					<div class="col-md-12">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-sitemap fa-fw"></i> Edit CSR Hotline
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-group">
									<label class="col-md-3 control-label">Contact Number<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="contactNumber" name="contactNumber"
											value="${CSRHotline.contactNumber}" type="text"
											class="form-control required" placeholder="Contact Number" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Network Provider<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="networkProvider" name="networkProvider"
											value="${CSRHotline.networkProvider}" type="text"
											class="form-control required" placeholder="Network Provider" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Institution<span
										class="required">*</span></label>
									<div class="col-sm-4">
										<input type="hidden" id="instCode" name="instCode">
										<div class="input-group">
											<input id="instDesc" name="instDesc" type="text"
												style="cursor: pointer;"
												class="form-control showInstitutionPopup"
												readonly="readonly" placeholder="Institution" /> <span
												class="input-group-addon"><i class="fa fa-search"></i></span>
										</div>
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