<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>Mobile Branch Officer | Create/Edit</title>
<script type="text/javascript">
        $(document).ready(function() {
            $('form[name="mbo"]').validate();

            $("#account").change(function(){
				var account	= $(this).val();
				if (account != '') {
					data = {field:"account", string:account};
					retrieve('/mbo/list/get', JSON.stringify(data), function (data) {
                    });
				}
        	});
            
			$("#btn-reset").click(function(){
            	$('#branchCode').val($('#default_branch_code').val());
            	$('#branchDesc').val($('#default_branch_desc').val());
            	$('#name').val($('#default_name').val());
            	$('#address').val($('#default_address').val());
            	$('#account').val($('#default_account').val());
            	$('#actcode').val($('#default_actcode').val());
            });
			
            $("#btn-save").click(function(){
                if ($('form[name="mbo"]').valid()) {
                	var dataJson = $('form[name="mbo"]').serializeObject();
                    submit('/mbo/list/save', JSON.stringify(dataJson), function (data) {
                    	$("input[name='id']").val(data.id);
                   	});
                }
            });
            
            popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showBranchPopup",
                modalId		: "popupBranchTable",
                modalTitle	: "Branch List",
                hiddenId	: "branchCode",
                varValue	: "description",
               	ajax_data	: [
       					{
       						fieldVar	: "type",
       						fieldValue	: "BRANCH"
       					}
            		]
            });
            
        });

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
						<li>Mobile Branch Officer</li>
						<li><a href="${pageContext.request.contextPath}/mbo/list/">List</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit
					</h3>
					<em>Mobile Branch Officer</em>
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

		<form class="form-horizontal" role="form" method="post" name="mbo">
			<input type="hidden" name="id" value="${mbo.id}"> <input
				type="hidden" id="default_branch_code" value="${mbo.branch.code}">
			<input type="hidden" id="default_branch_desc"
				value="${mbo.branch.description}"> <input type="hidden"
				id="default_name" value="${mbo.name}"> <input type="hidden"
				id="default_address" value="${mbo.address}"> <input
				type="hidden" id="default_account" value="${mbo.account}"> <input
				type="hidden" id="default_actcode" value="${mbo.actCode}">

			<div class="row">
				<div class="col-md-12">
					<div class="widget">
						<div class="widget-header">
							<h3>
								<i class="fa fa-user-o"></i> Mobile Branch Officer
							</h3>
						</div>
						<div class="widget-content form">
							<div class="form-body">
								<div class="form-body">

									<div class="form-group">
										<label class="col-md-3 control-label">Internal Account
											<span class="required">*</span>
										</label>
										<div class="col-md-4">
											<input id="account" name="account" value="${mbo.account}"
												type="text" class="form-control required"
												placeholder="Internal Account" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Branch Name <span
											class="required">*</span></label>
										<div class="col-md-4">
											<input type="hidden" name="branchCode" id="branchCode"
												value="${mbo.branch.code}">
											<div class="input-group">
												<input name="branchDesc" id="branchDesc" type="text"
													style="cursor: pointer;"
													class="form-control required showBranchPopup"
													readonly="readonly" value="${mbo.branch.description}" /> <span
													class="input-group-addon"><i class="fa fa-search"></i></span>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">MBO Teller Name
											<span class="required">*</span>
										</label>
										<div class="col-md-4">
											<input id="name" name="name" value="${mbo.name}" type="text"
												class="form-control required" placeholder="MBO Teller Name" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">MBO Address <span
											class="required">*</span></label>
										<div class="col-md-4">
											<textarea id="address" name="address"
												class="form-control required" rows="4">${mbo.address}</textarea>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Activation Code
											<span class="required">*</span>
										</label>
										<div class="col-md-4">
											<input id="actcode" name="actcode" value="${mbo.actCode}"
												type="text" class="form-control required"
												placeholder="Activation Code" />
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