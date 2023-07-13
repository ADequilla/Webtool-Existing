<!DOCTYPE html>
<html>
<head>
<title>Reset Credential | Administration</title>

<script type="text/javascript">
        $(document).ready(function() {
            // var oTable = $("#dataTable").dataTable({
            //     "sAjaxSource"	: "${pageContext.request.contextPath}/administration/resetcredential/search",
            //     "sServerMethod"	: "POST",
            //     "scrollX"		: true,
            //     "fnServerData"	: function (sSource, aoData, fnCallback) {
            //         var firstName	= $.trim($("#searchFirstName").val());
            //         var middleName	= $.trim($("#searchMiddleName").val());
            //         var lastName	= $.trim($("#searchLastName").val());
            //         var userLogin	= $.trim($("#searchUserLogin").val());
            //         var branch		= $.trim($("#searchBranchCode").val());
            //         var status		= $.trim($("#searchStatus").val());
            //         aoData.push({ "name": "firstName",	"value": firstName });
            //         aoData.push({ "name": "middleName",	"value": middleName });
            //         aoData.push({ "name": "lastName",	"value": lastName });
            //         aoData.push({ "name": "userLogin",	"value": userLogin });
            //         aoData.push({ "name": "branch", 	"value": branch });
            //         aoData.push({ "name": "status", 	"value": status });
            //         jQuery.ajax({
            //             "dataType"	: 'json',
            //             "type"		: "POST",
            //             "url"		: sSource,
            //             "data"		: aoData,
            //             "success"	: fnCallback
            //         });
            //     },
            //     "aoColumns": [
			// 		{ "mDataProp": "login" },          
            //         { "mDataProp": "givenName" },
            //         { "mDataProp": "middleName" },
            //         { "mDataProp": "lastName" },
            //         { "mDataProp": "branches", "bSortable": false},
            //         { "mDataProp": "roles", "bSortable": false},
            //         { "mDataProp": "status" },
            //         { "mDataProp": "id", "bSortable": false }
            //     ],
            //     "aoColumnDefs" : [
			// 		{	
			// 			className	: "text-center",
			// 			"mRender"	: UserWebStatusFormatter,
			// 			"aTargets"	: [6]
			// 		},
            //         {
            //         	className	: "text-center",
            //             "mRender"	: EditlinkFormatter,
            //             "aTargets"	: [7]
            //         }
            //     ]
            // });
            
            // $("#btn-reset").click(function(){
            // 	$("#searchFirstName").val("");
            // 	$("#searchMiddleName").val("");
            // 	$("#searchLastName").val("");
            // 	$("#searchUserLogin").val("");
            // 	$("#searchBranchCode").val("");
            // 	$("#searchBranchDesc").val("");
			// 	$("#searchStatus").val("");
        		
            // 	oTable.fnDraw();
            // });

            // $("#btn-search").click(function(){
            //     oTable.fnDraw();
            // });
            
            
            
			popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showBranchPopup",
                modalTitle	: "Branch List",
                hiddenId	: "searchBranchCode",
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
        
    </script> 
</head>
<body>
	<div class="content">
		<div class="row">
			<div class="col-lg-4 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-key"></i></li>
						<li>Administrator</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/administration/resetcredential/">Credentials</a></li>
					</ul>
					<h3>
						<i class="fa fa-key"></i> Credentials
					</h3>
					<em>Administration</em>
				</div>
			</div>
			<!-- <div class="col-lg-7 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/administration/user/create">
								<div class="quick-access-item bg-color-blue">
									<i class="fa fa-plus"></i>
									<h5>New User</h5>
									<em>add new Users data</em>
								</div>
						</a></li>
					</ul>
				</div>
			</div> -->
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
						<div class="col-sm-6">
							<input type="text" id="searchFirstName" class="form-control"
								placeholder="CID">
						</div>
						<div class="col-sm-6">
							<input type="text" id="searchUserLogin" class="form-control"
								placeholder="Middle Name">
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<input type="text" id="searchMiddleName" class="form-control"
								placeholder="First Name">
						</div>
						<div class="col-sm-6">
							<input type="text" id="searchMiddleName" class="form-control"
								placeholder="Last Name">
						</div>
					</div>
					<!-- <div class="row">
						<div class="col-sm-6">
							<input type="text" id="searchLastName" class="form-control"
								placeholder="Last Name">
						</div>
						<div class="col-sm-6">
							<select id="searchStatus" class="form-control">
								<option value="">-- User Status --</option>
								<c:forEach items="${listUserStatus}" var="lookup">
									<option value="${lookup.value}">${lookup.description}</option>
								</c:forEach>
							</select>
						</div>
					</div> -->

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
						<i class="fa fa-table"></i> List of Clients
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>CID</th>
								<th>First Name</th>
								<th>Middle Name</th>
								<th>Last Name</th>
								<th>Birthday</th>
								<th>Mobile Number</th>
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