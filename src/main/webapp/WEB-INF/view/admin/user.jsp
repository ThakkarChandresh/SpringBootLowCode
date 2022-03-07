<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>ADMIN | USERS</title>
<!-- base:css -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/materialdesignicons.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/vendor.bundle.base.css">
<!-- endinject -->
<!-- plugin css for this page -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/dataTables.bootstrap4.css">
<!-- End plugin css for this page -->
<!-- inject:css -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/style.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/all.css">
<!-- endinject -->
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/adminResources/images/favicon.png" />
</head>

<body class="sidebar-icon-only sidebar-absolute">
	<div class="container-scroller">

		<!-- Header Start -->
		<jsp:include page="header.jsp"></jsp:include>
		<!-- Header End -->

		<div class="container-fluid page-body-wrapper">



			<!-- Menu -->
			<jsp:include page="menu.jsp"></jsp:include>


			<div class="main-panel">
				<div class="content-wrapper">
					<div class="d-flex justify-content-sm-between align-items-center">
						<div class="d-flex justify-content-start align-items-center">
							<i class="mdi mdi-account-circle display-2 mr-3"></i>
							<div>
								<h4>Users</h4>
								<nav aria-label="breadcrumb">
									<ol class="breadcrumb" id="top-bread">
										<li class="breadcrumb-item"><a href="index">Dashboard</a></li>
										<li class="breadcrumb-item active" aria-current="page">Users</li>
									</ol>
								</nav>
							</div>
						</div>
					</div>

					<div class="card mt-3">
						<div class="card-body">
							<div class="row">
								<div class="col-12">
									<div class="table-responsive">
										<table id="dataTable" class="table">
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- content-wrapper ends -->
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
		src="<%=request.getContextPath()%>/adminResources/js/jquery.dataTables.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/dataTables.bootstrap4.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/hotkeys.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/custom/adminkeypress.js"></script>
	<!-- End plugin js for this page -->

	<!-- custome js pluging for this page -->
	<script
		src="<%=request.getContextPath()%>/adminResources/js/TableComponents.js"></script>

	<script
		src="<%=request.getContextPath()%>/adminResources/js/custom/users.js"></script>
	<!-- end plugin custome js -->
</body>
</html>