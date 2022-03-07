<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Login</title>
<!-- base:css -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/materialdesignicons.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/vendor.bundle.base.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/jquery.toast.min.css">
<!-- endinject -->
<!-- plugin css for this page -->
<!-- End plugin css for this page -->
<!-- inject:css -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/style.css">
<!-- endinject -->
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/adminResources/images/favicon.png" />
</head>

<%-- <body onload="successMessage('${message}')" >--%>
<body>
	<div class="container-scroller">
		<div class="container-fluid page-body-wrapper full-page-wrapper">
			<div
				class="content-wrapper d-flex align-items-stretch auth auth-img-bg">
				<div class="row flex-grow">
					<div
						class="col-lg-6 d-flex align-items-center justify-content-center">
						<div class="auth-form-transparent text-left p-3">
							<div class="brand-logo">
								<img
									src="<%=request.getContextPath()%>/adminResources/images/logo.svg"
									alt="logo">
							</div>
							<form class="pt-3" id="loginForm"
								action="/j_spring_security_check" method="post">
								<!-- changes done overhere -->
								<div class="form-group">
									<label for="username">Username</label>
									<div class="input-group">
										<div class="input-group-prepend bg-transparent">
											<span class="input-group-text bg-transparent border-right-0">
												<i class="mdi mdi-account-outline text-primary"></i>
											</span>
										</div>
										<input type="text" name="username"
											class="form-control form-control-lg border-left-0"
											id="username" placeholder="Username">
									</div>
									<span id="usererror" class="text-danger font-weight-bold">
									</span>
									<!-- new line here -->
								</div>
								<div class="form-group">
									<label for="password">Password</label>
									<div class="input-group">
										<div class="input-group-prepend bg-transparent">
											<span class="input-group-text bg-transparent border-right-0">
												<i class="mdi mdi-lock-outline text-primary"></i>
											</span>
										</div>
										<input type="password" name="password"
											class="form-control form-control-lg border-left-0"
											id="password" placeholder="Password">
									</div>
									<span id="passworderror" class="text-danger font-weight-bold">
									</span>
									<!-- new line here -->
								</div>
								<div
									class="my-2 d-flex justify-content-between align-items-center">
									<div class="form-check">
										<label class="form-check-label text-muted"> <input
											type="checkbox" class="form-check-input" name="keepMeSignin">
											Keep me signed in <i class="input-helper"></i></label>
									</div>
									<a href="forgot-password" class="auth-link text-black">Forgot
										password?</a>
								</div>
								<div class="my-3">
									<button type="submit"
										class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">LOGIN</button>
									<!-- changes done above onsubmit is used -->
								</div>
								<div class="text-center mt-4 font-weight-light">
									Don't have an account? <a href="register" class="text-primary">Create</a>
								</div>
							</form>
						</div>
					</div>
					<div class="col-lg-6 login-half-bg d-flex flex-row"></div>
				</div>
			</div>
			<!-- content-wrapper ends -->
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
	<script
		src="<%=request.getContextPath()%>/adminResources/js/custom/loginvalidation.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/custom/generalvalidation.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/jquery.toast.min.js"></script>
	<!-- endinject -->
</body>
</html>