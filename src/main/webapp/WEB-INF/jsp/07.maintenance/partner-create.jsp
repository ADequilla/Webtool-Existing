<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Partner | Create/Edit</title>
<script type="text/javascript">
		$(document).ready(function() {
			$("form").validate();

			if(! ${isNew}) {
				/* $("#partnerId").attr("readonly","readonly"); */
			}
			
			$("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/utilities/partner/save', JSON.stringify(dataJson), function (data) {
                    	$("#isNew").val(false);
            /*         	$("#partnerId").attr("readonly","readonly"); */
                    });
                }
            });
			
			$("#btn-reset").click(function(){
				$('#partnerId').val("");
				$('#partnerName').val("");
				$('#partnerDesc').val("");
				$('#partnerAccount').val("");
				/* $('#myPartnerId').val(""); */
				$('#partnerApiUrl').val("");
				/* $('#myPartnerToken').val("");
				$('#secretKey').val(""); */
				$('#mriGroup').val("");
				$('#merchantUrl').val("");
				$('#merchantPrefix').val("");
				$('#status').val("");
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
							href="${pageContext.request.contextPath}/utilities/partner/">Partner</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Partner
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
				<input type="hidden" id="id" name="id" value="${partner.id}">

				<div class="row">
					<div class="col-md-12">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-sitemap fa-fw"></i> Create/Edit Partner
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-group">
									<label class="col-md-3 control-label">Partner Id<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="partnerId" name="partnerId"
											value="${partner.partnerId}" type="text"
											class="form-control required" placeholder="Partner Id" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Partner Name<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="partnerName" name="partnerName"
											value="${partner.partnerName}" type="text"
											class="form-control required" placeholder="Partner Name" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Partner Desc<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="partnerDesc" name="partnerDesc"
											value="${partner.partnerDesc}" type="text"
											class="form-control required" placeholder="Partner Desc" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Partner Account<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="partnerAccount" name="partnerAccount"
											value="${partner.partnerAccount}" type="text"
											class="form-control required" placeholder="Partner Account" />
									</div>
								</div>
								<%-- <div class="form-group">
								<label  class="col-md-3 control-label">My Partner Id<span class="required">*</span></label>
								<div class="col-md-4">
									<input id="myPartnerId" name="myPartnerId" value="${partner.myPartnerId}" type="text" class="form-control required" placeholder="My Partner Id"/>
								</div>
							</div> --%>
								<div class="form-group">
									<label class="col-md-3 control-label">Partner API URL<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="partnerApiUrl" name="partnerApiUrl"
											value="${partner.partnerApiUrl}" type="text"
											class="form-control required" placeholder="Partner API URL" />
									</div>
								</div>
								<%-- <div class="form-group">
								<label  class="col-md-3 control-label">My Partner Token<span class="required">*</span></label>
								<div class="col-md-4">
									<input id="myPartnerToken" name="myPartnerToken" value="${partner.myPartnerToken}" type="text" class="form-control required" placeholder="My Partner Token"/>
								</div>
							</div>
							<div class="form-group">
								<label  class="col-md-3 control-label">My Partner Secret Key<span class="required">*</span></label>
								<div class="col-md-4">
									<input id="secretKey" name="secretKey" value="${partner.secretKey}" type="text" class="form-control required" placeholder="My Partner Secret Key"/>
								</div>
							</div> --%>
								<div class="form-group">
									<label class="col-md-3 control-label">Merchant Payment
										Callback URL</label>
									<div class="col-md-4">
										<input id="merchantUrl" name="merchantUrl"
											value="${partner.merchantUrl}" type="text"
											class="form-control"
											placeholder="Merchant Payment Callback URL" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Merchant Id
										Prefix</label>
									<div class="col-md-4">
										<input id="merchantPrefix" name="merchantPrefix"
											value="${partner.merchantPrefix}" type="text"
											class="form-control" placeholder="Merchant Id Prefix" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">MRI Group<span
										class="required">*</span></label>
									<div class="col-md-4">
										<select id="mriGroup" name="mriGroup"
											class="form-control required">
											<option value="">-- MRI Group --</option>
											<c:forEach items="${listMriGroup}" var="lookup">
												<option value="${lookup.value}"
													${lookup.value == partner.mriGroup ? 'selected="selected"' : ''}>${lookup.description}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Status<span
										class="required">*</span></label>
									<div class="col-md-4">
										<select id="status" name="status"
											class="form-control required">
											<option value="">-- Status --</option>
											<c:forEach items="${listPartner}" var="lookup">
												<option value="${lookup.value}"
													${lookup.value == partner.status ? 'selected="selected"' : ''}>${lookup.description}</option>
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