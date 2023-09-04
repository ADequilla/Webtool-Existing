<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>Client Profile | Monitoring</title>
<style>
.left-icon-holder {
	text-align: left;
	padding-left: 20px;
	position: absolute;
}

/* .left-icon-holder {
    		position:relative;
		}
		.left-icon-holder .fa {
		    position:absolute;
		    line-height: 24px;
		    top:50%;
		    margin-top: -12px; /* Half of line height to keep left middle postion of container 
		    left: 10px;
		} */
</style>
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
<link href='https://fonts.googleapis.com/css?family=Poppins'
	rel='stylesheet'>
<link href='https://fonts.googleapis.com/css?family=Share+Tech+Mono'
	rel='stylesheet'>
<link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>

<style type="text/css">
@import
	url('https://fonts.googleapis.com/css?family=Roboto:400,500,700');

@import
	url('https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap')
	;

* {
	-webkit-box-sizing: border-box;
	box-sizing: border-box;
	margin: 0;
	padding: 0;
}

body {
	font-family: 'Roboto', sans-serif;
}

a {
	text-decoration: none;
}

.insti-desc {
	font-weight: 800;
}

.product-card {
	/* width: 380px; */
	position: relative;
	box-shadow: 0px 0px 0px 0px #7d7d7d;
	margin: 12px auto;
	background: #fafafa;
	border-radius: 9px;
	margin-left: 25px;
	margin-right: 25px;
}

.product-banner {
	/* width: 380px; */
	position: relative;
	box-shadow: 0px 5px 5px 1px #7d7d7d;
	margin: 8px auto;
	background: #fafafa;
	border-radius: 3px;
	margin-left: 10px;
	margin-right: 10px;
	margin-top: 49px;
}

.product-banner-details {
	/* padding: 30px; */
	padding-left: 30px;
	padding-top: 13px;
}

.account-details-label {
	float: left;
	font-size: 14px;
	font-family: 'Poppins';
	font-weight: 500;
	margin-right: 4px;
	text-transform: uppercase;
	color: #fff7f7;
}

.badge {
	position: absolute;
	left: 0;
	top: 20px;
	text-transform: uppercase;
	font-size: 13px;
	font-weight: 700;
	background: red;
	color: #fff;
	padding: 3px 10px;
}

.product-tumb {
	display: flex;
	align-items: center;
	justify-content: center;
	height: 300px;
	padding: 50px;
	background: #f0f0f0;
}

.product-tumb img {
	max-width: 100%;
	max-height: 100%;
}

.product-details {
	padding: 30px;
}

.modal-dialog {
	width: 1000px !important;
}

.account-name {
	display: block;
	font-size: 12px;
	font-weight: 700;
	text-transform: uppercase;
	color: #fff7f7;
	margin-bottom: -1px;
	font-family: Poppins;
}

.account-details {
	display: block;
	font-size: 14px;
	/* font-weight: 100; */
	/* text-transform: uppercase; */
	color: #fff7f7;
	margin-bottom: -1px;
	font-family: 'Poppins';
	font-weight: lighter;
	font-style: italic;
}

.account-number {
	display: block;
	font-size: 29px;
	font-weight: 700;
	text-transform: uppercase;
	color: #fff7f7;
	margin-bottom: -3px;
	font-family: 'Share Tech Mono';
}

.account-details h4 a {
	font-weight: 500;
	display: block;
	margin-bottom: 18px;
	text-transform: uppercase;
	color: #363636;
	text-decoration: none;
	transition: 0.3s;
}

.product-details h4 a:hover {
	color: #fbb72c;
}

.account-details p {
	font-size: 15px;
	line-height: 22px;
	margin-bottom: 18px;
	color: #999;
}

.account-bottom-details {
	overflow: hidden;
	border-top: 1px solid #eee;
	/* padding-top: 20px; */
}

.account-bottom-details div {
	float: left;
	width: 33.33%;
}

.product-price {
	font-size: 18px;
	color: #fbb72c;
	font-weight: 600;
}

.product-price small {
	font-size: 80%;
	font-weight: 400;
	text-decoration: line-through;
	display: inline-block;
	margin-right: 5px;
}

.product-links {
	text-align: right;
}

.product-links a {
	display: inline-block;
	margin-left: 5px;
	color: #e1e1e1;
	transition: 0.3s;
	font-size: 17px;
}

.product-links a:hover {
	color: #fbb72c;
}
</style>


<style>
.card {
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
	transition: 0.3s;
	width: 40%;
	border-radius: 5px;
}

.card:hover {
	box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
}

.container {
	padding: 2px 16px;
}
</style>

<style type="text/css">
.switch {
	position: relative;
	display: inline-block;
	width: 99px;
	height: 24px;
	margin-top: -2px;
}

.switch input {
	display: none;
}

.slider {
	position: absolute;
	cursor: pointer;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: #ca2222;
	-webkit-transition: .4s;
	transition: .4s;
	border-radius: 34px;
}

.slider:before {
	position: absolute;
	content: "";
	height: 26px;
	width: 26px;
	left: 4px;
	bottom: 4px;
	background-color: white;
	-webkit-transition: .4s;
	transition: .4s;
	border-radius: 50%;
}

input:checked+.slider {
	background-color: #2ab934;
}

input:focus+.slider {
	box-shadow: 0 0 1px #2196F3;
}

input:checked+.slider:before {
	-webkit-transform: translateX(26px);
	-ms-transform: translateX(26px);
	transform: translateX(55px);
}

/*------ ADDED CSS ---------*/
.slider:after {
	content: 'OFF';
	color: white;
	display: block;
	position: absolute;
	transform: translate(-50%, -50%);
	top: 50%;
	left: 50%;
	font-size: 10px;
	font-family: Verdana, sans-serif;
}

input:checked+.slider:after {
	content: 'ON';
}

.slider {
	border: 0;
	padding: 0;
	display: block;
	margin: -5px 5px !important;
	max-height: 34px;
}

/*--------- END --------*/
</style>


<style type="text/css">
/*-----Swtich table-----*/
.switch-table {
	position: relative;
	display: inline-block;
	width: 90px;
	height: 16px;
	margin-top: -2px;
	margin-bottom: -4px;
}

.switch-table input {
	display: none;
}

.slider-table {
	position: absolute;
	cursor: pointer;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: #ca2222;
	-webkit-transition: .4s;
	transition: .4s;
	border-radius: 34px;
}

.slider-table:before {
	position: absolute;
	content: "";
	height: 18px;
	width: 18px;
	left: 4px;
	bottom: 4px;
	background-color: white;
	-webkit-transition: .4s;
	transition: .4s;
	border-radius: 50%;
}

input:checked+.slider-table {
	background-color: #2ab934;
}

input:focus+.slider-table {
	box-shadow: 0 0 1px #2196F3;
}

input:checked+.slider-table:before {
	-webkit-transform: translateX(26px);
	-ms-transform: translateX(26px);
	transform: translateX(55px);
}

/*------ ADDED CSS ---------*/
.slider-table:after {
	content: 'OFF';
	color: white;
	display: block;
	position: absolute;
	transform: translate(-50%, -50%);
	top: 50%;
	left: 50%;
	font-size: 10px;
	font-family: Verdana, sans-serif;
}

input:checked+.slider-table:after {
	content: 'ON';
}

.slider-table {
	border: 0;
	padding: 0;
	display: block;
	margin: -5px 5px !important;
	max-height: 34px;
}

/*--------- END --------*/
</style>



