<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>User | Projects</title>
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
	href="<%=request.getContextPath()%>/adminResources/css/coloris.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/style.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/loader.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/all.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/switch.css">
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
					<div class="d-flex justify-content-between align-items-center">
						<div class="d-flex justify-content-start align-items-center">
							<i class="mdi mdi-airplay mr-3"></i>
							<div>
								<h4>Projects</h4>
								<nav aria-label="breadcrumb">
									<ol class="breadcrumb" id="top-bread">
										<li class="breadcrumb-item"><a href="index">Dashboard</a></li>
										<li class="breadcrumb-item active" aria-current="page">Project</li>
									</ol>
								</nav>
							</div>


						</div>
						<button type="button"
							class="btn btn-outline-primary btn-icon-text" data-toggle="modal"
							data-target="#addProjectModel">
							<i class="mdi mdi-plus mr-2"></i>Add
						</button>
					</div>

					<div class="card mt-3">
						<div id="cover-spin"></div>
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



	<!--Project Modal starts -->
	<div class="modal fade" id="addProjectModel" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel-2" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="model-title">Add Project</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<f:form action="projects" method="POST" id="projectForm"
					modelAttribute="projectVO">
					<div class="modal-body">
						<div class="form-group mb-2">
							<label class="form-label">Project Name <span
								class="required-field">*</span></label>
							<f:input path="projectName" id="projectName"
								class="form-control form-control-lg border-left-1 modal-input"
								value="" />
							<span id="nameError" class="text-danger font-weight-bold">
							</span>
						</div>
						<div class="form-group mb-2">
							<label class="form-label">Project Description <span
								class="required-field">*</span></label>

							<f:textarea path="projectDescription" id="projectDescription"
								class="form-control form-control-lg pt-1 px-1" value="" rows="2" />
							<span id="descError" class="text-danger font-weight-bold">
							</span>

						</div>
						<div class="form-group mb-2">
							<label
								class="form-label d-flex justify-content-between align-items-center">
								<span> Project Icon <span class="required-field">*</span>
							</span> <span
								class="btn btn-outline-secondary btn-rounded btn-icon icon-info-back"
								data-toggle="tooltip" data-html="true" data-placement="bottom"
								title=""
								data-original-title="<b>&emsp;&ensp;&nbsp; Icon Supported</b><ul><li>Fontawesome Icon</li><li>Mdi Icon</li></ul>">
									<i class="fas fa-info icon-info"></i>
							</span>
							</label>
							<f:input path="projectIcon" id="projectIcon"
								class="form-control form-control-lg border-left-1 modal-input"
								value="" />
							<span id="iconError" class="text-danger font-weight-bold">
							</span>
						</div>
						<f:input path="id" type="hidden" id="projectId" value="0" />
					</div>
					<div class="modal-footer">
						<input type="submit" id="submitButton" value="Add"
							class="btn btn-primary">
						<button type="button" class="btn btn-light" data-dismiss="modal">Cancel</button>
					</div>
				</f:form>
			</div>
		</div>
	</div>
	<!--Project Modal Ends -->


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
		src="<%=request.getContextPath()%>/adminResources/js/hotkeys.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/custom/userkeypress.js"></script>
	<!-- End plugin js for this page -->

	<script
		src="<%=request.getContextPath()%>/adminResources/js/tooltips.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/TableComponents.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/sweetalert.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/custom/project.js"></script>
	<!-- End custom js for this page-->
</body>
</html>