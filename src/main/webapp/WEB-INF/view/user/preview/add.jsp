<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add</title>

<!-- Fonts Awesome Icon CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/adminResources/theme/css/all.css">
<!--  End -->

<!-- CSS for this page -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/adminResources/theme/css/style.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/adminResources/theme/css/general.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/adminResources/theme/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/adminResources/theme/css/bread.css">
<!-- End -->

<!-- JQuery.min.js -->
<script src="<%=request.getContextPath()%>/adminResources/theme/js/jquery.min.js"></script>
<!-- End JQuery -->

<!-- Internal Js -->
<script type="text/javascript">
	var formId;
	var pageName = 'add';
	
	<c:if test='${not empty formId}'>
		formId = ${formId};
	</c:if>
</script>
<!-- end internal js -->
</head>
<body>
	<!-- Header-->
	<jsp:include page="header.jsp"></jsp:include>
	<!-- End Header -->
	<script type="text/javascript">
		$(".header").css("background-color", '${colorMap.headerColor}');
	</script>


	<!-- Menu Starts -->
	<jsp:include page="menu.jsp"></jsp:include>
	<!-- Menu End -->
	<script type="text/javascript">
		$(".sidebar").css("background-color", '${colorMap.menuColor}');
	</script>

	<!-- Main -->
	<div id="main">
	
		<!-- Breadcrum -->
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="index.html">Country</a></li>
			<li class="breadcrumb-item active" aria-current="page">Add</li>
		</ol>
		<!-- End Bredcrum -->


		<div class="container">
			<div class="card">
				<div class="card-body" id="form-card">
					<h4 class="card-title"></h4>
				</div>
			</div>
		</div>
	</div>
	<!-- End Main -->


	<!-- Footer -->
	<jsp:include page="footer.jsp"></jsp:include>
	<!-- End Footer -->
	<script type="text/javascript">
		$(".footer").css("background-color", '${colorMap.footerColor}');
	</script>

	<!-- Custom JS for this page -->
	<script src="<%=request.getContextPath()%>/adminResources/theme/js/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/adminResources/js/jquery.xcolor.min.js"></script>
	<script src="<%=request.getContextPath()%>/adminResources/theme/js/action.js"></script>
	<script
		src="<%=request.getContextPath()%>/adminResources/js/custom/formgenerator.js"></script>
	<script src="<%=request.getContextPath()%>/adminResources/theme/js/main.js"></script>
	<!-- End Inject-->
</body>
</html>