<script type="text/javascript">
    	var obj;
		var updateEnableMerchanDone = false;
		var updateMerchanBusinessNameDone = false;
		var arr = [];  
		var hasUser = false;
		var isBlocked = false;
		var cid1;

		buildTable(arr);

		function checkForSpecialChar(string){
			var specialChars = "<>@!#$%^&*()_+[]{}?:;|'\"\\,./~`-=";
			for(i = 0; i < specialChars.length;i++){
				if(string.indexOf(specialChars[i]) > -1){
				    return true
				}
			}
			return false;
		}
		
		var getBranch = function(branch){
			var brnch = "";
			$.ajax({
				type: 'GET',
				url: '${pageContext.request.contextPath}/administration/user/getBranchDesc/'+branch,
				async : false,
				success: function(data){
					dta1 = "["+JSON.stringify(data)+"]";
					dta = JSON.parse(dta1);
					brnch = dta[0].description;
					
				},
				error: function(data){
					console.log(data);
				}
	       
       		})
       		return brnch;
       		
		}
			
		var getUnit = function(unit){
			var unt = "";
			$.ajax({
				type: 'GET',
				url: '${pageContext.request.contextPath}/administration/user/getUnitDesc/'+unit,
				async : false,
				success: function(data){
					dta1 = "["+JSON.stringify(data)+"]";
					dta = JSON.parse(dta1);
					unt = dta[0].description;
					
				},
				error: function(data){
					console.log(data);
				}
	       
       		})
       		return unt;
       		
		}
		
		var getCenter = function(center){
			var cntr = "";
			$.ajax({
				type: 'GET',
				url: '${pageContext.request.contextPath}/administration/user/getCenterDesc/'+center,
				async : false,
				success: function(data){
					dta1 = "["+JSON.stringify(data)+"]";
					dta = JSON.parse(dta1);
					cntr = dta[0].description;
					
				},
				error: function(data){
					console.log(data);
				}
	       
       		})
       		return cntr;
		}
		
		var getInstitution = function(institution){
			var insti = "";
			$.ajax({
				type: 'GET',
				url: '${pageContext.request.contextPath}/administration/user/getInstitutionDesc/'+institution,
				async : false,
				success: function(data){
					dta1 = "["+JSON.stringify(data)+"]";
					dta = JSON.parse(dta1);
					insti = dta[0].description;
					
				},
				error: function(data){
					console.log(data);
					
				}
	       
       		})
       		return insti;
		}
		
		var DeviceStatus = function(status,blocked){
			switch(blocked){
	    		case 0 :
	    			switch(status){
		    			case 0:
		    				return '<span class="label label-warning"><i class="fa fa-ban"></i> <b>False</b> </span>';
		    			break;
		    			case 1:
		    				return '<span class="label label-success"><i class="fa fa-check"></i> <b>True</b> </span>';
		    			break;
	    			}
	    		break;	
	    		case 1 :
	    			return '<span class="label label-warning"><i class="fa fa-ban"></i> <b>False</b> </span>';
	    		break;	
    		}
		}

		function setAgentFeatureMFS(id){
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/monitoring/profile/enableAgentFeature/'+ id,
				async : true,
				success: function(data){
					console.log(data);
				},
				error: function(data){
					console.log(data);
					if(agent == 1){
       					$('#'+id).prop('checked', false);
       				}else{
       					$('#'+id).prop('checked', true);
       				}
				}
	       
       		}).done(function(rs, textStatus, xhr) {
       			if(xhr.status == 200){
       				
       			}else{
       				response = JSON.parse(rs);
       				App.alert({type	: 'error', message	: response.message , icon	: 'error'});
       				if(agent == 1){
       					$('#'+id).prop('checked', false);
       				}else{
       					$('#'+id).prop('checked', true);
       				}
       			}
       		    
       		    
       		});
		}
		
		function setAgentFeature(agent,insti,uname,id){
			let cid = $("#cid").val();
			console.log("CID:" + cid)
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/monitoring/profile/updateAgentFeature',
				async : true,
				data:{
					username:uname,
					isAgent:agent,
					insti:insti,
					cid:cid,
				},
				success: function(data){
					console.log(data);
					
					
				},
				error: function(data){
					console.log(data);
					if(agent == 1){
       					$('#'+id).prop('checked', false);
       				}else{
       					$('#'+id).prop('checked', true);
       				}
				}
	       
       		}).done(function(rs, textStatus, xhr) {
       			if(xhr.status == 200){
       				response = JSON.parse(rs);
       				App.alert({type	: 'success', message	: response.message , icon	: 'success'});
       			}else{
       				response = JSON.parse(rs);
       				App.alert({type	: 'error', message	: response.message , icon	: 'error'});
       				if(agent == 1){
       					$('#'+id).prop('checked', false);
       				}else{
       					$('#'+id).prop('checked', true);
       				}
       			}
       		    
       		    
       		});
		}
		
		function setEnableStatusFeature(status,insti,uname,id){
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/monitoring/profile/updateStatusFeature',
				async : true,
				data:{
					username:uname,
					isEnabled:status,
					insti:insti
				},
				success: function(data){
					console.log(data);
					
				},
				error: function(data){
					console.log(data);
					if(status == 1){
       					$('#'+id).prop('checked', false);
       				}else{
       					$('#'+id).prop('checked', true);
       				}
				}
	       
       		}).done(function(rs, textStatus, xhr) {
       			if(xhr.status == 200){
       				response = JSON.parse(rs);
       				App.alert({type	: 'success', message	: response.message , icon	: 'success'});
       				
       			}else{
       				response = JSON.parse(rs);
       				App.alert({type	: 'error', message	: response.message , icon	: 'error'});
       				if(status == 1){
       					$('#'+id).prop('checked', false);
       				}else{
       					$('#'+id).prop('checked', true);
       				}
       			}
       		});
		}
		
		function setMerchantFeature(status,insti,uname,id){
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/monitoring/profile/updateMerchantFeature',
				async : true,
				data:{
					username:uname,
					isMerchant:status,
					insti:insti
				},
				success: function(data){
					console.log(data);
					
				},
				error: function(data){
					console.log(data);
					if(status == 1){
       					$('#'+id).prop('checked', false);
       				}else{
       					$('#'+id).prop('checked', true);
       				}
					
					
				}
	       
       		}).done(function(rs, textStatus, xhr) {
       			if(xhr.status == 200){
       				response = JSON.parse(rs);
       				App.alert({type	: 'success', message	: response.message , icon	: 'success'});
       			}else{
       				response = JSON.parse(rs);
       				App.alert({type	: 'error', message	: response.message , icon	: 'error'});
       				if(status == 1){
       					$('#'+id).prop('checked', false);
       				}else{
       					$('#'+id).prop('checked', true);
       				}
       			}
       		    
       		});
		}

		function resetPassword(uname,instiCode,to,msg,action ){
			let cid = $("#cid").val();
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/monitoring/profile/requestOTP',
				async : true,
				data:{
					username:uname,
					instiCode:instiCode,
					to:to,
					msg:msg,
					action:action,
					cid:cid
				},
				beforeSend	: function() {
	            	App.blockUI({target:'.wrapper', boxed: true});
		                $("#loader").show();
	            },
				success: function(data){
					$("#modal-reset-password").modal('hide');
					App.alert({type	: 'success', message	: "Successfully update device!", icon	: 'success', close: 'true'});
					
				},
				error: function(data){
					$("#modal-reset-password").modal('hide');
					App.alert({type	: 'error', message	: "Error: Password reset!", icon	: 'error', close: 'true'});
				},
				complete: function() {

                    setTimeout(function(){
                        App.unblockUI('.wrapper');
                        $("#loader").hide();
                    }, 1000);
                }
	       
       		}).done(function(rs, textStatus, xhr) {
       			if(xhr.status == 200){
       				response = JSON.parse(rs);
       				App.alert_inside_modal({type	: 'success', message	: response.message , icon	: 'success'});
					App.alert({type	: 'success', message	: "Successfully update device!", icon	: 'success', close: 'true'});
       				
       				
       			}else{
       				response = JSON.parse(rs);
       				App.alert_inside_modal({type	: 'error', message	: response.message , icon	: 'error'});
       				
       			}
       		    
       		});
		}
		
		function setDeviceBlock(status,uname,id){
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/monitoring/profile/updateDeviceBlock',
				async : true,
				data:{
					username:uname,
					status:status,
					id:id
				},
				success: function(data){
					console.log(data);
					
				},
				error: function(data){
					console.log(data);
					if(status == 1){
       					$('#'+id).prop('checked', false);
       				}else{
       					$('#'+id).prop('checked', true);
       				}
					
					
				}
	       
       		}).done(function(rs, textStatus, xhr) {
       			if(xhr.status == 200){
       				response = JSON.parse(rs);
       				$('#'+id).prop('checked', true);
       				App.alert_inside_modal({container:'klikAlertMessageModalListDevice',type	: 'success', message	: response.message , icon	: 'success'});
       				console.log("ID:"+id.substring(id.indexOf(':') + 1));
       				//$('#'+"togBtnActive:"+id.substring(id.indexOf(':') + 1)).prop('disabled', true);
       				//document.getElementById("togBtnActive:"+id.substring(id.indexOf(':') + 1)).innerHTML = '<span class="label label-warning"><i class="fa fa-ban"></i> <b>False</b> </span>';
       				
       			}else{
       				response = JSON.parse(rs);
       				App.alert_inside_modal({type	: 'error', message	: response.message , icon	: 'error'});
       				if(status == 1){
       					$('#'+id).prop('checked', false);
       				}else{
       					$('#'+id).prop('checked', true);
       				}
       			}
       		    
       		});
		}
		
		function setDeviceStatus(uname,id){
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/monitoring/profile/updateDeviceStatus',
				async : true,
				data:{
					username:uname,
					id:id
				},
				success: function(data){
					console.log(data);
					$('#'+"togBtnActive:"+id.substring(id.indexOf(':') + 1)).prop('disabled', true);
					$('#slider:'+id.substring(id.indexOf(':') + 1)).css('cursor', 'not-allowed');
					
				},
				error: function(data){
					console.log(data);
					if(status == 1){
       					$('#'+id).prop('checked', false);
       				}else{
       					$('#'+id).prop('checked', true);
       				}

					$('#'+"togBtnActive:"+id.substring(id.indexOf(':') + 1)).prop('disabled', false);
					$('#slider:'+id.substring(id.indexOf(':') + 1)).css('cursor', 'default');
					
					
				}
	       
       		}).done(function(rs, textStatus, xhr) {
       			if(xhr.status == 200){
       				response = JSON.parse(rs);
       				App.alert_inside_modal({type	: 'success', message	: response.message , icon	: 'success'});
       				console.log("ID:"+id.substring(id.indexOf(':') + 1));
					$('#'+"togBtnActive:"+id.substring(id.indexOf(':') + 1)).prop('disabled', true);
					$('#slider:'+id.substring(id.indexOf(':') + 1)).css('cursor', 'not-allowed');
       				// document.getElementById("td:"+id.substring(id.indexOf(':') + 1)).innerHTML="";
					// document.getElementById("td:"+id.substring(id.indexOf(':') + 1)).innerHTML='<span class="label label-warning"><i class="fa fa-ban"></i> <b>Unused</b> </span>';
       			}else{
       				response = JSON.parse(rs);
       				App.alert_inside_modal({container:'klikAlertMessageModalListDevice',type	: 'error', message	: response.message , icon	: 'error'});
       				if(status == 1){
       					$('#'+id).prop('checked', false);
       				}else{
       					$('#'+id).prop('checked', true);
       				}
       			}
       		    
       		});
		}

		var mobileNumberRegex = /^09\d{9}$/;

