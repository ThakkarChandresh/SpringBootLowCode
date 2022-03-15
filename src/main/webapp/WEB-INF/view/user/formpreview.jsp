<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<!-- Fonts Awesome Icon CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/adminResources/theme/css/all.css">
<!--  End -->



<!-- CSS for this page -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/adminResources/theme/css/style.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/adminResources/theme/css/general.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/adminResources/theme/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/adminResources/theme/css/bread.css">
<!-- End -->
</head>
<body>
	<!-- Header-->
	<div class="header">
		<div class="head-text">HEADER</div>
		<ul class="navigation_bar">
			<li><img src="<%=request.getContextPath()%>/adminResources/theme/profile/profile.png" alt="profile"></li>
			<li>Logout</li>
		</ul>
	</div>
	<!-- End Header -->


	<!-- Menu Starts -->
	<div class="sidebar closed">
		<div class="logo-details">
			<i class="fab fa-cuttlefish"></i> <span class="logo_name">CHANDRESH</span>
			<i class="fas fa-bars"></i>
		</div>
		<ul class="nav-links">
			<li><a href="index.html"> <i class="fas fa-home"></i> <span
					class="link_name">Home</span>
			</a>
				<ul class="sub-menu blank">
					<li><a class="link_name" href="index.html">Home</a></li>
				</ul></li>
			<li>
				<div class="iocn-link">
					<a href="index.html"> <i class="fas fa-globe-africa"></i> <span
						class="link_name">Country</span>
					</a> <i class="fas fa-angle-down arrow"></i>
				</div>
				<ul class="sub-menu">
					<li><a class="link_name" href="index.html">Country</a></li>
					<li><a href="froms.html">Add</a></li>
					<li><a href="tables.html">View</a></li>
				</ul>
			</li>

		</ul>
	</div>
	<!-- Menu End -->

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
				<div class="card-body">
					<h4 class="card-title">Basic form elements</h4>
					<p class="card-description">Basic form elements</p>
					<form class="forms-sample">
						<div class="form-group">
							<label for="exampleInputName1">Name</label> <input type="text"
								class="form-control" id="exampleInputName1" placeholder="Name">
						</div>
						<div class="form-group">
							<label for="exampleInputEmail3">Email address</label> <input
								type="email" class="form-control" id="exampleInputEmail3"
								placeholder="Email">
						</div>
						<div class="form-group">
							<label for="exampleInputPassword4">Password</label> <input
								type="password" class="form-control" id="exampleInputPassword4"
								placeholder="Password">
						</div>
						<div class="form-group">
							<label for="exampleSelectGender">Gender</label> <select
								class="form-control" id="exampleSelectGender">
								<option>Male</option>
								<option>Female</option>
							</select>
						</div>
						<div class="form-group">
							<label>File upload</label> <input type="file" name="img[]"
								class="file-upload-default">
							<div class="input-group col-xs-12">
								<input type="text" class="form-control file-upload-info"
									disabled placeholder="Upload Image"> <span
									class="input-group-append">
									<button class="file-upload-browse btn btn-sm btn-primary"
										type="button">Upload</button>
								</span>
							</div>
						</div>
						<div class="form-group">
							<label for="exampleInputCity1">City</label> <input type="text"
								class="form-control" id="exampleInputCity1"
								placeholder="Location">
						</div>
						<div class="form-group">
							<label for="exampleTextarea1">Textarea</label>
							<textarea class="form-control" id="exampleTextarea1" rows="4"></textarea>
						</div>
						<button type="submit" class="btn btn-primary mr-2">Submit</button>
						<button class="btn btn-light" type="reset">Cancel</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- End Main -->


	<!-- Footer -->
	<div class="footer">
		<div class="foot-text">Copyright@ Chandresh Thakkar</div>
	</div>
	<!-- End Footer -->

	<!-- Custom JS for this page -->
	<script src="<%=request.getContextPath()%>/adminResources/theme/js/action.js"></script>
	<!-- End Inject-->
</body>
</html>