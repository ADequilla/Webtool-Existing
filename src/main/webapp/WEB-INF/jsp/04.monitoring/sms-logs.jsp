<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Sms Logs | Monitoring</title>
<script type="text/javascript">
        $(document).ready(function() {
        	 //$("#modal-question").modal("show");
        	var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/monitoring/sms/search",
                "sServerMethod"	: "GET",
                "bProcessing"	: false,
                "fnServerData"	: function (sSource, aoData, fnCallback) {
                	var cid 				= $.trim($("#searchCid").val());
					var searchDateStart 	= $.trim($("#searchDateStart").val());
					var searchDateEnd 	= $.trim($("#searchDateEnd").val());
                	var mobileNo	= $.trim($("#searchMobileNo").val());
					var msgStatus 	= $.trim($("#searchMsgStatus").val());
					var searchMt 	= $.trim($("#searchMt").val());
					
                    aoData.push({ "name": "cid", "value": cid });
                    aoData.push({ "name": "searchDateStart", "value": searchDateStart});
                    aoData.push({ "name": "searchDateEnd", "value": searchDateEnd });
                    aoData.push({ "name": "mobileNo", "value": mobileNo });
					aoData.push({ "name": "msgStatus", "value": msgStatus });
					aoData.push({ "name": "searchMt", "value": searchMt });
                    jQuery.ajax({
                        "type"		: "GET",
                        "url"		: sSource,
                        "data"		: aoData,
                        "success"	: fnCallback
                    });
                },
                "aoColumns": [
					{ "mDataProp": "msgId" },
                    { "mDataProp": "sendDate" },
					{ "mDataProp": "mobileNumber" },
					{ "mDataProp": "cid" },
					{ "mDataProp": "name" },
					{ "mDataProp": "messageCommand" },
					{ "mDataProp": "messageType" },
					{ "mDataProp": "status" }	
                ],
				"aoColumnDefs" : [
					{	
						className	: "text-center",
		            	"mRender"	: SmsLogStatusFormatter,
		            	"aTargets"	: [7]
					}
                ]

				
				
            });

            
        	$("#btn-search").click(function(){
                oTable.fnDraw();
            });
			
            $("#btn-reset").click(function(){
            	$('#searchMobileNo').val("");
				$('#searchMsgStatus').val("");
				$('#searchCid').val("")
				$('#searchDateStart').val("")
				$('#searchDateEnd').val("")
				$('#searchMt').val("")
            	oTable.fnDraw();
            });

		});
		// function listTransaction(){	
        // 	   	var date = new Date();
		// 		today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
		// 		//$( '#dateRangeStart , #dateRangeEnd' ).datepicker( 'setDate', today )
		// 		var dtStart = $.trim($("#dateRangeStart").val());
		//         var dtEnd = $.trim($("#dateRangeEnd").val());
        // 	   $.ajax({
		// 			type: 'POST',
		// 			url: '${pageContext.request.contextPath}/monitoring/sms/search',
		// 			async : true,
		// 			data:{
		// 				dtStart:formatDate1(dtStart),
		// 				dtEnd:formatDate1(dtEnd)
		// 			},
		// 			beforeSend	: function() {
		//             	App.blockUI({target:'.wrapper', boxed: true});
		// 	                $("#loader").show();
		//             },
		// 			success: function(data){
						
		// 				buildTable(data)
		// 				console.log("Data: "+ data);
		// 			},
		// 			error: function(data){
		// 				console.log("Error: "+ data);
		// 			},
		// 			complete: function() {
	    //                 setTimeout(function(){
	    //                     App.unblockUI('.wrapper');
	    //                     $("#loader").hide();
	    //                 }, 1000);
	    //             }
		       
        //    		})
        // }

// 		function buildTable(arr){
				
// 		        var table = $('#tableTransaction').DataTable();
// 		    	table.destroy();
		    	
		    	
// 		    	var list = "";

// 				for (var i = 0; i < arr.length; i++){
// 					var sn = i + 1;
// 					list = list + "<tr>\
// 									<td>"+arr[i].id+"</td>\
// 									<td>"+arr[i].smsFrom+"</td>\
// 									<td>"+arr[i].smsTo+"</td>\
// 									<td>"+arr[i].msg+"</td>\
// 									<td>"+arr[i].appID+"</td>\
// 									<td>"+arr[i].transID+"</td>\
// 									<td>"+arr[i].appOrigin+"</td>\
// 									<td>"+formatDate($.trim(arr[i].dateTimeLogs))+"</td>\
// 									<td>"+arr[i].action+"</td>\
// 									<td>"+arr[i].remarks+"</td>\
									
