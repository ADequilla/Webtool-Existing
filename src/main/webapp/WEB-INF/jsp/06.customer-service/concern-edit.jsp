<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Type Of Concern | Create/Edit</title>
<script type="text/javascript">
		$(document).ready(function() {
            $("form").validate();
            
			$("#btn-save").click(function(){
	            if ($("form").valid()) {
		            var dataJson = $('form').serializeObject();
		            submit('/cs/concern/save', JSON.stringify(dataJson), function (data) {
			        	$("input[name='id']").val(data.id);
			        });
	            }
            });
			
			$("#btn-reset").click(function(){
				$('#name').val($('#default_name').val());
				$('#time').val($('#default_time').val());
				$('#desc').val($('#default_desc').val());
				$('#level').val($('#default_level').val());
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
						<li>Customer Service</li>
						<li><a href="${pageContext.request.contextPath}/cs/concern/">Type
								Of Concern</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Type Of Concern
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
				<input type="hidden" name="id" value="${concern.id}"> <input
					type="hidden" id="default_name" value="${concern.name}"> <input
					type="hidden" id="default_desc" value="${concern.desc}"> <input
					type="hidden" id="default_level" value="${concern.level}">
				<input type="hidden" id="default_time" value="${concern.time}">

				<div class="row">
					<div class="col-md-12">
						<div class="widget">
							<div class="widget-header">
								<h3>
									<i class="fa fa-sticky-note"></i> Type Of Concern
								</h3>
							</div>

							<div class="widget-content form">
								<div class="form-group">
									<label class="col-md-3 control-label">Concern Name <span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="name" name="name" value="${concern.name}"
											type="text" class="form-control required"
											placeholder="Concern" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3 control-label">Concern
										Description <span class="required">*</span>
									</label>
									<div class="col-md-4">
										<input id="desc" name="desc" value="${concern.desc}"
											type="text" class="form-control required"
											placeholder="Concern Description" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3 control-label">Turn Around Time
										(In Minutes) <span class="required">*</span>
									</label>
									<div class="col-md-4">
										<div class="input-group">
											<input id="time" name="time" value="${concern.time}"
												type="number" class="form-control required"
												placeholder="Turn Around Time" /> <span
												class="input-group-addon">Minutes</span>
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3 control-label">Complexity Level
										<span class="required">*</span>
									</label>
									<div class="col-md-4">
										<select id="level" name="level" class="form-control required">
											<option value="">-- Complexity Level --</option>
											<c:forEach items="${listComplexityLevel}" var="lookup">
												<option value="${lookup.value}"
													${lookup.value == concern.level ? 'selected="selected"' : ''}>${lookup.description}</option>
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