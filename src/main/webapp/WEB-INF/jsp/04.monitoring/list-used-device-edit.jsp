<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>List of Used Device ID | Edit</title>
<script type="text/javascript">
		$(document).ready(function() {
			$("form").validate();

			if(! ${isNew}) {
				$("#deviceId").attr("readonly","readonly");
				$("#deviceModel").attr("readonly","readonly");
				$("#cid").attr("readonly","readonly");
				$("#branchCode").attr("readonly","readonly");
				$("#mobileNumber").attr("readonly","readonly");
				$("#clientName").attr("readonly","readonly");
				$("#clientType").attr("readonly","readonly");
			}
			
			$("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/monitoring/list-used-device/save', JSON.stringify(dataJson), function (data) {
                    	$("#isNew").val(false);
                    	$("#deviceId").attr("readonly","readonly");
        				$("#deviceModel").attr("readonly","readonly");
        				$("#cid").attr("readonly","readonly");
        				$("#branchCode").attr("readonly","readonly");
        				$("#mobileNumber").attr("readonly","readonly");
        				$("#clientName").attr("readonly","readonly");
        				$("#clientType").attr("readonly","readonly");
                    });
                }
            });
			
			$("#btn-reset").click(function(){
				$('#deviceStatus').val("");
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
							href="${pageContext.request.contextPath}/monitoring/list-used-device/">List
								Of Used Device ID</a></li>
						<li class="active">Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Edit List Of Used Device ID
					</h3>
					<em>Monitoring</em>
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
				<input type="hidden" id="id" name="id" value="${usedDevice.id}">

				<div class="row">
					<div class="col-md-12">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-sitemap fa-fw"></i> Edit List Of Used Device ID
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-group">
									<label class="col-md-3 control-label">Device ID<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="deviceId" name="deviceId"
											value="${usedDevice.deviceId}" type="text"
											class="form-control required" placeholder="Device Id" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Device Model<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="deviceModel" name="deviceModel"
											value="${usedDevice.deviceModel}" type="text"
											class="form-control required" placeholder="Device Model" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">CIF<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="cid" name="cid" value="${usedDevice.cid}"
											type="text" class="form-control required"
											placeholder="CIF Number" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Branch<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="branchCode" name="branchCode"
											value="${usedDevice.branchCode}" type="text"
											class="form-control required" placeholder="Branch" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Mobile Number<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="mobileNumber" name="mobileNumber"
											value="${usedDevice.mobileNumber}" type="text"
											class="form-control required" placeholder="Mobile Number" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Customer Name<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="clientName" name="clientName"
											value="${usedDevice.clientName}" type="text"
											class="form-control required" placeholder="Name of Customer" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">ClientType<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="clientType" name="clientType"
											value="${usedDevice.clientType}" type="text"
											class="form-control required" placeholder="Client Type" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Status<span
										class="required">*</span></label>
									<div class="col-md-4">
										<select id="deviceStatus" name="deviceStatus"
											class="form-control required">
											<option value="">-- Status --</option>
											<c:forEach items="${listStatusDevice}" var="lookup">
												<option value="${lookup.value}"
													${lookup.value == usedDevice.deviceStatus ? 'selected="selected"' : ''}>${lookup.description}</option>
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