// 							  </tr>";
				
// 				}
				
// 				$('.td-num').each(function(){
//                     text = $(this).text();
//                     console.log(text);
//                     if(text == "0.00" || text == "0"){
//                         $(this).text("-")
//                     }else{
//                         $(this).number(true, 2 )
//                     }
//                 });
				
// 				$('#txtNetCash').number( true, 2 );
				
				
				
// 				$("#tableTransactionBody").html(list);

// 				$('#tableTransaction').DataTable({
// 					"processing" : true,
// 					"serverSide":false,
// 					"paging" : true,
// 					"destroy" : true,
// 					"responsive" : true,
// 					"scrollY" : "400px",
// 					"info" : false,
// 					'iDisplayLength' : 10,
// 					"search":'',
// 					"searching" : true,
// 					"scrollX": true,
// 					"columnDefs":
// 						[
// 						    {
// 						        targets: 12,
// 						        render: $.fn.dataTable.render.number(',', '.', 2, '')
// 						    },
// 						    {
// 						        targets: 13,
// 						        render: $.fn.dataTable.render.number(',', '.', 2, '')
// 						    }
// 						],
// 					dom: 'Bfrtpi',
// 					buttons: [ 
// 				        {
// 				            extend: 'pdfHtml5',
// 				            orientation: 'landscape',
// 				            pageSize: 'A4',
// 				            style: 'fullWidth',
// 				            title: 'K2C Transaction',
// 				            filename: 'K2C Transaction ('+ formatDate2($( "#dateRangeStart" ).val()) +' - '+formatDate2($( "#dateRangeEnd" ).val())+') - ' + usr ,
// 				            pageMargins: [ 150, 150, 150, 150 ],
// 				            text: 'PDF',
// 				            key: {
// 				                key: 'e',
// 				                altKey: false
// 				            },
// 				            download: 'download',
// 				            exportOptions: {
// 				                modifier: {
// 				                    style: 'fullWidth',
// 				                    pageMargins: [ 150, 150, 150, 150 ],
// 				                    margin: [ 0, 0, 0, 120 ],
// 				                    alignment: 'center'
// 				                },
// 				                content: [{style: 'fullWidth' }],
// 				                styles: {
// 				                    fullWidth: {
// 				                        fontSize: 18,
// 				                        bold: true,
// 				                        alignment: 'center',
// 				                        margin: [0,190,0,80]		
// 				                    },
// 				                    subheader: {
// 				                        fontSize: 14
// 				                    },
// 				                    superMargin: {
// 				                        margin: [20, 0, 40, 0],
// 				                        fontSize: 15,
// 				                    }
// 				                },
				            
// 				                columns: [0,1,2,3,4,5,6,7,8,9,10,11,12,13], //r.broj kolone koja se stampa u PDF      
// 				                columnGap: 10 // optional space between columns
// 				            }
// 				        },
// 				        {
// 				            extend: 'excelHtml5',
// 				            orientation: 'landscape',
// 				            pageSize: 'A4',
// 				            style: 'fullWidth',
// 				            title: 'K2C Transaction',
// 				            filename: 'K2C Transaction ('+ formatDate2($( "#dateRangeStart" ).val()) +' - '+formatDate2($( "#dateRangeEnd" ).val())+') - ' + usr ,
// 				            pageMargins: [ 150, 150, 150, 150 ],
// 				            text: 'Excel',
// 				            key: {
// 				                key: 'e',
// 				                altKey: false
// 				            },
// 				            download: 'download',
// 				            exportOptions: {
// 				                modifier: {
// 				                    style: 'fullWidth',
// 				                    pageMargins: [ 150, 150, 150, 150 ],
// 				                    margin: [ 0, 0, 0, 120 ],
// 				                    alignment: 'center'
// 				                },
// 				                content: [{style: 'fullWidth' }],
// 				                styles: {
// 				                    fullWidth: {
// 				                        fontSize: 18,
// 				                        bold: true,
// 				                        alignment: 'center',
// 				                        margin: [0,190,0,80]		
// 				                    },
// 				                    subheader: {
// 				                        fontSize: 14
// 				                    },
// 				                    superMargin: {
// 				                        margin: [20, 0, 40, 0],
// 				                        fontSize: 15,
// 				                    }
// 				                },
				            
