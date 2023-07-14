<%@ page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9 ]><html class="ie ie9" lang="en" class="no-js"> <![endif]-->
<!--[if !(IE)]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->

<head>
<title><decorator:title default="konek2CARD PLUS Webtool " /></title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />

<link href="${pageContext.request.contextPath}/assets/img/favicon.ico"
	rel="shortcut icon">

<!-- CSS -->
<link
	href="${pageContext.request.contextPath}/assets/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/assets/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/assets/plugins/bootstrap-toastr/toastr.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/assets/plugins/bootstrap-dialog/bootstrap-dialog.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/assets/plugins/select2/css/select2.min.css"
	rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/main.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/assets/css/plugins.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/assets/css/jquery.dataTables.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/assets/css/custom-style.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/assets/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen" />

<script
	src="${pageContext.request.contextPath}/assets/themes/kingadmin/js/jquery/jquery-2.1.0.min.js"></script>

</head>

<body>

	<!-- WRAPPER -->
	<div class="wrapper">

		<!-- TOP BAR -->
		<div class="top-bar">
			<div class="line"></div>
			<div class="container">
				<div class="row">
					<!-- logo -->
					<div class="col-md-6 logo">
						<a href="${pageContext.request.contextPath}/"><img
							src="${pageContext.request.contextPath}/assets/img/konek2card.png"
							height="75px" alt="logo" /></a>
					</div>
					<!-- end logo -->
					<div class="col-md-6">
						<div class="row">
							<!-- logged user and the menu -->
							<div class="logged-user">
								<div class="btn-group">
									<a href="javascript:void(0);"
										class="btn btn-link dropdown-toggle" data-toggle="dropdown">
										<img
										src="${pageContext.request.contextPath}/assets/img/avatar.png"
										class="img-circle" alt="User Avatar"> <span class="name">${loginSecUser.givenName}</span>
										<span class="caret"></span> <span id="uname"
										style="display: none">${loginSecUser.usrLoginname}</span>
									</a>
									<ul class="dropdown-menu" role="menu">
										<li><a
											href="${pageContext.request.contextPath}/password/change">
												<i class="fa fa-lock"></i> <span class="text">Change
													Password</span>
										</a></li>
										<li><a href="${pageContext.request.contextPath}/logout">
												<i class="fa fa-power-off"></i> <span class="text">Logout</span>
										</a></li>
									</ul>
								</div>
							</div>
							<!-- end logged user and the menu -->
						</div>
						<!-- /row -->
					</div>
				</div>
				<!-- /row -->
			</div>
			<!-- /container -->
		</div>
		<!-- /top -->

		<!-- BOTTOM: LEFT NAV AND RIGHT MAIN CONTENT -->
		<div class="bottom">
			<div class="container">
				<div class="row menuside">
					<!-- left sidebar -->
					<div class="col-md-2 left-sidebar">

						<!-- main-nav -->
						<nav class="main-nav">
							<ul class="main-menu">
								<c:forEach items="${menuList}" var="menuModel">
									<sec:authorize access="hasAnyRole('${menuModel.menu.name}')">
										<li id="menu-${menuModel.menu.name}"><c:choose>
												<c:when test="${fn:length(menuModel.childs) gt 0}">
													<a href="javascript:void(0);" class="js-sub-menu-toggle">
														<i class="fa fa-${menuModel.menu.icon} fa-fw"></i><span
														class="text">${menuModel.menu.description}</span> <i
														class="toggle-icon fa fa-angle-left"></i>
													</a>
													<ul class="sub-menu ">
														<c:forEach items="${menuModel.childs}"
															var="menuChildModel">
															<li id="sub-menu-${menuChildModel.menu.name}"><a
																href="${pageContext.request.contextPath}${menuChildModel.menu.url}">
																	<i class="fa fa-${menuChildModel.menu.icon} fa-fw"></i><span
																	class="text">${menuChildModel.menu.description}</span>
															</a></li>
														</c:forEach>
													</ul>
												</c:when>
												<c:otherwise>
													<a
														href="${pageContext.request.contextPath}${menuModel.menu.url}">
														<i class="fa fa-${menuModel.menu.icon} fa-fw"></i><span
														class="text">${menuModel.menu.description}</span>
													</a>
												</c:otherwise>
											</c:choose></li>
									</sec:authorize>
								</c:forEach>
							</ul>
						</nav>
						<!-- /main-nav -->

						<div class="sidebar-minified js-toggle-minified">
							<i class="fa fa-angle-left"></i>
						</div>

					</div>
					<!-- end left sidebar -->

					<!-- top general alert -->
					<div id="klikAlertMessage"></div>
					<!-- end top general alert -->

					<!-- content-wrapper -->
					<div class="col-md-10 content-wrapper">
						<c:if test="${err_msg != null}">
							<div class="alert alert-danger">
								<span> ${err_msg} </span>
							</div>
						</c:if>
						<c:if test="${msg != null}">
							<div class="alert alert-success">
								<span> ${msg} </span>
							</div>
						</c:if>
						<decorator:body />
						<div id="loader"
							style="position: fixed; bottom: 10px; right: 20px; display: none;">
							<img
								src="${pageContext.request.contextPath}/assets/img/loading-cube.gif" />
						</div>
					</div>
					<!-- /content-wrapper -->
				</div>
				<!-- /row -->
			</div>
			<!-- /container -->
		</div>
		<!-- END BOTTOM: LEFT NAV AND RIGHT MAIN CONTENT -->
	</div>
	<!-- /wrapper -->

	<!-- FOOTER -->
	<footer class="footer">
		<div class="copyright">2021 &copy;Card Bank</div>
		<div class="copyright version">Version 1.0.${buildNumber} build
			20150704.1727</div>
	</footer>
	<!-- END FOOTER -->

	<!-- Javascript -->
	
	<script
		src="${pageContext.request.contextPath}/assets/plugins/jquery-migrate-1.2.1.min.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/assets/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/assets/plugins/jquery.blockui.min.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/assets/plugins/jquery.cokie.min.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/assets/plugins/jquery-validation/dist/jquery.validate.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/plugins/jquery-validation/dist/additional-methods.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/themes/kingadmin/js/plugins/datatable/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/plugins/data-tables/DT_bootstrap.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/plugins/bootstrap-toastr/toastr.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/themes/kingadmin/js/plugins/modernizr/modernizr.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/themes/kingadmin/js/plugins/bootstrap-tour/bootstrap-tour.custom.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/themes/kingadmin/js/plugins/bootstrap-multiselect/bootstrap-multiselect.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/themes/kingadmin/js/king-common.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/plugins/bootstrap-dialog/bootstrap-dialog.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/plugins/underscore-min.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/plugins/select2/js/select2.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/core/app.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/js/custom/components-pickers.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/js/jquery.serialize-object.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/js/popup_selection.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/js/popup_upload.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/js/popover_selection.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/custom.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/formatter.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/js/validation.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/moment.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/js/formatUtil.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/date.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/js/jquery.number.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/js/bootstrap-datetimepicker.js"
		charset="UTF-8"></script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>


	<script type="text/javascript">
		var contextPath = '${pageContext.request.contextPath}';
		var fnBlank = function() { return null; };
		
        $(document).ready(function() {
        	$("#menu-${SELECTED_MENU}").addClass("active");
        	$("#menu-${SELECTED_MENU}").find("ul").addClass("open");
        	$("#menu-${SELECTED_MENU}").find(' > a .toggle-icon').removeClass('fa-angle-left').addClass('fa-angle-down');
        	
            $("#sub-menu-${SELECTED_SUBMENU}").addClass("active");
            
            if('${forceChangePasswd}' == 'true'){
            	document.location.href = contextPath;
    		}
        });
        
        $.extend( $.fn.dataTable.defaults, {
            "bFilter": false,
            "bSort": true,
            "aaSorting": [],
            "bLengthChange": false,
            "bPaginate": true,
            "bProcessing": true,
            "bServerSide": true,
            "sServerMethod": "POST",
            "fnServerData": function (sSource, aoData, fnCallback) {
                aoData.push({ "name": "field", "value": $.trim($("#field").val()) });
                aoData.push({ "name": "value", "value": $.trim($("#value").val()) });
                jQuery.ajax({
                    "dataType": 'json',
                    "type": "POST",
                    "url": sSource,
                    "data": aoData,
                    "success": fnCallback
                });
            }
        });
        
        function isJson(str) {
        	try {
            	JSON.parse(str);
        	} catch (e) {
            	return false;
        	}
        	return true;
    	}
        
        function submit(url, data, handler) {
            $.ajax({
                contentType: (isJson(data)? 'application/json':'application/x-www-form-urlencoded'),
                type : 'POST',
                processData: false,
                url: contextPath + url,
                data: data,
                beforeSend: function(){
                    App.blockUI({target:'.wrapper', boxed: true});
                    $("#loader").show();
                },
                success: function(response){
                    if (response.success) {
                        handler(response.data);
                        toastr['success']( response.message || 'Success' );
                    } else {
                        App.alert({type: 'danger', message: response.message, icon: 'warning'});
                    }
                },
                error: function(jqXHR, status, error){
                    console.log(status);
                    console.log(error);
                    App.alert({
                        type: 'danger',
                        message: status.substr(0, 1).toUpperCase() + status.substr(1) + '. ' + error,
                        icon: 'warning'
                    });
                },
                complete: function() {
                    setTimeout(function(){
                        App.unblockUI('.wrapper');
                        $("#loader").hide();
                    }, 1000); // for demo blockUI
                }
            });
        }
        
        function retrieve(url, data, handler) {
            $.ajax({
                contentType	: (isJson(data)? 'application/json':'application/x-www-form-urlencoded'),
                type 		: 'POST',
                processData	: false,
                url			: contextPath + url,
                data		: data,
                beforeSend	: function() {
                    App.blockUI({target:'.wrapper', boxed: true});
	                $("#loader").show();
                },
                success: function(response){
                	console.log(response)
                    if (response.success) {
                        handler(response.data);
                    } else {
                    	
                    	handler(null);
                    	toastr['warning']( response.message || 'Warning' );
                    }
                },
                error: function(jqXHR, status, error){
                    App.alert({type	: 'danger', message	: status.substr(0, 1).toUpperCase() + status.substr(1) + '. ' + error, icon	: 'warning'});
                },
                complete: function() {
                    setTimeout(function(){
                        App.unblockUI('.wrapper');
                        $("#loader").hide();
                    }, 1000);
                }
            });
        }
        
        jQuery(document).ready(function() {
            App.init();
            ComponentsPickers.init();

            $('.datepicker').datepicker({
                format: 'dd-M-yyyy',
                autoclose: true,
                clearBtn: true,
                todayBtn: true
            });

            $('.datepicker-month').datepicker({
                clearBtn: true,
                autoclose: true,
                format: "M-yyyy",
                startView: "months",
                minViewMode: "months",
                startDate: '+1m'
            });
            
            $('.datepicker-month-all').datepicker({
                clearBtn: true,
                autoclose: true,
                format: "M-yyyy",
                startView: "months",
                minViewMode: "months"
            });
            
            
    		$('.startdatepicker').datepicker({ 
                format: 'dd-M-yyyy',
                autoclose: true,
                clearBtn: true,
                todayBtn: true
    		});
    		$('.startdatepicker').on('changeDate', function(e) {
    			$( '.enddatepicker' ).val('');
    		});

    		$('.enddatepicker').datepicker({ 
                format: 'dd-M-yyyy',
                autoclose: true,
                clearBtn: true,
                todayBtn: true
    		});
    		$('.enddatepicker').on('show', function(e) {
                $( '.enddatepicker' ).datepicker( 'setStartDate', $('.startdatepicker').datepicker( 'getDate') );
    		});

    		$('.timepicker').datetimepicker({
    			format		: 'hh:ii',
                autoclose	: true,
                startView	: 1,
                maxView		: 2,
                pickDate: false,
    		}).on("show", function(){
    			$(".table-condensed thead").css('visibility', 'hidden');
    		});
    		
    		
            $('form').bind('reset', function() {
                $.each($(this).find(":input"), function(){
                    $(this).parent().removeClass('checked');
                });
    			$('.previewImage').attr('src', noImage);
            });

        });
        
        $('.role-menu .js-sub-menu-toggle').click( function(e){

    		$div = $(this).parent('div');
    		if( !$div.hasClass('active')){
    			$div.find(' > a .menu-icon').removeClass('fa-plus-square-o').addClass('fa-minus-square-o');
    			$div.addClass('active');
    		} else {
    			$div.find(' > a .menu-icon').removeClass('fa-minus-square-o').addClass('fa-plus-square-o');
    			$div.removeClass('active');
    		} 

    		$div.find(' > .sub-role-menu').slideToggle(300);
    	});
        
        
        
        jQuery.fn.extend({
		    disable: function(state) {
		        return this.each(function() {
		            this.disabled = state;
		        });
		    }
		});
        
		function showMessage(response){
			BootstrapDialog.show({
            	title	: ( response.success ? 'Success' : 'Warning' ),
                message	: response.message,
                type	: ( response.success ? BootstrapDialog.TYPE_SUCCESS : BootstrapDialog.TYPE_WARNING)
            });
		}
		
		// window.setTimeout(checkIfContinue, (4 * 60 + 55) * 1000);

		// function checkIfContinue() {
  		// 	window.location.replace("${pageContext.request.contextPath}/logout");
		// 	}

		

	    function ArrangeOption(id){
	        var options = $('select.'+id+' option');
	        var arr = options.map(function(_, o) {
	            return {
	                t: $(o).text(),
	                v: o.value
	            };
	        }).get();
	        arr.sort(function(o1, o2) {
	            return o1.t > o2.t ? 1 : o1.t < o2.t ? -1 : 0;
	        });
	        options.each(function(i, o) {
	            o.value = arr[i].v;
	            $(o).text(arr[i].t);
	        });
	    }
	    
	</script>

	<decorator:head />

	<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">Confirmation</div>
				<div class="modal-header">Are you sure to delete this
					record(s)?</div>
				<div class="modal-header text-right">
					<a class="btn btn-default" type="button" data-dismiss="modal"><i
						class="fa fa-times"></i>Cancel</a> <a id="btn-delete"
						class="btn btn-danger" type="button" data-dismiss="modal"><i
						class="fa fa-trash"></i> Delete</a>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="confirm-reject" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">Confirmation</div>
				<div class="modal-header">Are you sure to reject this
					record(s)?</div>
				<div class="modal-header text-right">
					<a class="btn btn-default" type="button" data-dismiss="modal"><i
						class="fa fa-times"></i>Cancel</a> <a id="btn-reject"
						class="btn btn-danger" type="button" data-dismiss="modal"><i
						class="fa fa-times"></i> Reject</a>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="confirm-approve" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">Confirmation</div>
				<div class="modal-header">Are you sure to approve this
					record(s)?</div>
				<div class="modal-header text-right">
					<a class="btn btn-default" type="button" data-dismiss="modal"><i
						class="fa fa-times"></i>Cancel</a> <a id="btn-approve"
						class="btn btn-success" type="button" data-dismiss="modal"><i
						class="fa fa-check"></i> Confirm</a>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="confirm-send" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">Confirmation</div>
				<div class="modal-header">Are you sure to send activation code
					for this record(s)?</div>
				<div class="modal-header text-right">
					<a class="btn btn-default" type="button" data-dismiss="modal"><i
						class="fa fa-times"></i>Cancel</a> <a id="btn-send"
						class="btn btn-primary" type="button" data-dismiss="modal"><i
						class="fa fa-send"></i> Send Activation Code</a>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="confirm-reset" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">Confirmation</div>
				<div class="modal-header">Are you sure to reset this user
					password?</div>
				<div class="modal-header text-right">
					<a class="btn btn-default" type="button" data-dismiss="modal"><i
						class="fa fa-times"></i>Cancel</a> <a id="btn-reset-password"
						class="btn btn-danger" type="button" data-dismiss="modal"><i
						class="fa fa-minus-circle"></i> Reset Password</a>
				</div>
			</div>
		</div>
	</div>

</body>

</html>