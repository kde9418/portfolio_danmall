<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	    	<header class="main-header">
				<!-- Logo -->
			<a href="/admin/" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels --> <span class="logo-mini">
					<b>D</b>M
				</span> <!-- logo for regular state and mobile devices --> <span class="logo-lg">
					<b>DAN</b>MALL
				</span>
			</a>

			<!-- Header Navbar -->
			<nav class="navbar navbar-static-top" role="navigation">
				<!-- Sidebar toggle button-->
				<a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button"> <span class="sr-only">Toggle navigation</span>
				</a>
				<!-- Navbar Right Menu -->
				<div class="navbar-custom-menu">
				
				<!-- 로그인 전 -->
				<c:if test="${sessionScope.adLoginStatus == null }">
					<ul class="nav navbar-nav">
						<li><a href="/admin/admin_login">로그인</a></li>
						<li><a href="/admin/admin_join">계정추가</a></li>
					</ul>
				</c:if>
				
				<!-- 로그인 후 -->
				<c:if test="${sessionScope.adLoginStatus != null }">
					<ul class="nav navbar-nav">
						<li><a>[ ${sessionScope.adLoginStatus.admin_name } ] 님</a></li>
						<li><a>
							<span style="color: lightgray; font-size: 12px;">
							최근 로그인한 시간 : <fmt:formatDate value="${sessionScope.adLoginStatus.admin_date_late }" pattern="yyyy-MM-dd HH:mm:ss" />
							</span>
						</a></li>
						<li style="padding: 13px 10px;">
							<form action="/admin/admin_logout" method="post">
								<input type="submit" value="로그아웃" style="background-color: #3c8dbc; border: none; color:white;">
							</form>
						</li>
					</ul>
				</c:if>
				</div>
			</nav>
		</header>