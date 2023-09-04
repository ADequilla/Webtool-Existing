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
				$('#ticketNo').val('');
				$('#clientCid').val('');
				$('#username').val('');
				$('#dateRangeStart').val('');
            	$('#dateRangeEnd').val('');
            	$('#concern').val('');
            	$('#status').val('');
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
										<label class="col-md-2 control-label">Posted Date <span
											class="required">*</span></label>
										<div class="col-sm-2">
											<div class="input-group">
												<input id="dateRangeStart" name="dateRangeStart"
													style="cursor: pointer;" type="text"
													class="form-control startdatepicker required"
													placeholder="Date Start"> <span
													class="input-group-addon"><i class="fa fa-calendar"></i></span>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="input-group">
												<input id="dateRangeEnd" name="dateRangeEnd"
													style="cursor: pointer;" type="text"
													class="form-control enddatepicker required"
													placeholder="Date End"> <span
													class="input-group-addon"><i class="fa fa-calendar"></i></span>
											</div>
										</div>

										<label class="col-md-2 control-label">Concern </label>
										<div class="col-sm-4">
											<select id="concern" name="concern" class="form-control">
												<option value="">-- Concern --</option>
												<c:forEach items="${listConcern}" var="concern">
													<option value="${concern.id}">${concern.name}</option>
												</c:forEach>
											</select>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-2 control-label">Ticket Number </label>
										<div class="col-sm-4">
											<input id="ticketNo" name="ticketNo" type="text"
												class="form-control" placeholder="Ticket Number" />
										</div>
										<label class="col-md-2 control-label">Status </label>
										<div class="col-sm-4">
											<select id="status" name="status" class="form-control">
												<option value="">-- Status --</option>
												<c:forEach items="${listStatus}" var="lookup">
													<option value="${lookup.value}">${lookup.description}</option>
												</c:forEach>
											</select>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-2 control-label">CID </label>
										<div class="col-sm-4">
											<input id="clientCid" name="clientCid" type="text"
												class="form-control" placeholder="CID" />
										</div>
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

									<div class="form-group">
										<label class="col-md-2 control-label">Posted By </label>
										<div class="col-sm-4">
											<input id="username" name="username" type="text"
												class="form-control" placeholder="Posted By" />
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