function updateMobileNumber(uname, newMobile, oldMobile, cid) {

	if (newMobile.trim() === "") {
      App.alert_inside_modal({
        container: 'klikAlertMessageModalUpdateMobile',
        type: 'success',
        message: "Mobile number is required.",
        icon: 'success'
      });
	  return;
    }
	if (!mobileNumberRegex.test(newMobile)) {
      App.alert_inside_modal({
        container: 'klikAlertMessageModalUpdateMobile',
        type: 'success',
        message: "Invalid mobile number format.",
        icon: 'success'
      });
      return;
    }

  $.ajax({
    type: 'POST',
    url: '${pageContext.request.contextPath}/monitoring/profile/updateMobileNumber',
    async: true,
    data: {
      cid: cid,
      username: uname,
      newMobile: newMobile,
      oldMobile: oldMobile,
    },
    success: function(data) {
      console.log(data);
    },
    error: function(data) {
      console.log(data);
    }
  }).done(function(rs, textStatus, xhr) {
    console.log("Done Response: " + rs);
    
    if (xhr.status == 200) {
      var response;
      try {
        response = JSON.parse(rs);
        if (response.hasOwnProperty('message')) {
          App.alert_inside_modal({
            container: 'klikAlertMessageModalUpdateMobile',
            type: 'success',
            message: response.message,
            icon: 'success'
          });
        } else {
          App.alert_inside_modal({
            container: 'klikAlertMessageModalUpdateMobile',
            type: 'success',
            message: "Mobile number successfully changed",
            icon: 'success'
          });
          $("#mobile").val(newMobile);
        }
      } catch (e) {
        App.alert_inside_modal({
          container: 'klikAlertMessageModalUpdateMobile',
          type: 'success',
          message: "Unable to parse JSON",
          icon: 'success'
        });
      }
    } else {
      var response;
      try {
        response = JSON.parse(rs);
        App.alert_inside_modal({
          container: 'klikAlertMessageModalUpdateMobile',
          type: 'success',
          message: response.message,
          icon: 'success'
        });
      } catch (e) {
        App.alert_inside_modal({
          container: 'klikAlertMessageModalUpdateMobile',
          type: 'success',
          message: "Unable to parse JSON",
          icon: 'success'
        });
      }
    }
  });
}


			function resetCredential(mobile, newuname, newpass) {

				if(newuname == "" && newpass == ""){
					App.alert_inside_modal({
				container: 'klikAlertMessageModalResetCredential',
				type: 'success',
				message: "New Username and Password are required.",
				icon: 'success'
			});
			return;	
				}
			
				if (newuname ==  "") {
			App.alert_inside_modal({
				container: 'klikAlertMessageModalResetCredential',
				type: 'success',
				message: "New Username is required.",
				icon: 'success'
			});
			return;
			}

			if (newpass == "") {
			App.alert_inside_modal({
				container: 'klikAlertMessageModalResetCredential',
				type: 'success',
				message: "New Password is required.",
				icon: 'success'
			});
			return;
			}

			$.ajax({
			type: 'POST',
			url: '${pageContext.request.contextPath}/monitoring/profile/resetCredential',
			async: true,
			data: {
			mobile: mobile,
			username: newuname,
			password: newpass,
			},
			success: function(data) {
			console.log(data);
			},
			error: function(data) {
			console.log(data);
			}
			}).done(function(rs, textStatus, xhr) {
			console.log("Done Response: " + rs);

			if (xhr.status == 200) {
			var response;
			try {
				response = JSON.parse(rs);
				if (response.hasOwnProperty('message')) {
				App.alert_inside_modal({
					container: 'klikAlertMessageModalResetCredential',
					type: 'success',
					message: response.message,
					icon: 'success'
				});
				} else {
				App.alert_inside_modal({
					container: 'klikAlertMessageModalResetCredential',
					type: 'success',
					message: "Credential successfully Reset",
					icon: 'success'
				});
				$.ajax({
					url: '${pageContext.request.contextPath}/monitoring/profile/'
				})
				}
			} catch (e) {
				App.alert_inside_modal({
				container: 'klikAlertMessageModalResetCredential',
				type: 'success',
				message: "Unable to parse JSON",
				icon: 'success'
				});
			}
			} else {
			var response;
			try {
				response = JSON.parse(rs);
				App.alert_inside_modal({
				container: 'klikAlertMessageModalResetCredential',
				type: 'success',
				message: response.message,
				icon: 'success'
				});
			} catch (e) {
				App.alert_inside_modal({
				container: 'klikAlertMessageModalResetCredential',
				type: 'success',
				message: "Unable to parse JSON",
				icon: 'success'
				});
			}
			}
			});
			}


		function updateRestrict(uname,newMobile,oldMobile,cid){
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/monitoring/profile/updateMobileNumber',
				async : true,
				data:{
					cid:cid,
					username:uname,
					newMobile:newMobile,
					oldMobile:oldMobile,
				},
				success: function(data){
					console.log(data);
					
				},
				error: function(data){
					console.log(data);
				}
	       
       		}).done(function(rs, textStatus, xhr) {
				   console.log("Done Response: " + rs)
				  
       			if(xhr.status == 200){
					   var response;
					try{
						response = JSON.parse(rs);
						if(response.hasOwnProperty('message')){
							App.alert_inside_modal({container:'klikAlertMessageModalUpdateMobile',type	: 'success', message	: response.message , icon	: 'success'});
						}else{
							App.alert_inside_modal({container:'klikAlertMessageModalUpdateMobile',type	: 'success', message	: "Mobile number successfully changed", icon	: 'success'});
							$("#mobile").val(newMobile);
						}
						
					}catch(e){
						App.alert_inside_modal({container:'klikAlertMessageModalUpdateMobile',type	: 'success', message	: "Unable to parse JSON", icon	: 'success'});
					}
					
       			}else{
       				response = JSON.parse(rs);
       				App.alert_inside_modal({container:'klikAlertMessageModalUpdateMobile',type	: 'error', message	: response.message , icon	: 'error'});
       			}
       		    
       		});
		
		}

		function setSavingAccountStatus(_insti,_accNumber,username,_isEnabled,id){
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/monitoring/profile/updateSavingsStatusFeature',
				async : true,
				data:{
					instiCode:_insti,
					accountNumber:_accNumber,
					isEnabled:_isEnabled,
					username:username,
				},
				success: function(data){
					console.log(data);
					
				},
				error: function(data){
					console.log(data);
					if(_isEnabled == 1){
       					$('#'+id).prop('checked', false);
       				}else{
       					$('#'+id).prop('checked', true);
       				}
				}
	       
       		}).done(function(rs, textStatus, xhr) {
       			if(xhr.status == 200){
       				response = JSON.parse(rs);
       				App.alert({type	: 'success', message : response.message, icon	: 'success'});
       			}else{
       				response = JSON.parse(rs);
       				App.alert({type	: 'error', message	: response.message, icon	: 'error'});
       				if(_isEnabled == 1){
       					$('#'+id).prop('checked', false);
       				}else{
       					$('#'+id).prop('checked', true);
       				}
       				
       			}
       		    
       		});
		}
		
		$( function() {
			  $( document ).on( "change", ":checkbox", function () {
			   		var id = $(this).attr('id');
			   		var insti =  id.substring(id.indexOf(':') + 1)
			   		
			   		var _class= id.substr(0, id.indexOf(':'));
			   		
			   		if(this.checked) {
			   	      	//setAgentFeature("1",acc,obj[0].username)
				   		switch(_class){
					   		case "togBtnAgent":
					   			console.log("insti: " + insti + " class: " +_class  +" username: "+obj[0].username);
					   			setAgentFeature("1",insti,obj[0].username,id)
					   		break;
					   		case "togBtnStatus":
					   			console.log("insti: " + insti + " class: " +_class  +" username: "+obj[0].username);
					   			setEnableStatusFeature("1",insti,obj[0].username,id)
					   		break;
					   		case "togBtnMerchant":
					   			console.log("insti: " + insti + " class: " +_class  +" username: "+obj[0].username);
					   			setMerchantFeature("1",insti,obj[0].username,id)
					   		break;
					   		case "togBtnBlock":
					   			console.log("uname: " + obj[0].username + " ID: " +id);
					   			setDeviceBlock("1",obj[0].username,id.substring(id.indexOf(':') + 1))
					   		break;
					   		case "togInnerBtnStatus":
					   			//togInnerBtnStatus:100:1012-0000-07128923:Microsavings Account:no-token-2504347
					   			var insti = id.split(':')[1].split(';')[0];
					   			var accNum = id.split(':')[2].split(';')[0];
					   			var accName = id.split(':')[3].split(';')[0];
					   			var token =  id.split(':')[4].split(';')[0];
					   			console.log("insti: " + insti + 
					   						"\n account number: "+accNum + 
					   						"\n account name: " +accName+
					   						"\n token: " +token
					   						);
					   			//setMerchantFeature("1",insti,obj[0].username)
					   			setSavingAccountStatus(insti,accNum,obj[0].username,1,id)
					   		break;
					   		
					   			
				   		}
			   		}else{
			   	    	
			   	    	switch(_class){
					   		case "togBtnAgent":
					   			console.log("insti: " + insti + " class: " +_class  +" username: "+obj[0].username);
					   			setAgentFeature("0",insti,obj[0].username,id)
					   		break;
					   		case "togBtnStatus":
					   			console.log("insti: " + insti + " class: " +_class  +" username: "+obj[0].username);
					   			setEnableStatusFeature("0",insti,obj[0].username,id)
					   		break;
					   		case "togBtnMerchant":
					   			console.log("insti: " + insti + " class: " +_class  +" username: "+obj[0].username);
					   			setMerchantFeature("0",insti,obj[0].username,id)
					   		break;
					   		case "togBtnBlock":
					   			console.log("uname: " + obj[0].username + " ID: " +id);
					   			setDeviceBlock("0",obj[0].username,id.substring(id.indexOf(':') + 1))
					   		break;
							case "togBtnActive":
					   			console.log("uname: " + obj[0].username + " ID: " +id);
					   			setDeviceStatus(obj[0].username,id.substring(id.indexOf(':') + 1))
					   		break;
					   		case "togInnerBtnStatus":
					   			//togInnerBtnStatus:100:1012-0000-07128923:Microsavings Account:no-token-2504347
					   			var insti = id.split(':')[1].split(';')[0];
					   			var accNum = id.split(':')[2].split(';')[0];
					   			var accName = id.split(':')[3].split(';')[0];
					   			var token =  id.split(':')[4].split(';')[0];
					   			console.log("insti: " + insti + 
					   						"\n account number: "+accNum + 
					   						"\n account name: " +accName+
					   						"\n token: " +token
					   						);
					   			setSavingAccountStatus(insti,accNum,obj[0].username,0,id)
					   			
					   		break;
			   			}
			   	    }

			   		
			   		
			  });
		});
			
		function buildTable(arr){
			
	        var table = $('#tableDevices').DataTable();
	    	table.destroy();
	    	
	    	
	    	var list = "";

	    	for (var i = 0; i < arr.length; i++){
				var sn = i + 1;
				list = list + "<tr>\
								<td>"+arr[i].ime+"</td>\
								<td>"+arr[i].device_model+"</td>\
								<td>"+formatDate2(arr[i].dateAdded)+"</td>\
								<td>"+formatDate2(arr[i].lastActive)+"</td>\
								<td>\
									<label class='switch-table'>\
										<input type='checkbox'  id='togBtnBlock:"+arr[i].ime+"' " +isAgentAcc(arr[i].isBlocked)+">\
										<div class='slider-table round'></div>\
									</label>\
								</td>\
								<td id='td:"+arr[i].ime+"'>\
									<label class='switch-table'>\
										<input type='checkbox'  id='togBtnActive:"+arr[i].ime+"' " + isAgentAcc(arr[i].isActive)  + isUsed(arr[i].isActive)+">\
										<div class='slider-table round' id='slider:"+arr[i].ime+"'></div>\
									</label>\
								</td>\
						  </tr>";
						  console.log(isUsed(arr[i].isActive));

						  //<td id='togBtnActive:"+arr[i].ime+"'>"+DeviceStatus(arr[i].isActive,arr[i].isBlocked)+"</td>\
						  
			
			}
			
			$("#tableDevicesBody").html(list);

			$('#tableDevices').DataTable({
				"processing" : true,
				"serverSide":false,
				"paging" : true,
				"destroy" : true,
				"responsive" : true,
				"info" : false,
				'iDisplayLength' : 10,
				"search":'',
				"searching" : true,
				//dom: 'Bfrtpi',
				buttons: [ 
			        {
			            extend: 'pdfHtml5',
			            orientation: 'landscape',
			            pageSize: 'A4',
			            style: 'fullWidth',
			            title: 'K2C Transaction',
			            filename: 'K2C Transaction ('+ formatDate2($( "#dateRangeStart" ).val()) +' - '+formatDate2($( "#dateRangeEnd" ).val())+')',
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
			            
			                columns: [0,1,2,3,4,5], //r.broj kolone koja se stampa u PDF      
			                columnGap: 10 // optional space between columns
			            }
			        },
			        {
			            extend: 'excelHtml5',
			            orientation: 'landscape',
			            pageSize: 'A4',
			            style: 'fullWidth',
			            title: 'K2C Transaction',
			            filename: 'K2C Transaction ('+ formatDate2($( "#dateRangeStart" ).val()) +' - '+formatDate2($( "#dateRangeEnd" ).val())+')' ,
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
			            
			                columns: [0,1,2,3,4,5], //r.broj kolone koja se stampa u PDF      
			                columnGap: 10 // optional space between columns
			            },
			            customize: function( xlsx ) {
			            	 
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
			            	    $('row c[r^="J"]', sheet).attr( 's', '67');
			            	    $('row c[r^="K"]', sheet).attr( 's', '64' );
			            	    $('row c[r^="L"]', sheet).attr( 's', '64' );
			            	    $('row c[r^="M"]', sheet).attr( 's', '64' );
//			            	                $('row c', sheet).attr( 's', '25' ); //for all rows
			            	    $('row:eq(0) c', sheet).attr( 's', greyBoldCentered );  //grey background bold and centered, as added above
			            	    $('row:eq(1) c', sheet).attr( 's', greyBoldCentered );  //grey background bold
			            	    
			            	    
			            	    sumDebit = 0;
			            	    sumCredit = 0;
			            	    lastRow = 2;

			                    // read each row
			                    //Sum Debit
			                    $('row c[r^="L"]', sheet).each(function() {
									  lastRow++;
				                      var value = $(this).text();
				                      sumDebit += Number(value.replace(/[^0-9\.-]+/g, ""));
			                    });
			                    
			                    //Sum Credit
			                    $('row c[r^="M"]', sheet).each(function() {
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
			                        msg += '<c t="inlineStr" r="' + 'L' + lastRow + '" s="'+BoldLeft+'">';
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
				                        msg += '<c t="inlineStr" r="' + 'M' + lastRow + '" s="'+BoldLeft+'">';
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
			
			

			var tables = $('#tableDevices').DataTable();
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
		
		$("#cid").change(function(){
			var cid	= $(this).val();
			cid1 = cid;
			
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/monitoring/profile/get',
				async : true,
				data:{
					cid:cid
				},
				beforeSend	: function() {
	            	App.blockUI({target:'.wrapper', boxed: true});
		                $("#loader").show();
	            },
				success: function(data){
					data1 = '[{"id":258,"username":"pilinuts","mobile":"09615909989","birthday":"1986-11-20T00:00:00.000+0000","cid":2504379,"isEnabled":1,"firstName":"Ma. Elena","middleName":"S","lastName":"Tamayo","isDefault":0,"dateAdded":"2021-06-29T06:20:13.000+0000","instiCode":200,"roles":[{"id":4,"name":"ROLE_K2CUSER","abbr":"k2cUser"},{"id":7,"name":"ROLE_K2CAGENT","abbr":"k2cAgent"}],"accounts":[{"id":110,"instiCode":100,"instiName":null,"cid":2504379,"brCode":"467856","unitCode":"915218","centerCode":"9256905","memberClassification":null,"clientType":null,"isDefault":0,"isEnabled":1,"isAgent":1,"isMerchant":0,"dateAdded":"2021-06-29T06:20:16.000+0000","savings":[{"id":13,"accountNumber":"1012-0000-07130515","accountName":"Microsavings Account","token":"no-token-2504379","isEnabled":1,"dateAdded":"2021-07-01T07:21:13.000+0000"},{"id":13,"accountNumber":"1012-0000-07130516","accountName":"Microsavings Accountsss","token":"no-token-2504379","isEnabled":1,"dateAdded":"2021-07-01T07:21:13.000+0000"}]},{"id":110,"instiCode":300,"instiName":null,"cid":2504379,"brCode":"467856","unitCode":"915218","centerCode":"9256905","memberClassification":null,"clientType":null,"isDefault":0,"isEnabled":1,"isAgent":1,"isMerchant":0,"dateAdded":"2021-06-29T06:20:16.000+0000","savings":[{"id":13,"accountNumber":"1012-0000-07130520","accountName":"Microsavings Accountss12","token":"no-token-2504379","isEnabled":1,"dateAdded":"2021-07-01T07:21:13.000+0000"},{"id":13,"accountNumber":"1012-0000-07130521","accountName":"Microsavings Accountsss121","token":"no-token-2504379","isEnabled":1,"dateAdded":"2021-07-01T07:21:13.000+0000"}]}],"devices":[{"id":665,"ime":"4fa2cc304585c588","device_model":"LDN-LX2","isBlocked":0,"dateAdded":"2021-06-29T06:20:13.000+0000","isActive":0,"lastActive":"2021-07-01T08:14:27.000+0000"},{"id":694,"ime":"a0b4c9790c424735","device_model":"SM-P205","isBlocked":0,"dateAdded":"2021-07-01T07:21:11.000+0000","isActive":0,"lastActive":"2021-07-01T09:28:05.000+0000"},{"id":696,"ime":"a303a3056676b920","device_model":"Redmi Note 9S","isBlocked":0,"dateAdded":"2021-07-01T10:57:31.000+0000","isActive":1,"lastActive":"2021-07-01T10:57:31.000+0000"}]}'+
							']';						
					obj = JSON.parse(data);
					console.log(obj[0].hasOwnProperty('id'));
					if(obj[0].hasOwnProperty('id')){
						
						distribute(JSON.parse(data));
						buildTable(obj[0].devices);
						App.alert({type	: 'success', message	: "Success:  Client found!", icon	: 'success', close: 'true'});
					}else{
						buildTable(obj[0].devices = "");
						setTimeout(function(){
		                	App.unblockUI('.wrapper');
		                    $("#loader").hide();
		                }, 1000);
						App.alert({type	: 'error', message	: "Error: Client not found!", icon	: 'error', close: 'true'});
						$('#cid').val('');
		            	$("#fullname").val('');
		            	$('#mobile').val('');
						$('#updateMobile').val('');
		            	$('#username').val('');
		            	$('#birthday').val('');

						$("#btn-devices").disable(true);
						$("#btn-reset-password").disable(true);
						$("#btn-change-mobile-number").disable(true);
						$("#btn-reset-credential").disable(true);
						$("#btn-restrict").disable(true);
		            	document.getElementById("accounts").innerHTML = '';
					}
				},
				error: function(data){
					console.log(data);
					setTimeout(function(){
                        App.unblockUI('.wrapper');
                        $("#loader").hide();
                    }, 1000);
					$('#cid').val('');
		            	$("#fullname").val('');
		            	$('#mobile').val('');
						$('#updateMobile').val('');
		            	$('#username').val('');
		            	$('#birthday').val('');

						$("#btn-devices").disable(true);
						$("#btn-reset-password").disable(true);
						$("#btn-change-mobile-number").disable(true);
						$("#btn-reset-credential").disable(true);
						$("#btn-restrict").disable(true);
				},
				complete: function() {
                    setTimeout(function(){
                        App.unblockUI('.wrapper');
                        $("#loader").hide();
                    }, 1000);
                }
	       
       		})
    	});
		
		$("#mobile").change(function(){
			var mobile	= $(this).val();
			
			
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/monitoring/profile/get',
				async : true,
				data:{
					mobile:mobile
				},
				beforeSend	: function() {
	            	App.blockUI({target:'.wrapper', boxed: true});
		                $("#loader").show();
	            },
				success: function(data){
					data1 = '[{"id":258,"username":"pilinuts","mobile":"09615909989","birthday":"1986-11-20T00:00:00.000+0000","cid":2504379,"isEnabled":1,"firstName":"Ma. Elena","middleName":"S","lastName":"Tamayo","isDefault":0,"dateAdded":"2021-06-29T06:20:13.000+0000","instiCode":200,"roles":[{"id":4,"name":"ROLE_K2CUSER","abbr":"k2cUser"},{"id":7,"name":"ROLE_K2CAGENT","abbr":"k2cAgent"}],"accounts":[{"id":110,"instiCode":100,"instiName":null,"cid":2504379,"brCode":"467856","unitCode":"915218","centerCode":"9256905","memberClassification":null,"clientType":null,"isDefault":0,"isEnabled":1,"isAgent":1,"isMerchant":0,"dateAdded":"2021-06-29T06:20:16.000+0000","savings":[{"id":13,"accountNumber":"1012-0000-07130515","accountName":"Microsavings Account","token":"no-token-2504379","isEnabled":1,"dateAdded":"2021-07-01T07:21:13.000+0000"},{"id":13,"accountNumber":"1012-0000-07130516","accountName":"Microsavings Accountsss","token":"no-token-2504379","isEnabled":1,"dateAdded":"2021-07-01T07:21:13.000+0000"}]},{"id":110,"instiCode":300,"instiName":null,"cid":2504379,"brCode":"467856","unitCode":"915218","centerCode":"9256905","memberClassification":null,"clientType":null,"isDefault":0,"isEnabled":1,"isAgent":1,"isMerchant":0,"dateAdded":"2021-06-29T06:20:16.000+0000","savings":[{"id":13,"accountNumber":"1012-0000-07130520","accountName":"Microsavings Accountss12","token":"no-token-2504379","isEnabled":1,"dateAdded":"2021-07-01T07:21:13.000+0000"},{"id":13,"accountNumber":"1012-0000-07130521","accountName":"Microsavings Accountsss121","token":"no-token-2504379","isEnabled":1,"dateAdded":"2021-07-01T07:21:13.000+0000"}]}],"devices":[{"id":665,"ime":"4fa2cc304585c588","device_model":"LDN-LX2","isBlocked":0,"dateAdded":"2021-06-29T06:20:13.000+0000","isActive":0,"lastActive":"2021-07-01T08:14:27.000+0000"},{"id":694,"ime":"a0b4c9790c424735","device_model":"SM-P205","isBlocked":0,"dateAdded":"2021-07-01T07:21:11.000+0000","isActive":0,"lastActive":"2021-07-01T09:28:05.000+0000"},{"id":696,"ime":"a303a3056676b920","device_model":"Redmi Note 9S","isBlocked":0,"dateAdded":"2021-07-01T10:57:31.000+0000","isActive":1,"lastActive":"2021-07-01T10:57:31.000+0000"}]}'+
							']';						
					obj = JSON.parse(data);
					console.log(obj[0].hasOwnProperty('id'));
					if(obj[0].hasOwnProperty('id')){
						distribute(JSON.parse(data));
						buildTable(obj[0].devices);
						App.alert({type	: 'success', message	: "Success:  Client found!", icon	: 'success'});
					}else{
						buildTable(obj[0].devices = "");
						setTimeout(function(){
		                	App.unblockUI('.wrapper');
		                    $("#loader").hide();
		                }, 1000);
						App.alert({type	: 'error', message	: "Error: Client not found!", icon	: 'error'});
						$('#cid').val('');
		            	$("#fullname").val('');
		            	$('#mobile').val('');
						$('#updateMobile').val('');
		            	$('#username').val('');
		            	$('#birthday').val('');

						$("#btn-devices").disable(true);
						$("#btn-reset-password").disable(true);
						$("#btn-change-mobile-number").disable(true);
						$("#btn-reset-credential").disable(true);
						$("#btn-restrict").disable(true);
		            	document.getElementById("accounts").innerHTML = '';
					}
				},
				error: function(data){
					console.log(data);
					setTimeout(function(){
                        App.unblockUI('.wrapper');
                        $("#loader").hide();
                    }, 1000);
					$('#cid').val('');
		            	$("#fullname").val('');
		            	$('#mobile').val('');
						$('#updateMobile').val('');
		            	$('#username').val('');
		            	$('#birthday').val('');

						$("#btn-devices").disable(true);
						$("#btn-reset-password").disable(true);
						$("#btn-change-mobile-number").disable(true);
						$("#btn-reset-credential").disable(true);
						$("#btn-restrict").disable(true);
				},
				complete: function() {
                    setTimeout(function(){
                        App.unblockUI('.wrapper');
                        $("#loader").hide();
                    }, 1000);
                }
	       
       		})
    	});
		
		$("#username").change(function(){
			var username	= $(this).val();
				
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/monitoring/profile/get',
				async : true,
				data:{
					username:username
				},
				beforeSend	: function() {
	            	App.blockUI({target:'.wrapper', boxed: true});
		                $("#loader").show();
	            },
				success: function(data){
					data1 = '[{"id":258,"username":"pilinuts","mobile":"09615909989","birthday":"1986-11-20T00:00:00.000+0000","cid":2504379,"isEnabled":1,"firstName":"Ma. Elena","middleName":"S","lastName":"Tamayo","isDefault":0,"dateAdded":"2021-06-29T06:20:13.000+0000","instiCode":200,"roles":[{"id":4,"name":"ROLE_K2CUSER","abbr":"k2cUser"},{"id":7,"name":"ROLE_K2CAGENT","abbr":"k2cAgent"}],"accounts":[{"id":110,"instiCode":100,"instiName":null,"cid":2504379,"brCode":"467856","unitCode":"915218","centerCode":"9256905","memberClassification":null,"clientType":null,"isDefault":0,"isEnabled":1,"isAgent":1,"isMerchant":0,"dateAdded":"2021-06-29T06:20:16.000+0000","savings":[{"id":13,"accountNumber":"1012-0000-07130515","accountName":"Microsavings Account","token":"no-token-2504379","isEnabled":1,"dateAdded":"2021-07-01T07:21:13.000+0000"},{"id":13,"accountNumber":"1012-0000-07130516","accountName":"Microsavings Accountsss","token":"no-token-2504379","isEnabled":1,"dateAdded":"2021-07-01T07:21:13.000+0000"}]},{"id":110,"instiCode":300,"instiName":null,"cid":2504379,"brCode":"467856","unitCode":"915218","centerCode":"9256905","memberClassification":null,"clientType":null,"isDefault":0,"isEnabled":1,"isAgent":1,"isMerchant":0,"dateAdded":"2021-06-29T06:20:16.000+0000","savings":[{"id":13,"accountNumber":"1012-0000-07130520","accountName":"Microsavings Accountss12","token":"no-token-2504379","isEnabled":1,"dateAdded":"2021-07-01T07:21:13.000+0000"},{"id":13,"accountNumber":"1012-0000-07130521","accountName":"Microsavings Accountsss121","token":"no-token-2504379","isEnabled":1,"dateAdded":"2021-07-01T07:21:13.000+0000"}]}],"devices":[{"id":665,"ime":"4fa2cc304585c588","device_model":"LDN-LX2","isBlocked":0,"dateAdded":"2021-06-29T06:20:13.000+0000","isActive":0,"lastActive":"2021-07-01T08:14:27.000+0000"},{"id":694,"ime":"a0b4c9790c424735","device_model":"SM-P205","isBlocked":0,"dateAdded":"2021-07-01T07:21:11.000+0000","isActive":0,"lastActive":"2021-07-01T09:28:05.000+0000"},{"id":696,"ime":"a303a3056676b920","device_model":"Redmi Note 9S","isBlocked":0,"dateAdded":"2021-07-01T10:57:31.000+0000","isActive":1,"lastActive":"2021-07-01T10:57:31.000+0000"}]}'+
							']';						
					obj = JSON.parse(data);
					console.log(obj[0].hasOwnProperty('id'));
					if(obj[0].hasOwnProperty('id')){
						distribute(JSON.parse(data));
						buildTable(obj[0].devices);
						App.alert({type	: 'success', message	: "Success:  Client found!", icon	: 'success', close: 'true'});
						hasUser = true;
					}else{

						$("#btn-devices").disable(true);
						$("#btn-reset-password").disable(true);
						$("#btn-change-mobile-number").disable(true);
						$("#btn-reset-cridential").disable(true);
						$("#btn-restrict").disable(true);
						buildTable(obj[0].devices = "");
						setTimeout(function(){
		                	App.unblockUI('.wrapper');
		                    $("#loader").hide();
		                }, 1000);
						App.alert({type	: 'error', message	: "Error: Client not found!", icon	: 'error', close: 'true'});
						hasUser = false;
						$('#cid').val('');
		            	$("#fullname").val('');
		            	$('#mobile').val('');
		            	$('#username').val('');
		            	$('#birthday').val('');
		            	document.getElementById("accounts").innerHTML = '';
					}
				},
				error: function(data){
					$("#btn-devices").disable(true);
						$("#btn-reset-password").disable(true);
						$("#btn-change-mobile-number").disable(true);
						$("#btn-reset-password").disable(true);
						$("#btn-reset-credential").disable(true);
						$("#btn-restrict").disable(true);
					obj = null;
					console.log(data);
					setTimeout(function(){
                        App.unblockUI('.wrapper');
                        $("#loader").hide();
                    }, 1000);
				},
				complete: function() {
                    setTimeout(function(){
                        App.unblockUI('.wrapper');
                        $("#loader").hide();
                    }, 1000);
                }
	       
       		})
    	});
		
        $("#btn-syncronize").click(function(){
				showConfirmation("Are you sure want to Syncronize ?", "warning", "refresh", "Syncronize", "syncronize");
        });
		
		$(document).ready(function() {
            
        	
        	buildTable("");
			// $("#mobile").change(function(){
			// 	var mobile	= $(this).val();
			// 	if (mobile != '') {
			// 		data = {field:"mobileNo", string:mobile};
			// 		retrieve('/monitoring/profile/get', JSON.stringify(data), function (data) {
    		// 			populate(data);
            //         });
			// 	}
        	// });
			
			$("#accountNumber").change(function(){
				var accountNumber	= $(this).val();
				if (accountNumber != '') {
					data = {field:"accountNumber", string:accountNumber};
					retrieve('/monitoring/profile/get', JSON.stringify(data), function (data) {
    					populate(data);
                    });
				}
        	});
			
			$("#btn-syncronize").click(function(){
				showConfirmation("Are you sure want to Syncronize ?", "warning", "refresh", "Syncronize", "syncronize");
            });
			
			// $("#btn-reset-password").click(function(){
			// 	showConfirmation("Are you sure want to Reset Client Password?", "warning", "refresh", "Reset", "resetPassword");
            // });
			
			$("#btn-reset-mpin").click(function(){
				showConfirmation("Are you sure want to Reset Client MPIN?", "warning", "refresh", "Reset", "resetMpin");
            });
			
			$("#btn-deactivate").click(function(){
				showConfirmation("Are you sure want to Deactivate this Client?", "danger", "times-circle", "Deactivate", "deactivate");
            });
			
			$("#btn-restrict").click(function(){
				var restrict = $("#restrict").val();
				if (restrict == '1') {
					showConfirmation("Are you sure want to Unrestrict this Client?", "primary", "unlock", "Urestrict", "unrestrict");
				} else {
					showConfirmation("Are you sure want to Restrict this Client?", "danger", "lock", "Restrict", "restrict");
				}
			});
			
			$("#btn-view-username").click(function(){
				sendAction("viewUsername", null);
            });
			
			$("#btn-agentFeature-enabled").click(function(){
				var agentFeature = $("#agentFeature").val();
				if (agentFeature == '1') {
					showConfirmation("Are you sure want to Disable Agent Feature?", "link", "close", "Disabled", "disableAgentFeature");
				} else {
					showConfirmation("Are you sure want to Enable Agent Feature?", "success", "check", "Enabled", "enableAgentFeature");
				}
            });

			$("#btn-blocked").click(function(){
				var status = $("#status").val();
				if (status == 'Blocked Activation Code') {
					showConfirmation("Are you sure want to Unblock Activation?", "danger", "unlock", "Unblock Activation", "blockedActCode");
				} else {
					showConfirmation("Are you sure want to Unblock Reset Credential?", "danger", "unlock", "Unblock Reset Credential", "blockedValCode");
				}
            });
			
			$("#btn-merchant-feature").click(function(){
				var merchantFeature = $("#merchantFeature").val();
				if (merchantFeature == '1') {
					showConfirmation("Are you sure want to Disable Merchant Feature?", "primary", "close", "Disabled", "disableMerchantFeature");
				} else {
					showConfirmation("Are you sure want to Enable Merchant Feature?", "warning", "check", "Enabled", "enableMerchantFeature");
				}
            });
			
			$("#btn-update-merchant").click(function(){
				var businessName = $('#businessName').val();
				if(checkForSpecialChar(businessName)){
					BootstrapDialog.show({
                        message: 'Merchant Business Name contains special character',
                        type: BootstrapDialog.TYPE_WARNING
                    });
				} else {
					showConfirmationUpdateBusinessNameDirect("Are you sure want to Update Merchant Business Name and Enable Merchant Feature?", "info", "floppy-o", "Updated");
				}
            });

			var modal = document.getElementById("myModal");
			var modalImg = document.getElementById("img01");
			var captionText = document.getElementById("captionQr");
			var modalTitleText = document.getElementById("modalTitleQr");
			
			$("#btn-merchantCode").click(function(){
				var id = $("#id").val();
				var businessName = $('#businessName').val();
				
	        	var merchantCode = $("#merchantCode").val();
				modal.style.display = "block";
			   	modalImg.src = "${pageContext.request.contextPath}/assets/img/konek2PAY/Merchant-"+id+"-"+businessName+".png";
			   	captionText.innerHTML = merchantCode;
			   	modalTitleText.innerHTML = "Merchant QR Code";
			});
			
			
			// Get the <span> element that closes the modal
			var span = document.getElementsByClassName("closeQr")[0];
			
			// When the user clicks on <span> (x), close the modal
			span.onclick = function() { 
				modal.style.display = "none";
			}
			 
        });
        
        $("#btn-devices").click(function(){
		   	$("#modal-list-device").modal('show');
		});
		
		$("#btn-reset-password").click(function(){
		   	$("#modal-reset-password").modal('show');
		});

		$("#btn-change-mobile-number").click(function(){
		   	$("#modal-change-mobile-number").modal('show');
		});

		$("#btn-save-mobile-number").click(function(){
			let uname	= $("#username").val();
			let newMobile	= $("#updateMobileNumber").val();
			let oldMobile	= $("#mobile").val();
			let cid	= $("#cid").val();
			updateMobileNumber(uname,newMobile,oldMobile,cid)
		});

		$("#btn-reset-credential").click(function(){
		   	$("#modal-reset-credential").modal('show');
		});

		$("#btn-save-reset-credential").click(function(){
			let mobile	= $("#mobile").val();
			let newuname	= $("#updateUsername").val();
			let newpass	= $("#updatePassword").val();
			let cid	= $("#cid").val();
			resetCredential(mobile,newuname,newpass,cid)
		});

		$("#confirm-reset-password").click(function(){
			resetPassword(
				obj[0].username,
				obj[0].instiCode,
				obj[0].mobile,
				"Your temporary password is: ", 
				"CHANGE PASS WEB" 
			)
		});


        var isAgentAcc = function(val,id){
        	console.log("isAgent::" + val);
        	switch(val){
        		case 0:
        			//$("#togBtnAgent:"+id).removeAttr("checked");
        			return ""
        		break;
        		case 1:
        			//$("#togBtnAgent:"+id).attr("checked","checked");
        			return "checked";
        		break;
        	}
        }

		var isUsed = function(val,id){
        	console.log("isUsed::" + val);
        	switch(val){
        		case 0:
        			//$("#togBtnAgent:"+id).removeAttr("checked");
        			return "disabled";
        		break;
        		case 1:
        			//$("#togBtnAgent:"+id).attr("checked","checked");
					
        			return "";
        		break;
        	}
        }
        
        var isEnableAcc = function(val,id){
        	console.log("isEnable::" + val);
        	switch(val){
        		case 0:
        			//$("#togBtnStatus:"+id).removeAttr("checked");
        			return ""
        		break;
        		case 1:
        			//$("#togBtnStatus:"+id).attr("checked","checked");
        			return "checked"
        		break;
        	}
        }
        
         var isMerchant = function(val,id){
        	console.log("isMerchant::" + val);
        	switch(val){
        		case 0:
        			//$("#togBtnStatus:"+id).removeAttr("checked");
        			return ""
        		break;
        		case 1:
        			//$("#togBtnStatus:"+id).attr("checked","checked");
        			return "checked"
        		break;
        	}
        }
        
        var getPantone = function(instiCode){
        	
        	switch(instiCode){
        		case 101 :
        			return "#04540f 20%, #159a15";
        		break;	
        		case 200 :
        			return "#001ea5 20%, #15759a";
        		break;	
        		case 300 :
        			return "#57783a 20%, #159a15";
        		break;	
        		case 100 :
        			return "#04540f 20%, #159a15";
        		break;	
        	}
        }
        
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

		    return day + " "+ monthName + " "+ year ;
		}
             
        function distribute(data){
			$('#restrict').val(data[0].isBlocked);
			
        	$('#cid').val(data[0].cid);
        	$('#mobile').val(data[0].mobile);
        	$('#username').val(data[0].username);
        	$('#fullname').val(data[0].lastName +", "+ data[0].firstName +" "+ data[0].middleName);
        	$('#birthday').val(formatDate(data[0].birthday));
        	$("#btn-devices").disable(false);
			$("#btn-reset-password").disable(false);
			$("#btn-change-mobile-number").disable(false);
			$("#btn-reset-credential").disable(false);
        	var acc = "";
        	document.getElementById("accounts").innerHTML = '';
        	if(data[0].hasOwnProperty('accounts')){
	        	for (let i = 0; i < data[0].accounts.length; i++) {
	        		console.log(data[0].accounts[i].instiCode);
	        		document.getElementById("accounts").innerHTML += '<div class="col-md-12">'+
						'<div class="product-banner" style="background-image: linear-gradient(311deg, '+getPantone(data[0].accounts[i].instiCode)+');">'+
							'<div class="product-banner-details">'+
								'<div class="row">'+
									'<div>'+
										'<span class="account-details-label insti-desc" id="agent-'+data[0].accounts[i].instiCode+'">'+getInstitution(data[0].accounts[i].instiCode)+'</span>'+
									
										'<span class="account-details-label" id="agent-'+data[0].accounts[i].instiCode+'">|</span>'+
									'</div>'+
									'<div class="col-md-3">'+
										'<span class="account-details-label" id="agent-'+data[0].accounts[i].instiCode+'">Agent:</span>'+
									
										'<label class="switch" >'+
											'<input type="checkbox"  id="togBtnAgent:'+data[0].accounts[i].instiCode+'" '+isAgentAcc(data[0].accounts[i].isAgent)+'>'+
											'<div class="slider round"></div>'+
										'</label>'+
									'</div>'+
									'<div class="col-md-3">'+
										'<span class="account-details-label" id="status-'+data[0].accounts[i].instiCode+'">Enable:</span>'+
									
										'<label class="switch" >'+
											'<input type="checkbox"  id="togBtnStatus:'+data[0].accounts[i].instiCode+'" '+isEnableAcc(data[0].accounts[i].isEnabled)+'>'+
											'<div class="slider round"></div>'+
										'</label>'+
									'</div>'+
									
									'<div class="col-md-3">'+
										'<span class="account-details-label" id="merchant-'+data[0].accounts[i].instiCode+'">Merchant:</span>'+
										'<label class="switch" >'+
											'<input type="checkbox"  id="togBtnMerchant:'+data[0].accounts[i].instiCode+'" '+isMerchant(data[0].accounts[i].isMerchant)+'>'+
											'<div class="slider round"></div>'+
										'</label>'+
									'</div>'+
								'</div>'+
							'</div>'+
						'</div>'+
					'</div>';
					
	        		for (let j = 0; j < data[0].accounts[i].savings.length; j++){
	        			
	        			console.log(data[0].accounts[i].savings[j].accountNumber);
	        			console.log(data[0].accounts[i].savings[j].accountName);
	        			
	        			document.getElementById("accounts").innerHTML += 	'<div class="col-md-6" >'+
						'<div class="product-card "  style="background-image: linear-gradient(311deg, '+getPantone(data[0].accounts[i].instiCode)+');">'+
							'<div class="product-details">'+
								'<span class="account-name">'+data[0].accounts[i].savings[j].accountName+'</span><span class="account-number">'+data[0].accounts[i].savings[j].accountNumber+'</span>'+
								'<div class="account-bottom-details">'+
									'<div>'+
										'<label class="account-details-label" for="branch-'+data[0].accounts[i].savings[j].accountNumber+'">Branch:</label>'+
										'<span class="account-details"  id="branch-'+data[0].accounts[i].savings[j].accountNumber+'">'+getBranch(data[0].accounts[i].brCode)+'</span>'+
									'</div>'+
									'<div>'+
										'<label class="account-details-label" for="unit-'+data[0].accounts[i].savings[j].accountNumber+'">Unit:</label>'+
										'<span class="account-details"  id="unit-'+data[0].accounts[i].savings[j].accountNumber+'">'+getUnit(data[0].accounts[i].unitCode)+'</span>'+
									'</div>'+
									'<div>'+
										'<label class="account-details-label" for="center-'+data[0].accounts[i].savings[j].accountNumber+'">Center:</label>'+
										'<span class="account-details"  id="center-'+data[0].accounts[i].savings[j].accountNumber+'">'+getCenter(data[0].accounts[i].centerCode)+'</span>'+
									'</div>'+
	
								'</div>'+
								'<div class="row">'+
									'<div class="col-md-6">'+
	
										'<label class="account-details-label" for="accttype:'+data[0].accounts[i].savings[j].accountNumber+'">Type:</label>'+
										'<span class="account-details"  id="accttype-'+data[0].accounts[i].savings[j].accountNumber+'_'+data[0].accounts[i].instiCode+'">'+data[0].accounts[i].clientType+'</span>'+
									'</div>'+
									'<div class="col-md-6">'+
										'<label class="account-details-label" for="acctclass-'+data[0].accounts[i].savings[j].accountNumber+'">Classification:</label>'+
										'<span class="account-details"  id="accclass-'+data[0].accounts[i].savings[j].accountNumber+'_'+data[0].accounts[i].instiCode+'">'+data[0].accounts[i].memberClassification+'</span>'+
									'</div>'+
								'</div>'+
								'<div class="row">'+
									'<div class="col-md-6">'+
										'<span class="account-details-label" id="inner-status:'+data[0].accounts[i].instiCode+'">Enable:</span>'+
										'<label class="switch" >'+
											'<input type="checkbox"  id="togInnerBtnStatus:'+data[0].accounts[i].instiCode+':'+data[0].accounts[i].savings[j].accountNumber+':'+data[0].accounts[i].savings[j].accountName+':'+data[0].accounts[i].savings[j].token+'" '+isMerchant(data[0].accounts[i].savings[j].isEnabled)+'>'+
											'<div class="slider round"></div>'+
										'</label>'+
									'</div>'+
								'</div>'+
							'</div>'+
						'</div>'+
					'</div>';
	        			
	        		}
					
	        	}
        	}
        	if (data[0].isBlocked == '1') {
            		$("#btn-restrict").html('<i class="fa fa-lock"></i> Restricted</a>');
            		$("#btn-restrict").removeClass("btn-primary").addClass("btn-danger");
			} else {
				$("#btn-restrict").html('<i class="fa fa-unlock"></i> Unrestricted</a>');
				$("#btn-restrict").removeClass("btn-danger").addClass("btn-primary");
			}
			$("#btn-restrict").disable(false);
        }
        
        function populate(data) {
        	if (data == null) {
        		$('#id').val('');
        		$('#clentTypeCode').val('');
        		$('#agentFeature').val('');
        		$('#restrict').val('');
        		$('#merchantFeature').val('');
        		$('#cid').val('');
            	$("#fullname").val('');
            	$('#mobileno').val('');
            	$('#accountNumber').val('');
            	$('#accountType').val('');
            	$('#clientType').val('');
            	$('#status').val('');
            	$('#branch').val('');
            	$('#unit').val('');
            	$('#center').val('');
            	$('#birthday').val('');
            	$('#address').val('');
            	$('#enrolled').val('');
            	$('#registered').val('');
            	$('#imei').val('');
            	$('#deviceModelNumber').val('');
            	$('#monthlyAccumulationValue').val('');
            	$('#username').val('');
            	
            	
            	$("#btn-reset-password").disable(true);
            	$("#btn-reset-mpin").disable(true);
            	$("#btn-deactivate").disable(true);
            	$("#btn-restrict").disable(true);
            	$("#btn-view-username").disable(true);
            	$("#btn-agentFeature-enabled").disable(true);
            	$("#btn-resetStatus-enabled").disable(true);
            	$("#btn-blocked").disable(true);
            	$("#btn-merchant-feature").disable(true);
            	$("#btn-update-merchant").disable(true);
            	$("#btn-merchantCode").disable(true);
            	$("#btn-syncronize").disable(true);
            	
            	$('#businessName').val('');
            	$("#businessName").attr('readonly', true);
            	$('#merchantCode').val('');
            	//$('#MQRCodeExpirationDate').val('');
        	} else {
        		$('#id').val(data.id);
        		$('#clentTypeCode').val(data.typeCode);
        		$('#agentFeature').val(data.agentFeature);
        		$('#restrict').val(data.restrict);
        		$('#merchantFeature').val(data.merchantStatus);
        		$('#cid').val(data.cid);
        		$("#fullname").val(data.fullname);
            	$('#mobileno').val(data.mobileNo);
            	$('#accountNumber').val(data.accountNumber);
            	$('#accountType').val(data.accountType);
            	$('#clientType').val(data.typeDesc);
            	$('#status').val(data.accStatusDesc);
            	$('#branch').val(data.branchDesc);
            	$('#unit').val(data.unitDesc);
            	$('#center').val(data.centerDesc);
            	$('#birthday').val(data.dob);
            	$('#address').val(data.address);
            	$('#registered').val(data.registered);
            	$('#imei').val(data.clientImei);
            	$('#deviceModelNumber').val(data.deviceModel);
            	$('#monthlyAccumulationValue').val(data.monthlyAccumulationValue);
            	$('#monthlyAccumulationValue').val('');
            	
            	$("#btn-reset-password").disable(false);
            	$("#btn-reset-mpin").disable(false);
            	$("#btn-deactivate").disable(false);
            	$("#btn-restrict").disable(false);
            	$("#btn-view-username").disable(false);
            	$("#btn-agentFeature-enabled").disable(false);
            	$("#btn-resetStatus-enabled").disable(false);
            	$("#btn-blocked").disable(true);
            	$("#btn-merchant-feature").disable(false);
            	$("#btn-update-merchant").disable(true);
            	$("#btn-merchantCode").disable(true);
            	$("#btn-syncronize").disable(false);
            	
            	$('#businessName').val(data.merchantName);
        		$('#merchantCode').val(data.merchantQrCode);
        		//$('#MQRCodeExpirationDate').val(data.merchantExpiredDate);
            	
            	if (data.restrict == '1') {
            		$("#btn-restrict").html('<i class="fa fa-lock"></i> Restricted</a>');
            		$("#btn-restrict").removeClass("btn-primary").addClass("btn-danger");
        		} else {
        			$("#btn-restrict").html('<i class="fa fa-unlock"></i> Unrestricted</a>');
        			$("#btn-restrict").removeClass("btn-danger").addClass("btn-primary");
        		}
            	
            	if (data.agentFeature == '1') {
            		$("#btn-agentFeature-enabled").html('<i class="fa fa-check"></i> Enable</a>');
            		$("#btn-agentFeature-enabled").removeClass("btn-link").addClass("btn-success");
        		} else {
        			$("#btn-agentFeature-enabled").html('<i class="fa fa-close"></i> Enabling</a>');
        			$("#btn-agentFeature-enabled").removeClass("btn-success").addClass("btn-link");
        		}
            	
            	if (data.accStatusDesc == "Blocked Activation Code" || data.accStatusDesc =="Blocked Reset Credential") {
            		$("#btn-blocked").disable(false);
            		if (data.accStatusDesc == 'Blocked Activation Code') {
                		$("#btn-blocked").html('<i class="fa fa-unlock"></i> Unblock AC</a>');
                		$("#btn-blocked").removeClass("btn-danger").addClass("btn-danger");
            		} else {
            			$("#btn-blocked").html('<i class="fa fa-unlock"></i> Unblock RC</a>');
            			$("#btn-blocked").removeClass("btn-danger").addClass("btn-danger");
            		}
            	}
            	
				console.log(data.merchantStatus)
            	if (data.merchantStatus == '1') {    		
	        		$("#btn-merchant-feature").html('<i class="fa fa-check"></i> Disable Merchant Feature</a>');
            		$("#btn-merchant-feature").removeClass("btn-warning").addClass("btn-primary");
            		$("#businessName").attr('readonly', false);
            		$("#btn-update-merchant").disable(false);
            		if (data.merchantQrCode == null) { 
            			$("#btn-merchantCode").disable(true);
            		} else{
            			$("#btn-merchantCode").disable(false);
            		}
        		} else {
            		$("#btn-merchant-feature").html('<i class="fa fa-close"></i> Enabling Merchant Feature</a>');
        			$("#btn-merchant-feature").removeClass("btn-primary").addClass("btn-warning");
        			$("#businessName").attr('readonly', true);
        			$("#btn-update-merchant").disable(true);
        			$("#btn-merchantCode").disable(true);
        		}
    
        	}
        }
		
		function showConfirmation(msg, style, icon, label, url, val){
			let uname = $("#username").val();
			let cid = $("#cid").val();

			var type = BootstrapDialog.TYPE_DEFAULT;
			
			if (style == 'info'){
				type = BootstrapDialog.TYPE_INFO;
			} else if (style == 'primary'){
				type = BootstrapDialog.TYPE_PRIMARY;
			} else if (style == 'warning'){
				type = BootstrapDialog.TYPE_WARNING;
			} else if (style == 'danger'){
				type = BootstrapDialog.TYPE_DANGER;
			} else if (style == 'success'){
				type = BootstrapDialog.TYPE_SUCCESS;
			}
			
			BootstrapDialog.show({
            	title	: 'Confirmation',
                message	: msg,
                type	: type,
                buttons	: [
                       	   {
                       			label	: '<i class="fa fa-times"></i> Cancel',
                       			cssClass: 'btn-default',
                    			action	: function(dialog){
                    				dialog.close();
                                }
                			},
                			{
                       			label	: '<i class="fa fa-' + icon + '"></i> ' + label,
                       			cssClass: 'btn-' + style,
                    			action	: function(dialog){
									if(url === 'enableMerchantFeature'){
										showModalUpdateBusinessName("floppy-o", "Updated",url);
									}else{
										console.log("")
										sendAction(url, val, uname, cid);
									}
                    				dialog.close(); 
                                }
                			}
                ]
            });
		}
		
		function showModalUpdateBusinessName(icon, label, url){
			

			BootstrapDialog.show({
            title: 'Merchant Business Name',
            message: $('<input type="text" class="form-control" placeholder="Business Name" maxlength="25"/>'),
			closable: false,
            buttons: [{
                label	: '<i class="fa fa-' + icon + '"></i> ' + label,
                cssClass: 'btn-primary',
                action: function(dialog) {
					dialog.close();
					var bn = dialog.getModalBody().find('input').val()
					if(checkForSpecialChar(bn)){
						 BootstrapDialog.alert({
							title: 'WARNING',
							message: 'Merchant Business Name contains special character.',
							type: BootstrapDialog.TYPE_WARNING, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
							closable: false, // <-- Default value is false
							buttonLabel: '<i class="fa fa-close"></i> Close', // <-- Default value is 'OK',
							callback: function(result) {
								if(result){
									showModalUpdateBusinessName("floppy-o", "Updated", url);
								}
							}
						});
					} else {
						showConfirmationUpdateBusinessName("Are you sure want to Update Merchant Business Name and Enable Merchant Feature?", "info", "floppy-o", "Updated", bn, url);
					}
                }
            }]
        });
		}
		
		function showConfirmationUpdateBusinessNameDirect(msg, style, icon, label){
			
			var type = BootstrapDialog.TYPE_DEFAULT;
			 
			if (style == 'info'){
				type = BootstrapDialog.TYPE_INFO;
			} else if (style == 'primary'){
				type = BootstrapDialog.TYPE_PRIMARY;
			} else if (style == 'warning'){
				type = BootstrapDialog.TYPE_WARNING;
			} else if (style == 'danger'){
				type = BootstrapDialog.TYPE_DANGER;
			} else if (style == 'success'){
				type = BootstrapDialog.TYPE_SUCCESS;
			}
			
			BootstrapDialog.show({
            	title	: 'Confirmation',
                message	: msg,
                type	: type,
				closable: false,
                buttons	: [
                       	   {
                       			label	: '<i class="fa fa-times"></i> Cancel',
                       			cssClass: 'btn-default',
                    			action	: function(dialog){
                    				dialog.close();
                                }
                			},
                			{
                       			label	: '<i class="fa fa-' + icon + '"></i> ' + label,
                       			cssClass: 'btn-' + style,
                    			action	: function(dialog){
									dialog.close();
									sendActionUpdatedNameDirect();
									$("#btn-merchantCode").disable(false);
                                }
                			}
                ]
            });
		}
		
		function showConfirmationUpdateBusinessName(msg, style, icon, label, value, url){
			
			var type = BootstrapDialog.TYPE_DEFAULT;
			 
			if (style == 'info'){
				type = BootstrapDialog.TYPE_INFO;
			} else if (style == 'primary'){
				type = BootstrapDialog.TYPE_PRIMARY;
			} else if (style == 'warning'){
				type = BootstrapDialog.TYPE_WARNING;
			} else if (style == 'danger'){
				type = BootstrapDialog.TYPE_DANGER;
			} else if (style == 'success'){
				type = BootstrapDialog.TYPE_SUCCESS;
			}
			
			BootstrapDialog.show({
            	title	: 'Confirmation',
                message	: msg,
                type	: type,
				closable: false,
                buttons	: [
                       	   {
                       			label	: '<i class="fa fa-times"></i> Cancel',
                       			cssClass: 'btn-default',
                    			action	: function(dialog){
                    				dialog.close();
									if(value === null || value === ""){
										BootstrapDialog.alert({
										title: 'WARNING',
										message: 'Please input business name first.',
										type: BootstrapDialog.TYPE_WARNING, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
										closable: false, // <-- Default value is false
										buttonLabel: '<i class="fa fa-close"></i> Close', // <-- Default value is 'OK',
										callback: function(result) {
											if(result){
												showModalUpdateBusinessName("floppy-o", "Updated", url);
											}
										}
										});
									}
                                }
                			},
                			{
                       			label	: '<i class="fa fa-' + icon + '"></i> ' + label,
                       			cssClass: 'btn-' + style,
                    			action	: function(dialog){
									dialog.close();
									if(value === null || value === ""){
										BootstrapDialog.alert({
										title: 'WARNING',
										message: 'Please input business name first.',
										type: BootstrapDialog.TYPE_WARNING, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
										closable: false, // <-- Default value is false
										buttonLabel: '<i class="fa fa-close"></i> Close', // <-- Default value is 'OK',
										callback: function(result) {
											if(result){
												showModalUpdateBusinessName("floppy-o", "Updated", url);
											}
										}
										});
									}else{
										if(url !== ""){
											sendAction(url, value);
										}	 
									}
                                }
                			}
                ]
            });
		}
		
		function sendActionUpdatedNameDirect(){
			var id = $('#id').val();
			var businessName = "";

			businessName = $('#businessName').val();
			 
			businessName = businessName.replace(".", "-");
			var dataParsing = $('#id').val() + "|" + businessName;

			 $.ajax({
				url		: "${pageContext.request.contextPath}/monitoring/profile/updateMerchant/" + dataParsing,
               	cache	: false,
               	success	: function(response){
               		showMessage(response);
               		if (response.success) {
               			data = {field:"id", number:id};
        				retrieve('/monitoring/profile/get', JSON.stringify(data), function (data) {
        					populate(data);
                        });
               		}
               	}
         	}); 
		}
		
		function sendActionUpdatedName(value){
			var id = $('#id').val();
			var businessName = "";
			if(value !== ""){
				businessName = value;
			}else{
				businessName = $('#businessName').val();
			}
			 
			businessName = businessName.replace(".", "-");
			var dataParsing = $('#id').val() + "|" + businessName;

			 $.ajax({
				url		: "${pageContext.request.contextPath}/monitoring/profile/updateMerchant/" + dataParsing,
               	cache	: false,
               	success	: function(response){
               		this.updateMerchanBusinessNameDone = true;
               		if (response.success) {
               			data = {field:"id", number:id};
						$('#businessName').val(value);
        				retrieve('/monitoring/profile/get', JSON.stringify(data), function (data) {
        					populate(data);
                        });
						
						BootstrapDialog.show({
							title: 'Success',
							message: 'Update merchant business name and enable merchant feature success.',
							type	: BootstrapDialog.TYPE_SUCCESS
							});
               		}else{
						disableMerchant("disableMerchantFeature");
						showMessage(response);
					}
               	}
         	}); 
		}
		
		function disableMerchant(action){
			var id = $('#id').val();
			$.ajax({
				url		: "${pageContext.request.contextPath}/monitoring/profile/" + action + '/' + id,
               	cache	: false,
               	success	: function(response){
               		if (response.success) {
               			data = {field:"id", number:id};

        				retrieve('/monitoring/profile/get', JSON.stringify(data), function (data) {
        					populate(data);
                        });
               		}
               	}
         	});
		}
		
		function sendAction(action, value,username,cid){
			console.log("action:" + action)	
			console.log("value:" + value)	
			console.log("username:" + username)	
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/monitoring/profile/'+action,
				async : true,
				data:{
					cid:cid,
					username:username,
					value:value,
				},
				success: function(data){
					console.log(data);
					
				},
				error: function(data){
					console.log(data);
				}
		
			}).done(function(rs, textStatus, xhr) {
						console.log("Done Response: " + rs)
						
						if(xhr.status == 200){
							var response;
							try{
								response = JSON.parse(rs);
								if(response.hasOwnProperty('message')){
									App.alert_inside_modal({container:'klikAlertMessageModalUpdateMobile',type	: 'success', message	: response.message, icon	: 'success'});
								}else{
									App.alert_inside_modal({container:'klikAlertMessageModalUpdateMobile',type	: 'success', message	: "Mobile number successfully changed", icon	: 'success'});
									$("#mobile").val(newMobile);
								}
								
							}catch(e){
								App.alert_inside_modal({container:'klikAlertMessageModalUpdateMobile',type	: 'success', message	: "Unable to parse JSON", icon	: 'success'});
							}
							
						}else{
							response = JSON.parse(rs);
							App.alert_inside_modal({container:'klikAlertMessageModalUpdateMobile',type	: 'error', message	: response.message , icon	: 'error'});
						}
						
					});
		}

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
			<div class="col-lg-6 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Monitoring</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/monitoring/profile/">Client
								Profile</a></li>
					</ul>
					<h3>
						<i class="fa fa-user-secret"></i> Client Profile
					</h3>
					<em>Monitoring</em>
				</div>
			</div>
		</div>

		<form class="form-horizontal" role="form" method="post" name="profile">
			<div class="row">
				<div class="col-md-12">
					<div class="widget">
						<div class="widget-header">
							<h3>
								<i class="fa fa-user-secret"></i> Client Profile
							</h3>
						</div>

						<input type="hidden" id="id"> 
						<input type="hidden" id="clentTypeCode"> 
						<input type="hidden" id="restrict">
						<input type="hidden" id="agentFeature"> 
						<input type="hidden" id="merchantFeature"> 
						<input type="hidden" id="merchantCode"> 
						<input type="hidden" id="buttonStatus">

						<!-- The Modal -->
						<div id="myModal" class="modalQr">
							<div class="modal-header-qr">
								<span class="closeQr">&times;</span>
								<div id="modalTitleQr"></div>
							</div>
							<div class="modal-body-qr">
								<img class="modal-content-qr" id="img01">
								<div id="captionQr"></div>
							</div>
						</div>

						<div class="widget-content form">
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Customer I.D.</label>
									<div class="col-md-4">
										<input id="cid" type="text" class="form-control"
											placeholder="Customer I.D." />
									</div>

									<div class="col-sm-offset-1 col-md-3">
										<button id="btn-devices" type="button"
											class="btn btn-warning btn-block center-icon-holder" disabled>
											<i class="fas fa-mobile-alt"></i> Devices
										</button>
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Username</label>
									<div class="col-md-4">
										<input id="username" type="text" class="form-control"
											placeholder="Username" />
									</div>

									<div class="col-sm-offset-1 col-md-3">
										<button id="btn-reset-password" type="button"
											class="btn btn-warning btn-block center-icon-holder" disabled>
											<i class="fas fa-mobile-alt"></i> Reset Password
										</button>
									</div>
								</div>
							</div>
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Mobile No.</label>
									<div class="col-md-4">
										<input id="mobile" type="text" class="form-control"
											placeholder="Mobile No." />
									</div>

									<div class="col-sm-offset-1 col-md-3">
										<button id="btn-change-mobile-number" type="button"
											class="btn btn-warning btn-block center-icon-holder" disabled>
											<i class="fas fa-mobile-alt"></i> Change Mobile Number
										</button>
									</div>
								</div>
							</div>

							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Full Name</label>
									<div class="col-md-4">
										<input id="fullname" type="text" class="form-control"
											readonly="readonly" placeholder="Full Name" />
									</div>

									<div class="col-sm-offset-1 col-md-3">
										<button id="btn-reset-credential" type="button"
											class="btn btn-warning btn-block center-icon-holder" disabled>
											<i class="fas fa-mobile-alt"></i> Reset Credential
										</button>
									</div>
								</div>
							</div>

							<div class="form-body">
								<div class="form-group">
									<label class="col-md-3 control-label">Birthday</label>
									<div class="col-md-4">
										<input id="birthday" type="text" class="form-control"
											placeholder="Birthday" readonly="readonly" />
									</div>
									<div class="col-sm-offset-1 col-md-3">
										<button id="btn-restrict" type="button"
											class="btn btn-primary btn-block center-icon-holder" disabled>
											
										</button>
									</div>
									<div class="col-sm-offset-1 col-md-3">
										<!-- <button id="btn-update-merchant" type="button" class="btn btn-info btn-block left-icon-holder" disabled><i class="fa fa-floppy-o"></i> Update Merchant Business Name</button> -->
									</div>
								</div>
							</div>
							

							<!--  <div class="form-body">
	                        <div class="form-group">
                                <label class="col-md-3 control-label">Enrolled Account Number</label>
                                <div class="col-md-4">
                                    <input id="accountNumber" type="text" class="form-control" placeholder="Enrolled Account Number"/>
                                </div>
                                
                                <div class="col-sm-offset-1 col-md-3">
                                	<button id="btn-deactivate" type="button" class="btn btn-danger btn-block left-icon-holder" disabled><i class="fa fa-times-circle"></i> Deactivate</button>
                                </div>
                            </div>
	                    </div> -->
							<!-- <div class="form-body">
	                        <div class="form-group">
                                <label class="col-md-3 control-label">Address</label>
                                <div class="col-md-4">
                                	<textarea id="address" class="form-control" rows="3" readonly="readonly" placeholder="Address"></textarea>
                                </div>
								<label class="col-sm-offset-1 col-md-3 control-label">Business Name</label>
                                <div class="col-sm-offset-1 col-md-3">
                                    <input id="businessName" type="text" class="form-control" placeholder="Business Name" maxlength="25" readonly="readonly"/>
                                </div>
                            </div>
	                    </div> -->
							<div class="form-body">
								<div class="form-group">
									<div class="row">
										<div id="accounts"></div>
									</div>
								</div>
							</div>
						</div>

						<!--  <div class="form-body">
	                        <div class="form-group">
                                 <label class="col-md-3 control-label">Type of enrolled savings account</label>
                                 <div class="col-md-4">
                                    <input id="accountType" type="text" class="form-control" placeholder="Type of enrolled savings account" readonly="readonly"/>
                                 </div>
                                
                                 <div class="col-sm-offset-1 col-md-3">
                                	<button id="btn-restrict" type="button" class="btn btn-primary btn-block left-icon-holder" disabled><i class="fa fa-lock"></i> Restrict/Unrestrict</button>
                                </div>
                            </div>
	                    </div> -->



						<!--   <div class="form-body">
	                        <div class="form-group">
                                <label class="col-md-3 control-label">Client Type</label>
                                <div class="col-md-4">
                                    <input id="clientType" type="text" class="form-control" placeholder="Client Type" readonly="readonly"/>
                                </div>
                                <div class="col-sm-offset-1 col-md-3">
                                	<button id="btn-agentFeature-enabled" type="button" class="btn btn-success btn-block left-icon-holder" disabled><i class="fa fa-check"></i> Agent Feature (Enabled/Disabled)</button>
                                </div>
                            </div>
	                    </div>
	                    <div class="form-body">
	                        <div class="form-group">
                                <label class="col-md-3 control-label">Account Status</label>
                                <div class="col-md-4">
                                    <input id="status" type="text" class="form-control" placeholder="Account Status" readonly="readonly"/>
                                </div>
                                <div class="col-sm-offset-1 col-md-3">
                                	<button id="btn-blocked" type="button" class="btn btn-danger btn-block left-icon-holder" disabled><i class="fa fa-unlock"></i> Unblock Account</button>
                                </div>
                            </div>
	                    </div>
	                    <div class="form-body">
	                        <div class="form-group">
                                <label class="col-md-3 control-label">Branch</label>
                                <div class="col-md-4">
                                    <input id="branch" type="text" class="form-control" placeholder="Branch" readonly="readonly"/>
                                </div>
                                 <div class="col-sm-offset-1 col-md-3">
                                	<button id="btn-syncronize" type="button" class="btn btn-info btn-block left-icon-holder" disabled><i class="fa fa-refresh"></i> Synchronize</button>
                                </div>
                            </div>
	                    </div>
	                    <div class="form-body">
	                        <div class="form-group">
                                <label class="col-md-3 control-label">Unit</label>
                                <div class="col-md-4">
                                    <input id="unit" type="text" class="form-control" placeholder="Unit" readonly="readonly"/>
                                </div>
                            </div>
	                    </div>
	                    <div class="form-body">
	                        <div class="form-group">
                                <label class="col-md-3 control-label">Center</label>
                                <div class="col-md-4">
                                    <input id="center" type="text" class="form-control" placeholder="Center" readonly="readonly"/>
                                </div>
                                
                                <div class="col-sm-offset-1 col-md-3">
                                	<button id="btn-merchant-feature" type="button" class="btn btn-primary btn-block left-icon-holder" disabled><i class="fa fa-shopping-bag"></i> Merchant Feature (Enabled/Disabled)</button>
                                </div>
                            </div>
	                    </div> -->


						<!-- <div class="form-body">
	                    	<div class="form-group">
                                <label class="col-md-3 control-label">Date Registered</label>
                                <div class="col-md-4">
                                    <input id="registered" type="text" class="form-control" placeholder="Date Registered" readonly="readonly"/>
                                </div>
	                    
	                     		<label class="col-sm-offset-1 col-md-3 control-label">MQR Code Expiration date</label>
                                <div class="col-sm-offset-1 col-md-3">
                                    <input id="MQRCodeExpirationDate" type="text" class="form-control" placeholder="MQR Code Expiration date" readonly="readonly"/>
                                </div>
                        	</div>
	                    </div> -->
						<!--  <div class="form-body">
	                    	<div class="form-group">
                                <label class="col-md-3 control-label">IMEI (Device I.D)</label>
                                <div class="col-md-4">
                                    <input id="imei" type="text" class="form-control" placeholder="Imei (Device I.D)" readonly="readonly"/>
                                </div>
                                
                                <div class="col-sm-offset-1 col-md-3">
                                	<button id="btn-merchantCode" type="button" class="btn btn-success btn-block left-icon-holder" disabled><i class="fa fa-search-plus"></i> View Merchant QR Code</button>
                                </div>
                        	</div>
	                    </div> -->
						<!--  <div class="form-body">
	                        <div class="form-group">
                                <label class="col-md-3 control-label">Device Model Number</label>
                                <div class="col-md-4">
                                    <input id="deviceModelNumber" type="text" class="form-control" placeholder="Device Model Number" readonly="readonly"/>
                                </div>
                            </div>
			   
	                    </div> -->

						<!--  <div class="form-body">
	                        <div class="form-group">
                                <label class="col-md-3 control-label">Monthly Accumulation Value</label>
                                <div class="col-md-4">
                                    <input id="monthlyAccumulationValue" type="text" class="form-control" placeholder="Monthly Accumulation Value" readonly="readonly"/>
                                </div>
                            </div>
			   
	                    </div> -->
					</div>

				</div>
			</div>
		</form>
	</div>
	<div class="modal fade" id="modal-list-device" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header text-center">Devices</div>

				<div class="modal-body">
					<div class="widget widget-table">
						<div class="widget-header">
							<h3>
								<i class="fa fa-table"></i> List of Devices
							</h3>
						</div>
						<div class="widget-content">
							<!-- top general alert -->
							<div id="klikAlertMessageModalListDevice"></div>
							<!-- end top general alert -->
							<table class="display nowrap" id="tableDevices">
								<thead>
									<tr>
										<th>Device ID</th>
										<th>Device Model</th>
										<th>Date Added</th>
										<th>Last Active</th>
										<th>Block/Restrict</th>
										<th>Active</th>
									</tr>
								</thead>

								<tbody id="tableDevicesBody">

								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="modal-footer text-right">
					<a class="btn btn-default" type="button" data-dismiss="modal"><i
						class="fa fa-times"></i>Close</a>
					<!-- 	                <a id="btn-reset-password" class="btn btn-danger" type="button" data-dismiss="modal"><i class="fa fa-minus-circle"></i> Reset Password</a>
 -->
				</div>
			</div>
		</div>
	</div>


	<div class="modal fade" id="modal-reset-password" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div id="klikAlertMessageModal"></div>
				<div class="modal-header text-center">Reset Password</div>

				<div class="modal-body">
					<div class="widget widget-table">

						<div class="widget-content">
							<p>Are you sure want to Reset Client Password?</p>
						</div>
					</div>
				</div>
				<div class="modal-footer text-right">
					<a class="btn btn-default" id="confirm-reset-password"
						type="button">Reset</a> <a class="btn btn-default" type="button"
						data-dismiss="modal"><i class="fa fa-times"></i>Close</a>
					<!-- 	                <a id="btn-reset-password" class="btn btn-danger" type="button" data-dismiss="modal"><i class="fa fa-minus-circle"></i> Reset Password</a>
 -->
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="modal-change-mobile-number" tabindex="-1"
		role="dialog" aria-labelledby="mobileNumberLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header text-center">Change Mobile Number</div>

				<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<div class="widget">
									<!-- top general alert -->
									<div id="klikAlertMessageModalUpdateMobile"></div>
									<div class="widget-content" id="updateMobileNumberWidget" name="updateMobileNumberWidget">
										
										<div class="form-body">
											<div class="form-group">
												<label class="col-md-3 control-label">Mobile No.<span
													class="required">*</span></label>
												<div class="col-md-4">
													<input id="updateMobileNumber" name="updateMobileNumber" 
													type="number" class="form-control required phoneUS" placeholder="Mobile No." required />
												</div>
			
												<div class="col-sm-offset-1 col-md-3">
													<button id="btn-save-mobile-number" type="button"
														class="btn btn-warning btn-block center-icon-holder">
														<i class="fas fa-mobile-alt"></i> Save
													</button>
												</div>
											</div>
										</div>
									</div>
			
								</div>
			
							</div>
						</div>
				</div>
				<div class="modal-footer text-right">
					
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="modal-reset-credential" tabindex="-1"
	role="dialog" aria-labelledby="resetCredentialLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header text-center">Reset Credential</div>

			<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
							<div class="widget">
								<!-- top general alert -->
								<div id="klikAlertMessageModalResetCredential"></div>
								<div class="widget-content" id="resetCredentialWidget" name="resetCredentialWidget">
									
									<div class="form-body">
										<div class="form-group">
											<label class="col-md-3 control-label">Username<span
												class="required">*</span></label>
											<div class="col-md-7">
												<input id="updateUsername" name="updateUsername" 
												type="text" class="form-control required" placeholder="new username" autocomplete="off" required />
											</div>

											<div class="form-body">
												<div class="form-group">
													<label class="col-md-3 control-label">Password<span
														class="required">*</span></label>
													<div class="col-md-7">
														<input id="updatePassword" name="updatePassword" 
														type="password" class="form-control" placeholder="new password" autocomplete="off" required />
													</div>
		
											<div class="col-sm-offset-2 col-md-8">
												<button id="btn-save-reset-credential" type="button"
													class="btn btn-warning btn-block center-icon-holder">
													<i class="fas fa-mobile-alt"></i> Save
												</button>
											</div>
										</div>
									</div>
								</div>
		
							</div>
		
						</div>
					</div>
			</div>
			<div class="modal-footer text-right">
				
			</div>
		</div>
	</div>
</div>
</body>

</html>