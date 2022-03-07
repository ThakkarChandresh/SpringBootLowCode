<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>User | Change Password</title>
<!-- base:css -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/materialdesignicons.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/all.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/vendor.bundle.base.css">
<!-- endinject -->
<!-- plugin css for this page -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/css-stars.css">
<!-- End plugin css for this page -->
<!-- inject:css -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/style.css">
<!-- endinject -->
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/adminResources/images/favicon.png" />
</head>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<body class="sidebar-icon-only sidebar-absolute">
	<div class="container-scroller">
		<!-- partial:../../partials/_navbar.html -->
		<jsp:include page="header.jsp"></jsp:include>
		<!-- partial -->
		<div class="container-fluid page-body-wrapper">
			<!-- partial:../../partials/_sidebar.html -->
			<jsp:include page="menu.jsp"></jsp:include>
			<!-- partial -->
			<div class="main-panel">
				<div class="content-wrapper">
					<div class="col-12">
						<div class="card">
							<div class="row w-100 mx-0">
								<div class="col-lg-4 mx-5">
									<div class="auth-form-light pb-5 pt-2 px-4 px-sm-6 my-5">
										<div class="d-flex justify-content-center mb-4">
											<i class="fa fa-key fa-4x"></i>
										</div>
										<form class="pt-3" id="changePasswordForm"
											action="updatePassword" method="post">
											<div class="form-group">
												<div class="input-group">
													<input name="oldpassword" id="oldpassword"
														onpaste="return false" placeholder="Old Password"
														type="password"
														class="form-control form-control-lg border-left-1" />

												</div>

												<span id="oldpassworderror"
													class="text-danger font-weight-bold">${passwordError}</span>
											</div>
												<div class="form-group">
												<div class="input-group">

													<input name="newpassword" id="newpassword"
														onpaste="return false" placeholder="New Password"
														type="password"
														class="form-control form-control-lg border-left-1"
														oninput='validatePassword()' />

												</div>

												<span id="newpassworderror"
													class="text-danger font-weight-bold"></span>
											</div>
											<div class="form-group">
												<div class="input-group">

													<input name="confirmpassword" id="confirmpassword"
														onpaste="return false" placeholder="Confirm Password"
														type="password"
														class="form-control form-control-lg border-left-1" />

												</div>

												<span id="confirmpassworderror"
													class="text-danger font-weight-bold"></span>
											</div>
											<input class="btn btn-primary btn-block mb-2" type="submit"
												value="Update" id="updatebutton">
										</form>
									</div>
								</div>
								<div class="col-lg-4 mx-auto">
									<div class="auth-form-light py-5 px-4 px-sm-6 my-5">
										<div class="d-flex justify-content-center">
											<h4>Password requirements</h4>
										</div>
										<ul class="my-3 mx-5 h5">
											<li id="letter" class="text-danger">At least <strong>one
													letter</strong></li>
											<li id="capital" class="text-danger">At least <strong>one
													capital letter</strong></li>
											<li id="number" class="text-danger">At least <strong>one
													number</strong></li>
											<li id="length" class="text-danger">At least <strong>8
													characters</strong></li>
											<li id="space" class="text-danger">At least<strong>
													one special character</strong></li>
										</ul>
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
		src="<%=request.getContextPath()%>/adminResources/js/jquery.barrating.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/hotkeys.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/custom/userkeypress.js"></script>
	<!-- End plugin js for this page -->
	<!-- Custom js for this page-->
	<script
		src="<%=request.getContextPath()%>/adminResources/js/profile-demo.js"></script>
	<!--
<script src="<%=request.getContextPath()%>/adminResources/js/custom/registervalidation.js"></script>
<script src="<%=request.getContextPath()%>/adminResources/js/custom/generalvalidation.js"></script>
  -->
	<script
		src="<%=request.getContextPath()%>/adminResources/js/custom/changepasswordvalidate.js"></script>
	<!-- End custom js for this page-->
</body>

</html>
