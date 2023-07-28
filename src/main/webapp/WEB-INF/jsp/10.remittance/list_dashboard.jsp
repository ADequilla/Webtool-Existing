<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Remittance | Dashboard</title>
<script type="text/javascript">

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
<style>
.margin {
	padding-top: 30px;
	padding-left: 140px;
	padding-bottom: 30px;
}

.Rectangle {
	width: 160px;
	height: 184px;
	background-color: #BF8F00;
}

.PENDING {
	padding-left: 28px;
	width: 55px;
	height: 15px;
	font-family: Bahnschrift Condensed;
	font-size: 22px;
	font-weight: bold;
	font-style: normal;
	font-stretch: normal;
	line-height: normal;
	letter-spacing: normal;
	color: #ffffff;
}

.Group {
	padding-top: 12px;
	width: 42px;
	height: 42px;
	object-fit: contain;
}

.huge {
	object-fit: contain;
	text-align: center;
	padding-top: 15px;
	font-family: Bahnschrift Condensed;
	font-size: 66px;
	font-weight: bold;
	font-style: normal;
	font-stretch: normal;
	line-height: normal;
	letter-spacing: -1px;
	color: #ffffff;
}

.details {
	padding-left: 38px;
	padding-top: 8px;
	width: 29px;
	height: 11px;
	font-family: Bahnschrift Condensed;
	font-size: 17px;
	font-weight: normal;
	font-style: normal;
	font-stretch: normal;
	line-height: normal;
	letter-spacing: normal;
	color: #ffffff;
}

.details:hover {
	color: #000000;
}

.RectangleAll {
	width: 160px;
	height: 184px;
	background-color: #F2F2F2;
}

.GroupAll {
	width: 32px;
	height: 32px;
	object-fit: contain;
}

.hugeSENT {
	object-fit: contain;
	text-align: center;
	padding-top: 18px;
	font-family: Bahnschrift Condensed;
	font-size: 66px;
	font-weight: bold;
	font-style: normal;
	font-stretch: normal;
	line-height: normal;
	letter-spacing: normal;
	color: #548235;
}

.SENT {
	padding-left: 46px;
	width: 55px;
	height: 15px;
	font-family: Bahnschrift Condensed;
	font-size: 22px;
	font-weight: bold;
	font-style: normal;
	font-stretch: normal;
	line-height: normal;
	letter-spacing: -1px;
	color: #548235;
}

.detailsSENT {
	padding-left: 38px;
	padding-top: 8px;
	width: 29px;
	height: 11px;
	font-family: Bahnschrift Condensed;
	font-size: 17px;
	font-weight: normal;
	font-style: normal;
	font-stretch: normal;
	line-height: normal;
	letter-spacing: normal;
	color: #548235;
}

.detailsSENT:hover {
	color: #000000;
}

.hugeCLAIMED {
	object-fit: contain;
	text-align: center;
	padding-top: 18px;
	font-family: Bahnschrift Condensed;
	font-size: 66px;
	font-weight: bold;
	font-style: normal;
	font-stretch: normal;
	line-height: normal;
	letter-spacing: normal;
	color: #305496;
}

.CLAIMED {
	padding-left: 29px;
	width: 55px;
	height: 15px;
	font-family: Bahnschrift Condensed;
	font-size: 22px;
	font-weight: bold;
	font-style: normal;
	font-stretch: normal;
	line-height: normal;
	letter-spacing: -1px;
	color: #305496;
}

.detailsCLAIMED {
	padding-left: 38px;
	padding-top: 8px;
	width: 29px;
	height: 11px;
	font-family: Bahnschrift Condensed;
	font-size: 17px;
	font-weight: normal;
	font-style: normal;
	font-stretch: normal;
	line-height: normal;
	letter-spacing: normal;
	color: #305496;
}

.detailsCLAIMED:hover {
	color: #000000;
}

.Bitmap {
	width: 19px;
	height: 19px;
	background-color: #BE5108;
}

.hugeCANCELLED {
	object-fit: contain;
	text-align: center;
	padding-top: 18px;
	font-family: Bahnschrift Condensed;
	font-size: 66px;
	font-weight: bold;
	font-style: normal;
	font-stretch: normal;
	line-height: normal;
	letter-spacing: normal;
	color: #BE5108;
}

.CANCELLED {
	padding-left: 22px;
	width: 55px;
	height: 15px;
	font-family: Bahnschrift Condensed;
	font-size: 22px;
	font-weight: bold;
	font-style: normal;
	font-stretch: normal;
	line-height: normal;
	letter-spacing: -1px;
	color: #BE5108;
}

