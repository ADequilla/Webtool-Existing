<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Customer Service | Edit</title>
<script type="text/javascript">
    	var userLogin	= ${loginSecUser.id};
    	
        $(document).ready(function() {
            $("form").validate();
            
            $("#btn-reset").click(function(){
            	$('#concernId').val($('#default_concernId').val());
            	$('#transType').val($('#default_transType').val());
            	$('#assignedToId').val($('#default_assignedToId').val());
            	$('#assignedToName').val($('#default_assignedToName').val());
            	$('#action').val($('#default_action').val());
            });
            
            $("#btn-save").click(function(){
            	if ($("form").valid()) {
            		showConfirmation();
            	}
            });
            
            initAction();
        	
            popoverFunction.getUserPopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showAssignedToPopup",
                modalId		: "popupAssignedToTable",
                modalTitle	: "User List",
                hiddenId	: "assignedToId",
                varValue	: "name",
                callback	: function(){
                	initAction();
                }
            });
        });
        
        function initAction() {
        	var userId = $('#assignedToId').val();
        	if (userLogin == userId) {
        		$("#action").prop('disabled', false);
        	} else {
        		$('#action').val("");
        		$("#action").prop('disabled', true);
        	}
        }
        
        function showConfirmation(){
			BootstrapDialog.show({
            	title	: 'Confirmation',
                message	: 'Are you sure want to save Ticket?',
                type	: BootstrapDialog.TYPE_INFO,
                buttons	: [
                       	   {
                       			label	: '<i class="fa fa-times"></i> Cancel',
                       			cssClass: 'btn-default',
                    			action	: function(dialog){
                    				dialog.close();
                                }
                			},
                			{
                       			label	: '<i class="fa fa-save"></i> Save',
                       			cssClass: 'btn-info',
                    			action	: function(dialog){
                    				var data = $('form').serializeObject();
                    				submit('/cs/dashboard/save', JSON.stringify(data), function (data) {
                                        $("input[name='id']").val(data.id);
                                    });
                    				
                    				dialog.close();
                                }
                			}
                ]
            });
		}

		if (localStorage.getItem('isPageOpen')) {
      alert('Page is already open in another tab!');
	  window.location.href = '${pageContext.request.contextPath}/logout'; 
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
						<li>Customer Service</li>
						<li><a
							href="${pageContext.request.contextPath}/cs/dashboard/">Dashboard</a></li>
						<li class="active">Edit</li>
					</ul>
					<h3>
						<i class="fa fa-pencil"></i> Edit CS Dashboard
					</h3>
					<em>Customer Service</em>
				</div>
			</div>
			<div class="col-lg-6">
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
			<form class="form-horizontal" role="form" method="post"
				enctype="multipart/form-data">
				<input type="hidden" name="id" value="${ticket.id}"> <input
					type="hidden" id="default_concernId" value="${ticket.concernId}">
				<input type="hidden" id="default_transType"
					value="${ticket.transType}"> <input type="hidden"
					id="default_assignedToId" value="${ticket.assignedToId}"> <input
					type="hidden" id="default_assignedToName"
					value="${ticket.assignedTo}"> <input type="hidden"
					id="default_action" value="${ticket.action}">

				<div class="row">
					<div class="col-md-12">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-"></i> Dashboard
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">Submitted By</label>
										<div class="col-md-3">
											<input type="text" value="${ticket.submittedBy}"
												class="form-control" disabled="disabled" />
										</div>
										<label class="col-md-3 control-label">Posted Date</label>
										<div class="col-md-3">
											<input type="text"
												value="<fmt:formatDate value='${ticket.createdDate}' pattern='dd-MMM-yyyy HH:mm'/>"
												class="form-control" disabled="disabled" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">Ticket Number</label>
										<div class="col-md-3">
											<input type="text" value="${ticket.ticketNo}"
												class="form-control" disabled="disabled" />
										</div>
										<label class="col-md-3 control-label">Accomplished
											Date</label>
										<div class="col-md-3">
											<input type="text"
												value="<fmt:formatDate value='${ticket.closedDate}' pattern='dd-MMM-yyyy HH:mm'/>"
												class="form-control" disabled="disabled" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">Turn Around Time
											(minutes)</label>
										<div class="col-md-3">
											<input type="text" value="${ticket.concernTime}"
												class="form-control" disabled="disabled" />
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
									<i class="fa fa-user"></i> Client Information
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-body">
									<div class="form-group">
										<label class="col-md-4 control-label">CID </label>
										<div class="col-md-8">
											<input id="clientCid" name="clientCid" type="text"
												class="form-control" readonly="readonly"
												value="${client.cid }" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Type of Client </label>
										<div class="col-md-8">
											<input id="clientType" name="clientType" type="text"
												class="form-control" readonly="readonly"
												value="${client.typeDesc }" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Family Name</label>
										<div class="col-md-8">
											<input id="familyName" type="text" class="form-control"
												readonly="readonly" value="${client.lastname }" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Given Name</label>
										<div class="col-md-8">
											<input id="clientName" name="clientName" type="text"
												class="form-control" readonly="readonly"
												value="${client.firstname }" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Middle Name</label>
										<div class="col-md-8">
											<input id="middleName" type="text" class="form-control"
												readonly="readonly" value="${client.middlename }" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Mobile Number</label>
										<div class="col-md-8">
											<input id="mobileNo" type="text" class="form-control"
												readonly="readonly" value="${client.mobileNo }" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Branch Name</label>
										<div class="col-md-8">
											<input id="branch" type="text" class="form-control"
												readonly="readonly" value="${client.branchDesc }" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Unit Name</label>
										<div class="col-md-8">
											<input id="unit" type="text" class="form-control"
												readonly="readonly" value="${client.unitDesc }" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Center Name</label>
										<div class="col-md-8">
											<input id="center" type="text" class="form-control"
												readonly="readonly" value="${client.centerDesc }" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Birthday</label>
										<div class="col-md-8">
											<input id="birthday" type="text" class="form-control"
												readonly="readonly"
												value="<fmt:formatDate value='${client.dob }' pattern='dd-MMM-yyyy'/>" />
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
									<i class="fa fa-phone"></i> Detail Information
								</h3>
							</div>
							<c:choose>
								<c:when test="${ticket.assignedTo != null}">
									<div class="widget-content form">
										<div class="form-group">
											<label class="col-md-4 control-label">Type of Concern</label>
											<div class="col-md-8">
												<input type="text" value="${ticket.concernName}"
													class="form-control" disabled="disabled" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-4 control-label">Type of
												Transaction</label>
											<div class="col-md-8">
												<input type="text" value="${ticket.transDesc}"
													class="form-control" disabled="disabled" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-4 control-label">Assigned To</label>
											<div class="col-md-8">
												<input type="hidden" id="assignedToId" name="assignedToId"
													value="${ticket.assignedToId}"> <input type="text"
													value="${ticket.assignedTo}" class="form-control"
													disabled="disabled" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-4 control-label">Action Taken <span
												class="required">*</span></label>
											<div class="col-md-8">
												<textarea id="action" name="action"
													class="form-control required" rows="6">${ticket.action}</textarea>
											</div>
										</div>
										<div class="form-group ">
											<label class="col-md-4 control-label">Detail</label>
											<div class="col-md-8">
												<textarea class="form-control" rows="6" disabled="disabled">${ticket.message}</textarea>
											</div>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="widget-content form">
										<div class="form-group">
											<label class="col-md-4 control-label">Type of Concern
												<span class="required">*</span>
											</label>
											<div class="col-md-8">
												<select id="concernId" name="concernId"
													class="form-control required">
													<option value="">-- Choose --</option>
													<c:forEach items="${listConcern}" var="concern">
														<option value="${concern.id}"
															${concern.id == ticket.concernId ? 'selected="selected"' : ''}>${concern.name}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-4 control-label">Type of
												Transaction <span class="required">*</span>
											</label>
											<div class="col-md-8">
												<select id="transType" name="transType"
													class="form-control required">
													<option value="">-- Choose --</option>
													<c:forEach items="${listTransaction}" var="lookup">
														<option value="${lookup.value}"
															${lookup.value == ticket.transType ? 'selected="selected"' : ''}>${lookup.description}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-4 control-label">Assigned To <span
												class="required">*</span></label>
											<div class="col-md-8">
												<input type="hidden" id="assignedToId" name="assignedToId"
													value="${ticket.assignedToId}">
												<div class="input-group">
													<input id="assignedToName" name="assignedToName"
														type="text" style="cursor: pointer;"
														class="form-control showAssignedToPopup required"
														readonly="readonly" value="${ticket.assignedTo}" /> <span
														class="input-group-addon"><i class="fa fa-search"></i></span>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-4 control-label">Action Taken <span
												class="required">*</span></label>
											<div class="col-md-8">
												<textarea id="action" name="action"
													class="form-control required" rows="6">${ticket.action}</textarea>
											</div>
										</div>
										<div class="form-group ">
											<label class="col-md-4 control-label">Detail</label>
											<div class="col-md-8">
												<textarea class="form-control" rows="6" disabled="disabled">${ticket.message}</textarea>
											</div>
										</div>
									</div>
								</c:otherwise>
							</c:choose>

						</div>
					</div>
				</div>
			</form>
		</div>
		<!-- /main-content -->
	</div>
	<!-- /main -->
</body>
</html>