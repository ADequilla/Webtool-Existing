<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>List of Agent | Monitoring</title>
<script type="text/javascript">
        $(document).ready(function() {
        	var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/monitoring/list-agent/search",
                "sServerMethod"	: "GET",
                "bProcessing"	: false,
                "fnServerData"	: function (sSource, aoData, fnCallback) {
					var searchDateStart 	= $.trim($("#searchDateStart").val());
					var searchDateEnd 	= $.trim($("#searchDateEnd").val());
					var mobileNumber 	= $.trim($("#mobileNumber").val());
					var cid 	= $.trim($("#cid").val());
					var instiCode 	= $.trim($("#instiCode").val());
					var branchCode 	= $.trim($("#branchCode").val());
					var unitCode 	= $.trim($("#unitCode").val());
					var centerCode 	= $.trim($("#centerCode").val());
                    aoData.push({ "name": "searchDateStart", "value": searchDateStart});
                    aoData.push({ "name": "searchDateEnd", "value": searchDateEnd });
					aoData.push({ "name": "mobileNumber", "value": mobileNumber });
					aoData.push({ "name": "cid", "value": cid});
                    aoData.push({ "name": "instiCode", "value": instiCode });
					aoData.push({ "name": "branchCode", "value": branchCode });
					aoData.push({ "name": "unitCode", "value": unitCode});
                    aoData.push({ "name": "centerCode", "value": centerCode });
                    jQuery.ajax({
                        "type"		: "GET",
                        "url"		: sSource,
                        "data"		: aoData,
                        "success"	: fnCallback
                    });
                },
                "aoColumns": [
					{ "mDataProp": "enableAgentFeatures"},	
					{ "mDataProp": "enableAgentFeaturesByName" },
					{ "mDataProp": "cid" },
					{ "mDataProp": "fullname" },
					{ "mDataProp": "mobileNo" },
					{ "mDataProp": "instDesc" },
					{ "mDataProp": "branchDesc" },
					{ "mDataProp": "unitDesc" },
					{ "mDataProp": "centerDesc" }

                ]
		
            });
            
        	$("#btn-search").click(function(){
                oTable.fnDraw();
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
        
			
            $("#btn-reset").click(function(){
				$('#mobileNumber').val("")
				$('#searchDateStart').val("")
				$('#searchDateEnd').val("")
				$('#instiCode').val("");
				$('#cid').val("");
				$('#branchCode').val("");
            	$('#branchDesc').val("");
            	$('#unitCode').val("");
                $('#unitDesc').val("");
                $('#centerCode').val("");
                $('#centerDesc').val("");
				
            	oTable.fnDraw();
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
			<div class="col-lg-5 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Monitoring</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/monitoring/list-agent/">List
								of Agent</a></li>
					</ul>
					<h3>
						<i class="fa fa-envelope"></i> List of Agent
					</h3>
					<em>Monitoring</em>
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
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchDateStart" style="cursor: pointer;" type="text" class="form-control startdatepicker" placeholder="Enabled Date Start"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchDateEnd" style="cursor: pointer;" type="text" class="form-control enddatepicker" placeholder="Enabled Date End"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-6">
							<input type="text" placeholder="Mobile Number" id="mobileNumber" class="form-control">
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<input type="text" placeholder="CID" id="cid" class="form-control">
						</div>
						<div class="col-sm-6">
						  <input type="hidden" id="unitCode" name="unitCode" value="">
						  <div class="input-group">
							  <input id="unitDesc" name="unitDesc" type="text" style="cursor: pointer;" class="form-control showUnitPopup" readonly="readonly" placeholder="Unit">
							  <span class="input-group-addon"><i class="fa fa-search"></i></span>
						  </div>
					  	</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<input type="hidden" id="instiCode" name="instiCode" value="">
							<div class="input-group">
								<input id="instiDesc" name="instiDesc" type="text" style="cursor: pointer;" class="form-control showInstitutionPopup" readonly="readonly" placeholder="Institution" data-original-title="" title="">
								<span class="input-group-addon"><i class="fa fa-search"></i></span>
							</div>
						</div>
						<div class="col-sm-6">
							<input type="hidden" id="centerCode" name="centerCode" value="">
							<div class="input-group">
								<input id="centerDesc" name="centerDesc" type="text" style="cursor: pointer;" class="form-control showCenterPopup" readonly="readonly" placeholder="Center">
								<span class="input-group-addon"><i class="fa fa-search"></i></span>
							</div>
						</div>
					</div>
					<div class="row">
						
						<div class="col-sm-6">
							<input type="hidden" id="branchCode" name="branchCode" value="">
							<div class="input-group">
								<input id="branchDesc" name="branchDesc" type="text" style="cursor: pointer;" class="form-control showBranchPopup" readonly="readonly" placeholder="Branch" data-original-title="" title=""> <span class="input-group-addon"><i class="fa fa-search"></i></span>
							</div>
						</div>
						<div class="col-sm-6">
						</div>
					</div>
					<div class="row widget-button-search">
						<div class="col-sm-6">
							<div class="form-group">
								<a id="btn-search" class="btn btn-custom-primary " type="button"><i class="fa fa-search"></i> Search</a> <a id="btn-reset" class="btn btn-default" type="button"><i class="fa fa-refresh"></i> Reset</a>
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
						<i class="fa fa-table"></i> List of Agent
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Date & Time Enabled Agent Feature</th>
								<th>Enabled By</th>
								<th>CID</th>
								<th>Fullname</th>
								<th>Mobile No.</th>
								<th>Institution</th>
								<th>Branch</th>
								<th>Unit</th>
								<th>Center</th>
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