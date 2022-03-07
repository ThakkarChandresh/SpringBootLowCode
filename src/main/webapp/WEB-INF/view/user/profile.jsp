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
<title>User | Profile</title>
<!-- base:css -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/materialdesignicons.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/vendor.bundle.base.css">

<!-- endinject -->
<!-- plugin css for this page -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/css-stars.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/croppie.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/cropper.css">
<!-- End plugin css for this page -->


<!-- inject:css -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/style.css">

<!-- endinject -->
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/adminResources/images/favicon.png" />
</head>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<body>
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
					<div class="row">
						<div class="col-12">
							<div class="card">
								<div class="card-body">
									<div class="row">
										<div class="col-lg-4">
											<f:form class="pt-3" onsubmit="return validateregister()"
												modelAttribute="RegisterVO" action="update" method="post">
												<div class="border-bottom text-center pb-4">
													<img
														src="<%=request.getContextPath()%>/adminResources/images/defaultUser.png"
														alt="profile" class="img-lg rounded-circle mb-3" />
													<div class="mb-3">
														<h3>${RegisterVO.firstName}${RegisterVO.lastName}</h3>
														<div
															class="d-flex align-items-center justify-content-center">
															<h5 class="mb-0 mr-2 text-muted">Canada</h5>
															<select id="profile-rating" name="rating"
																autocomplete="off">
																<option value="1">1</option>
																<option value="2">2</option>
																<option value="3">3</option>
																<option value="4">4</option>
																<option value="5">5</option>
															</select>
														</div>
													</div>
													<p class="w-75 mx-auto mb-3">Bureau Oberhaeuser is a
														design bureau focused on Information- and Interface
														Design.</p>
													<div class="d-flex justify-content-center">
														<label class="btn btn-primary" for="file-input" id="file">Edit
															Image</label> <input type="file" name="" class="item-img"
															id="file-input" accept="image/*" style="display: none">
													</div>
													<input type="file" name="img-file" class="img-file">
												</div>
												<div class="border-bottom py-4">


													<div class="form-group">
														<div class="input-group">
															<div class="input-group-prepend bg-transparent">
																<span
																	class="input-group-text bg-transparent border-right-0">
																	<i class="mdi mdi-account-outline text-primary"></i>
																</span>
															</div>

															<f:input path="firstName" id="firstName"
																onpaste="return false" placeholder="Firstname"
																onkeypress="islettersOnly(event)" type="text"
																class="form-control form-control-lg border-left-0" />

														</div>

														<span id="firsterror" class="text-danger font-weight-bold"></span>
														<!-- new line here -->

													</div>
													<div class="form-group">
														<div class="input-group">
															<div class="input-group-prepend bg-transparent">
																<span
																	class="input-group-text bg-transparent border-right-0">
																	<i class="mdi mdi-account-outline text-primary"></i>
																</span>
															</div>

															<f:input path="lastName" id="lastName"
																onpaste="return false" placeholder="Lastname"
																onkeypress="islettersOnly(event)" type="text"
																class="form-control form-control-lg border-left-0" />

														</div>

														<span id="lasterror" class="text-danger font-weight-bold"></span>
														<!-- new line here -->

													</div>
													<div class="form-group">
														<div class="input-group">
															<div class="input-group-prepend bg-transparent">
																<span
																	class="input-group-text bg-transparent border-right-0">
																	<i class="mdi mdi-email-outline text-primary"></i>
																</span>
															</div>

															<f:input path="loginVO.username" id="username"
																onpaste="return false" placeholder="Email" type="text"
																class="form-control form-control-lg border-left-0"
																readonly="true" />

														</div>

														<span id="emailerror" class="text-danger font-weight-bold"></span>
														<!-- new line here -->

													</div>
													<div class="form-group">
														<div class="input-group">
															<div class="input-group-prepend bg-transparent">
																<span
																	class="input-group-text bg-transparent border-right-0">
																	<i class="mdi mdi-lock-outline text-primary"></i>
																</span>
															</div>

															<f:input path="loginVO.password" id="password"
																onpaste="return false" placeholder="Password"
																type="password"
																class="form-control form-control-lg border-left-0"
																readonly="true" />

														</div>

														<span id="passworderror"
															class="text-danger font-weight-bold"></span>
														<!-- new line here -->

													</div>

													<%-- <f:input path="registerId" type="hidden"/>
													<f:input path="loginVO.loginId" type="hidden"/>
													<f:input path="loginVO.enabled" type="hidden"/>
													<f:input path="loginVO.role" type="hidden"/>
													<f:input path="emailVerificationVO.email" type="hidden"/>
													<f:input path="emailVerificationVO.status" type="hidden"/>
													<f:input path="emailVerificationVO.uuid" type="hidden"/>
													<f:input path="emailVerificationVO.id" type="hidden"/> --%>


													<input class="btn btn-primary btn-block mb-2" type="submit"
														value="Update">
												</div>
											</f:form>
										</div>
										<div class="col-lg-8">
											<div
												class="d-block d-md-flex justify-content-between mt-4 mt-md-0">
												<div class="text-center mt-4 mt-md-0">
													<button class="btn btn-outline-primary">Message</button>
													<button class="btn btn-primary">Request</button>
												</div>
											</div>
											<div class="mt-4 py-2 border-top border-bottom">
												<ul class="nav profile-navbar">
													<li class="nav-item"><a class="nav-link" href="#">
															<i class="mdi mdi-account-outline"></i> Info
													</a></li>
													<li class="nav-item"><a class="nav-link active"
														href="#"> <i class="mdi mdi-newspaper"></i> Feed
													</a></li>
													<li class="nav-item"><a class="nav-link" href="#">
															<i class="mdi mdi-calendar"></i> Agenda
													</a></li>
													<li class="nav-item"><a class="nav-link" href="#">
															<i class="mdi mdi-attachment"></i> Resume
													</a></li>
												</ul>
											</div>
											<div class="profile-feed">
												<div class="d-flex align-items-start profile-feed-item">
													<img
														src="<%=request.getContextPath()%>/adminResources/images/face13.jpg"
														alt="profile" class="img-sm rounded-circle" />
													<div class="ml-4">
														<h6>
															Mason Beck <small class="ml-4 text-muted"><i
																class="mdi mdi-clock mr-1"></i>10 hours</small>
														</h6>
														<p>There is no better advertisement campaign that is
															low cost and also successful at the same time.</p>
														<p class="small text-muted mt-2 mb-0">
															<span> <i class="mdi mdi-star mr-1"></i>4
															</span> <span class="ml-2"> <i
																class="mdi mdi-comment mr-1"></i>11
															</span> <span class="ml-2"> <i class="mdi mdi-reply"></i>
															</span>
														</p>
													</div>
												</div>
												<div class="d-flex align-items-start profile-feed-item">
													<img
														src="<%=request.getContextPath()%>/adminResources/images/face16.jpg"
														alt="profile" class="img-sm rounded-circle" />
													<div class="ml-4">
														<h6>
															Willie Stanley <small class="ml-4 text-muted"><i
																class="mdi mdi-clock mr-1"></i>10 hours</small>
														</h6>
														<img
															src="<%=request.getContextPath()%>/adminResources/images/12.jpg"
															alt="sample" class="rounded mw-100" />
														<p class="small text-muted mt-2 mb-0">
															<span> <i class="mdi mdi-star mr-1"></i>4
															</span> <span class="ml-2"> <i
																class="mdi mdi-comment mr-1"></i>11
															</span> <span class="ml-2"> <i class="mdi mdi-reply"></i>
															</span>
														</p>
													</div>
												</div>
												<div class="d-flex align-items-start profile-feed-item">
													<img
														src="<%=request.getContextPath()%>/adminResources/images/face19.jpg"
														alt="profile" class="img-sm rounded-circle" />
													<div class="ml-4">
														<h6>
															Dylan Silva <small class="ml-4 text-muted"><i
																class="mdi mdi-clock mr-1"></i>10 hours</small>
														</h6>
														<p>When I first got into the online advertising
															business, I was looking for the magical combination that
															would put my website into the top search engine rankings
														</p>
														<img
															src="<%=request.getContextPath()%>/adminResources/images/2.jpg"
															alt="sample" class="rounded mw-100" />
														<p class="small text-muted mt-2 mb-0">
															<span> <i class="mdi mdi-star mr-1"></i>4
															</span> <span class="ml-2"> <i
																class="mdi mdi-comment mr-1"></i>11
															</span> <span class="ml-2"> <i class="mdi mdi-reply"></i>
															</span>
														</p>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<!--Project Modal starts -->
				<div class="modal fade" id="addProfilePicture" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalLabel-2"
					aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="model-title">Update Profile
									Picture</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>


							<div class="crop-container" id="crop-container">
								<div id="upload-demo"></div>
							</div>

							<button
								class="center-button btn btn-outline-primary btn-icon-text mt-5 mb-3"
								id="crop-button">
								<i class="mdi mdi-crop"></i> Crop Selection
							</button>

							<span class="image-output"> <img src="" alt=""
								id="item-img-output" />
							</span>


							<div class="modal-footer" style="display: none">
								<button type="button" class="btn btn-primary" id="save-crop">Save</button>
								<button type="button" class="btn btn-light" data-dismiss="modal"
									aria-label="Close">Cancel</button>
							</div>

						</div>
					</div>
				</div>
				<!--Project Modal Ends -->

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
	<script
		src="<%=request.getContextPath()%>/adminResources/js/jquery.dataTables.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/dataTables.bootstrap4.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/croppie.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/cropper.js"></script>

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


	<!-- End plugin js for this page -->
	<!-- Custom js for this page-->
	<script
		src="<%=request.getContextPath()%>/adminResources/js/profile-demo.js"></script>


	<!-- <script src="<%=request.getContextPath()%>/adminResources/js/custom/registervalidation.js"></script> -->
	<!-- <script src="<%=request.getContextPath()%>/adminResources/js/custom/generalvalidation.js"></script> -->

	<!-- End custom js for this page-->
</body>

</html>
