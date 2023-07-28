<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>Registered Client | Dashboard</title>
    <script type="text/javascript">
        $(document).ready(function() {
        	// var oTable = $("#dataTable").dataTable({
            //     "sAjaxSource"	: "${pageContext.request.contextPath}/dashboard/main/search",
            //     "sServerMethod"	: "POST",
            //     "bProcessing"	: false,
            //     "fnServerData"	: function (sSource, aoData, fnCallback) {
			// 		var searchDateStart 	= $.trim($("#dateRangeStart").val());
			// 		var searchDateEnd 	= $.trim($("#dateRangeEnd").val());
            //     	var insti	= $.trim($("#instiCode").val());
			// 		var branch 	= $.trim($("#branchCode").val());
			// 		var unit 	= $.trim($("#unitCode").val());
			// 		var center 	= $.trim($("#centerCode").val());
					
            //         aoData.push({ "name": "startDate", "value": searchDateStart});
            //         aoData.push({ "name": "endDate", "value": searchDateEnd });
            //         aoData.push({ "name": "insti", "value": insti });
			// 		aoData.push({ "name": "branch", "value": branch });
			// 		aoData.push({ "name": "unit", "value": unit });
			// 		aoData.push({ "name": "center", "value": center });
            //         jQuery.ajax({
            //             "type"		: "GET",
            //             "url"		: sSource,
            //             "data"		: aoData,
            //             "success"	: fnCallback
            //         });
            //     },
            //     "aoColumns": [
            //         { "mDataProp": "createdDate" },
            //         { "mDataProp": "accountNumber" },
			// 		{ "mDataProp": "dateBirth" },
			// 		{ "mDataProp": "mobileNumber" },
			// 		{ "mDataProp": "clientType" },
			// 		{ "mDataProp": "deviceId" },
			// 		{ "mDataProp": "deviceModel" },
			// 		{ "mDataProp": "errorMessage" }
            //     ]
            // });
			
			var resp;

			function listClients(){	
        	   	var date = new Date();
				today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
				//$( '#dateRangeStart , #dateRangeEnd' ).datepicker( 'setDate', today )
				var dtStart = $.trim($("#dateRangeStart").val());
		        var dtEnd = $.trim($("#dateRangeEnd").val());
				var insti = $("#instiCode").val();
		        var branch = $("#branchCode").val();
				var unit = $("#unitCode").val();
				var center = $("#centerCode").val();
				console.log("Data Start: " + dtStart);
				console.log("Data End: " + dtEnd);

        	   $.ajax({
					type: 'POST',
					url: '${pageContext.request.contextPath}/dashboard/main/search',
					async : false,
					data:{
						dateStart:dtStart,
						dateEnd:dtEnd,
						insti:insti,
						branch:branch,
						unit:unit,
						center:center

					},
					beforeSend	: function() {
		            	App.blockUI({target:'.wrapper', boxed: true});
			                $("#loader").show();
		            },
					success: function(data){
						resp = JSON.stringify(data[0].id);

						var agentMemCount = 0;
						var regularMemCount = 0;

						for(let i = 0; i < data.length; i++){
							if(data[i].agentFeature == 1){
								agentMemCount++;
								console.log("Agent:" + agentMemCount);
							}

							if(data[i].agentFeature == 0){
								regularMemCount++;
								console.log("Regular:" + regularMemCount);
							}

						}
						//buildTable(data)
						console.log("Agent:" + agentMemCount);
						console.log("Regular:" + regularMemCount);
						SetupMemberGraph(agentMemCount,regularMemCount);

					},
					error: function(data){
						console.log("Error: "+ data);
					},
					complete: function() {
	                    setTimeout(function(){
	                        App.unblockUI('.wrapper');
	                        $("#loader").hide();
	                    }, 1000);
	                }
		       
           		})
           }

		   function SetupMemberGraph(agent,regular){
				const labels = ["Agent", "Regular"]
				const data = {
					labels: labels,
					datasets: [{
						label: 'Total Number of Registered Member: ' + parseInt(agent)+parseInt(regular),
						data: [agent, regular],
						backgroundColor: [
							'rgba(255, 205, 86, 0.2)',
							'rgba(75, 192, 192, 0.2)'
						],
						borderColor: [
							'rgb(255, 205, 86)',
							'rgb(75, 192, 192)'
						],
						borderWidth: 5
					}]
				};

				ConfigGraph(data,true);
		   }

		   
		   function ConfigGraph(data,beginAtZero){
				const config = {
					type: 'bar',
					data: data,
					options: {
						scales: {
							y: {
								beginAtZero: beginAtZero
							}
						}
					},
				};

				document.querySelector("#chart").innerHTML = '<canvas id="canvasBarChart"></canvas>';

				var myChart = new Chart(
					document.getElementById('canvasBarChart'),
					config
				);
		   }

		  



		   // Format: yyyy-mm-dd
			 function formatDate1(date) {
			     var d = new Date(date),
			         month = '' + (d.getMonth() + 1),
			         day = '' + d.getDate(),
			         year = d.getFullYear();

			     if (month.length < 2) month = '0' + month;
			     if (day.length < 2) day = '0' + day;

			     return [year, month, day].join('-');
			 }
            
        	$("#btn-search").click(function(){
                //oTable.fnDraw();
				listClients();
            });
			
            $("#btn-reset").click(function(){
            		$("#branchCode").val("");
                    $("#branchDesc").val("");
                	$("#unitCode").val("");
                    $("#unitDesc").val("");
                    $("#centerCode").val("");
                    $("#centerDesc").val("");
					$("#instiCode").val("");
                    $("#instiDesc").val("");
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
		            <li>Dashboard</li>
		            <li class="active"><a href="${pageContext.request.contextPath}/dashboard/registered-client/">Registered Client</a></li>
		        </ul>
		        <h3>
		           <i class="fa fa-cube"></i> Registered Client
		       	</h3><em>Dashboard</em>
	    	</div>
	    </div>
	</div>

	<!-- main -->
    <div class="main-content">
       
        <div class="widget widget-table">
            <div class="widget-header">
                <h3><i class="fa fa-table"></i>Registered Client</h3>
            </div>
            <div class="widget-content" style="padding:0px;">
				<div id="reportRegisteredClient">
					<iframe width="100%" height="800" src="${REGISTERED_CLIENT_REPORT}" frameborder="0" style="border:0" allowfullscreen></iframe>
				</div>
            </div>
        </div>
        <!-- END SHOW HIDE COLUMNS DATA TABLE -->
    </div>
    <!-- /main-content -->
</div>
<!-- /main -->
</body>
</html>