<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
<!-- css file -->
<%@include file="/WEB-INF/views/admin/include/head_inc.jsp" %>
<script src="/ckeditor/ckeditor.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

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
					Page Header <small>Optional description</small>
				</h1>
				<ol class="breadcrumb">
					<li>
						<a href="#"><i class="fa fa-dashboard"></i> Level</a>
					</li>
					<li class="active">Here</li>
				</ol>
			</section>

			<!-- Main content -->
			<section class="content container-fluid">
				<!--  -->
				<div class="row">
					<div class="col-lg-12">
						<form id="searchForm" action="" method="get">
							<select name="type" id="type">
								<option value="" <c:out value="${pageMaker.cri.type == null ? 'selected':'' }" />>---</option>
								<option value="N" <c:out value="${pageMaker.cri.type == 'N' ? 'selected':'' }" />>상품명</option>
								<option value="D" <c:out value="${pageMaker.cri.type == 'D' ? 'selected':'' }" />>상품설명</option>
								<option value="C" <c:out value="${pageMaker.cri.type == 'C' ? 'selected':'' }" />>상품회사</option>
								<option value="ND" <c:out value="${pageMaker.cri.type == 'ND' ? 'selected':'' }" />>상품명 or 상품설명</option>
								<option value="NC" <c:out value="${pageMaker.cri.type == 'NC' ? 'selected':'' }" />>상품명 or 상품회사</option>
								<option value="NDC" <c:out value="${pageMaker.cri.type == 'NDC' ? 'selected':'' }" />>상품명 or 상품설명 or 상품회사</option>
							</select>
							<input type="text" name="keyword" value="${pageMaker.cri.keyword }">
							<input type="hidden" name="pageName" value="${pageMaker.cri.pageNum }">
							<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
							<button id="btnSearch" type="button" class="btn btn-primary">검색</button>
						</form>
					</div>
				</div>

				<!-- 상품 리스트 -->
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
							BoardList. <button id="regBtn" type="button" class="btn btn-primary pull-right">글쓰기</button>
							</div>
							  	
							<div class="panel-body">
								<!-- 리스트 -->
								<table class="table table-striped">
									<thead>
										<tr>
										<th scope="col">아이디</th>
										<th scope="col">이름</th>
										<th scope="col">전자우편</th>
										<th scope="col">우편번호</th>
										<th scope="col">주소</th>
										<th scope="col">나머지주소</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${user_list }" var="userVO" varStatus="status">
											<tr class="orderList">
												<th scope="row"><input type="text" value="${userVO.user_id}"></th>
												<th scope="row"><input type="text" value="${userVO.user_name}"></th>
												<th scope="row"><input type="text" value="${userVO.user_email}"></th>
												<th scope="row"><input type="text" value="${userVO.user_zipcode}"></th>
												<th scope="row"><input type="text" value="${userVO.user_addr}"></th>
												<th scope="row"><input type="text" value="${userVO.user_addr_d}"></th>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				
				
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->

		<!-- Main Footer -->
			<%@include file="/WEB-INF/views/admin/include/main-footer.jsp" %>

		<!-- Control Sidebar -->
		<aside class="control-sidebar control-sidebar-dark">
			<!-- Create the tabs -->
			<ul class="nav nav-tabs nav-justified control-sidebar-tabs">
				<li class="active">
					<a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a>
				</li>
				<li>
					<a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a>
				</li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<!-- Home tab content -->
				<div class="tab-pane active" id="control-sidebar-home-tab">
					<h3 class="control-sidebar-heading">Recent Activity</h3>
					<ul class="control-sidebar-menu">
						<li>
							<a href="javascript:;"> <i class="menu-icon fa fa-birthday-cake bg-red"></i>

								<div class="menu-info">
									<h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

									<p>Will be 23 on April 24th</p>
								</div>
							</a>
						</li>
					</ul>
					<!-- /.control-sidebar-menu -->

					<h3 class="control-sidebar-heading">Tasks Progress</h3>
					<ul class="control-sidebar-menu">
						<li>
							<a href="javascript:;">
								<h4 class="control-sidebar-subheading">
									Custom Template Design
									<span class="pull-right-container">
										<span class="label label-danger pull-right">70%</span>
									</span>
								</h4>

								<div class="progress progress-xxs">
									<div class="progress-bar progress-bar-danger" style="width: 70%"></div>
								</div>
							</a>
						</li>
					</ul>
					<!-- /.control-sidebar-menu -->

				</div>
				<!-- /.tab-pane -->
				<!-- Stats tab content -->
				<div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>
				<!-- /.tab-pane -->
				<!-- Settings tab content -->
				<div class="tab-pane" id="control-sidebar-settings-tab">
					<form method="post">
						<h3 class="control-sidebar-heading">General Settings</h3>

						<div class="form-group">
							<label class="control-sidebar-subheading">
								Report panel usage
								<input type="checkbox" class="pull-right" checked>
							</label>

							<p>Some information about this general settings option</p>
						</div>
						<!-- /.form-group -->
					</form>
				</div>
				<!-- /.tab-pane -->
			</div>
		</aside>
		<!-- /.control-sidebar -->
		<!-- Add the sidebar's background. This div must be placed
  immediately after the control sidebar -->
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->

	<!-- REQUIRED JS SCRIPTS -->
	<%@include file="/WEB-INF/views/admin/include/scripts.jsp" %>
	
	<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. -->

	<!-- jquery 파일이 여기 있기 때문에 -->

	<script>
		
	</script>

</body>
</html>