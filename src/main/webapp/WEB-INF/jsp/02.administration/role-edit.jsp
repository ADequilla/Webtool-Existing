<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>Roles | Create/Edit</title>
<script type="text/javascript">
        $(document).ready(function() {
        	$('form[name="role"]').validate({
                rules : {
                	menus		: "required",
                },
                messages: {
                	menus: {
                    	required: "Please select menus."
                	}
                },
                errorPlacement: function(error, element) {
                	if (element.attr('name') == 'menus') {
                    	error.insertAfter('#checkboxMenus');
                    }else{
                    	error.insertAfter(element.parent());
                    }
            	}
            });
        	
			$("#btn-reset").click(function(){
            	$('#roleName').val($('#default_name').val());
            	$('#roleDescription').val($('#default_description').val());
				$('input[name="menus"]').prop('checked', false);
            });
			
            $("#btn-save").click(function(){
                if ($('form[name="role"]').valid()) {
                	var dataJson = $('form[name="role"]').serializeObject();
                	var menus = [];
                	$('.action-box').each(function() {
                    	$(this).find('input[type="checkbox"]').each(function() {
                    		
                    		var state = "0";
                    		if ($(this).is(":checked")){
                				state = "1";
           					}
                    		var row = {"id" : $(this).val(), "state" : state};
                    		menus.push(row);
                    	});
                    });

    				dataJson.menus = menus;
                    submit('/administration/role/save', JSON.stringify(dataJson), function (data) {
	                	$("input[name='id']").val(data.id);
	            	});
                }
            });
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
						<li>Administration</li>
						<li><a
							href="${pageContext.request.contextPath}/administration/role/">Roles</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Roles
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

		<form class="form-horizontal" role="form" method="post" name="role">
			<input type="hidden" name="id" value="${role.id}"> <input
				type="hidden" id="default_name" value="${role.name}"> <input
				type="hidden" id="default_description" value="${role.description}">

			<div class="row">
				<div class="col-md-12">
					<div class="widget">
						<div class="widget-header">
							<h3>
								<i class="fa fa-sitemap"></i> Role
							</h3>
						</div>
						<div class="widget-content form">
							<div class="form-body">
								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">Role Name <span
											class="required">*</span></label>
										<div class="col-md-4">
											<div>
												<input id="roleName" type="text"
													class="form-control required" name="name"
													value="${role.name}" placeholder="Role Name" />
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">Description <span
											class="required">*</span></label>
										<div class="col-md-4">
											<div>
												<input id="roleDescription" type="text"
													class="form-control required" name="description"
													value="${role.description}" placeholder="Description" />
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-12">
					<div class="widget">
						<div class="widget-header">
							<h3>
								<i class="fa fa-sitemap"></i> Role - Menus
							</h3>
						</div>
						<div class="widget-content form role-menu">
							<c:forEach items="${menuList}" var="menuModel">
								<div>
									<a href="javascript:void(0);" class="js-sub-menu-toggle"> <span
										class="menu-description"> <i
											class="menu-icon fa fa-plus-square-o"></i>
											${menuModel.menu.description}
									</span>
									</a> <span class="action-box"> <c:forEach
											items="${menuModel.components}" var="component">
											<c:set var="checked" value="false" />
											<c:forEach var="roledetail" items="${listRoledetail}">
												<c:if test="${roledetail.component.id == component.id}">
													<c:set var="checked" value="true" />
												</c:if>
											</c:forEach>
											<input type="checkbox" name="menus" value="${component.id}"
												${checked ? 'checked="checked"' : ''} />
			                                ${component.name}
	                                </c:forEach>
									</span>

									<div class="sub-role-menu">
										<c:forEach items="${menuModel.childs}" var="menuChildModel">
											<div class="divmenu">
												<h4>
													<b><span class="sub-menu-description"> <i
															class="menu-icon fa"></i>
															${menuChildModel.menu.description}
													</span></b>
												</h4>
												<span class="action-box">
													<fieldset>
														<c:forEach items="${menuChildModel.components}"
															var="component">
															<c:set var="checked" value="false" />
															<c:forEach var="roledetail" items="${listRoledetail}">
																<c:if test="${roledetail.component.id == component.id}">
																	<c:set var="checked" value="true" />
																</c:if>
															</c:forEach>
															<div>
																<input type="checkbox" id="coding" name="menus"
																	value="${component.id}"
																	${checked ? 'checked="checked"' : ''} /> <label
																	for="${component.id}">${component.name}</label>
															</div>
														</c:forEach>
													</fieldset>
												</span>
											</div>
										</c:forEach>
									</div>
								</div>
							</c:forEach>
							<!-- <div id="checkboxMenus"></div> -->
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>