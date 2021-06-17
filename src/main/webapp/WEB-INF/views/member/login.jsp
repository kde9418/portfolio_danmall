<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
<meta name="generator" content="Hugo 0.80.0">
<meta name="theme-color" content="#563d7c">

<%@include file="/WEB-INF/views/common/config.jsp" %>

<title>DAN_MALL</title>
<script type="text/javascript" src="/js/member/login.js"></script>

<!-- Custom styles for this template -->
<link href="/resources/floating-labels.css" rel="stylesheet">
<link href="/resources/footer.css" rel="stylesheet">

<style>
	.bd-placeholder-img {
		font-size: 1.125rem;
		text-anchor: middle;
		-webkit-user-select: none;
		-moz-user-select: none;
		-ms-user-select: none;
		user-select: none;
	}

	@media (min-width: 768px) {
		.bd-placeholder-img-lg {
		font-size: 3.5rem;
		}
	}
	
	.sub a {
		color: black;
	}
</style>
</head>

<body>
<!-- nav.jsp -->
<%@include file="/WEB-INF/views/common/nav.jsp"%>

<%-- Main content  로그인 UI --%>
<div role="main" class="container">
	<!-- content-wrapper -->
	<div class="wrapper">
		<%-- Main content --%>
		<form id="loginForm" class="form-signin" action="/member/loginPost" method="post">
			<div class="text-center mb-4">
				<h1 class="h3 mb-3 font-weight-normal">LOGIN</h1>
			</div>
			
			<div class="form-label-group">
				<input type="text" id="mem_id" name="mem_id" class="form-control" placeholder="ID" required autofocus>
				<label for="inputId">ID</label>
			</div>
			
			<div class="form-label-group">
				<input type="password" id="mem_pw" name="mem_pw" class="form-control" placeholder="PASSWORD" required>
				<label for="inputPassword">PASSWORD</label>
			</div>
			
			<div class="checkbox mb-3">
				<label>
				<input type="checkbox" value="remember" id="remember"> Remember me
				</label>
			</div>
			
			<button id="btn_login" class="btn btn-lg btn-primary btn-block" type="submit">로그인</button>
			<br>
			
			<div class="sub" style="text-align: center">
				<a href="/member/join">회원가입</a> &nbsp; | &nbsp; 
				<a href="/member/find_id">아이디 찾기</a> &nbsp; | &nbsp; 
				<a href="/member/find_pw">비밀번호 찾기</a>
			</div>
			<p class="mt-4 mb-3 text-muted text-center">&copy; D A N M A L L</p>
		  
		</form>
	</div>
	<!-- /.content-wrapper -->
</div>

<%@include file="/WEB-INF/views/common/footer.jsp" %>
     
</body>
</html>
