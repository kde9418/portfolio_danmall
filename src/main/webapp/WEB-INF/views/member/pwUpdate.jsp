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
 
<script type="text/javascript" src="/js/member/pwUpdate.js"></script>
<script>
	var massage = '${status}';
		
	if(massage == 'PWUPDATE_SUCCESS'){
		alert('비밀번호를 변경했습니다.');
	}

</script>
</head>
<body>
    
<!-- nav.jsp -->
<%@include file="/WEB-INF/views/common/nav.jsp"%>

<main role="main" class="container">
	<div class="wrapper">
	<%@include file="/WEB-INF/views/common/mypage_nav.jsp"%>
		<%-- Main content --%>
		<section class="content container-fluid" style="background-color: ivory;">
				<div class="container" style="width: 70%; min-width: 900px; background-color: ivory; font-size: 14px;" >
					<form id="pwUpdateForm" action="/member/pwUpdatePost" method="post">
						<div class="form-group" style="width: 800px; padding: 5%;">
							<label for="mem_pw">* 기존 비밀번호</label>
							<input type="password" class="form-control" id="mem_pw" placeholder="기존 비밀번호를 입력해주세요."
								style="max-width: 630px;">
							<label for="mem_pw_change">* 변경 비밀번호</label>
							<input type="password" class="form-control" id="mem_pw_change" name="mem_pw" placeholder="변경할 비밀번호를 입력해주세요."
								style="max-width: 630px;">
							<label for="mem_pw_check">* 변경 비밀번호 확인</label>
							<input type="password" class="form-control" id="mem_pw_check" placeholder="변경할 비밀번호를 다시 입력해주세요."
								style="max-width: 630px;">
						</div>
						<div class="form-group text-center" style="padding: 5%;">
							<button type="button" id="btn_submit" class="btn btn-primary">비밀번호 수정</button>
							<!-- 
							&nbsp;&nbsp;
							<button type="button" class="btn btn-primary" onclick="location.href='/';">취소</button>
							 -->
						</div>
					</form>
				</div>
				
			</section>
		<!-- /.content -->
	</div>
	<!-- /.content-wrapper -->

</main><!-- /.container -->

</body>
</html>
