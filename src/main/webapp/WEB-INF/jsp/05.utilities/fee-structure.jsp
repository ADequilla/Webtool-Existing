<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Fee Structure | Utilities</title>
<script type="text/javascript">
        $(document).ready(function() {
            var oTable = $("#dataTable").dataTable({
                "sAjaxSource"	: "${pageContext.request.contextPath}/utilities/fee/search",
                "sServerMethod"	: "POST",
                "fnServerData"	: function (sSource, aoData, fnCallback) {
                    var transType = $.trim($("#searchTransType").val());
                    aoData.push({ "name": "transType", "value": transType });
                    jQuery.ajax({
                        "dataType"	: 'json',
                        "type"		: "POST",
                        "url"		: sSource,
                        "data"		: aoData,
                        "success"	: fnCallback
                    });
                },
                "aoColumns": [
                    { "mDataProp": "transType" },
                    { "mDataProp": "startEndRange" },
                    { "mDataProp": "totalCharge" },
                    { "mDataProp": "agentIncome" },
                    { "mDataProp": "bankIncome" },
                    { "mDataProp": "agentTargetIncome" },
                    { "mDataProp": "bancnetIncome" },
                    { "mDataProp": "id", "bSortable": false },
                    { "mDataProp": "id", "bSortable": false }
                ],
                "aoColumnDefs" : [
					{	
						"mRender"	: TransactionTypeFormatter,
						"aTargets"	: [0]
					}, 
                    {
                    	class		: "text-center",
                        "mRender"	: EditlinkFormatter,
                        "aTargets"	: [7]
                    },
					{
                    	class		: "text-center",
                        "mRender"	: CheckboxFormatter,
                        "aTargets"	: [8]
                    }
                ]
            });
            
            $("#btn-reset").click(function(){
            	$("#searchTransType").val("");
                
            	oTable.fnDraw();
            });

            $("#btn-search").click(function(){
                oTable.fnDraw();
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
    				submit('/utilities/fee/delete', JSON.stringify(data), function (data) {
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
			<div class="col-lg-5 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Utilities</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/utilities/fee/">Fee
								Structure</a></li>
					</ul>
					<h3>
						<i class="fa fa-money"></i> Fee Structure
					</h3>
					<em>Utilities</em>
				</div>
			</div>
			<div class="col-lg-7 ">
				<div class="top-content">
					<ul class="list-inline quick-access">
						<li><a
							href="${pageContext.request.contextPath}/utilities/fee/create">
								<div class="quick-access-item bg-color-blue">
									<i class="fa fa-plus"></i>
									<h5>New Fee Structure</h5>
									<em>add new Fee Structure data</em>
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
							<select id="searchTransType" class="form-control">
								<option value="">-- Transaction --</option>
								<c:forEach items="${listTransType}" var="lookup">
									<c:if test="${lookup.value != 'RF_KONEK2PAY'}">
										<option value="${lookup.value}">${lookup.description}</option>
									</c:if>
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
						<i class="fa fa-table"></i> List of Fee Structure
					</h3>
				</div>
				<div class="widget-content">
					<table id="dataTable"
						class="table table-striped table-hover table-bordered datatable">
						<thead>
							<tr>
								<th>Transaction</th>
								<th>Range</th>
								<th>Total Charge</th>
								<th>Agent's Income</th>
								<th>Bank Income</th>
								<th>Agent Target's Income</th>
								<th>AP Bancnet Instapay</th>
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