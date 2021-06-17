<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		<aside class="main-sidebar">
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">

				<!-- 로그인 전 -->
				<c:if test="${sessionScope.adLoginStatus == null }">
				<ul class="sidebar-menu" data-widget="tree">
					<li class="header">MENU</li>
					<li>
						<a href="/admin/admin_login"><i class="fa fa-link"></i> <span>로그인</span></a>
					</li>
					<li>
						<a href="/admin/admin_join"><i class="fa fa-link"></i> <span>계정추가</span></a>
					</li>
				</ul>
				</c:if>

				<!-- 로그인 후 -->
				<c:if test="${sessionScope.adLoginStatus != null }">
				<!-- Sidebar user panel (optional) -->
				<div class="user-panel">
					<div class="pull-left image">
						<img src="/dist/img/red_heart.png" class="img-circle" alt="User Image">
					</div>
					<div class="pull-left info">
						<p>${sessionScope.adLoginStatus.admin_name }</p>
					</div>
				</div>

				<!-- Sidebar Menu -->
				<ul class="sidebar-menu" data-widget="tree">
					<li class="header">MENU</li>
					<!-- Optionally, you can add icons to the links -->
					<li class="treeview">
						<a href="#"><i class="fa fa-link"></i> <span>상품관리</span> <span class="pull-right-container">
								<i class="fa fa-angle-left pull-right"></i>
							</span> </a>
						<ul class="treeview-menu">
							<li>
								<a href="${pageContext.request.contextPath }/admin/product/product_insert">상품등록</a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath }/admin/product/pro_list">상품목록</a>
							</li>
						</ul>
					</li>
					<li class="treeview">
						<a href="#"><i class="fa fa-link"></i> <span>주문관리</span> <span class="pull-right-container">
								<i class="fa fa-angle-left pull-right"></i>
							</span> </a>
						<ul class="treeview-menu">
							<li>
								<a href="${pageContext.request.contextPath }/admin/order/order_list">주문목록</a>
							</li>
							<li>
								<a href="#">Link in level 2</a>
							</li>
						</ul>
					</li>
					<li class="treeview">
						<a href="#"><i class="fa fa-link"></i> <span>회원관리</span> <span class="pull-right-container">
								<i class="fa fa-angle-left pull-right"></i>
							</span> </a>
						<ul class="treeview-menu">
							<li>
								<a href="/admin/member/userInfo_list">회원목록</a>
							</li>
							<li>
								<a href="#">Link in level 2</a>
							</li>
						</ul>
					</li>
					<li class="treeview">
						<a href="#"><i class="fa fa-link"></i> <span>게시판관리</span> <span class="pull-right-container">
								<i class="fa fa-angle-left pull-right"></i>
							</span> </a>
						<ul class="treeview-menu">
							<li>
								<a href="#">Link in level 2</a>
							</li>
							<li>
								<a href="#">Link in level 2</a>
							</li>
						</ul>
					</li>
					<li class="treeview">
						<a href="#"><i class="fa fa-link"></i> <span>통계/매출현황관리</span> <span class="pull-right-container">
								<i class="fa fa-angle-left pull-right"></i>
							</span> </a>
						<ul class="treeview-menu">
							<li>
								<a href="/admin/order/order_sale">매출통계</a>
							</li>
							<li>
								<a href="#">Link in level 2</a>
							</li>
						</ul>
					</li>
				</ul>
				</c:if>
				<!-- /.sidebar-menu -->
			</section>
			<!-- /.sidebar -->
		</aside>