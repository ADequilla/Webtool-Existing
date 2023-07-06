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
            	$('#clientCid').val('');
            	$('#branchCode').val('');
            	$('#branchDesc').val('');
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
            
            popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showBranchPopup",
                modalId		: "popupBranchTable",
                modalTitle	: "Branch List",
                hiddenId	: "branchCode",
                varValue	: "description",
                callback	: function callback(){
                	$("#unitCode").val("");
                    $("#unitDesc").val("");
                    $("#centerCode").val("");
                    $("#centerDesc").val("");
    			},
               	ajax_data	: [
       					{
       						fieldVar	: "type",
       						fieldValue	: "BRANCH"
       					},
       					{
    	                	fieldVar	: "filter",
    						fieldValue	: "Y"
    	                },
    	                {
    	                    fieldVar	: "instiCode",
    	                    fieldValue	: function getInstitution() {
    	                        			  return $.trim($("#instiCode").val())
    	                    			  }
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
                callback	: function callback(){
                	$("#centerCode").val("");
                    $("#centerDesc").val("");
    			},
               	ajax_data	: [
       					{
       						fieldVar	: "type",
       						fieldValue	: "UNIT"
       					},
       					{
    	                	fieldVar	: "filter",
    						fieldValue	: "Y"
    	                },
    	                {
    	                    fieldVar	: "branchCode",
    	                    fieldValue	: function getBranch() {
    	                        			  return $.trim($("#branchCode").val())
    	                    			  }
    	                },
    	                {
    	                    fieldVar	: "instiCode",
    	                    fieldValue	: function getInstitution() {
    	                        			  return $.trim($("#instiCode").val())
    	                    			  }
    	                }
            		]
            });
            
            popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showInstitutionPopup",
                modalId		: "popupInstitutionTable",
                modalTitle	: "Institution List",
                hiddenId	: "instiCode",
                varValue	: "description",
                callback	: function callback(){
                	$("#branchCode").val("");
                    $("#branchDesc").val("");
                	$("#unitCode").val("");
                    $("#unitDesc").val("");
                    $("#centerCode").val("");
                    $("#centerDesc").val("");
                    
    			},
               	ajax_data	: [
       					{
       						fieldVar	: "type",
       						fieldValue	: "INSTITUTION"
       					},
       					{
    	                	fieldVar	: "filter",
    						fieldValue	: "Y"
    	                }
            		]
            });
            
            popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showCenterPopup",
                modalId		: "popupCenterTable",
                modalTitle	: "Center List",
                hiddenId	: "centerCode",
                varValue	: "description",
               	ajax_data	: [
       					{
       						fieldVar	: "type",
       						fieldValue	: "CENTER"
       					},
       					{
    	                	fieldVar	: "filter",
    						fieldValue	: "Y"
    	                },
    	                {
    	                    fieldVar	: "branchCode",
    	                    fieldValue	: function getBranch() {
    	                        			  return $.trim($("#branchCode").val())
    	                    			  }
    	                },
    	                {
    	                    fieldVar	: "unitCode",
    	                    fieldValue	: function getUnit() {
    	                        			  return $.trim($("#unitCode").val())
    	                    			  }
    	                },
    	                {
    	                    fieldVar	: "instiCode",
    	                    fieldValue	: function getInstitution() {
    	                        			  return $.trim($("#instiCode").val())
    	                    			  }
    	                }
            		]
            });
        
        });
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
										<label class="col-md-2 control-label">Registration Date <span class="required">*</span></label>
										<div class="col-sm-2">
											  <div class="input-group">
												  <input id="dateRangeStart" name="dateRangeStart" style="cursor: pointer;" type="text" class="form-control datepicker required" placeholder="Select Date...">
												  <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
											  </div>
										  </div>
										  <div class="col-sm-2">
											<div class="input-group">
												<input id="dateRangeEnd" name="dateRangeEnd" style="cursor: pointer;" type="text" class="form-control datepicker required" placeholder="Select Date...">
												<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
											</div>
										 </div>
										  
										  <label class="col-md-2 control-label">Unit</label>
											<div class="col-sm-4">
												<input type="hidden" id="unitCode" name="unitCode">
												<div class="input-group">
													<input id="unitDesc" name="unitDesc" type="text" style="cursor: pointer;" class="form-control showUnitPopup" readonly="readonly" placeholder="Unit">
													<span class="input-group-addon"><i class="fa fa-search"></i></span>
												</div>
										  	</div>
									</div>

									<div class="form-group">
										<label class="col-md-2 control-label">Institution</label>
										<div class="col-sm-4">
										   <input type="hidden" id="instiCode" name="instiCode">
										   <div class="input-group">
											   <input id="instiDesc" name="instiDesc" type="text" style="cursor: pointer;" class="form-control showInstitutionPopup" readonly="readonly" placeholder="Institution">
											   <span class="input-group-addon"><i class="fa fa-search"></i></span>
										   </div>
									   	</div>
									   	<label class="col-md-2 control-label">Center</label>
										 <div class="col-sm-4">
										   <input type="hidden" id="centerCode" name="centerCode">
										   <div class="input-group">
											   <input id="centerDesc" name="centerDesc" type="text" style="cursor: pointer;" class="form-control showCenterPopup" readonly="readonly" placeholder="Center">
											   <span class="input-group-addon"><i class="fa fa-search"></i></span>
										   </div>
									   </div>
								   </div>

									<div class="form-group">
										<label class="col-md-2 control-label">Branch</label>
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