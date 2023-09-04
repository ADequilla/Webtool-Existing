<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Operation | Mobile Branch Officer</title>
<script type="text/javascript">
    $(document).ready(function() {
    	
    	$("#btn-reset").click(function(){
    		location.reload();
        });
    	
    	$("#btn-save").click(function(){
    		var data 	= [];
    		var isError	= false;
    		$('#dataTable').find('tbody').find('tr').each(function () {
    			var checkbox	= $(this).find('input[type="checkbox"]');
    			var hoursStart 	= $(this).find('#hoursStart').val();
				var hoursEnd 	= $(this).find('#hoursEnd').val();
    			var id 			= checkbox.val();
    			var enabled 	= 0;
    			
    			if (checkbox.is(':checked')){
    				enabled		= 1;
    				if(hoursStart == '' || hoursEnd == ''){
						BootstrapDialog.show({
	                        message	: 'Please enter range time.',
	                        type	: BootstrapDialog.TYPE_WARNING
	                    });
						
						isError	= true;
						return false;
					}
    			}
    			
    			var row = {"id" : id, "enabled": enabled, "hoursStart" : hoursStart, "hoursEnd" : hoursEnd};
				data.push(row);
    		});
    		
    		if(! isError){
	    		submit('/mbo/operation/save', JSON.stringify(data), function (data) {
	    			
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
			<div class="col-lg-6">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Mobile Branch Officer</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/mbo/operation/">Operation</a></li>
					</ul>
					<h3>
						<i class="fa fa-calendar"></i> Operation
					</h3>
					<em>Mobile Branch Officer</em>
				</div>
			</div>
			<div class="col-lg-6">
				<div class="top-content-button">
					<ul class="list-inline quick-access">
						<li>
							<button id="btn-reset" class="btn btn-default" type="button">
								<i class="fa fa-refresh"></i> Reset Form
							</button>
						</li>
						<li>
							<button id="btn-save" class="btn btn-custom-secondary"
								type="button">
								<i class="fa fa-save"></i> Save Data
							</button>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<!-- SHOW HIDE COLUMNS DATA TABLE -->
		<div class="widget widget-table">
			<div class="widget-header">
				<h3>
					<i class="fa fa-calendar"></i> Mobile Branch Officer Operation
				</h3>
			</div>

			<div class="widget-content">
				<table id="dataTable"
					class="table table-striped table-hover table-bordered datatable">
					<thead>
						<tr>
							<th width="30%">Days</th>
							<th width="10%">Enabled</th>
							<th width="60%">Range Time</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${operationList}" var="operation">
							<tr>
								<td>${operation.daysName}</td>
								<td class="text-center"><input type="checkbox"
									value="${operation.id}"
									${(operation.enabled == 1 ? 'checked' : '')}></td>
								<td><label class="col-md-1 control-label"> Hours </label>
									<div class="col-md-4">
										<input type="text" id="hoursStart"
											class="form-control timepicker"
											value="${operation.hoursStart}">
									</div> <label class="col-md-1 control-label"> to </label>
									<div class="col-md-4">
										<input type="text" id="hoursEnd"
											class="form-control timepicker" value="${operation.hoursEnd}">
									</div></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<!-- END SHOW HIDE COLUMNS DATA TABLE -->
	</div>
	<!-- /main -->
</body>
</html>