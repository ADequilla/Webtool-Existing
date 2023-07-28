<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Service DownTime | Create/Edit</title>
<script type="text/javascript">
		$(document).ready(function() {
			$("form").validate();
			
			$('#startDate').datetimepicker().on('show', function(e) {
                $( '#startDate' ).datetimepicker( 'setStartDate', new Date());
    		});
			$('#startDate').datetimepicker().on('changeDate', function(e) {
    			$( '#endDate' ).val('');
    		});
			
			$('#endDate').datetimepicker().on('show', function(e) {
                $( '#endDate' ).datetimepicker( 'setStartDate', $('#startDate').datetimepicker( 'getDate') );
    		});
			
			$("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/utilities/service/save', JSON.stringify(dataJson), function (data) {
	                	$("input[name='id']").val(data.id);
	                });
                }
            });
			
			$("#btn-reset").click(function(){
				$('#startDate').val($('#default_startDate').val());
				$('#endDate').val($('#default_endDate').val());
				$('#desc').val($('#default_desc').val());
				$('#type').val($('#default_type').val());
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
							href="${pageContext.request.contextPath}/utilities/service/">Service
								DownTime</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Service DownTime
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
				<input type="hidden" name="id" value="${downtime.id}"> <input
					type="hidden" id="default_desc" value="${downtime.desc}"> <input
					type="hidden" id="default_startDate"
					value="<fmt:formatDate value="${downtime.startDate}" pattern="dd MMM yyyy - HH:mm"/>">
				<input type="hidden" id="default_endDate"
					value="<fmt:formatDate value="${downtime.endDate}" pattern="dd MMM yyyy - HH:mm"/>">
				<input type="hidden" id="default_type" value="${downtime.type}">

				<div class="row">
					<div class="col-md-12">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-server fa-fw"></i> Create Service DownTime
								</h3>
							</div>
							<div class="widget-content form">
								<div class="form-group">
									<label class="col-md-3 control-label">Start Date <span
										class="required">*</span></label>
									<div class="col-md-4">
										<div class="input-group">
											<input type="text"
												class="form-control form_datetime required"
												style="cursor: pointer;" id="startDate" name="startDate"
												value="<fmt:formatDate value="${downtime.startDate}" pattern="dd MMM yyyy - HH:mm"/>"
												data-date-format="dd MMM yyyy - HH:mm"
												placeholder="Start Date" readonly="readonly" /> <span
												class="input-group-addon"><i class="fa fa-calendar"></i></span>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">End Date <span
										class="required">*</span></label>
									<div class="col-md-4">
										<div class="input-group">
											<input type="text"
												class="form-control form_datetime required"
												style="cursor: pointer;" id="endDate" name="endDate"
												value="<fmt:formatDate value="${downtime.endDate}" pattern="dd MMM yyyy - HH:mm"/>"
												data-date-format="dd MMM yyyy - HH:mm"
												placeholder="End Date" readonly="readonly" /> <span
												class="input-group-addon"><i class="fa fa-calendar"></i></span>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Client Type <span
										class="required">*</span></label>
									<div class="col-md-4">
										<select id="type" name="type" class="form-control required">
											<option value="">-- Client Type --</option>
											<c:forEach items="${listClientType}" var="lookup">
												<option value="${lookup.value}"
													${lookup.value == downtime.type ? 'selected="selected"' : ''}>${lookup.description}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">Description <span
										class="required">*</span></label>
									<div class="col-md-4">
										<textarea id="desc" name="desc" class="form-control required"
											rows="6">${downtime.desc}</textarea>
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