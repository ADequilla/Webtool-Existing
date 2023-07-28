<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Structure | Create/Edit</title>
<script type="text/javascript">
        $(document).ready(function() {
            $("form").validate();

			$("#btn-reset").click(function(){
				$('#clientType').val($('#default_clientType').val());
            	$('#transType').val($('#default_transType').val());
            	$('#startRange').val($('#default_startRange').val());
            	$('#endRange').val($('#default_endRange').val());
            	$('#totalCharge').val($('#default_totalCharge').val());
            	$('#agentIncome').val($('#default_agentIncome').val());
            	$('#bankIncome').val($('#default_bankIncome').val());
            	$('#agentTargetIncome').val($('#default_agentTargetIncome').val());
            	$('#bancnetIncome').val($('#default_bancnetIncome').val());
            });

            $("#btn-save").click(function(){
            	if ($("form").valid()) {
                	var dataJson = $('form').serializeObject();
                    submit('/utilities/fee/save', JSON.stringify(dataJson), function (data) {
	                	$("input[name='id']").val(data.id);
	                });
                }
            });

            
            $("#transType").change(function(){
            	if ($(this).val() == 'MINI_STATEMENT') {
            		if ($('#agentIncome').hasClass('required')) {
            			$("#agentIncome").removeClass("required");
            		}
            	} else {
            		if (! $('#agentIncome').hasClass('required')) {
            			$("#agentIncome").addClass("required");
            		}
            	}
            	if($(this).val() == 'KONEK2PAY'){
            		$( "#agentIncome" ).prop( "disabled", true );
            		$( "#agentTargetIncome" ).prop( "disabled", true );
            		
            		$("#agentIncome" ).val("0");
             		$("#agentTargetIncome" ).val("0");
                }else if ($(this).val() == 'IBFT'){
            		$( "#agentIncome" ).prop( "disabled", true );
            		$( "#agentTargetIncome" ).prop( "disabled", true );
            		
            		$("#agentIncome" ).val("0");
             		$("#agentTargetIncome" ).val("0");
                }else {
                	$( "#agentIncome" ).prop( "disabled", false );
            		$( "#agentTargetIncome" ).prop( "disabled", false );
            	}
            	
            	if($(this).val() != 'IBFT'){
            		$( "#bancnetIncome" ).prop( "disabled", true );
            		
             		$("#bancnetIncome" ).val(0);
                }else {
                	$( "#bancnetIncome" ).prop( "disabled", false );
            	}
            });

            disableOnTransactionType();
            
            $("#agentIncome").change(function(){
            	calculateTotalCharge();
            });
            $("#bankIncome").change(function(){
            	calculateTotalCharge();
            });
            $("#agentTargetIncome").change(function(){
            	calculateTotalCharge();
            });
            $("#bancnetIncome").change(function(){
            	calculateTotalCharge();
            });
        });

        function disableOnTransactionType(){
        	 if($('#transType').val() == 'KONEK2PAY' || $('#transType').val() == 'IBFT'){
         		$( "#agentIncome" ).prop( "disabled", true );
         		$( "#agentTargetIncome" ).prop( "disabled", true );
             }else {
             	$( "#agentIncome" ).prop( "disabled", false );
         		$( "#agentTargetIncome" ).prop( "disabled", false );
         	}
        	 
        	if($('#transType').val() != 'IBFT'){
         		$( "#bancnetIncome" ).prop( "disabled", true );
             }else {
             	$( "#bancnetIncome" ).prop( "disabled", false );
         	}
        }
        
        function calculateTotalCharge(){
        	$('#totalCharge').val(Number($('#agentIncome').val()) + Number($('#bankIncome').val()) + Number($('#agentTargetIncome').val())+ Number($('#bancnetIncome').val()));
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
    </script>
</head>

<body>
	<div class="content">
		<div class="row">
			<div class="col-lg-6 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Utilities</li>
						<li><a
							href="${pageContext.request.contextPath}/utilities/fee/">Fee
								Structure</a></li>
						<li class="active">Create/Edit</li>
					</ul>
					<h3>
						<i class="fa fa-plus"></i> Create/Edit Fee Structure
					</h3>
					<em>Utilities</em>
				</div>
			</div>
			<div class="col-lg-6 ">
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

		<form class="form-horizontal" role="form" method="post">
			<input type="hidden" name="id" value="${feeStructure.id}"> <input
				type="hidden" id="default_clientType"
				value="${feeStructure.clientType}"> <input type="hidden"
				id="default_transType" value="${feeStructure.transType}"> <input
				type="hidden" id="default_startRange"
				value="${feeStructure.startRange}"> <input type="hidden"
				id="default_endRange" value="${feeStructure.endRange}"> <input
				type="hidden" id="default_totalCharge"
				value="${feeStructure.totalCharge}"> <input type="hidden"
				id="default_agentIncome" value="${feeStructure.agentIncome}">
			<input type="hidden" id="default_bankIncome"
				value="${feeStructure.bankIncome}"> <input type="hidden"
				id="default_agentTargetIncome"
				value="${feeStructure.agentTargetIncome}"> <input
				type="hidden" id="default_bancnetIncome"
				value="${feeStructure.bancnetIncome}">

			<div class="row">
				<div class="col-md-12">
					<div class="widget">
						<div class="widget-header">
							<h3>
								<i class="fa fa-money"></i> Fee Structure
							</h3>
						</div>
						<div class="widget-content form">
							<div class="form-body">
								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">Client Type <span
											class="required">*</span></label>
										<div class="col-md-4">
											<select id="clientType" name="clientType"
												class="form-control required">
												<option value="">-- Client Type --</option>
												<c:forEach items="${listClientType}" var="lookup">
													<option value="${lookup.value}"
														${lookup.value == feeStructure.clientType ? 'selected="selected"' : ''}>${lookup.description}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>

								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">Transaction Type
											<span class="required">*</span>
										</label>
										<div class="col-md-4">
											<select id="transType" name="transType"
												class="form-control required">
												<option value="">-- Transaction --</option>
												<c:forEach items="${listTransType}" var="lookup">
													<c:if test="${lookup.value != 'RF_KONEK2PAY'}">
														<option value="${lookup.value}"
															${lookup.value == feeStructure.transType ? 'selected="selected"' : ''}>${lookup.description}</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>

								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">Range <span
											class="required">*</span></label>
										<div class="col-md-2">
											<input id="startRange" name="startRange" type="number"
												class="form-control required"
												value="${feeStructure.startRange}" />
										</div>
										<div class="col-md-2">
											<input id="endRange" name="endRange" type="number"
												class="form-control required"
												value="${feeStructure.endRange}" />
										</div>
									</div>
								</div>

								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">Total Charge </label>
										<div class="col-md-4">
											<input id="totalCharge" name="totalCharge" type="number"
												class="form-control required"
												value="${feeStructure.totalCharge}" readonly="readonly" />
										</div>
									</div>
								</div>

								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">Agent's Income <span
											class="required">*</span></label>
										<div class="col-md-4">
											<input id="agentIncome" name="agentIncome" type="number"
												class="form-control required"
												value="${feeStructure.agentIncome}" />
										</div>
									</div>
								</div>

								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">Bank Income <span
											class="required">*</span></label>
										<div class="col-md-4">
											<input id="bankIncome" name="bankIncome" type="number"
												class="form-control required"
												value="${feeStructure.bankIncome}" />
										</div>
									</div>
								</div>

								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">Agent Target's
											Income <span class="required">*</span>
										</label>
										<div class="col-md-4">
											<input id="agentTargetIncome" name="agentTargetIncome"
												type="number" class="form-control required"
												value="${feeStructure.agentTargetIncome}" />
										</div>
									</div>
								</div>

								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">AP Bancnet
											Instapay <span class="required">*</span>
										</label>
										<div class="col-md-4">
											<input id="bancnetIncome" name="bancnetIncome" type="number"
												class="form-control required"
												value="${feeStructure.bancnetIncome}" />
										</div>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</form>

	</div>
</body>
</html>