.detailsCANCELLED {
	padding-left: 38px;
	padding-top: 8px;
	width: 29px;
	height: 11px;
	font-family: Bahnschrift Condensed;
	font-size: 17px;
	font-weight: normal;
	font-style: normal;
	font-stretch: normal;
	line-height: normal;
	letter-spacing: normal;
	color: #BE5108;
}

.detailsCANCELLED:hover {
	color: #000000;
}

.RectangleTotal {
	width: 200px;
	height: 184px;
	background-color: #F2F2F2;
}

.hugeTOTAL {
	object-fit: contain;
	text-align: center;
	padding-top: 65px;
	font-family: Bahnschrift Condensed;
	font-size: 80px;
	font-weight: bold;
	font-style: normal;
	font-stretch: normal;
	line-height: normal;
	letter-spacing: normal;
	color: #00602B;
}

.TOTAL {
	padding-top: 20px;
	padding-left: 40px;
	width: 55px;
	height: 15px;
	font-family: Bahnschrift Condensed;
	font-size: 38px;
	font-weight: bold;
	font-style: normal;
	font-stretch: normal;
	line-height: normal;
	letter-spacing: -1px;
	color: #00602B;
}

</style>
</head>
<body>
	<div class="content">
		<div class="row">
			<div class="col-lg-8 ">
				<div class="main-header">
					<ul class="breadcrumb">
						<li><i class="fa fa-home"></i></li>
						<li>Remittance</li>
						<li class="active"><a
							href="${pageContext.request.contextPath}/monitoring/remittance-dashboard/">Dashboard</a></li>
					</ul>
					<h3>
						<i class="fa fa-clone"></i> Remittance
					</h3>
					<em>Dashboard</em>
				</div>
			</div>
		</div>

		<!-- main -->
		<div class="main-content">
			<!-- SEARCH DATA TABLE -->
			<div class="widget widget-table">
				<div class="widget-header">
					<h3>
						<i class="fa fa-user-o"></i>DashBoard
					</h3>
				</div>
				<!-- /.row -->
				<div class="margin">
					<div class="row">
						<div class="col-lg-2 col-md-4">
							<div class="Rectangle">
								<div class="col-xs-12 text-center">
									<div class="PENDING">PENDING</div>
									<img
										src="${pageContext.request.contextPath}/assets/img/pending.png"
										class="Group">
									<div class="huge">${countStatus3}</div>

									<a
										href="${pageContext.request.contextPath}/monitoring/remittance-dashboard/pending">
										<div class="details">DETAILS</div>
									</a>
								</div>
							</div>
						</div>
						<div class="col-lg-2 col-md-4">
							<div class="RectangleAll">
								<div class="col-xs-12 text-center">
									<img
										src="${pageContext.request.contextPath}/assets/img/sent.png"
										class="GroupAll">
									<div class="SENT">SENT</div>
									<div class="hugeSENT">${countStatus0}</div>

									<a
										href="${pageContext.request.contextPath}/monitoring/remittance-dashboard/sent">
										<div class="detailsSENT">DETAILS</div>
									</a>
								</div>
							</div>
						</div>
						<div class="col-lg-2 col-md-4">
							<div class="RectangleAll">
								<div class="col-xs-12 text-center">
									<img
										src="${pageContext.request.contextPath}/assets/img/receive.png"
										class="GroupAll">
									<div class="CLAIMED">CLAIMED</div>
									<div class="hugeCLAIMED">${countStatus1}</div>

									<a
										href="${pageContext.request.contextPath}/monitoring/remittance-dashboard/claimed">
										<div class="detailsCLAIMED">DETAILS</div>
									</a>
								</div>
							</div>
						</div>
						<div class="col-lg-2 col-md-4">
							<div class="RectangleAll">
								<div class="col-xs-12 text-center">
									<img
										src="${pageContext.request.contextPath}/assets/img/cancelled.png"
										class="GroupAll">
									<div class="CANCELLED">CANCELLED</div>
									<div class="hugeCANCELLED">${countStatus2}</div>

									<a
										href="${pageContext.request.contextPath}/monitoring/remittance-dashboard/cancelled">
										<div class="detailsCANCELLED">DETAILS</div>
									</a>
								</div>
							</div>
						</div>
						<div class="col-lg-2 col-md-4">
							<div class="RectangleTotal">
								<div class="col-xs-12 text-center">
									<div class="TOTAL">TOTAL</div>
									<div class="hugeTOTAL">${countStatusAll}</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- /.row -->
			</div>
			<!-- END SEARCH DATA TABLE -->

		</div>
		<!-- /main-content -->
	</div>
	<!-- /main -->
</body>
</html>