// 				                columns: [0,1,2,3,4,5,6,7,8,9,10,11,12,13], //r.broj kolone koja se stampa u PDF      
// 				                columnGap: 10 // optional space between columns
// 				            },
// 				            customize: function( xlsx ) {
// 				            	// see built in styles here: https://datatables.net/reference/button/excelHtml5
// 				            	// take a look at "buttons.html5.js", search for "xl/styles.xml"
// 				            	//styleSheet.childNodes[0].childNodes[0] ==> number formats  <numFmts count="6"> </numFmts>
// 				            	//styleSheet.childNodes[0].childNodes[1] ==> fonts           <fonts count="5" x14ac:knownFonts="1"> </fonts>
// 				            	//styleSheet.childNodes[0].childNodes[2] ==> fills           <fills count="6"> </fills>
// 				            	//styleSheet.childNodes[0].childNodes[3] ==> borders         <borders count="2"> </borders>
// 				            	//styleSheet.childNodes[0].childNodes[4] ==> cell style xfs  <cellStyleXfs count="1"> </cellStyleXfs>
// 				            	//styleSheet.childNodes[0].childNodes[5] ==> cell xfs        <cellXfs count="67"> </cellXfs>
// 				            	//on the last line we have the 67 currently built in styles (0 - 66), see link above
				            	 
// 				            	    var sSh = xlsx.xl['styles.xml'];
// 				            	    var lastXfIndex = $('cellXfs xf', sSh).length - 1;
				            	 
// 				            	    var n1 = '<numFmt formatCode="" numFmtId="300"/>';
// 				            	    var s1 = '<xf numFmtId="300" fontId="0" fillId="0" borderId="0" applyFont="1" applyFill="2" applyBorder="1" xfId="0" applyNumberFormat="1"/>';
// 				            	    var s2 = '<xf numFmtId="0" fontId="2" fillId="0" borderId="0" applyFont="1" applyFill="6" applyBorder="1" xfId="0" applyAlignment="1">'+
// 				            	                '<alignment horizontal="center"/></xf>';
// 				            	    var s3 = '<xf numFmtId="0" fontId="2" fillId="0" borderId="0" applyFont="1" applyFill="6" applyBorder="1" xfId="0" applyAlignment="1">'+
// 				                    		 '<alignment horizontal="right"/></xf>';
	                                        
// 				            	    sSh.childNodes[0].childNodes[0].innerHTML += n1;
// 				            	    sSh.childNodes[0].childNodes[5].innerHTML += s1 + s2 + s3;
				            	 
// 				            	    var fourDecPlaces = lastXfIndex + 1;
// 				            	    var greyBoldCentered = lastXfIndex + 2;
// 				            	    var BoldLeft = lastXfIndex + 3;
				            	 
// 				            	    var sheet = xlsx.xl.worksheets['sheet1.xml'];
// 				            	    $('row c[r^="A"]', sheet).attr( 's', '50' );
// 				            	    $('row c[r^="B"]', sheet).attr( 's', '50' );
// 				            	    $('row c[r^="C"]', sheet).attr( 's', '50' );
// 				            	    $('row c[r^="D"]', sheet).attr( 's', '50' );
// 				            	    $('row c[r^="E"]', sheet).attr( 's', '51' );	
// 				            	    $('row c[r^="F"]', sheet).attr( 's', '50' );
// 				            	    $('row c[r^="G"]', sheet).attr( 's', '51' );
// 				            	    $('row c[r^="H"]', sheet).attr( 's', fourDecPlaces );  //% 4 decimal places, as added above
// 				            	    $('row c[r^="J"]', sheet).attr( 's', '67');
// 				            	    $('row c[r^="K"]', sheet).attr( 's', '64' );
// 				            	    $('row c[r^="L"]', sheet).attr( 's', '64' );
// 				            	    $('row c[r^="M"]', sheet).attr( 's', '64' );
// 				            	    $('row c[r^="N"]', sheet).attr( 's', '64' );
// //				            	                $('row c', sheet).attr( 's', '25' ); //for all rows
// 				            	    $('row:eq(0) c', sheet).attr( 's', greyBoldCentered );  //grey background bold and centered, as added above
// 				            	    $('row:eq(1) c', sheet).attr( 's', greyBoldCentered );  //grey background bold
				            	    
				            	    
// 				            	    sumDebit = 0;
// 				            	    sumCredit = 0;
// 				            	    lastRow = 2;

