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
					<form id="deleteForm" action="/member/deletePost" method="post">
						<div class="form-group" style="width: 800px; padding: 5%;">
							<p>[ <b style="color: orange;">${sessionScope.loginStatus.mem_name }</b> ] 님 정말 탈퇴하시겠습니까?</p>
							<p>아래 현재 비밀번호를 입력해주세요.</p>
							<input type="password" class="form-control" id="mem_pw" placeholder="현재 비밀번호를 입력해주세요."
								style="max-width: 630px;">

						</div>
						<div class="form-group text-center" style="padding: 5%;">
							<button type="button" id="btn_submit" class="btn btn-danger">회원탈퇴</button>
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

<script>
$(document).ready(function(){
	
	var form = $("#deleteForm");
	
	var mem_pw = $("#mem_pw");

	/* 확인 버튼 클릭 시 */
	$("#btn_submit").on("click", function(){
		// 유효성 검사
		if(mem_pw.val()==null || mem_pw.val()==""){
			alert("현재 비밀번호를 입력해주세요.");
			mem_pw.focus();
			return;
			
		} 
		
		// 현재 비밀번호 확인 검사
		var mem_pw_val = mem_pw.val();
		
		$.ajax({
			url: "/member/checkPwAjax",
			type: "post",
			datatype: "text",
			data: {mem_pw : mem_pw_val},
			success: function(data){
				if(data=='SUCCESS'){
					confirm("회원탈퇴를 정말 하시겠습니까?");
					form.submit();
					
				} else{
					alert("현재 비밀번호가 다릅니다.\n다시 입력해주세요.");
					mem_pw.val("");
					mem_pw.focus();
				}
			} 
		});
	});
});
</script>

</body>
</html>
