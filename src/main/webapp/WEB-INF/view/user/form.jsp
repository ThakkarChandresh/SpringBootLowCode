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
<title>User | Forms</title>


<!-- directly coming from on module name click -->
<script type="text/javascript">
	var clickedModuleId;
	var clickedProjectId;
	
	<c:if test='${not empty projectId}'>
		clickedProjectId = ${projectId};
	</c:if>
	
	<c:if test='${not empty moduleId}'>
		clickedModuleId = ${moduleId};
	</c:if>
</script>


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
							<i style="font-size: 35px" class="mdi mdi-file-check mr-3"></i>
							<div>
								<h4>Forms</h4>
								<nav aria-label="breadcrumb">
									<ol class="breadcrumb" id="top-bread">
										<li class="breadcrumb-item"><a href="index">Dashboard</a></li>
										<li class="breadcrumb-item"><a href="projects">Projects</a></li>
										<li class="breadcrumb-item"><a href="modules">Modules</a></li>
										<li class="breadcrumb-item active" aria-current="page">Forms</li>
									</ol>
								</nav>
							</div>
						</div>
						<button type="button"
							class="btn btn-outline-primary btn-icon-text" data-toggle="modal"
							data-target="#formModal">
							<i class="mdi mdi-plus mr-2"></i>Add
						</button>
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

				<!--Forms Modal starts -->
				<div class="modal fade" id="formModal" tabindex="-1" role="dialog"
					aria-labelledby="exampleModalLabel" aria-hidden="true">
					<div class="modal-dialog modal-lg75 mt-4" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">Add Forms</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
							</div>
							<div class="modal-body">
								<div class="row">
									<div class="form-group col">
										<label class="form-label">Project Name: <span
											class="required-field">*</span></label> <select
											class="form-control form-control-lg border-left-1 modal-input"
											id="projectId">
											<c:forEach var="p" items="${projectList}">
												<option value="${p.id}">${p.projectName}</option>
											</c:forEach>
										</select>
									</div>

									<div class="form-group col">
										<label class="form-label">Module Name: <span
											class="required-field">*</span></label> <select
											class="form-control form-control-lg border-left-1 modal-input"
											id="moduleId">
											<c:forEach var="m" items="${moduleList}">
												<option value="${m.id}">${m.moduleName}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<hr>
								<label class="font-weight-bold">Form</label>
								<div class="row">
									<div class="form-group col">
										<label class="form-label">Name <span
											class="required-field">*</span></label> <input name="name" id="name"
											class="form-control form-control-lg border-left-1 modal-input"
											value="" /> <span id="formNameError"
											class="text-danger font-weight-bold"> </span>
									</div>
									<div class="form-group col">
										<label class="form-label">Description<span
											class="required-field">*</span></label> <input name="description"
											id="description"
											class="form-control form-control-lg border-left-1 modal-input"
											value="" /> <span id="formDescError"
											class="text-danger font-weight-bold"> </span>
									</div>
								</div>
								<hr>
								<label class="font-weight-bold">Form Details</label>
								<div class="form-group row">
									<div class="col-sm-5">
										<label class="form-label">Field Name<span
											class="required-field">*</span></label> <input name="fieldName"
											class="form-control form-control-lg border-left-1 modal-input"
											id="fieldName"> <span id="fieldNameError"
											class="text-danger font-weight-bold"> </span>
									</div>
									<div class="col-sm-5">
										<label class="form-label">Field Type<span
											class="required-field">*</span></label> <select name="fieldType"
											class="form-control form-control-lg border-left-1 modal-input"
											id="fieldType">
											<option value="text">Text</option>
											<option value="password">Password</option>
											<option value="email">Email</option>
											<option value="text-area">Text-area</option>
											<option value="number">Number</option>
											<option value="date">Date</option>
											<option value="file">File</option>
											<option value="datetime-local">Datetime-local</option>
											<option value="radiobutton">Radio Button</option>
											<option value="checkbox">Checkbox</option>
											<option value="dropdown">Dropdown</option>
										</select>
									</div>
									<div class="col-sm-2">
										<button type="button" id="fieldAddButton"
											class="btn btn-outline-primary btn-icon-text py-2 mt-1_8">
											Add</button>
									</div>
								</div>

								<div class="form-group row" id="sub-menu"></div>
								<hr>


								<table class="table table-striped border">
									<thead>
										<tr class="text-center">
											<th rowspan="2" class="pb-3 border-right" scope="col">#</th>
											<th rowspan="2" class="pb-3 border-right" scope="col">Field
												Name</th>
											<th rowspan="2" class="pb-3 border-right" scope="col">Field
												Type</th>
											<th colspan="2" scope="col">Options</th>
										</tr>
										<tr class="text-center">
											<th class="border-right" scope="col">values</th>
											<th scope="col">Lables</th>
										</tr>
									</thead>
									<tbody id="table-data">
										<tr id="instruction">
											<td class="text-center" colspan="5">Add Fields From
												Above Section</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div class="modal-footer">
								<button type="button" id="submit" class="btn btn-primary">Submit</button>
								<button type="button" class="btn btn-light" data-dismiss="modal">Cancel</button>
							</div>
						</div>
					</div>
				</div>
				<!--Forms Modal Ends -->

				<!--Forms Details Modal starts -->
				<div class="modal fade" id="formDetailModal" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalLabel"
					aria-hidden="true">
					<div class="modal-dialog modal-lg75" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">Form Details</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
							</div>
							<div class="modal-body">
								<table class="table table-striped border">
									<thead>
										<tr class="text-center">
											<th rowspan="2" class="pb-3 border-right" scope="col">#</th>
											<th rowspan="2" class="pb-3 border-right" scope="col">Field
												Name</th>
											<th rowspan="2" class="pb-3 border-right" scope="col">Field
												Type</th>
											<th colspan="2" scope="col">Options</th>
										</tr>
										<tr class="text-center">
											<th class="border-right" scope="col">values</th>
											<th scope="col">Lables</th>
										</tr>
									</thead>
									<tbody id="form-details-data">
									</tbody>
								</table>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-light" data-dismiss="modal">Cancel</button>
							</div>
						</div>
					</div>
				</div>
				<!--Forms Details Modal Ends -->
			</div>
			<!-- content-wrapper ends -->
		</div>
		<!-- main-panel ends -->
	</div>
	<!-- page-body-wrapper ends -->

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
		src="<%=request.getContextPath()%>/adminResources/js/jquery.steps.min.js"></script>

	<script
		src="<%=request.getContextPath()%>/adminResources/js/jquery.validate.min.js"></script>

	<script
		src="<%=request.getContextPath()%>/adminResources/js/hotkeys.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/custom/userkeypress.js"></script>
	<!-- End plugin js for this page -->

	<!-- Custom js for this page-->
	<script
		src="<%=request.getContextPath()%>/adminResources/js/tooltips.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/TableComponents.js"></script>

	<script
		src="<%=request.getContextPath()%>/adminResources/js/sweetalert.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/custom/form.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/custom/formgenerator.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/custom/formCreator.js"></script>
	<!-- End custom js for this page-->
</body>
</html>