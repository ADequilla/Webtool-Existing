<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>CSR Hotline | Customer Service</title>
<script type="text/javascript">
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/cs/csr-hotlain/search",
                "sServerMethod"	: "POST",
                "bProcessing"	: false,
                "scrollX"		: true,
                "fnServerData"	: function (sSource, aoData, fnCallback) {
                    var contactNumber			= $.trim($("#contactNumber").val());
                    var networkProvider			= $.trim($("#networkProvider").val());
					var institutionCode			= $.trim($("#instiCode").val());
                   
                    
                    aoData.push({ "name": "contactNumber",			"value": contactNumber });
                    aoData.push({ "name": "networkProvider", 		"value": networkProvider });
					aoData.push({ "name": "institutionCode", 		"value": institutionCode });
                    jQuery.ajax({
                        "dataType"	: 'json',
                        "type"		: "POST",
                        "url"		: sSource,
                        "data"		: aoData,
                        "success"	: fnCallback
                    });
                }, 
                "order"		: [ [ 1, "desc" ] ],  
                "aoColumns"	: [
                    { "mDataProp": "contactNumber" },
                    { "mDataProp": "networkProvider" },
					{ "mDataProp": "instDesc" },
					{ "mDataProp": "id", "bSortable": false },
					{ "mDataProp": "id", "bSortable": false }
                ],
                
                "aoColumnDefs" : [
                   {
                   	class		: "text-center",
                       "mRender"	: EditlinkFormatter,
                       "aTargets"	: [3]
                   },
                   {
                   	class		: "text-center",
                       "mRender"	: CheckboxFormatter,
                       "aTargets"	: [4]
                   }	
               ]
                
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
	    			submit('/cs/csr-hotlain/delete', JSON.stringify(data), function (data) {
	    				oTable.fnDraw();
	    			});
	           	} else {
	           		BootstrapDialog.show({
	                    message: 'Please select at least one data.',
	                    type: BootstrapDialog.TYPE_WARNING
	                });
	           	}
			});
            
            $("#btn-reset").click(function(){
            	$("#contactNumber").val("");
            	$("#networkProvider").val("");
				$("#instDesc").val("");
            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
            });


			popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showInstitutionPopup",
                modalId		: "popupInstitutionTable",
                modalTitle	: "Institution List",
                hiddenId	: "instiCode",
                varValue	: "description",
                callback	: function callback(){
                    
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
            
        });
        
    </script>
</head>
<body>
	<div class="content">
		<div class="row">
			<div class="col-lg-5 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>CSR Hotline</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/cs/csr-hotlain/">CSR
								Hotline</a></li>
					</ul>
					<h3>
						<i class="fa fa-list-alt"></i> CSR Hotline
					</h3>
					<em>CSR Hotline</em>
				</div>
			</div>
			<div class="col-lg-7 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/cs/csr-hotlain/create">
								<div class="quick-access-item bg-color-blue">
									<i class="fa fa-plus"></i>
									<h5>CSR Hotline</h5>
									<em>add new data</em>
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
							<input type="text" id="contactNumber" class="form-control"
								placeholder="Contact Number">
						</div>
						<div class="col-sm-6">
							<input type="text" id="networkProvider" class="form-control"
								placeholder="Network Provider">
						</div>

					</div>

					<!-- <div class="row">
						<div class="col-sm-6">
							<input type="hidden" id="instiCode" name="instiCode">
							<div class="input-group">
								<input id="instiDesc" name="instiDesc" type="text"
									style="cursor: pointer;"
									class="form-control showInstitutionPopup" readonly="readonly"
									placeholder="Institution" /> <span class="input-group-addon"><i
									class="fa fa-search"></i></span>
							</div>
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
						<i class="fa fa-table"></i>CSR Hotline
					</h3>
				</div>

				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Contact Number</th>
								<th>Network Provider</th>
								<th>Institution Desc</th>
								<th width="5%"></th>
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