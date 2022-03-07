<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="f" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Register</title>
<!-- base:css -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/materialdesignicons.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/vendor.bundle.base.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/loader.css">
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

<body>

	<div class="container-scroller"></div>
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

						<f:form class="pt-3" id="registerForm" modelAttribute="RegisterVO"
							action="insert" method="post">

							<div class="form-group">
								<div class="input-group">
									<div class="input-group-prepend bg-transparent">
										<span class="input-group-text bg-transparent border-right-0">
											<i class="mdi mdi-account-outline text-primary"></i>
										</span>
									</div>

									<f:input path="firstName" id="firstName"
										placeholder="Firstname" type="text"
										class="form-control form-control-lg border-left-0" />

								</div>

								<span id="firsterror" class="text-danger font-weight-bold"></span>
								<!-- new line here -->

							</div>
							<div class="form-group">
								<!--  <label>Lastname</label>  -->
								<div class="input-group">
									<div class="input-group-prepend bg-transparent">
										<span class="input-group-text bg-transparent border-right-0">
											<i class="mdi mdi-account-outline text-primary"></i>
										</span>
									</div>

									<f:input path="lastName" id="lastName" placeholder="Lastname"
										type="text" class="form-control form-control-lg border-left-0" />

								</div>

								<span id="lasterror" class="text-danger font-weight-bold"></span>
								<!-- new line here -->

							</div>
							<div class="form-group">
								<!--   <label>Email</label> -->
								<div class="input-group">
									<div class="input-group-prepend bg-transparent">
										<span class="input-group-text bg-transparent border-right-0">
											<i class="mdi mdi-email-outline text-primary"></i>
										</span>
									</div>

									<f:input path="loginVO.username" id="username"
										placeholder="Email" type="text"
										class="form-control form-control-lg border-left-0" />

								</div>

								<span id="emailerror" class="text-danger font-weight-bold"></span>
								<!-- new line here -->

							</div>
							<div class="form-group">
								<!--  <label>Password</label>  -->
								<div class="input-group">
									<div class="input-group-prepend bg-transparent">
										<span class="input-group-text bg-transparent border-right-0">
											<i class="mdi mdi-lock-outline text-primary"></i>
										</span>
									</div>

									<f:input path="loginVO.password" id="password"
										placeholder="Password" type="password"
										class="form-control form-control-lg border-left-0" />

								</div>

								<span id="passworderror" class="text-danger font-weight-bold"></span>
								<!-- new line here -->

							</div>
							<div class="form-group">
								<!--  <label>ConfirmPassword</label>  -->
								<div class="input-group">
									<div class="input-group-prepend bg-transparent">
										<span class="input-group-text bg-transparent border-right-0">
											<i class="mdi mdi-lock-outline text-primary"></i>
										</span>
									</div>

									<input id="confirmpassword" placeholder="ConfirmPassword"
										type="password"
										class="form-control form-control-lg border-left-0" />

								</div>

								<span id="confirmpassworderror"
									class="text-danger font-weight-bold"></span>
								<!-- new line here -->

							</div>
							<div class="mt-3">
								<button type="submit"
									class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">SIGN
									UP</button>
								<div class="loader loader">
									<div class="loader-outter"></div>
									<div class="loader-inner"></div>
								</div>
							</div>
							<div class="text-center mt-4 font-weight-light">
								Already have an account? <a href="login" class="text-primary">Login</a>
							</div>
						</f:form>
					</div>
				</div>
				<div class="col-lg-6 register-half-bg d-flex flex-row">
					<p
						class="text-white font-weight-medium text-center flex-grow align-self-end"></p>
				</div>
			</div>
		</div>
		<!-- content-wrapper ends -->
	</div>
	<!-- page-body-wrapper ends -->
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
		src="<%=request.getContextPath()%>/adminResources/js/custom/registervalidation.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/custom/generalvalidation.js"></script>
	<!-- endinject -->
</body>

</html>
