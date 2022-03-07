<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>USER | INDEX</title>
<!-- base:css -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/materialdesignicons.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/vendor.bundle.base.css">
<!-- endinject -->
<!-- plugin css for this page -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/jqvmap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/flag-icon.min.css">
<!-- End plugin css for this page -->
<!-- inject:css -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/style.css">
<!-- endinject -->
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/adminResources/images/favicon.png" />
</head>
<body class="sidebar-icon-only sidebar-absolute">

	<div class="container-scroller">
		<!-- partial:partials/_navbar.html -->
		<jsp:include page="header.jsp"></jsp:include>

		<!-- partial -->
		<div class="container-fluid page-body-wrapper">

			<!-- partial:partials/_settings-panel.html -->
			<jsp:include page="menu.jsp"></jsp:include>
			<!-- partial -->

			<div class="main-panel">

				<!-- analytics reports -->

			</div>
			<!-- main-panel ends -->
		</div>
		<!-- page-body-wrapper ends -->
	</div>
	<!-- container-scroller -->

	<!-- base:js -->
	<script
		src="<%=request.getContextPath()%>/adminResources/js/vendor.bundle.base.js"></script>
	<!-- endinject -->
	<!-- Plugin js for this page-->
	<script
		src="<%=request.getContextPath()%>/adminResources/js/jquery.flot.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/jquery.flot.pie.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/jquery.flot.resize.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/jquery.vmap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/jquery.vmap.world.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/jquery.vmap.usa.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/jquery.peity.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/jquery.flot.dashes.js"></script>
	<!-- End plugin js for this page-->
	<!-- inject:js -->
	<script
		src="<%=request.getContextPath()%>/adminResources/js/off-canvas.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/hoverable-collapse.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/template.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/settings.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/todolist.js"></script>
	<!-- endinject -->
	<!-- plugin js for this page -->
	<script
		src="<%=request.getContextPath()%>/adminResources/js/hotkeys.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/custom/userkeypress.js"></script>
	<!-- End plugin js for this page -->

	<script
		src="<%=request.getContextPath()%>/adminResources/js/dashboard.js"></script>
	<!-- End custom js for this page-->
</body>

</html>
