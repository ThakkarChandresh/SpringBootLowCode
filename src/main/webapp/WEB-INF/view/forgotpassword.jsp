<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Forgot-Password</title>
<!-- base:css -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/materialdesignicons.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/vendor.bundle.base.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/jquery.toast.min.css">
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
<body onload="showMessage('${message}','${status}')">
	<div class="loader" id="loader"></div>
	<div class="container-scroller">
		<div class="container-fluid page-body-wrapper full-page-wrapper">
			<div class="content-wrapper d-flex align-items-center auth px-0">
				<div class="row w-100 mx-0">
					<div class="col-lg-4 mx-auto">
						<div class="auth-form-light text-left py-5 px-4 px-sm-5 border">
							<h4>Forgot-Password</h4>
							<form class="pt-3" id="forgot-password-form">
								<div class="form-group">
									<label for="username">Email or Username</label>
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

								<div class="mt-3">
									<input type="submit"
										class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn"
										value="Send OTP">
								</div>

							</form>


							<div class="modal fade" id="verify-otp" tabindex="-1"
								role="dialog" aria-labelledby="exampleModalLabel-2"
								aria-hidden="true">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title" id="model-title">OTP
												Verification</h5>
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
										</div>
										<div class="modal-body">
											<div
												class="container height-100 d-flex justify-content-center align-items-center">
												<f:form action="check-otp-token" method="POST"
													modelAttribute="forgotPasswordVO" id="checkOtpForm">
													<div class="position-relative">
														<div class="text-center">
															<h6>
																Please enter the one time password <br> to verify
																your account
															</h6>
															<div>
																<span>A code has been sent to your mail-id</span>
															</div>
														</div>
														<div class="form-group">
															<label for="OTP" class="text-left mt-4">Enter
																OTP:</label>
															<div class="input-group">
																<div class="input-group-prepend bg-transparent">
																	<span
																		class="input-group-text bg-transparent border-right-0">
																		<i class="mdi mdi mdi-message-processing text-primary"></i>
																	</span>
																</div>
																<f:input path="email" type="hidden" id="email-id" />
																<f:input path="otp" type="password"
																	style="letter-spacing: 8px"
																	class="form-control form-control-lg border-left-0"
																	id="OTP" />
																<f:input path="token" type="hidden" id="token-id" />
															</div>
															<span id="otperror" class="text-danger font-weight-bold">
															</span>
														</div>
														<div class="text-center">
															<div class="mt-4">
																<input type="submit" id="validate"
																	class="btn btn-primary px-4 validate" value="Validate">
															</div>
														</div>

														<div class="card-2">
															<div
																class="content d-flex justify-content-center align-items-center mt-3">
																<span>Didn't get the code</span> <a id="resend"
																	class="text-decoration-none ml-1">Resend</a>
															</div>
														</div>
													</div>
												</f:form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
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
		src="<%=request.getContextPath()%>/adminResources/js/custom/forgotpasswordvalidator.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/jquery.toast.min.js"></script>
	<!-- endinject -->
</body>
</html>