<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<Title>Hierarchy | Administration</Title>
<script type="text/javascript">
	    function resetMultiselect(id) {
			$(id + " option").remove();
			if (id == '#unit'){
				$("#unit").append('<option value="">-- Unit --</option>');
			} else if (id == '#center'){
				$("#center").append('<option value="">-- Center --</option>');
			}else if (id == '#branch'){
				$("#branch").append('<option value="">-- Branch --</option>');
			}
		}
    
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/administration/hierarchy/search",
                "sServerMethod"	: "POST",
                "fnServerData"	: function (sSource, aoData, fnCallback) {
                	var institution	= $.trim($("#insti").val());
                    var branch 		= $.trim($("#branch").val());
                    var unit 		= $.trim($("#unit").val());
                    var center 		= $.trim($("#center").val());
                    aoData.push({ "name": "institution", "value": institution });
                    aoData.push({ "name": "branch", "value": branch });
                    aoData.push({ "name": "unit", "value": unit });
                    aoData.push({ "name": "center", "value": center });
                    jQuery.ajax({
                        "dataType"	: 'json',
                        "type"		: "POST",
                        "url"		: sSource,
                        "data"		: aoData,
                        "success"	: fnCallback
                    });
                },
                "aoColumns": [
                	{ "mDataProp": "branch.code", "defaultContent": ""},
                    { "mDataProp": "branch.description", "defaultContent": ""},
                    { "mDataProp": "unit.code", "defaultContent": ""},
                    { "mDataProp": "unit.description", "defaultContent": ""},
                    { "mDataProp": "center.code", "defaultContent": ""},
                    { "mDataProp": "center.description", "defaultContent": ""},
                    { "mDataProp": "id", "bSortable": false },
                    { "mDataProp": "id", "bSortable": false }
                ],
                "aoColumnDefs" : [
                    {
                    	class		: "text-center",
                        "mRender"	: EditlinkFormatter,
                        "aTargets"	: [6]
                    },
					{
	                	class		: "text-center",
	                    "mRender"	: CheckboxFormatter,
	                    "aTargets"	: [7]
	                }	
                ]
            });
            
            $("#btn-reset").click(function(){
            	$('#insti option:selected').each(function() {
            		$(this).prop('selected', false);
                });
				
				resetMultiselect('#unit');
				resetMultiselect('#center');
				resetMultiselect('#branch');
            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
            });
            
            $("#insti").change(function(){
				var values	= $(this).val();
				$.ajax({
	               	url: "${pageContext.request.contextPath}/administration/user/getBranch/"+values,
	               	cache: false,
	               	success: function(data){
	               		resetMultiselect('#branch');
	               		resetMultiselect('#unit');
	               		resetMultiselect('#center');
	               		
	               		$.each(data, function(){
	               			$("#branch").append('<option value="'+ this.code +'">'+ this.description +'</option>');
	               		});
	               	}
             	});
			});
            
            $("#branch").change(function(){
				var values	= $(this).val();
				$.ajax({
	               	url: "${pageContext.request.contextPath}/administration/user/getUnit/"+values,
	               	cache: false,
	               	success: function(data){
	               		resetMultiselect('#unit');
	               		resetMultiselect('#center');
	               		
	               		$.each(data, function(){
	               			$("#unit").append('<option value="'+ this.code +'">'+ this.description +'</option>');
	               		});
	               	}
             	});
				
				
			});

			// $("#btn-devices").click(function(){
		  	//  	$("#modal-hierarchy-upload").modal('show');
			// });
            
            $("#unit").change(function(){
				var values	= $(this).val();
				$.ajax({
	               	url: "${pageContext.request.contextPath}/administration/user/getCenter/"+values,
	               	cache: false,
	               	success: function(data){
	               		resetMultiselect('#center');

	               		$.each(data, function(){
	               			$("#center").append('<option value="'+ this.code +'">'+ this.description +'</option>');
	               		});
	               	}
             	});
				console.log("unit click");
			});
            
            // $(".showPopupUpload").popup_upload({
            //     url: "${pageContext.request.contextPath}/administration/hierarchy/upload",
            //     modal_title: "Upload File"
            //   	}, function (event){
            //   		oTable.fnDraw();
            //     	console.log("callback");
            //     	console.log(event);
        	// });

			$("#btnSubmitHierarchy").click(function(){
				event.preventDefault();
				var form = $('#uploadHierarchyForm')[0];
    			var data = new FormData(form);
				var institutionCode	= $.trim($("#instiCode").val());
				data.append("instCode", institutionCode);
				$("#btnSubmitHierarchy").prop("disabled", true);

				$.ajax({
					type: "POST",
					enctype: 'multipart/form-data',
					url: "${pageContext.request.contextPath}/administration/hierarchy/upload",
					data: data,
					processData: false,
					contentType: false,
					cache: false,
					timeout: 600000,
					success: function (data) {
						console.log("SUCCESS : ", data);
						$("#btnSubmitHierarchy").prop("disabled", false);

					},
					error: function (e) {
						console.log("ERROR : ", e);
						$("#btnSubmitHierarchy").prop("disabled", false);

					}
				});


			});

			
			$("#btnUploadHierarchy").click(function(){
				$("#modal-hierarchy-upload").modal('show');
				console.log("upload click");
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
	    			submit('/administration/hierarchy/delete', JSON.stringify(data), function (data) {
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
			<div class="col-lg-4 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Administrator</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/administration/hierarchy/">Hierarchy</a></li>
					</ul>
					<h3>
						<i class="fa fa-sitemap"></i> Hierarchy
					</h3>
					<em>Administration</em>
				</div>
			</div>
			<div class="col-lg-8 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li id="btnUploadHierarchy"><a href="javascript:void(0);" class="">
								<div class="quick-access-item bg-color-orange">
									<i class="fa fa-upload"></i>
									<h5>Upload</h5>
									<em>upload hierarchy data</em>
								</div>
						</a></li>
						<li>
	                    <a href="${pageContext.request.contextPath}/administration/hierarchy/create">
	                        <div class="quick-access-item bg-color-blue">
	                            <i class="fa fa-plus"></i>
	                            <h5>New Hierarchy</h5>
	                            <em>add new Hierarchy data</em>
	                        </div>
	                    </a>
	                </li> 
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
							<select id="insti" class="form-control">
								<option value="">-- Institution --</option>
								<c:forEach items="${availableInstiList}" var="availInsti">
									<option value="${availInsti.code}">${availInsti.description}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-6">
							<select id="branch" class="form-control">
								<option value="">-- Branch --</option>
								<c:forEach items="${availableBranchList}" var="availBranch">
									<option value="${availBranch.code}">${availBranch.description}</option>
								</c:forEach>
							</select>
						</div>

					</div>
					<div class="row">
						<div class="col-sm-6">
							<select id="unit" class="form-control">
								<option value="">-- Unit --</option>
								<c:forEach items="${availableUnitList}" var="availUnit">
									<option value="${availUnit.code}">${availUnit.description}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-6">
							<select id="center" class="form-control">
								<option value="">-- Center --</option>
								<c:forEach items="${availableCenterList}" var="availCenter">
									<option value="${availCenter.code}">${availCenter.description}</option>
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
						<div class="col-md-6 text-right">
							<div class="controls form-inline">
								<div class="form-group">
									<a class="btn btn-danger" type="button" data-toggle="modal"
										data-target="#confirm-delete"><i class="fa fa-trash"></i>
										Delete</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!-- END SEARCH DATA TABLE -->

			<div class="widget widget-table">
				<div class="widget-header">
					<h3>
						<i class="fa fa-table"></i> List of Hierarchy
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Branch Code</th>
								<th>Branch Name</th>
								<th>Unit Code</th>
								<th>Unit Name</th>
								<th>Center Code</th>
								<th>Center Name</th>
								<th width="5%"></th>
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
	<div class="modal fade" id="modal-hierarchy-upload" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div id="klikAlertMessageModal"></div>
				<div class="modal-header text-center">Upload File</div>

				<div class="modal-body">
					<form method="post" enctype="multipart/form-data" id="uploadHierarchyForm">
						<div class="row">
							<div class="form-group">
								<label class="col-md-4 control-label">Institution</label>
								<div class="col-md-8">
									<input type="hidden" id="instiCode" name="instiCode">
									<div class="input-group">
										<input id="instiDesc" name="instiDesc" type="text" style="cursor: pointer;" class="form-control showInstitutionPopup" readonly="readonly" placeholder="Institution">
										<span class="input-group-addon"><i class="fa fa-search"></i></span>
									</div>
								</div>
							</div>
						</div>
						<div class="row">

							<div class="form-group">
								<label class="col-md-4 control-label">Browse File</label>
								<div class="col-md-8">
									<div class="input-group">
										<input type="file" class="form-control required" name="files" placeholder="Upload Files">
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="row">
					<div class="modal-footer text-right">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<i class="fa fa-times"></i>Cancel</button>
						<button type="submit" id="btnSubmitHierarchy" class="btn btn-custom-primary">
							<i class="fa fa-upload"></i> Upload</button>
					</div>
				</div>
				
			</div>
		</div>
	</div>

	

</body>
</html>