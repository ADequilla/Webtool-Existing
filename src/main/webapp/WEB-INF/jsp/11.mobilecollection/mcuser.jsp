<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>MC User Management | Mobile Collection</title>
<script type="text/javascript">
		function resetMultiselect(id) {
			$(id + " option").remove();
			$("#unit").append('<option value="">-- Unit --</option>');
		}
		
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/mobilecollection/user/search",
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
                    var aoStatus		= $.trim($("#searchAoStatus").val());
                    aoData.push({ "name": "mcuId",	"value": mcuId });
                    aoData.push({ "name": "staffId",	"value": staffId });
                    aoData.push({ "name": "mobileNumber",	"value": mobileNumber });
                    aoData.push({ "name": "internalAccount",	"value": internalAccount });
                    aoData.push({ "name": "branchCode", 	"value": branchCode });
                    aoData.push({ "name": "unitCode", 	"value": unitCode });
                    aoData.push({ "name": "designation", 	"value": designation });
                    aoData.push({ "name": "aoStatus", 	"value": aoStatus });
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
                    { "mDataProp": "branch.description", "bSortable": false},
                    { "mDataProp": "unit.description", "bSortable": false},
                    { "mDataProp": "internalAccount", "bSortable": false},
                    { "mDataProp": "aoStatus", "bSortable": false},
                    { "mDataProp": "actCode", "bSortable": false},
                    { "mDataProp": "id", "bSortable": false }
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
						"mRender"	: MCUserWebStatusFormatter,
						"aTargets"	: [8]
					},
                    {
                    	className	: "text-center",
                        "mRender"	: EditlinkFormatter,
                        "aTargets"	: [10]
                    }
                ]
            });
            
            $("#btn-reset").click(function(){
            	$("#searchMcuId").val("");
            	$("#searchInternalAccount").val("");
            	$("#searchDesignation").val("");
            	$("#searchStaffId").val("");
            	$("#searchAoStatus").val("");
				$("#searchMobileNumber").val("");
				
				$('#branch option:selected').each(function() {
            		$(this).prop('selected', false);
                });
				
				resetMultiselect('#unit');
            	
            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
            });

			 $(".showPopupUpload").popup_upload({
	            url: "${pageContext.request.contextPath}/mobilecollection/user/upload",
	            modal_title: "Upload File"
	          	}, function (event){
	          		oTable.fnDraw();
	               	console.log("callback");
	               	console.log(event);
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
			<div class="col-lg-4 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Mobile Collection</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/mobilecollection/user/">MC
								User Management</a></li>
					</ul>
					<h3>
						<i class="fa fa-user"></i> MC User Management
					</h3>
					<em>Mobile Collection</em>
				</div>
			</div>
			<div class="col-lg-8 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a href="javascript:void(0);" class="showPopupUpload">
								<div class="quick-access-item bg-color-orange">
									<i class="fa fa-upload"></i>
									<h5>Upload</h5>
									<em>upload MC Users data</em>
								</div>
						</a></li>
						<li><a
							href="${pageContext.request.contextPath}/mobilecollection/user/create">
								<div class="quick-access-item bg-color-green">
									<i class="fa fa-plus"></i>
									<h5>New MC User</h5>
									<em>add new MC Users data</em>
								</div>
						</a></li>
						<li><a
							href="${pageContext.request.contextPath}/mobilecollection/user/report">
								<div class="quick-access-item bg-color-blue">
									<i class="fa fa-download"></i>
									<h5>Download</h5>
									<em>download MC Users data</em>
								</div>
						</a></li>
					</ul>
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
							<select id="searchAoStatus" class="form-control">
								<option value="">-- Mobile Collection User Status --</option>
								<c:forEach items="${listMCUserStatus}" var="lookup">
									<option value="${lookup.value}">${lookup.description}</option>
								</c:forEach>
							</select>
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
					</div>
				</div>
			</div>
			<!-- END SEARCH DATA TABLE -->

			<!-- SHOW HIDE COLUMNS DATA TABLE -->
			<div class="widget widget-table">
				<div class="widget-header">
					<h3>
						<i class="fa fa-table"></i> List of Mobile Collection Users
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
								<th>Branch Name</th>
								<th>Unit Name</th>
								<th>Internal Account Number</th>
								<th>Mc User Status</th>
								<th>Latest Activation Code</th>
								<th width="5%"></th>
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