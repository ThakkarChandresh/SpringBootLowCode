<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

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
									<h4 class="card-title">Form Creation</h4>
									<form id="example-vertical-wizard" action="#">
										<div>
											<h3>Project</h3>
											<section>
												<div class="container mt-5" id="projectGrid">
													<c:forEach var="p" items="${projectList}">
														<div class="card d-flex justify-content-center projectDiv" id="projectCard">
															<div class="card_body">
																<p>${p.projectName}</p>
																<input type="checkbox" class="radio" value="${p.id}"
																	name="projectId" />
															</div>
														</div>
													</c:forEach>
												</div>
											</section>
											<h3>Module</h3>
											<section>
												<div class="container mt-5" id="moduleGrid">
												</div>
											</section>
											<h3>Form</h3>
											<section>
												<h3>Form</h3>
												<div class="form-check">
													<label class="form-check-label"> <input
														class="checkbox" type="checkbox"> I agree with the
														Terms and Conditions.
													</label>
												</div>
											</section>
										</div>
									</form>
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
		src="<%=request.getContextPath()%>/adminResources/js/jquery.min.js"></script>



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
		src="<%=request.getContextPath()%>/adminResources/js/jquery.steps.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/jquery.validate.min.js"></script>

	<script
		src="<%=request.getContextPath()%>/adminResources/js/colcade.js"></script>
	<!-- End plugin js for this page -->


	<!-- Custom js for this page-->
	<script src="<%=request.getContextPath()%>/adminResources/js/wizard.js"></script>

	<script
		src="<%=request.getContextPath()%>/adminResources/js/tight-grid.js"></script>

	<script src="<%=request.getContextPath()%>/adminResources/js/threed.js"></script>
	<!-- End custom js for this page-->

</body>

</html>
