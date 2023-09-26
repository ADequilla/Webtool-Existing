<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>MC User Access Authorization | Mobile Collection</title>
<script type="text/javascript">
	    function resetMultiselect(id) {
			$(id + " option").remove();
			$("#unit").append('<option value="">-- Unit --</option>');
		}
	    
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/mobilecollection/userauthorization/search",
                "sServerMethod"	: "POST",
                "scrollX"		: true,
                "fnServerData"	: function (sSource, aoData, fnCallback) {
                    var mcuId			= $.trim($("#searchMcuId").val());
                    var staffId			= $.trim($("#searchStaffId").val());
                    var mobileNumber	= $.trim($("#searchMobileNumber").val());
                    var internalAccount	= $.trim($("#searchInternalAccount").val());
                    var branchCode		= $.trim($("#branch").val());
                    var unitCode		= $.trim($("#unit").val());
                    var designation		= $.trim($("#searchDesignation").val());
                    var dateStart		= $.trim($("#searchDateStart").val());
                    var dateEnd			= $.trim($("#searchDateEnd").val());
                    aoData.push({ "name": "mcuId",	"value": mcuId });
                    aoData.push({ "name": "staffId",	"value": staffId });
                    aoData.push({ "name": "mobileNumber",	"value": mobileNumber });
                    aoData.push({ "name": "internalAccount",	"value": internalAccount });
                    aoData.push({ "name": "branchCode", 	"value": branchCode });
                    aoData.push({ "name": "unitCode", 	"value": unitCode });
                    aoData.push({ "name": "designation", 	"value": designation });
                    aoData.push({ "name": "dateStart", 	"value": dateStart });
                    aoData.push({ "name": "dateEnd", 	"value": dateEnd });
                    jQuery.ajax({
                        "dataType"	: 'json',
                        "type"		: "POST",
                        "url"		: sSource,
                        "data"		: aoData,
                        "success"	: fnCallback
                    });
                },
                "aoColumns": [
					{ "mDataProp": "mcuId", "bSortable": false},          
                    { "mDataProp": "staffId", "bSortable": false},
                    { "mDataProp": "givenName", "bSortable": false},
                    { "mDataProp": "designation", "bSortable": false},
                    { "mDataProp": "mobileNumber", "bSortable": false},
                    { "mDataProp": "branch.code", "bSortable": false},
                    { "mDataProp": "branch.description", "bSortable": false},
                    { "mDataProp": "unit.code", "bSortable": false},
                    { "mDataProp": "unit.description", "bSortable": false},
                    { "mDataProp": "internalAccount", "bSortable": false},
                    { "mDataProp": "email", "bSortable": false},
                    { "mDataProp": "createdOn", "bSortable": false},
                    { "mDataProp": "createdBy", "bSortable": false},
                    { "mDataProp": "updatedOn", "bSortable": false},
                    { "mDataProp": "updatedBy", "bSortable": false},
                    { "mDataProp": "approvedStatus", "bSortable": false}
                ],
                "aoColumnDefs" : [
                	{
                    	className	: "text-center",
                        "mRender"	: FullNameFormatter,
                        "aTargets"	: [2]
                    },
                    {
                    	className	: "text-center",
						"mRender"	: DesignationFormatter,
						"aTargets"	: [3]
                    },
                	{
                    	className	: "text-center",
                        "mRender"	: FullNameCreatedByFormatter,
                        "aTargets"	: [12]
                    },
                    {
                    	className	: "text-center",
                        "mRender"	: FullNameUpdatedByFormatter,
                        "aTargets"	: [14]
                    },
                    {
                    	className	: "text-center",
                        "mRender"	: ApprovalMcCheckboxFormatter,
                        "aTargets"	: [15]
                    }
                ]
            });

            $("#check_all").click(function(){
                $('input:checkbox').not(this).prop('checked', this.checked);
            });
            
            $("#btn-reset").click(function(){
            	$("#searchMcuId").val("");
            	$("#searchInternalAccount").val("");
            	$("#searchDesignation").val("");
            	$("#searchStaffId").val("");
				$("#searchMobileNumber").val("");
				$("#searchDateStart").val("");
				$("#searchDateEnd").val("");
				
				$('#branch option:selected').each(function() {
            		$(this).prop('selected', false);
                });
				
				resetMultiselect('#unit');
				
            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
            });
            
            $("#btn-reject").click(function(){
            	var data = [];
            	$('#dataTable').find('tr').each(function () {
    				var checkbox = $(this).find('input[type="checkbox"]');
    				if(checkbox.attr('id') != 'check_all'){
	   					if (checkbox.is(':checked')){
	        				var row = {"id" : checkbox.val(), "state" : checkbox.attr('class')};
	        				data.push(row);
	   					}
    				}
				});
            	
            	if(data.length > 0){
    				submit('/mobilecollection/userauthorization/reject', JSON.stringify(data), function (data) {
    					oTable.fnDraw();
    				});
            	} else{
            		BootstrapDialog.show({
                        message: 'Please select at least one data.',
                        type: BootstrapDialog.TYPE_WARNING
                    });
            	}
            });
            
            $("#btn-approve").click(function(){
            	var data = [];
            	$('#dataTable').find('tr').each(function () {
    				var checkbox = $(this).find('input[type="checkbox"]');
    				if(checkbox.attr('id') != 'check_all'){
	   					if (checkbox.is(':checked')){
	        				var row = {"id" : checkbox.val(), "state" : checkbox.attr('class')};
	        				data.push(row);
	   					}
    				}
				});
            	
            	if(data.length > 0){
    				submit('/mobilecollection/userauthorization/approve', JSON.stringify(data), function (data) {
    					oTable.fnDraw();
    				});
            	} else{
            		BootstrapDialog.show({
                        message: 'Please select at least one data.',
                        type: BootstrapDialog.TYPE_WARNING
                    });
            	}
            });
            
            $("#branch").click(function(){
				var values	= $(this).val();
				$.ajax({
	               	url: "${pageContext.request.contextPath}/administration/user/getUnit/"+values,
	               	cache: false,
	               	success: function(data){
	               		resetMultiselect('#unit');

	               		$.each(data, function(){
	               			$("#unit").append('<option value="'+ this.code +'">'+ this.description +'</option>');
	               		});
	               	}
             	});
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
			<div class="col-lg-5 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Mobile Collection</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/mobilecollection/userauthorization/">MC
								User Access Authorization</a></li>
					</ul>
					<h3>
						<i class="fa fa-user"></i> MC User Access Authorization
					</h3>
					<em>Mobile Collection</em>
				</div>
			</div>
		</div>

		<!-- main -->
		<div class="main-content">
			<!-- SEARCH DATA TABLE -->
			<div class="widget widget-table">
				<div class="widget-header">
					<h3>
						<i class="fa fa-search"></i>
					</h3>
				</div>
				<div class="widget-content-search">
					<div class="row">
						<div class="col-sm-4">
							<input type="text" id="searchMcuId" class="form-control"
								placeholder="Mobile Collection User ID">
						</div>
						<div class="col-sm-4">
							<input type="text" id="searchInternalAccount"
								class="form-control" placeholder="Internal Account">
						</div>
						<div class="col-sm-4">
							<select id="searchDesignation" class="form-control">
								<option value="">-- Designation --</option>
								<c:forEach items="${listDesignation}" var="lookup">
									<option value="${lookup.value}">${lookup.description}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-4">
							<input type="text" id="searchStaffId" class="form-control"
								placeholder="Staff ID">
						</div>
						<div class="col-sm-4">
							<select id="branch" class="form-control">
								<option value="">-- Branch --</option>
								<c:forEach items="${availableBranchList}" var="availBranch">
									<option value="${availBranch.code}">${availBranch.description}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-4">
							<div class="input-group">
								<input id="searchDateStart" style="cursor: pointer;" type="text"
									class="form-control datepicker required"
									placeholder="Date Start"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-4">
							<input type="text" id="searchMobileNumber" class="form-control"
								placeholder="Mobile Number">
						</div>
						<div class="col-sm-4">
							<select id="unit" class="form-control">
								<option value="">-- Unit --</option>
								<c:forEach items="${availableUnitList}" var="availUnit">
									<option value="${availUnit.code}">${availUnit.description}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-4">
							<div class="input-group">
								<input id="searchDateEnd" style="cursor: pointer;" type="text"
									class="form-control datepicker required" placeholder="Date End">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
					</div>

					<div class="row widget-button-search">
						<div class="col-sm-6">
							<div class="form-group">
								<a id="btn-search" class="btn btn-custom-primary " type="button"><i
									class="fa fa-search"></i> Search</a> <a id="btn-reset"
									class="btn btn-default" type="button"><i
									class="fa fa-refresh"></i> Reset</a>
							</div>
						</div>
						<div class="col-sm-6 text-right">
							<a class="btn btn-success" type="button" data-toggle="modal"
								data-target="#confirm-approve"><i class="fa fa-check"></i>
								Approve</a> <a class="btn btn-danger" type="button"
								data-toggle="modal" data-target="#confirm-reject"><i
								class="fa fa-times"></i> Reject</a>
						</div>
					</div>
				</div>
			</div>
			<!-- END SEARCH DATA TABLE -->

			<!-- SHOW HIDE COLUMNS DATA TABLE -->
			<div class="widget widget-table">
				<div class="widget-header">
					<h3>
						<i class="fa fa-table"></i> List of MC User for Approval
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>MC User ID</th>
								<th>Staff ID Number</th>
								<th>Full Name</th>
								<th>Designation</th>
								<th>Mobile Number</th>
								<th>Branch Code</th>
								<th>Branch Name</th>
								<th>Unit Code</th>
								<th>Unit Name</th>
								<th>Internal Account</th>
								<th>Email Address</th>
								<th>Date&Time Created</th>
								<th>Created By</th>
								<th>Date&Time Modified</th>
								<th>Modified By</th>
								<th width="5%"><input type="checkbox" id="check_all" /></th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<!-- END SHOW HIDE COLUMNS DATA TABLE -->
		</div>
		<!-- /main-content -->
	</div>
	<!-- /main -->
</body>
</html>