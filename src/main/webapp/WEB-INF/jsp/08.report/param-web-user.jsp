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
            	$('#username').val('');
            	$('#status').val('');
            	$('#branchCode').val('');
            	$('#branchDesc').val('');
            });

			$("#btn-save").click(function() {
  if ($("form").valid()) {
    var dataJson = $('form').serializeObject();
    if (dataJson.id) {
      window.location.href = `${pageContext.request.contextPath}/report/web-user/save`;
    } else {
      submit('/report/web-user/save', JSON.stringify(dataJson), function(data) {
      });
    }
  }
});
            
            popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showBranchPopup",
                modalTitle	: "Branch List",
                hiddenId	: "branchCode",
                varValue	: "description",
               	ajax_data	: [
						{
							fieldVar	: "filter",
							fieldValue	: "Y"
						},
       					{
       						fieldVar: "type",
       						fieldValue: "BRANCH"
       					}
            		]
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
										<label class="col-md-3 control-label">Date Range <span
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
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Username </label>
										<div class="col-sm-4">
											<input id="username" name="username" type="text"
												class="form-control" placeholder="Username" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Status </label>
										<div class="col-sm-4">
											<select id="status" name="status" class="form-control">
												<option value="">-- User Status --</option>
												<c:forEach items="${listUserStatus}" var="lookup">
													<option value="${lookup.value}">${lookup.description}</option>
												</c:forEach>
											</select>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">Branch</label>
										<div class="col-sm-4">
											<input type="hidden" id="branchCode" name="branchCode">
											<div class="input-group">
												<input id="branchDesc" name="branchDesc" type="text"
													style="cursor: pointer;"
													class="form-control showBranchPopup" readonly="readonly"
													placeholder="Branch" /> <span class="input-group-addon"><i
													class="fa fa-search"></i></span>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label">File Type <span
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