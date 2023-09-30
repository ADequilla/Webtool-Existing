<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>List | Mobile Branch Officer</title>
<script type="text/javascript">
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/mbo/list/search",
                "sServerMethod"	: "POST",
                "scrollX"		: true,
                "fnServerData"	: function (sSource, aoData, fnCallback) {
                    var branch	= $.trim($("#searchBranchCode").val());
                    var name 	= $.trim($("#searchName").val());
                    aoData.push({ "name": "branch",	"value": branch });
                    aoData.push({ "name": "name", 	"value": name });
                    jQuery.ajax({
                        "dataType"	: 'json',
                        "type"		: "POST",
                        "url"		: sSource,
                        "data"		: aoData,
                        "success"	: fnCallback
                    });
                },
                "aoColumns": [
                    { "mDataProp": "branchDesc" },
                    { "mDataProp": "fullname" },
                    { "mDataProp": "address" },
                    { "mDataProp": "account" },
                    { "mDataProp": "actCode" },
                    { "mDataProp": "id", "bSortable": false }
                ],
                "aoColumnDefs" : [
                    {
                    	class		: "text-center",
                        "mRender"	: EditlinkFormatter,
                        "aTargets"	: [5]
                    }
                ]
            });
            
            $("#btn-reset").click(function(){
            	$("#searchBranchCode").val("");
            	$("#searchBranchDesc").val("");
            	$("#searchName").val("");

            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
            });
            
            
            
            popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showBranchPopup",
                modalId		: "popupBranchTable",
                modalTitle	: "Branch List",
                hiddenId	: "searchBranchCode",
                varValue	: "description",
               	ajax_data	: [
       					{
       						fieldVar	: "type",
       						fieldValue	: "BRANCH"
       					},
       					{
    	                	fieldVar	: "filter",
    						fieldValue	: "Y"
    	                }
            		]
            });
        });

		// 	if (localStorage.getItem('isPageOpen')) {
    //   alert('Page is already open in another tab!');
	//   window.location.href = '${pageContext.request.contextPath}/logout'; 
    // } else {
    //   localStorage.setItem('isPageOpen', true);
    //   window.addEventListener('beforeunload', function () {
    //     localStorage.removeItem('isPageOpen');
    //   });
    // }
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
						<li>Mobile Branch Officer</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/mbo/list/">List</a></li>
					</ul>
					<h3>
						<i class="fa fa-user-o"></i> List
					</h3>
					<em>Mobile Branch Officer</em>
				</div>
			</div>
			<div class="col-lg-7 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/mbo/list/create">
								<div class="quick-access-item bg-color-blue">
									<i class="fa fa-plus"></i>
									<h5>New MBO</h5>
									<em>add new MBO data</em>
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
						<div class="col-sm-6">
							<input type="text" id="searchName" class="form-control"
								placeholder="Name">
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<input type="hidden" id="searchBranchCode">
							<div class="input-group">
								<input id="searchBranchDesc" type="text"
									style="cursor: pointer;" class="form-control showBranchPopup"
									readonly="readonly" placeholder="Branch" /> <span
									class="input-group-addon"><i class="fa fa-search"></i></span>
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
					</div>
				</div>
			</div>
			<!-- END SEARCH DATA TABLE -->

			<!-- SHOW HIDE COLUMNS DATA TABLE -->
			<div class="widget widget-table">
				<div class="widget-header">
					<h3>
						<i class="fa fa-table"></i> Mobile Branch Officer List
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Branch</th>
								<th>Name</th>
								<th>Address</th>
								<th>Internal Account</th>
								<th>Activation Code</th>
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