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

<title>DAN_MALL</title>

<!-- config.jsp -->
<%@include file="/WEB-INF/views/common/config.jsp" %>
<!-- Custom styles for this template -->
<link href="/resources/footer.css" rel="stylesheet">

<script>
	var massage = '${status}';
		
	if(massage == 'loginSuccess'){
		alert('로그인 되었습니다.');
	} else if(massage == 'loginIDFail'){
		alert('아이디가 틀립니다.');
	} else if(massage == 'loginPWFail'){
		alert('비밀번호가 틀립니다.');
	} else if(massage == 'loginFail'){
		alert('로그인에 실패했습니다.');
	} else if(massage == 'insertSuccess'){
		alert('회원가입 되었습니다.');
	} else if(massage == 'logout'){
		alert('로그아웃 되었습니다.');
	} else if(massage == 'modifySuccess'){
		alert('회원정보 수정이 되었습니다.');
	} else if(massage == 'modifyFail'){
		alert('회원정보 수정이 실패했습니다.');
	} else if(massage == 'deleteMember'){
		alert('회원탈퇴 되었습니다.');
	} else if(massage == 'pwFail'){
		alert('비밀번호가 틀렸습니다.');
	}

</script>

</head>

<body>
    
<!-- nav.jsp -->
<%@include file="/WEB-INF/views/common/nav.jsp"%>

<main role="main" class=".container-fluid" style="padding-bottom: 70px;">

	<div class="row">
		<!-- 카테고리 목록 -->
		<div class="col-md-2">
			<%@include file="/WEB-INF/views/common/category_list.jsp"%>
		</div>
		
		<!-- 컨텐츠 -->
		<div class="col-md-10">
		
		<div class="container">
			<div id="carouselExampleInterval" class="carousel slide" data-ride="carousel">
				<div class="carousel-inner" style="height: 500px;">
					<div class="carousel-item active" data-interval="10000">
						<img src="/product/displayFile?fileName=/img/red.jpg" class="d-block w-100" alt="...">
					</div>
					<div class="carousel-item" data-interval="2000">
						<img src="/product/displayFile?fileName=/img/yellow.jpg" class="d-block w-100" alt="...">
					</div>
					<div class="carousel-item">
						<img src="/product/displayFile?fileName=/img/green.png" class="d-block w-100" alt="...">
					</div>
				</div>
				<a class="carousel-control-prev" href="#carouselExampleInterval" role="button" data-slide="prev">
					<span class="carousel-control-prev-icon" aria-hidden="true"></span>
					<span class="sr-only">Previous</span>
				</a>
				<a class="carousel-control-next" href="#carouselExampleInterval" role="button" data-slide="next">
					<span class="carousel-control-next-icon" aria-hidden="true"></span>
					<span class="sr-only">Next</span>
				</a>
			</div>
		
			
		</div>
		</div>
	</div>

</main><!-- /.container -->

<%@include file="/WEB-INF/views/common/footer.jsp" %>

</body>
</html>
