<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Partner | Utilities</title>
<script type="text/javascript">
		$(document).ready(function() {
	    	var oTable = $("#dataTable").dataTable({
	        	"sAjaxSource"	: "${pageContext.request.contextPath}/utilities/partner/search",
	            "sServerMethod"	: "POST",
	            "scrollX"		: true,
	            "fnServerData"	: function (sSource, aoData, fnCallback) {
	            	var partnerId    	= $.trim($("#partnerId").val());
	            	var partnerName    	= $.trim($("#partnerName").val());
	            	var partnerDesc    	= $.trim($("#partnerDesc").val());
	            	var status    	= $.trim($("#status").val());
	            	
	                aoData.push({ "name": "partnerId", "value": partnerId });
	                aoData.push({ "name": "partnerName", "value": partnerName });
	                aoData.push({ "name": "partnerDesc", "value": partnerDesc });
	                aoData.push({ "name": "status", "value": status });
	                
	            	jQuery.ajax({
	                    "type"		: "GET",
	                    "url"		: sSource,
	                    "data"		: aoData,
	                    "success"	: fnCallback
	                });
				},
	            "aoColumns": [
	            	{ "mDataProp": "partnerId" },
					{ "mDataProp": "partnerName" },
					{ "mDataProp": "partnerDesc" },
					{ "mDataProp": "partnerAccount" },
					/* { "mDataProp": "myPartnerId" }, */
					{ "mDataProp": "partnerApiUrl" },
					/* { "mDataProp": "myPartnerToken" }, */
					/* { "mDataProp": "secretKey" }, */
					{ "mDataProp": "merchantUrl" },
					{ "mDataProp": "merchantPrefix" },
					{ "mDataProp": "mriGroup" },
					{ "mDataProp": "status" },
					{ "mDataProp": "partnerId" , "bSortable": false },
					{ "mDataProp": "id" , "bSortable": false }
				],
            	"aoColumnDefs" : [
            		/* {
                        render: function (data, type, full, meta) {
                            return "<div style='word-break: break-all; width: 600px;'>" + data + "</div>";
                        },
                        targets: 6
                    }, */
            		{
	            		class		: "text-center",
	                    "mRender"	: MriGroupFormatter,
	                    "aTargets"	: [7]
	                },{
	            		class		: "text-center",
	                    "mRender"	: StatusPartnerFormatter,
	                    "aTargets"	: [8]
	                },
	                {
	            		class		: "text-center",
	                    "mRender"	: EditlinkFormatter,
	                    "aTargets"	: [9]
	                },
					{
                    	class		: "text-center",
                        "mRender"	: CheckboxFormatter,
                        "aTargets"	: [10]
                    }	
            	]
			});
				
			$("#btn-search").click(function(){
	        	oTable.fnDraw();
			});
		
			$("#btn-reset").click(function(){
		        $('#partnerId').val("");
		        $('#partnerName').val("");
		        $('#partnerDesc').val("");
		        $('#status').val("");
		        
				oTable.fnDraw();
			});
		
			$("#btn-delete").click(function(){
	        	var data = [];
	            $('#dataTable').find('tr').each(function () {
	    			var checkbox = $(this).find('input[type="checkbox"]');
	   				if (checkbox.is(':checked')){
	        			var row = {"id" : checkbox.val(), "state" : checkbox.attr('class')};
	        			data.push(row);
	   				}
				});
	            	
	            if(data.length > 0){
	    			submit('/utilities/partner/delete', JSON.stringify(data), function (data) {
	    				oTable.fnDraw();
	    			});
	           	} else {
	           		BootstrapDialog.show({
	                    message: 'Please select at least one data.',
	                    type: BootstrapDialog.TYPE_WARNING
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
			<div class="col-lg-5 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Utilities</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/utilities/partner/">Partner</a></li>
					</ul>
					<h3>
						<i class="fa fa-sitemap fa-fw"></i> Partner
					</h3>
					<em>Utilities</em>
				</div>
			</div>
			<div class="col-lg-7 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/utilities/partner/create">
								<div class="quick-access-item bg-color-blue">
									<i class="fa fa-plus"></i>
									<h5>New Partner</h5>
									<em>add new partner</em>
								</div>
						</a></li>
					</ul>
				</div>
			</div>
		</div>

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
						<input type="text" id="partnerId" class="form-control"
							placeholder="Partner Id">
					</div>

					<div class="col-sm-6">
						<input type="text" id="partnerName" class="form-control"
							placeholder="Partner Name">
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<input type="text" id="partnerDesc" class="form-control"
							placeholder="Partner Desc">
					</div>


					<div class="col-sm-6">
						<select id="status" class="form-control">
							<option value="">-- Status --</option>
							<c:forEach items="${listPartner}" var="lookup">
								<option value="${lookup.value}">${lookup.description}</option>
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
					<div class="col-sm-6 text-right">
						<div class="form-group">
							<a class="btn btn-danger" type="button" data-toggle="modal"
								data-target="#confirm-delete"><i class="fa fa-trash"></i>
								Delete</a>
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
					<i class="fa fa-table"></i> Partner List
				</h3>
			</div>
			<div class="widget-content">
				<table id="dataTable"
					class="table table-striped table-hover table-bordered datatable">
					<thead>
						<tr>
							<th>Partner Id</th>
							<th>Partner Name</th>
							<th>Partner Desc</th>

							<th>Partner Account</th>
							<!--  <th>My Partner Id</th> -->
							<th>Partner API URL</th>
							<!--  <th>My Partner Token</th>
	                <th>My Partner Secret Key</th> -->
							<th>Merchant Payment Callback URL</th>
							<th>Merchant Id Prefix</th>
							<th>MRI Group</th>
							<th>Status</th>
							<th></th>
							<th></th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<!-- END SHOW HIDE COLUMNS DATA TABLE -->

	</div>
	<!-- /main-content -->
</body>
</html>