<!DOCTYPE html>
<html>
<head>
<Title>Roles | Administration</Title>
<script type="text/javascript">
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/administration/role/search",
                "sServerMethod"	: "POST",
                "fnServerData"	: function (sSource, aoData, fnCallback) {
                    var value = $.trim($("#searchRoleName").val());
                    aoData.push({ "name": "name", "value": value });
                    jQuery.ajax({
                        "dataType"	: 'json',
                        "type"		: "POST",
                        "url"		: sSource,
                        "data"		: aoData,
                        "success"	: fnCallback
                    });
                },
                "aoColumns": [
                    { "mDataProp": "name" },
                    { "mDataProp": "description" },
                    { "mDataProp": "id", "bSortable": false }
                ],
                "aoColumnDefs" : [
                    {
                    	class		: "text-center",
                        "mRender"	: EditlinkFormatter,
                        "aTargets"	: [2]
                    }
                ]
            });
            
            $("#btn-reset").click(function(){
            	$('#searchRoleName').val("");
            	
            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
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
						<li><i class="fa fa-home"></i></li>
						<li>Administrator</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/administration/role/">Roles</a></li>
					</ul>
					<h3>
						<i class="fa fa-users"></i> Roles
					</h3>
					<em>Administration</em>
				</div>
			</div>
			<div class="col-lg-8 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/administration/role/create">
								<div class="quick-access-item bg-color-blue">
									<i class="fa fa-plus"></i>
									<h5>New Role</h5>
									<em>add new Roles data</em>
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
							<input type="text" id="searchRoleName" class="form-control"
								placeholder="Role Name">
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
			<div class="widget widget-table">
				<div class="widget-header">
					<h3>
						<i class="fa fa-table"></i> List of Roles
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Role Name</th>
								<th>Description</th>
								<th width="5%"></th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<!-- END SHOW HIDE COLUMNS DATA TABLE -->

			<!-- /main-content -->
		</div>
		<!-- /main -->
	</div>

</body>
</html>