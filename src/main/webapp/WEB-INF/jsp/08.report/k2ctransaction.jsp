

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>${REPORT_TITLE}| Report</title>

<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/buttons/1.4.2/css/buttons.dataTables.css" />

<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/pdfmake.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/vfs_fonts.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/1.4.2/js/dataTables.buttons.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/1.4.2/js/buttons.flash.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/1.4.2/js/buttons.html5.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/1.4.2/js/buttons.print.js"></script>
<script src="/Resources/js/plugins/jquery.number.js"></script>
<script type="text/javascript">
	var usr = $("#uname").html();
	var arr = [];        
			//var date = new Date();
			/* var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
			$( '#dateRangeStart , #dateRangeEnd' ).datepicker( 'setDate', today )
			var dtStart = $.trim($("#dateRangeStart").val());
		    var dtEnd = $.trim($("#dateRangeEnd").val()); */
            
			buildTable(arr);
			
			$( "#dateRangeStart" ).change(function(_e) {
		        var val = $(this).val();

		        dtStart = formatDate2(val);
		        console.log(dtStart);
		    });
			
			$( "#dateRangeEnd" ).change(function(_e) {
		        var val = $(this).val();

		        dtEnd = formatDate2(val);
		        console.log(dtEnd);
		    });
			
           
           function listTransaction(){	
        	   	var date = new Date();
				today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
				//$( '#dateRangeStart , #dateRangeEnd' ).datepicker( 'setDate', today )
				var dtStart = $.trim($("#dateRangeStart").val());
		        var dtEnd = $.trim($("#dateRangeEnd").val());
        	   $.ajax({
					type: 'POST',
					url: '${pageContext.request.contextPath}/report/${REPORT_MENU}/search/core-api',
					async : true,
					data:{
						dtStart:formatDate1(dtStart),
						dtEnd:formatDate1(dtEnd)
					},
					beforeSend	: function() {
		            	App.blockUI({target:'.wrapper', boxed: true});
			                $("#loader").show();
		            },
					success: function(data){
						
						buildTable(data)
						console.log("Data: "+ data);
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
		
			function buildTable(arr){
				
		        var table = $('#tableTransaction').DataTable();
		    	table.destroy();
				
		    	
				console.log("Arr :\n" + arr);

		    	var list = "";

				for (var i = 0; i < arr.length; i++){
					var sn = i + 1;
					list = list + "<tr>\
									<td>"+sn+"</td>\
									<td>"+arr[i].area+"</td>\
									<td>"+arr[i].unit+"</td>\
									<td>"+arr[i].center+"</td>\
									<td>"+arr[i].cid+"</td>\
									<td>"+arr[i].clientName+"</td>\
									<td>"+arr[i].accnt+"</td>\
									<td>"+arr[i].accntName+"</td>\
									<td>"+formatDate($.trim(arr[i].trndate))+"</td>\
									<td>"+formatDate3($.trim(arr[i].sysTime))+"</td>\
									<td>"+arr[i].reference+"</td>\
									<td>"+arr[i].trndesc+"</td>\
									<td>Success</td>\
									<td class='text-right td-num'>"+arr[i].debit+"</td>\
									<td class='text-right td-num'>"+arr[i].credit+"</td>\
							  </tr>";
				
				}
				
				$('.td-num').each(function(){
                    text = $(this).text();
                    console.log(text);
                    if(text == "0.00" || text == "0"){
                        $(this).text("-")
                    }else{
                        $(this).number(true, 2 )
                    }
                });
				
				$('#txtNetCash').number( true, 2 );
				
				
				
				$("#tableTransactionBody").html(list);

				$('#tableTransaction').DataTable({
					"processing" : false,
					"serverSide":false,
					"paging" : true,
					"destroy" : true,
					"responsive" : true,
					"scrollY" : "400px",
					"info" : false,
					'iDisplayLength' : 50,
					"search":'',
					"searching" : true,
					"scrollX": true,
					"deferRender": true,
					"columnDefs":
						[
						    {
						        targets: 13,
						        render: $.fn.dataTable.render.number(',', '.', 2, '')
						    },
						    {
						        targets: 14,
						        render: $.fn.dataTable.render.number(',', '.', 2, '')
						    }
						],
					dom: 'Bfrtpi',
					buttons: [ 
				        {
				            extend: 'pdfHtml5',
				            orientation: 'landscape',
				            pageSize: 'A4',
				            style: 'fullWidth',
				            title: 'konek2CARD PLUS Transaction',
				            filename: 'konek2CARD PLUS Transaction ('+ formatDate2($( "#dateRangeStart" ).val()) +' - '+formatDate2($( "#dateRangeEnd" ).val())+') - ' + usr ,
				            pageMargins: [ 150, 150, 150, 150 ],
				            text: 'PDF',
				            key: {
				                key: 'e',
				                altKey: false
				            },
				            download: 'download',
				            exportOptions: {
				                modifier: {
				                    style: 'fullWidth',
				                    pageMargins: [ 150, 150, 150, 150 ],
				                    margin: [ 0, 0, 0, 120 ],
				                    alignment: 'center'
				                },
				                content: [{style: 'fullWidth' }],
				                styles: {
				                    fullWidth: {
				                        fontSize: 18,
				                        bold: true,
				                        alignment: 'center',
				                        margin: [0,190,0,80]		
				                    },
				                    subheader: {
				                        fontSize: 14
				                    },
				                    superMargin: {
				                        margin: [20, 0, 40, 0],
				                        fontSize: 15,
				                    }
				                },
				            
				                columns: [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14], //r.broj kolone koja se stampa u PDF      
				                columnGap: 10 // optional space between columns
				            }
				        },
				        {
				            extend: 'excelHtml5',
				            orientation: 'landscape',
				            pageSize: 'A4',
				            style: 'fullWidth',
				            title: 'konek2CARD PLUS Transaction',
				            filename: 'konek2CARD PLUS Transaction ('+ formatDate2($( "#dateRangeStart" ).val()) +' - '+formatDate2($( "#dateRangeEnd" ).val())+') - ' + usr ,
				            pageMargins: [ 150, 150, 150, 150 ],
				            text: 'Excel',
				            key: {
				                key: 'e',
				                altKey: false
				            },
				            download: 'download',
				            exportOptions: {
				                modifier: {
				                    style: 'fullWidth',
				                    pageMargins: [ 150, 150, 150, 150 ],
				                    margin: [ 0, 0, 0, 120 ],
				                    alignment: 'center'
				                },
				                content: [{style: 'fullWidth' }],
				                styles: {
				                    fullWidth: {
				                        fontSize: 18,
				                        bold: true,
				                        alignment: 'center',
				                        margin: [0,190,0,80]		
				                    },
				                    subheader: {
				                        fontSize: 14
				                    },
				                    superMargin: {
				                        margin: [20, 0, 40, 0],
				                        fontSize: 15,
				                    }
				                },
				            
				                columns: [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14], //r.broj kolone koja se stampa u PDF      
				                columnGap: 10 // optional space between columns
				            },
				            customize: function( xlsx ) {
				            	// see built in styles here: https://datatables.net/reference/button/excelHtml5
				            	// take a look at "buttons.html5.js", search for "xl/styles.xml"
				            	//styleSheet.childNodes[0].childNodes[0] ==> number formats  <numFmts count="6"> </numFmts>
				            	//styleSheet.childNodes[0].childNodes[1] ==> fonts           <fonts count="5" x14ac:knownFonts="1"> </fonts>
				            	//styleSheet.childNodes[0].childNodes[2] ==> fills           <fills count="6"> </fills>
				            	//styleSheet.childNodes[0].childNodes[3] ==> borders         <borders count="2"> </borders>
				            	//styleSheet.childNodes[0].childNodes[4] ==> cell style xfs  <cellStyleXfs count="1"> </cellStyleXfs>
				            	//styleSheet.childNodes[0].childNodes[5] ==> cell xfs        <cellXfs count="67"> </cellXfs>
				            	//on the last line we have the 67 currently built in styles (0 - 66), see link above
				            	 
				            	    var sSh = xlsx.xl['styles.xml'];
				            	    var lastXfIndex = $('cellXfs xf', sSh).length - 1;
				            	 
				            	    var n1 = '<numFmt formatCode="" numFmtId="300"/>';
				            	    var s1 = '<xf numFmtId="300" fontId="0" fillId="0" borderId="0" applyFont="1" applyFill="2" applyBorder="1" xfId="0" applyNumberFormat="1"/>';
				            	    var s2 = '<xf numFmtId="0" fontId="2" fillId="0" borderId="0" applyFont="1" applyFill="6" applyBorder="1" xfId="0" applyAlignment="1">'+
				            	                '<alignment horizontal="center"/></xf>';
				            	    var s3 = '<xf numFmtId="0" fontId="2" fillId="0" borderId="0" applyFont="1" applyFill="6" applyBorder="1" xfId="0" applyAlignment="1">'+
				                    		 '<alignment horizontal="right"/></xf>';
	                                        
				            	    sSh.childNodes[0].childNodes[0].innerHTML += n1;
				            	    sSh.childNodes[0].childNodes[5].innerHTML += s1 + s2 + s3;
				            	 
				            	    var fourDecPlaces = lastXfIndex + 1;
				            	    var greyBoldCentered = lastXfIndex + 2;
				            	    var BoldLeft = lastXfIndex + 3;
				            	 
				            	    var sheet = xlsx.xl.worksheets['sheet1.xml'];
				            	    $('row c[r^="A"]', sheet).attr( 's', '50' );
				            	    $('row c[r^="B"]', sheet).attr( 's', '50' );
				            	    $('row c[r^="C"]', sheet).attr( 's', '50' );
				            	    $('row c[r^="D"]', sheet).attr( 's', '50' );
				            	    $('row c[r^="E"]', sheet).attr( 's', '51' );	
				            	    $('row c[r^="F"]', sheet).attr( 's', '50' );
				            	    $('row c[r^="G"]', sheet).attr( 's', '51' );
				            	    $('row c[r^="H"]', sheet).attr( 's', fourDecPlaces );  //% 4 decimal places, as added above
				            	    $('row c[r^="K"]', sheet).attr( 's', '67');
				            	    $('row c[r^="L"]', sheet).attr( 's', '64' );
				            	    $('row c[r^="M"]', sheet).attr( 's', '64' );
				            	    $('row c[r^="N"]', sheet).attr( 's', '64' );
				            	    $('row c[r^="O"]', sheet).attr( 's', '64' );
//				            	                $('row c', sheet).attr( 's', '25' ); //for all rows
				            	    $('row:eq(0) c', sheet).attr( 's', greyBoldCentered );  //grey background bold and centered, as added above
				            	    $('row:eq(1) c', sheet).attr( 's', greyBoldCentered );  //grey background bold
				            	    
				            	    
				            	    sumDebit = 0;
				            	    sumCredit = 0;
				            	    lastRow = 2;

				                    // read each row
				                    //Sum Debit
				                    $('row c[r^="N"]', sheet).each(function() {
										  lastRow++;
					                      var value = $(this).text();
					                      sumDebit += Number(value.replace(/[^0-9\.-]+/g, ""));
				                    });
				                    
				                    //Sum Credit
				                    $('row c[r^="O"]', sheet).each(function() {
					                      var value = $(this).text();
					                      sumCredit += Number(value.replace(/[^0-9\.-]+/g, ""));
					                });
				                    

				                    // Create our number formatter.
				                    var formatter = new Intl.NumberFormat('en-US', {
				                      //style: 'currency',
				                      //currency: 'PHP',

				                      // the default value for minimumFractionDigits depends on the currency
				                      // and is usually already 2
				                      minimumFractionDigits: 2,
				                    });

				                    function addTotalDebit(index, data) {
				                      msg = '<row r="' + index + '">';
				                      for (i = 0; i < data.length; i++) {
				                        var key = data[i].k;
				                        var value = data[i].v;
				                        msg += '<c t="inlineStr" r="' + 'N' + lastRow + '" s="'+BoldLeft+'">';
				                        msg += '<is>';
				                        msg += '<t>' + formatter.format(sumDebit) + '</t>';
				                        msg += '</is>';
				                        msg += '</c>';
				                      }
				                      msg += '</row>';
				                      return msg;
				                    }
				                    
				                    function addTotalCredit(index, data) {
					                      msg = '<row r="' + index + '">';
					                      console.log("data: "+data.length);
					                      for (i = 0; i < data.length; i++) {
					                        var key = data[i].k;
					                        var value = data[i].v;
					                        msg += '<c t="inlineStr" r="' + 'O' + lastRow + '" s="'+BoldLeft+'">';
					                        msg += '<is>';
					                        msg += '<t>' + formatter.format(sumCredit) + '</t>';
					                        msg += '</is>';
					                        msg += '</c>';
					                      }
					                      msg += '</row>';
					                      return msg;
					                    }

				                    //insert
				                    var r1 = addTotalDebit(1, [{
				                      k: 'A',
				                      v: 'ColA'
				                    }]);
				                    
				                  //insert
				                    var r2 = addTotalCredit(1, [{
				                      k: 'A',
				                      v: 'ColA'
				                    }]);

				                    sheet.childNodes[0].childNodes[1].innerHTML = r1 + sheet.childNodes[0].childNodes[1].innerHTML;
				                    sheet.childNodes[0].childNodes[1].innerHTML = r2 + sheet.childNodes[0].childNodes[1].innerHTML;
				            	},
				            	customizeData: function(data) {
				                    for(var i = 0; i < data.body.length; i++) {
				                      for(var j = 0; j < data.body[i].length; j++) {
				                        data.body[i][8] = '\u200C' + data.body[i][8];
				                      }
				                    }
				                 }
				            
				            
				        }
				        
				       
				    
				      ],
					

				});
				
				

				var tables = $('#tableTransaction').DataTable();
				tables.columns.adjust().draw();
				
				function createCellPos( n ){
				    var ordA = 'A'.charCodeAt(0);
				    var ordZ = 'Z'.charCodeAt(0);
				    var len = ordZ - ordA + 1;
				    var s = "";
				 
				    while( n >= 0 ) {
				        s = String.fromCharCode(n % len + ordA) + s;
				        n = Math.floor(n / len) - 1;
				    }
				 
				    return s;
				}
		    	
				/* $.ajax({
					type: 'POST',
					url: '${pageContext.request.contextPath}/report/${REPORT_MENU}/search/k2c-api',
					async : true,
					data:{
						dtStart:formatDate(dtStart),
						dtEnd:formatDate(dtEnd)
					},
					success: function(response){
						//arr = response
						var list = "";

						for (var i = 0; i < arr.length; i++){
							list = list + `<tr>
											<td>`+arr[i].area+`</td>
											<td>`+arr[i].unit+`</td>
											<td>`+arr[i].center+`</td>
											<td>`+arr[i].cid+`</td>
											<td>`+arr[i].clientName+`</td>
											<td>`+arr[i].accnt+`</td>
											<td>`+arr[i].accntName+`</td>
											<td>`+formatDate($.trim(arr[i].trnDate))+`</td>
											<td>`+arr[i].reference+`</td>
											<td>`+arr[i].debit+`</td>
											<td>`+arr[i].credit+`</td>
									  </tr>`
						
						}
						
						$("#tableTransactionBody").html(list);

						$('#tableTransaction').DataTable({
							"processing" : true,
							"paging" : true,
							"destroy" : true,
							"responsive" : true,
							"scrollY" : "300px",
							"info" : false,
							'iDisplayLength' : 10,
							"columnDefs" : [ {
								"searchable" : false,
							} ],


						});

						var table = $('#tableTransaction').DataTable();
						table.columns.adjust().draw();
						
					},
					error : function(response){
						console.log("Error: "+ response);
					},
				}) */
				
			}
			
			
			$("#btn-search").click(function(){
				listTransaction();
			})
			
			
			//$( '#dateRangeStart , #dateRangeEnd' ).datepicker( 'setDate', today );
			
	    	/* var oTable = $("#dataTable").dataTable({
	    		dom: 'Bfrtpi',
	        	"sAjaxSource"	: "${pageContext.request.contextPath}/report/${REPORT_MENU}/search/k2c-api",
	            "sServerMethod"	: "POST",
	            "fnServerData"	: function (sSource, aoData, fnCallback) {
	            	var clientName		= $.trim($("#searchClientName").val());
	            	var dtStart = $.trim($("#dateRangeStart").val());
	            	var dtEnd = $.trim($("#dateRangeEnd").val());
					aoData.push({ "name": "dtStart", "value": formatDate(dtStart)});
					aoData.push({ "name": "dtEnd", "value": formatDate(dtEnd)});
	            	jQuery.ajax({
	                	"type"		: "POST",
	                    "url"		: sSource,
	                    "data"		: aoData,
	                    "success"	: fnCallback
	                });
				},
				
		        "buttons": [ 
		        {
		            extend: 'pdfHtml5',
		            orientation: 'landscape',
		            pageSize: 'A4',
		            style: 'fullWidth',
		            title: 'K2C Transactions',
		            pageMargins: [ 150, 150, 150, 150 ],
		            text: 'PDF',
		            key: {
		                key: 'e',
		                altKey: false
		            },
		            download: 'download',
		            exportOptions: {
		                modifier: {
		                    style: 'fullWidth',
		                    pageMargins: [ 150, 150, 150, 150 ],
		                    margin: [ 0, 0, 0, 120 ],
		                    alignment: 'center'
		                },
		                content: [{style: 'fullWidth' }],
		                styles: {
		                    fullWidth: {
		                        fontSize: 18,
		                        bold: true,
		                        alignment: 'right',
		                        margin: [0,190,0,80]		
		                    },
		                    subheader: {
		                        fontSize: 14
		                    },
		                    superMargin: {
		                        margin: [20, 0, 40, 0],
		                        fontSize: 15,
		                    }
		                },
		            
		                columns: [0,1,2,3,4,5], //r.broj kolone koja se stampa u PDF      
		                columnGap: 10 // optional space between columns
		            }
		        },
		        {
		            extend: 'excelHtml5',
		            orientation: 'landscape',
		            pageSize: 'A4',
		            style: 'fullWidth',
		            title: 'K2C Transactions',
		            pageMargins: [ 150, 150, 150, 150 ],
		            text: 'Excel',
		            key: {
		                key: 'e',
		                altKey: false
		            },
		            download: 'download',
		            exportOptions: {
		                modifier: {
		                    style: 'fullWidth',
		                    pageMargins: [ 150, 150, 150, 150 ],
		                    margin: [ 0, 0, 0, 120 ],
		                    alignment: 'center'
		                },
		                content: [{style: 'fullWidth' }],
		                styles: {
		                    fullWidth: {
		                        fontSize: 18,
		                        bold: true,
		                        alignment: 'right',
		                        margin: [0,190,0,80]		
		                    },
		                    subheader: {
		                        fontSize: 14
		                    },
		                    superMargin: {
		                        margin: [20, 0, 40, 0],
		                        fontSize: 15,
		                    }
		                },
		            
		                columns: [0,1,2,3,4,5], //r.broj kolone koja se stampa u PDF      
		                columnGap: 10 // optional space between columns
		            }
		        }
		        
		       
		    
		      ],
				 "aoColumns": [
					 
					 	{ "mDataProp": "area" }

						/* { "mDataProp": "area" },
					 	{ "mDataProp": "unit" },
						{ "mDataProp": "center" },
						{ "mDataProp": "cid" },
						{ "mDataProp": "clientName" },
						{ "mDataProp": "accNumber" },
						{ "mDataProp": "trnDate" },
						{ "mDataProp": "refNumber" },
						{ "mDataProp": "trnType" },
						{ "mDataProp": "status" },
						{ "mDataProp": "debitAmount" },
						{ "mDataProp": "creditAmount" } */
						/*]
            	 "aoColumnDefs" : [
					{
	            		class		: "text-center",
	                    "mRender"	: EditlinkFormatter,
	                    "aTargets"	: [8]
	                },
					{
                    	class		: "text-center",
                        "mRender"	: CheckboxFormatter,
                        "aTargets"	: [9]
                    }		
            	] 
			}); */
				
			/* $("#btn-search").click(function(){
	        	oTable.fnDraw();
			});
		
			$("#btn-reset").click(function(){
				$('#transType').val("");
				$('#searchCid').val("");
				$('#searchClientName').val("");
				
				oTable.fnDraw();
			});
			 */
			 
			
			
			function formatDate(date) {
				var days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
				var months = ['January', 'February', 'March', 'April', 'May', 'June','July','August','September','October','November','December'];
			    var d = new Date(date),
			        month = '' + (d.getMonth() + 1),
			        day = '' + d.getDate(),
			        year = d.getFullYear();
			    
			    var d = new Date(date);
			    var dayName = days[d.getDay()];
			    var monthName = months[d.getMonth()];
			    
			    if (month.length < 2) month = '0' + month;
			    if (day.length < 2) day = '0' + day;

			    return dayName +" "+ " "+day + " "+ monthName + " "+ year ;
			}
			 
			// Format: yyyy-mm-dd hh:mm:ss
			 function formatDate1(date) {
			     var d = new Date(date),
			         month = '' + (d.getMonth() + 1),
			         day = '' + d.getDate(),
			         year = d.getFullYear(),
					 hours = d.getHours(),
					 minutes = d.getMinutes(),
					 seconds = d.getSeconds(),
					 mseconds = d.getMilliseconds()
					 ;

			     if (month.length < 2) month = '0' + month;
			     if (day.length < 2) day = '0' + day;

			     return [year, month, day].join('-');
			 }
			
			 function formatDate2(date) {
					var days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
					var months = ['January', 'February', 'March', 'April', 'May', 'June','July','August','September','October','November','December'];
				    var d = new Date(date),
				        month = '' + (d.getMonth() + 1),
				        day = '' + d.getDate(),
				        year = d.getFullYear();
				    
				    var d = new Date(date);
				    var dayName = days[d.getDay()];
				    var monthName = months[d.getMonth()];
				    
				    if (month.length < 2) month = '0' + month;
				    if (day.length < 2) day = '0' + day;

				    return day + " "+ monthName + " "+ year ;
			}

			function formatDate3(date) {
				var days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
				var months = ['January', 'February', 'March', 'April', 'May', 'June','July','August','September','October','November','December'];
			    var d = new Date(date),
			        month = '' + (d.getMonth() + 1),
			        day = '' + d.getDate(),
			        year = d.getFullYear(),
					hours = d.getHours(),
					hours = ("0" + hours).slice(-2);
					minutes = d.getMinutes(),
					minutes = ("0" + minutes).slice(-2);
					seconds = d.getSeconds();
					seconds = ("0" + seconds).slice(-2);
			    
			    var d = new Date(date);
			    var dayName = days[d.getDay()];
			    var monthName = months[d.getMonth()];
			    
			    if (month.length < 2) month = '0' + month;
			    if (day.length < 2) day = '0' + day;

				return [dayName,day, monthName,year].join(' ') + " " + [hours,minutes,seconds].join(':');
			    //return dayName +" "+ " "+day + " "+ monthName + " "+ year  + " " + hours ;
			}
		
			
			
			/* $("#btn-delete").click(function(){
	        	var data = [];
	            $('#dataTable').find('tr').each(function () {
	    			var checkbox = $(this).find('input[type="checkbox"]');
	   				if (checkbox.is(':checked')){
	        			var row = {"code" : checkbox.val(), "state" : checkbox.attr('class')};
	        			data.push(row);
	   				}
				});
	            	
	            if(data.length > 0){
	    			submit('/configuration/routes/delete', JSON.stringify(data), function (data) {
	    				oTable.fnDraw();
	    			});
	           	} else {
	           		BootstrapDialog.show({
	                    message: 'Please select at least one data.',
	                    type: BootstrapDialog.TYPE_WARNING
	                });
	           	}
			}); */

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
			<div class="col-lg-8 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Report</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/report/${REPORT_MENU}/">${REPORT_TITLE}</a></li>
					</ul>
					<h3>
						<i class="fa fa-money"></i> ${REPORT_TITLE}
					</h3>
					<em>Report</em>
				</div>
			</div>
			<div class="col-lg-4 ">
				<%-- <div class="top-content">
	            <ul class="list-inline quick-access">
	                <li>
	                    <a href="${pageContext.request.contextPath}/report/${REPORT_MENU}/report-list">
	                        <div class="quick-access-item bg-color-green">
	                            <i class="fa fa-file-pdf-o"></i>
	                            <h5>Reports</h5>
	                            <em>Generate New Report</em>
	                        </div>
	                    </a>
	                </li>
	            </ul>
	        </div> --%>
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
						<div class="form-group">
							<div class="col-sm-4">
								<div class="input-group">
									<input id="dateRangeStart" name="dateRangeStart"
										style="cursor: pointer;" type="text"
										class="form-control datepicker required"
										placeholder="Date Start"> <span
										class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</div>
							<div class="col-sm-4">
								<div class="input-group">
									<input id="dateRangeEnd" name="dateRangeEnd"
										style="cursor: pointer;" type="text"
										class="form-control datepicker required"
										placeholder="Date End"> <span
										class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</div>
						</div>
					</div>


					<div class="row widget-button-search">
						<div class="col-sm-6">
							<div class="form-group">
								<a id="btn-search" class="btn btn-custom-primary " type="button"><i
									class="fa fa-search"></i> Search</a>
								<!-- <a id="btn-reset" class="btn btn-default" type="button"><i class="fa fa-refresh"></i> Reset</a>-->
							</div>
						</div>
						<div class="col-sm-6 text-right">
							<div class="form-group">
								<!-- <a class="btn btn-danger" type="button" data-toggle="modal" data-target="#confirm-delete"><i class="fa fa-trash"></i> Delete</a> -->
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
						<i class="fa fa-table"></i> List of konek2CARD PLUS Transaction
					</h3>
				</div>
				<div class="widget-content">
					<table class="display nowrap" id="tableTransaction">
						<thead>
							<tr>
								<th>SN</th>
								<th>Area</th>
								<th>Unit</th>
								<th>Center</th>
								<th>Client ID</th>
								<th>Client Name</th>
								<th>Source/Target Account Code</th>
								<th>Source/Target Account Name</th>
								<th>Branch Transaction Date</th>
								<th>Actual Transaction Date</th>
								<th>Reference Number</th>
								<th>Transaction Type</th>
								<th>Status</th>
								<th>DEBIT Amount</th>
								<th>CREDIT Amount</th>
							</tr>
						</thead>

						<tbody id="tableTransactionBody">

						</tbody>
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