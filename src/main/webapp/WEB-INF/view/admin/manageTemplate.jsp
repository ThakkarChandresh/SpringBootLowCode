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
<title>ADMIN | Manage Template</title>
<!-- base:css -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/materialdesignicons.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/vendor.bundle.base.css">
<!-- endinject -->


<!-- plugin css for this page -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adminResources/css/Tiny_Style.css">
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

<body>
	<div class="container-scroller">

		<!-- Header Start -->
		<jsp:include page="header.jsp"></jsp:include>
		<!-- Header End -->

		<div class="container-fluid page-body-wrapper">



			<!-- Menu -->
			<jsp:include page="menu.jsp"></jsp:include>


			<div class="main-panel">
				<div class="content-wrapper">
					<div class="card">
						<div class="card-body">
							<h4 class="card-title">Editor</h4>
							<ul class="nav nav-pills nav-pills-success" id="pills-tab"
								role="tablist">
								<li class="nav-item"><a class="nav-link active"
									id="editor-code-tab" data-toggle="pill" href="#editor-code"
									role="tab" aria-controls="editor-code" aria-selected="true">Code</a>
								</li>
								<li class="nav-item"><a class="nav-link"
									id="editor-preview-tab" data-toggle="pill"
									href="#editor-preview" role="tab"
									aria-controls="editor-preview" aria-selected="false">Preview</a>
								</li>
							</ul>
							<div class="tab-content" id="pills-tabContent">
								<div class="tab-pane fade show active" id="editor-code"
									role="tabpanel" aria-labelledby="editor-code-tab">
									<div class="card-body">
										<h4 class="card-title">Tinymce Editor</h4>
										<textarea id='full-featured-non-premium'>
                    						Edit your content here...
                  						</textarea>
									</div>
								</div>
								<div class="tab-pane fade" id="editor-preview" role="tabpanel"
									aria-labelledby="editor-preview-tab"></div>
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
		src="<%=request.getContextPath()%>/adminResources/js/jquery.dataTables.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/dataTables.bootstrap4.js"></script>
	<!-- endinject -->


	<!-- plugin js for this page -->
	<script
		src="<%=request.getContextPath()%>/adminResources/js/tinymce.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/Tiny_Style.js"></script>
	<!-- End plugin js for this page -->


</body>
</html>