<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>${REPORT_TITLE}| Generate</title>
<script type="text/javascript">
        $(document).ready(function() {
            $("form").validate();

			$("#btn-reset").click(function(){
            	$('#type').val('');
            	$('#dateRangeStart').val('');
            	$('#dateRangeEnd').val('');
            	$('#accountNumber').val('');
            	$('#dateBirth').val('');
            	$('#mobileNumber').val('');
            	$('#clientType').val('');
            	$('#errorMessage').val('');
            });

            $("#btn-save").click(function(){
                if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/report/${REPORT_MENU}/save', JSON.stringify(dataJson), function (data) {
                    	if(data.id){
                    		document.location.href = '${pageContext.request.contextPath}/report/${REPORT_MENU}/';
                    	}
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
    </script>
</head>
<body>
	<div class="content">
		<div class="row">
			<div class="col-lg-8 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Report</li>
						<li><a
							href="${pageContext.request.contextPath}/report/${REPORT_MENU}/">${REPORT_TITLE}</a></li>
						<li class="active">Generate</li>
					</ul>
					<h3>
						<i class="fa fa-file-pdf-o"></i> Generate ${REPORT_TITLE}
					</h3>
					<em>Report</em>
				</div>
			</div>
			<div class="col-lg-4 ">
				<div class="top-content-button">
					<ul class="list-inline quick-access">
						<li>
							<button id="btn-reset" class="btn btn-default" type="button">
								<i class="fa fa-refresh"></i> Reset Form
							</button>
						</li>
						<li>
							<button id="btn-save" class="btn btn-success" type="button">
								<i class="fa fa-save"></i> Generate Report
							</button>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<form class="form-horizontal" role="form" method="post">
			<div class="row">
				<div class="col-md-12">
					<div class="widget">
						<div class="widget-header">
							<h3>
								<i class="fa fa-clone"></i> Parameter ${REPORT_TITLE}
							</h3>
						</div>
						<div class="widget-content form">
							<div class="form-body">
								<div class="form-body">
									<div class="form-group">
										<label class="col-md-2 control-label">Activated Date <span
											class="required">*</span></label>
										<div class="col-sm-2">
											<div class="input-group">
												<input id="dateRangeStart" name="dateRangeStart"
													style="cursor: pointer;" type="text"
													class="form-control datepicker required"
													placeholder="Date Start"> <span
													class="input-group-addon"><i class="fa fa-calendar"></i></span>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="input-group">
												<input id="dateRangeEnd" name="dateRangeEnd"
													style="cursor: pointer;" type="text"
													class="form-control datepicker required"
													placeholder="Date End"> <span
													class="input-group-addon"><i class="fa fa-calendar"></i></span>
											</div>
										</div>
										<label class="col-md-2 control-label">AccountNumber </label>
										<div class="col-sm-4">
											<input id="accountNumber" name="accountNumber" type="text"
												class="form-control" placeholder="Account Number" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-2 control-label">Date Birth </label>
										<div class="col-sm-4">
											<div class="input-group">
												<input id="dateBirth" name="dateBirth"
													style="cursor: pointer;" type="text"
													class="form-control datepicker" placeholder="Date Birth">
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>
										<label class="col-md-2 control-label">Mobile Number </label>
										<div class="col-sm-4">
											<input id="mobileNumber" name="mobileNumber" type="text"
												class="form-control" placeholder="Mobile Number" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-2 control-label">Client Type </label>
										<div class="col-sm-4">
											<select id="clientType" name="clientType"
												class="form-control">
												<option value="">-- Client Type --</option>
												<c:forEach items="${listClientType}" var="lookup">
													<option value="${lookup.value}">${lookup.description}</option>
												</c:forEach>
											</select>
										</div>
										<label class="col-md-2 control-label">Error Message </label>
										<div class="col-sm-4">
											<input id="errorMessage" name="errorMessage" type="text"
												class="form-control" placeholder="Error Message" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-2 control-label">File Type <span
											class="required">*</span></label>
										<div class="col-sm-4">
											<select id="type" name="type" class="form-control required">
												<option value="">-- File Type --</option>
												<c:forEach items="${listFileType}" var="lookup">
													<option value="${lookup.value}">${lookup.description}</option>
												</c:forEach>
											</select>
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