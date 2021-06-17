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

<title>DAN_MALL : 비밀번호 찾기</title>
<script type="text/javascript" src="/js/member/id_pw_search.js"></script>

<!-- Custom styles for this template -->
<link href="/resources/floating-labels.css" rel="stylesheet">
</head>

<body>
<!-- nav.jsp -->
<%@include file="/WEB-INF/views/common/nav.jsp"%>

<%-- Main content --%>
<div role="main" class="container">
	<!-- content-wrapper -->
	<div class="wrapper">
		<%-- Main content --%>
		<form class="form-signin">
			<div class="text-center mb-4">
				<h1 class="h3 mb-3 font-weight-normal">비밀번호 찾기</h1>
			</div>
			
			<div class="form-label-group">
				<input type="text" id="mem_id" name="mem_id" class="form-control" placeholder="ID" required autofocus>
				<label for="inputId">ID</label>
			</div>
			
			<div class="form-label-group">
				<input type="text" id="mem_name" name="mem_name" class="form-control" placeholder="NAME" required>
				<label for="inputPassword">NAME</label>
			</div>
			
			<button type="button" id="btn_PW_Search" class="btn btn-lg btn-primary btn-block">찾 기</button>
			<br><br>
			<p id="result" style="color:red; text-align: center"></p>
			
			<div style="text-align: center">
				<a href="/member/find_id">아이디찾기</a> &nbsp; | &nbsp; 
				<a href="/member/login">로그인하기</a>
			</div>
			  
			<p class="mt-5 mb-3 text-muted text-center">&copy; D A N M A L L</p>
	</form>	
	</div>
	<!-- /.content-wrapper -->
</div>
      
</body>
</html>
