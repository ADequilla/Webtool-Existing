<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<script
	src="${pageContext.request.contextPath}/assets/js/popup_upload_param.js"></script>
<title>Upload | Registration</title>
<script type="text/javascript">
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource": "${pageContext.request.contextPath}/registration/upload/search",
                "sServerMethod": "POST",
                "scrollX": true,
                "fnServerData": function (sSource, aoData, fnCallback) {
                    var branch			= $.trim($("#searchBranchCode").val());
                    var unit			= $.trim($("#searchUnitCode").val());
                    var uploadStatus 	= $.trim($("#searchUploadStatus").val());
                    var uploadBy	 	= $.trim($("#searchUploadUserId").val());
                    var uploadDate	 	= $.trim($("#searchUploadDate").val());
                    aoData.push({ "name": "branch","value": branch });
                    aoData.push({ "name": "unit", "value": unit });
                    aoData.push({ "name": "uploadStatus", "value": uploadStatus });
                    aoData.push({ "name": "uploadBy", "value": uploadBy });
                    aoData.push({ "name": "uploadDate", "value": uploadDate });
                    jQuery.ajax({
                        "dataType": 'json',
                        "type": "POST",
                        "url": sSource,
                        "data": aoData,
                        "success": fnCallback
                    });
                },
                "aoColumns": [
                    { "mDataProp": "uploadDate" },
                    { "mDataProp": "branch", "bSortable": false},
                    { "mDataProp": "unit", "bSortable": false},
                    { "mDataProp": "displayFilename" },
                    { "mDataProp": "status" },
                    { "mDataProp": "totalClient" },
                    { "mDataProp": "validClient" },
                    { "mDataProp": "invalidClient" },
                    { "mDataProp": "user.usrLoginname", "defaultContent": ""},
                    { "mDataProp": fnBlank, "bSortable": false }
                ],
                "aoColumnDefs" : [
                    {
                    	class: "text-center",
                        "mRender": function(data, type, row){
                        	if(row.invalidFilename == null){
                        		return '';
                        	}else{
                        		return '<a href="${pageContext.request.contextPath}/registration/upload/download/'+ row.id +'"><span class="icon-download"><i class="fa fa-download"></i></span></a>';	
                        	}
                        },
                        "aTargets": [9]
                    }
                ]
            });
            
            $("#btn-reset").click(function(){
            	$("#searchBranchCode").val("");
            	$("#searchBranchDesc").val("");
                $("#searchUnitCode").val("");
                $("#searchUnitDesc").val("");
                $("#searchUploadStatus").val("");
                $("#searchUploadUser").val("");
                $("#searchUploadUserId").val("");
                $("#searchUploadDate").val("");
                
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
                callback	: function callback(){
                	$("#searchCenterCode").val("");
                    $("#searchCenterDesc").val("");
    			},
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
            
            popoverFunction.getStucturePopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showUnitPopup",
                modalId		: "popupUnitTable",
                modalTitle	: "Unit List",
                hiddenId	: "searchUnitCode",
                varValue	: "description",
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
    	                        			  return $.trim($("#searchBranchCode").val())
    	                    			  }
    	                }
            		]
            });
            
            popoverFunction.getUserPopup({
                url			: "${pageContext.request.contextPath}",
                classMain	: "showUserPopup",
                modalId		: "popupUserTable",
                modalTitle	: "User List",
                hiddenId	: "searchUploadUserId",
                varValue	: "usrName"
            });
            
            /*   $(".showPopupUpload").popup_upload({
                url			: "${pageContext.request.contextPath}/registration/upload/upload",
                modal_title	: "Upload File"}, 
                function (event){
                	console.log("callback");
                	console.log(event); 
                });
            
           */
            
           
              
 
        	
        	 
            
            
        	   
         
            
            
        });
        
        function clearjQueryCache(){
            for (var x in jQuery.cache){
                delete jQuery.cache[x];
            }
        }
        
        
        
        var tUploadNew = function(pilih){            	
        	clearjQueryCache();     	
        	 urlString ='${pageContext.request.contextPath}/registration/upload/upload' ;
        	 stitle ='Upload File';
        	 tag='.showPopupUpload';
        	 $('.showPopupUpload').popup_upload_param({
                 url		: urlString,
                 modal_title: stitle,
                 category	: pilih,
                 }, 
                 function (event){
                 	console.log("callback");
                 	console.log(event); 
                 });
         }
       
		 if (localStorage.getItem('isPageOpen')) {
      alert('Page is already open in another tab!');
	  window.location.href = '${pageContext.request.contextPath}/logout'; 
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
						<li>Registration</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/registration/upload/">Upload</a></li>
					</ul>
					<h3>
						<i class="fa fa-upload"></i> Upload
					</h3>
					<em>Registration</em>
				</div>
			</div>

			<!-- href="javascript:void(0);"  -->


			<div class="col-lg-7 ">
				<c:if test="${UPLOAD_PERMISSION != null}">
					<div class="top-content">
						<ul class="list-inline quick-access">


							<li><a href="#" class="showPopupUpload"
								onclick='tUploadNew(1);'>
									<div class="quick-access-item bg-color-orange">
										<i class="fa fa-upload"></i>
										<h5>Upload</h5>
										<em> Upload new customer data</em>
									</div>
							</a></li>

							<li><a href="#" class="showPopupUpload"
								onclick='tUploadNew(2);'>
									<div class="quick-access-item bg-color-orange">
										<i class="fa fa-upload"></i>
										<h5>Edit Upload</h5>
										<em> Edit customer data</em>
									</div>
							</a></li>


						</ul>
					</div>
				</c:if>

				<!--  
	        <c:if test="${UPLOAD_PERMISSION != null}">
	        <div class="top-content">
	            <ul class="list-inline quick-access">
	                <li>
	                    <a href="javascript:void(0);" class="showPopupUploadEdit">
	                        <div class="quick-access-item bg-color-orange">
	                            <i class="fa fa-upload"></i>
	                            <h5>Edit Upload</h5>
	                            <em>edit customer data</em>
	                        </div>
	                    </a>
	                </li>
	            </ul>
	        </div>
	        </c:if>
	        -->
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
						<input type="hidden" id="searchBranchCode">
						<div class="input-group">
							<input id="searchBranchDesc" type="text" style="cursor: pointer;"
								class="form-control showBranchPopup" readonly="readonly"
								placeholder="Branch" /> <span class="input-group-addon"><i
								class="fa fa-search"></i></span>
						</div>
					</div>
					<div class="col-sm-6">
						<input type="hidden" id="searchUploadUserId">
						<div class="input-group">
							<input id="searchUploadUser" type="text" style="cursor: pointer;"
								class="form-control showUserPopup" readonly="readonly"
								placeholder="Upload By" /> <span class="input-group-addon"><i
								class="fa fa-search"></i></span>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-6">
						<input type="hidden" id="searchUnitCode">
						<div class="input-group">
							<input id="searchUnitDesc" type="text" style="cursor: pointer;"
								class="form-control showUnitPopup" readonly="readonly"
								placeholder="Unit" /> <span class="input-group-addon"><i
								class="fa fa-search"></i></span>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="input-group">
							<input id="searchUploadDate" style="cursor: pointer;" type="text"
								class="form-control datepicker" placeholder="Upload Date">
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-6">
						<select id="searchUploadStatus" class="form-control">
							<option value="">-- Status --</option>
							<c:forEach items="${listUploadStatus}" var="lookup">
								<option value="${lookup.value}"
									${lookup.value == user.usrStatus ? 'selected="selected"' : ''}>${lookup.description}</option>
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
					<i class="fa fa-table"></i> List of Upload files
				</h3>
			</div>
			<div class="widget-content">
				<table id="dataTable"
					class="table table-striped table-hover table-bordered datatable">
					<thead>
						<tr>
							<th>Date and Time</th>
							<th>Branch</th>
							<th>Unit</th>
							<th>File</th>
							<th>Upload Status</th>
							<th>Number of Clients</th>
							<th>Valid Rows</th>
							<th>Invalid Rows</th>
							<th>Upload By</th>
							<th>Invalid File</th>
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