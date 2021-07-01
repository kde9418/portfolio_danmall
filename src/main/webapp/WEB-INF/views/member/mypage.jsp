<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
<meta name="generator" content="Hugo 0.80.0">
<meta name="theme-color" content="#563d7c">
<%@include file="/WEB-INF/views/common/config.jsp" %>

<title>DAN_MALL</title>

<!-- Custom styles for this template -->
<link href="/resources/footer.css" rel="stylesheet">

</head>
<body>
    
<!-- nav.jsp -->
<%@include file="/WEB-INF/views/common/nav.jsp"%>

<main role="main" class="container">
	<div class="wrapper">
		<%-- Main content --%>
		<%@include file="/WEB-INF/views/common/mypage_nav.jsp"%>
		<div style="padding-bottom: 350px"></div>
		
		<!-- /.content -->
	</div>
	<!-- /.content-wrapper -->

</main><!-- /.container -->

<%@include file="/WEB-INF/views/common/footer.jsp" %>

</body>
</html>
