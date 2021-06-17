<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
<!-- css file -->
<%@include file="/WEB-INF/views/admin/include/head_inc.jsp" %>

<!-- 상태코드 -->
<script>
	var massage = '${status}';
		
	if(massage == 'loginFail'){
		alert('아이디 또는 비밀번호가 틀립니다.');
	}
</script>
</head>
<!--
BODY TAG OPTIONS:
=================
Apply one or more of the following classes to get the
desired effect
|---------------------------------------------------------|
| SKINS         | skin-blue                               |
|               | skin-black                              |
|               | skin-purple                             |
|               | skin-yellow                             |
|               | skin-red                                |
|               | skin-green                              |
|---------------------------------------------------------|
|LAYOUT OPTIONS | fixed                                   |
|               | layout-boxed                            |
|               | layout-top-nav                          |
|               | sidebar-collapse                        |
|               | sidebar-mini                            |
|---------------------------------------------------------|
-->
<body class="hold-transition skin-blue sidebar-mini">
	<!-- wrapper -->
	<div class="wrapper">

		<!-- Main Header -->
			<%@include file="/WEB-INF/views/admin/include/main-header.jsp" %>
		
		<!-- Left side column. contains the logo and sidebar -->
			<%@include file="/WEB-INF/views/admin/include/main-sidebar.jsp" %>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					Login Page <small>관리자 로그인</small>
				</h1>
				<ol class="breadcrumb">
					<li>
						<a href="/admin/"><i class="fa fa-dashboard"></i> Main</a>
					</li>
					<li class="active">Here</li>
				</ol>
			</section>

			<!-- Main content -->
			<section class="content container-fluid">
				<div class="container" style="width: 450px; height:620px; background-color: none; margin-top:30px;">
					<%-- Main content --%>
					<form id="loginForm" class="form-signin" action="/admin/adminPost" method="post">
						<div class="text-center mb-4">
							<h1 class="h3 mb-3 font-weight-normal" style="margin-bottom: 20px"><b>LOGIN</b></h1>
						</div>
						
						<div class="form-label-group">
							<label for="inputId">ID</label>
							<input type="text" id="admin_id" name="admin_id" class="form-control" style="margin-bottom: 20px"
									placeholder="ID" required autofocus>
						</div>
						<div class="form-label-group">
							<label for="inputPassword">PASSWORD</label>
							<input type="password" id="admin_pw" name="admin_pw" class="form-control" placeholder="PASSWORD" required>
						</div>
						<br><br>
						<button id="btn_login" class="btn btn-lg btn-primary btn-block" type="button">로그인</button>
						<br>
						
						<p class="mt-4 mb-3 text-muted text-center">&copy; D A N M A L L</p>
					</form>
				</div>
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->

		<!-- Main Footer -->
			<%@include file="/WEB-INF/views/admin/include/main-footer.jsp" %>

		
		<!-- Add the sidebar's background. This div must be placed
  immediately after the control sidebar -->
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->

	<!-- REQUIRED JS SCRIPTS -->
	<%@include file="/WEB-INF/views/admin/include/scripts.jsp" %>
	<script type="text/javascript" src="/js/admin/admin_login.js"></script>
	
	<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. -->
</body>
</html>