// 				                    // read each row
// 				                    //Sum Debit
// 				                    $('row c[r^="M"]', sheet).each(function() {
// 										  lastRow++;
// 					                      var value = $(this).text();
// 					                      sumDebit += Number(value.replace(/[^0-9\.-]+/g, ""));
// 				                    });
				                    
// 				                    //Sum Credit
// 				                    $('row c[r^="N"]', sheet).each(function() {
// 					                      var value = $(this).text();
// 					                      sumCredit += Number(value.replace(/[^0-9\.-]+/g, ""));
// 					                });
				                    

// 				                    // Create our number formatter.
// 				                    var formatter = new Intl.NumberFormat('en-US', {
// 				                      //style: 'currency',
// 				                      //currency: 'PHP',

// 				                      // the default value for minimumFractionDigits depends on the currency
// 				                      // and is usually already 2
// 				                      minimumFractionDigits: 2,
// 				                    });

// 				                    function addTotalDebit(index, data) {
// 				                      msg = '<row r="' + index + '">';
// 				                      for (i = 0; i < data.length; i++) {
// 				                        var key = data[i].k;
// 				                        var value = data[i].v;
// 				                        msg += '<c t="inlineStr" r="' + 'M' + lastRow + '" s="'+BoldLeft+'">';
// 				                        msg += '<is>';
// 				                        msg += '<t>' + formatter.format(sumDebit) + '</t>';
// 				                        msg += '</is>';
// 				                        msg += '</c>';
// 				                      }
// 				                      msg += '</row>';
// 				                      return msg;
// 				                    }
				                    
// 				                    function addTotalCredit(index, data) {
// 					                      msg = '<row r="' + index + '">';
// 					                      console.log("data: "+data.length);
// 					                      for (i = 0; i < data.length; i++) {
// 					                        var key = data[i].k;
// 					                        var value = data[i].v;
// 					                        msg += '<c t="inlineStr" r="' + 'N' + lastRow + '" s="'+BoldLeft+'">';
// 					                        msg += '<is>';
// 					                        msg += '<t>' + formatter.format(sumCredit) + '</t>';
// 					                        msg += '</is>';
// 					                        msg += '</c>';
// 					                      }
// 					                      msg += '</row>';
// 					                      return msg;
// 					                    }

// 				                    //insert
// 				                    var r1 = addTotalDebit(1, [{
// 				                      k: 'A',
// 				                      v: 'ColA'
// 				                    }]);
				                    
// 				                  //insert
// 				                    var r2 = addTotalCredit(1, [{
// 				                      k: 'A',
// 				                      v: 'ColA'
// 				                    }]);

// 				                    sheet.childNodes[0].childNodes[1].innerHTML = r1 + sheet.childNodes[0].childNodes[1].innerHTML;
// 				                    sheet.childNodes[0].childNodes[1].innerHTML = r2 + sheet.childNodes[0].childNodes[1].innerHTML;
// 				            	},
// 				            	customizeData: function(data) {
// 				                    for(var i = 0; i < data.body.length; i++) {
// 				                      for(var j = 0; j < data.body[i].length; j++) {
// 				                        data.body[i][8] = '\u200C' + data.body[i][8];
// 				                      }
// 				                    }
// 				                 }
				            
				            
// 				        }
				        
				       
				    
// 				      ],
					

// 				});
				
				

// 				var tables = $('#tableTransaction').DataTable();
// 				tables.columns.adjust().draw();
				
// 				function createCellPos( n ){
// 				    var ordA = 'A'.charCodeAt(0);
// 				    var ordZ = 'Z'.charCodeAt(0);
// 				    var len = ordZ - ordA + 1;
// 				    var s = "";
				 
// 				    while( n >= 0 ) {
// 				        s = String.fromCharCode(n % len + ordA) + s;
// 				        n = Math.floor(n / len) - 1;
// 				    }
				 
// 				    return s;
// 				}
		    	
// 				/* $.ajax({
// 					type: 'POST',
// 					url: '${pageContext.request.contextPath}/report/${REPORT_MENU}/search/k2c-api',
// 					async : true,
// 					data:{
// 						dtStart:formatDate(dtStart),
// 						dtEnd:formatDate(dtEnd)
// 					},
// 					success: function(response){
// 						//arr = response
// 						var list = "";

