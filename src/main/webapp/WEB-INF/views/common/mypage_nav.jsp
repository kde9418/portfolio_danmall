<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h4 style="text-align: center">마이페이지</h4>
		<br>
		
<ul class="nav nav-pills nav-justified">
	<li class="nav-item">
		<a class="nav-link <c:out value="${myActive == 'modify'? 'active' : '' }" />" href="/member/modify">회원정보수정</a>
	</li>
	<li class="nav-item">
		<a class="nav-link <c:out value="${myActive == 'pwUpdate'? 'active' : '' }" />" href="/member/pwUpdate">비밀번호수정</a>
	</li>
	<li class="nav-item">
		<a class="nav-link <c:out value="${myActive == 'delete'? 'active' : '' }" />" href="/member/delete">회원탈퇴</a>
	</li>
	<li class="nav-item">
		<a class="nav-link <c:out value="${myActive == 'board'? 'active' : '' }" />" href="/board/board_list">상품문의</a>
	</li>
</ul>