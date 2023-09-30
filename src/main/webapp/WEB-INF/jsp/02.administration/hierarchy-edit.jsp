<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>Hierarchy | Create/Edit</title>
<script type="text/javascript">
        $(document).ready(function() {
            $('form[name="hierarchy"]').validate();

			$("#btn-reset").click(function(){
            	$('#branchCode').val($('#default_branch_code').val());
            	$('#branchDesc').val($('#default_branch_desc').val());
            	$('#unitCode').val($('#default_unit_code').val());
            	$('#unitDesc').val($('#default_unit_desc').val());
            	$('#centerCode').val($('#default_unit_code').val());
            	$('#centerDesc').val($('#default_unit_desc').val());
            });

            $("#btn-save").click(function() {
            	if ($('#unitCode').val() == '') {
            		App.alert({
                        type	: 'danger',
                        message	: 'Invalid mapping, wrong value unit.',
                        icon	: 'warning'
                    });
            	} else if ($('#centerCode').val() == '') {
            		App.alert({
                        type	: 'danger',
                        message	: 'Invalid mapping, wrong value center.',
                        icon	: 'warning'
                    });
            	} else {
            		if ($('form[name="hierarchy"]').valid()) {
                    	var dataJson = $('form[name="hierarchy"]').serializeObject();
                        submit('/administration/hierarchy/save', JSON.stringify(dataJson), function (data) {
                        	$("input[name='id']").val(data.id);
                       	});
                    }	
            	}
            });
            
           /*  popoverFunction.getStucturePopup({
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
            
            popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showUnitPopup",
                modalId		: "popupUnitTable",
                modalTitle	: "Unit List",
                hiddenId	: "unitCode",
                varValue	: "description",
               	ajax_data	: [
       					{
       						fieldVar	: "type",
       						fieldValue	: "UNIT"
       					}
            		]
            }); */
            
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
						<li>Administration</li>
						<li><a
							href="${pageContext.request.contextPath}/administration/hierarchy/">Hierarchy</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Hierarchy
					</h3>
					<em>Administration</em>
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

		<form class="form-horizontal" role="form" method="post"
			name="hierarchy">
			<input type="hidden" name="id" value="${hierarchy.id}"> <input
				type="hidden" id="default_branch_code"
				value="${hierarchy.branch.code}"> <input type="hidden"
				id="default_branch_desc" value="${hierarchy.branch.description}">
			<input type="hidden" id="default_unit_code"
				value="${hierarchy.unit.code}"> <input type="hidden"
				id="default_unit_desc" value="${hierarchy.unit.description}">
			<input type="hidden" id="default_center_code"
				value="${hierarchy.center.code}"> <input type="hidden"
				id="default_center_desc" value="${hierarchy.center.description}">

			<div class="row">
				<div class="col-md-12">
					<div class="widget">
						<div class="widget-header">
							<h3>
								<i class="fa fa-sitemap"></i> Hierarchy
							</h3>
						</div>
						<div class="widget-content form">
							<div class="form-body">
								<div class="form-body">

									<div class="form-group">
										<label class="col-md-3 control-label">Branch Code <span
											class="required">*</span></label>
										<div class="col-md-4">
											<input type="text" name="branchCode" class="form-control"
												id="branchCode" value="${hierarchy.branch.code}"
												placeholder="Branch Code">
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Branch Name <span
											class="required">*</span></label>
										<div class="col-md-4">
											<input type="text" name="branchDesc" class="form-control"
												id="branchDesc" value="${hierarchy.branch.description}"
												placeholder="Branch Name">
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Unit Code <span
											class="required">*</span></label>
										<div class="col-md-4">
											<input type="text" name="unitCode" class="form-control"
												id="unitCode" value="${hierarchy.unit.code}"
												placeholder="Unit Code">
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Unit Name <span
											class="required">*</span></label>
										<div class="col-md-4">
											<input type="text" name="unitDesc" class="form-control"
												id="unitDesc" value="${hierarchy.unit.description}"
												placeholder="Unit Name">
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Center Code <span
											class="required">*</span></label>
										<div class="col-md-4">
											<input type="text" name="centerCode" class="form-control"
												id="centerCode" value="${hierarchy.center.code}"
												placeholder="Center Code">
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Center Name <span
											class="required">*</span></label>
										<div class="col-md-4">
											<input type="text" name="centerDesc" class="form-control"
												id="centerDesc" value="${hierarchy.center.description}"
												placeholder="Center Name">
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