// 						for (var i = 0; i < arr.length; i++){
// 							list = list + `<tr>
// 											<td>`+arr[i].area+`</td>
// 											<td>`+arr[i].unit+`</td>
// 											<td>`+arr[i].center+`</td>
// 											<td>`+arr[i].cid+`</td>
// 											<td>`+arr[i].clientName+`</td>
// 											<td>`+arr[i].accnt+`</td>
// 											<td>`+arr[i].accntName+`</td>
// 											<td>`+formatDate($.trim(arr[i].trnDate))+`</td>
// 											<td>`+arr[i].reference+`</td>
// 											<td>`+arr[i].debit+`</td>
// 											<td>`+arr[i].credit+`</td>
// 									  </tr>`
						
// 						}
						
// 						$("#tableTransactionBody").html(list);

// 						$('#tableTransaction').DataTable({
// 							"processing" : true,
// 							"paging" : true,
// 							"destroy" : true,
// 							"responsive" : true,
// 							"scrollY" : "300px",
// 							"info" : false,
// 							'iDisplayLength' : 10,
// 							"columnDefs" : [ {
// 								"searchable" : false,
// 							} ],


// 						});

// 						var table = $('#tableTransaction').DataTable();
// 						table.columns.adjust().draw();
						
// 					},
// 					error : function(response){
// 						console.log("Error: "+ response);
// 					},
// 				}) */
				
// 			}

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
							href="${pageContext.request.contextPath}/monitoring/sms/">Sms
								Logs</a></li>
					</ul>
					<h3>
						<i class="fa fa-envelope"></i> Sms Logs
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
						<div class="col-sm-6">
							<input type="text" placeholder="CID" id="searchCid"
								class="form-control">
						</div>
					</div>

					<div class="row">
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchDateStart" style="cursor: pointer;" type="text"
									class="form-control startdatepicker"
									placeholder="Enrolled Date Start"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="input-group">
								<input id="searchDateEnd" style="cursor: pointer;" type="text"
									class="form-control enddatepicker"
									placeholder="Enrolled Date End"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<input type="text" placeholder="Mobile Number"
								id="searchMobileNo" class="form-control">
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<select id="searchMt" class="form-control">
								<option value="">-- Message Type --</option>
								<!-- <c:forEach items="${listMessageType}" var="lookup">
								<option value="${lookup.value}">${lookup.description}</option>
						    </c:forEach> -->
								<option value="Enrollment">Enrollment</option>
								<option value="Cash In">Cash In</option>
								<option value="Agent Assisted Payment">Agent Assisted
									Payment</option>
								<option value="Reset Password">Reset Password</option>
								<option value="Reset MPIN">Reset MPIN</option>
								<option value="Reset Credential">Reset Credential</option>
								<option value="Remittance">Remittance</option>
								<option value="Bill Payment">Bill Payment</option>
								<option value="Forgot Password - Mobcol">Forgot
									Password - Mobcol</option>
								<option value="Forgot MPIN - Mobcol">Forgot MPIN -
									Mobcol</option>
							</select>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<select id="searchMsgStatus" class="form-control">
								<option value="">-- SMS Status --</option>
								<c:forEach items="${listSmsStatus}" var="lookup">
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
					</div>
				</div>
			</div>
			<!-- END SEARCH DATA TABLE -->

			<!-- SHOW HIDE COLUMNS DATA TABLE -->
			<div class="widget widget-table">
				<div class="widget-header">
					<h3>
						<i class="fa fa-table"></i> List of Sms Logs
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>ID</th>
								<th>Date Time</th>
								<th>Mobile Number</th>
								<th>CID</th>
								<th>Name</th>
								<th>Message</th>
								<th>Message Type</th>
								<th>Status</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<!-- END SHOW HIDE COLUMNS DATA TABLE -->
		</div>
		<!-- /main-content -->
		<div class="modal fade" id="modal-question" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header text-center"></div>

					<div class="modal-body">
						<p>Your activity on this module will be logged.</p>
					</div>
					<div class="modal-footer text-right">
						<a class="btn btn-default" type="button" data-dismiss="modal">OK</a>
						<!-- 	                <a id="btn-reset-password" class="btn btn-danger" type="button" data-dismiss="modal"><i class="fa fa-minus-circle"></i> Reset Password</a>
 -->
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- /main -->
</body>
</html>
