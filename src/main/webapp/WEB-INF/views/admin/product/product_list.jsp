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
					?????? ?????? <small>Product List</small>
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
				<div class="row" style="margin-bottom: 5px;">
					<div class="col-lg-12">
						<form id="searchForm" action="/admin/product/pro_list" method="get" style="display: inline-block;">
							<select name="type" id="type">
								<option value="" <c:out value="${pageMaker.cri.type == null ? 'selected':'' }" />>---</option>
								<option value="N" <c:out value="${pageMaker.cri.type == 'N' ? 'selected':'' }" />>?????????</option>
								<option value="D" <c:out value="${pageMaker.cri.type == 'D' ? 'selected':'' }" />>????????????</option>
								<option value="C" <c:out value="${pageMaker.cri.type == 'C' ? 'selected':'' }" />>????????????</option>
								<option value="ND" <c:out value="${pageMaker.cri.type == 'ND' ? 'selected':'' }" />>????????? or ????????????</option>
								<option value="NC" <c:out value="${pageMaker.cri.type == 'NC' ? 'selected':'' }" />>????????? or ????????????</option>
								<option value="NDC" <c:out value="${pageMaker.cri.type == 'NDC' ? 'selected':'' }" />>????????? or ???????????? or ????????????</option>
							</select>
							<input type="text" name="keyword" value="${pageMaker.cri.keyword }">
							<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
							<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
							<button id="btnSearch" type="button" class="btn btn-primary">??????</button>
						</form>
						<button id="addPro" type="button" class="btn btn-primary pull-right">????????????</button>
					</div>
				</div>

				<!-- ?????? ????????? -->
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default">
							
							<div class="panel-body">
								<!-- ????????? -->
								<table class="table table-striped text-center">
									<thead>
										<tr>
										<th scope="col">??????</th>
										<th scope="col">?????????</th>
										<th scope="col">??????</th>
										<th scope="col">?????????</th>
										<th scope="col">????????????</th>
										<th scope="col">??????</th>
										<th scope="col">??????</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pro_list }" var="proVO">
											<tr>
												<th scope="row"><c:out value="${proVO.pdt_num }"></c:out></th>
												<td>
													<a class="move" href="${proVO.pdt_num }"><c:out value="${proVO.pdt_name }"></c:out></a>
				      								<img src="/admin/product/displayFile?fileName=${proVO.pdt_img}">
												</td>
												<td><fmt:formatNumber type="currency" value="${proVO.pdt_price }"></fmt:formatNumber></td>
												<td><fmt:formatDate pattern="yyyy-MM-dd" value="${proVO.pdt_date_sub }"/></td>
												<td>
													<select data-pdt_num="${proVO.pdt_num }" class="btn-buy-edit" id="pdt_buy">
														<option value="Y" <c:out value="${proVO.pdt_buy == 'Y' ? 'selected' : ''}"></c:out>>Y</option>
														<option value="N" <c:out value="${proVO.pdt_buy == 'N' ? 'selected' : ''}"></c:out>>N</option>
													</select>
												</td>
												<td><button type="button" data-pdt_num="${proVO.pdt_num }" class="btn btn-warning btn-edit">??????</button></td>
												<td><button type="button" data-pdt_num="${proVO.pdt_num }" data-pdt_img="${proVO.pdt_img}" class="btn btn-dark btn-del">??????</button></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-lg-12">
						<!-- ????????? ?????? -->	
						<div class="panel-footer">
							<ul class="pagination">
								<c:if test="${pageMaker.prev }">
									<li class="page-item">
									<a href="${pageMaker.startPage - 1 }" class="page-link" tabindex="-1">Prev</a>
									</li>
								</c:if>
								<c:forEach begin="${pageMaker.startPage }" end="${ pageMaker.endPage}" var="num">
									<li class="page-item ${pageMaker.cri.pageNum == num ? 'active' : ''}">
									<a href="${num }" class="page-link">${num }</a>
									</li>
								</c:forEach>
								<c:if test="${pageMaker.next }">
									<li class="page-item">
									<a href="${pageMaker.endPage + 1 }" class="page-link">Next</a>
									</li>
								</c:if>
							</ul>
							
							<hr>
							${pageMaker }
						</div>
					</div>
				</div>
				
				<form id="actionForm" action="/admin/product/pro_list" method="get">
					<input type="hidden" name="pageNum" value='<c:out value="${pageMaker.cri.pageNum }" />'>
					<input type="hidden" name="amount" value='<c:out value="${pageMaker.cri.amount }" />'>
					<input type="hidden" name="type" value='<c:out value="${pageMaker.cri.type }" />'>
					<input type="hidden" name="keyword" value='<c:out value="${pageMaker.cri.keyword }" />'>
				</form>

			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->

		<!-- Main Footer -->
			<%@include file="/WEB-INF/views/admin/include/main-footer.jsp" %>

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

	<!-- jquery ????????? ?????? ?????? ????????? -->
	<script>
		$(document).ready(function(){

			var actionForm = $("#actionForm");

			$(".page-item a").on("click", function(e){
				e.preventDefault();

				console.log("click");

				actionForm.find("input[name='pageNum']").val($(this).attr("href"));
				actionForm.submit();

			});

			// ?????? ?????????
			$("table td .btn-edit").on("click", function(){
				console.log('??????');
	
				// ???????????? ?????? ????????? ?????? ??????
				actionForm.append("<input type='hidden' name='pdt_num' value='"+ $(this).attr("data-pdt_num") + "'>");

				actionForm.attr("action", "/admin/product/modify");
				actionForm.submit();
				
			});

			// ?????? ?????????
			$("table td .btn-del").on("click", function(){
				console.log('??????');
	
				// ???????????? ?????? ????????? ?????? ??????
				actionForm.append("<input type='hidden' name='pdt_num' value='"+ $(this).attr("data-pdt_num") + "'>");
				actionForm.append("<input type='hidden' name='pdt_img' value='"+ $(this).attr("data-pdt_img") + "'>");

				actionForm.attr("action", "/admin/product/delete");
				actionForm.submit();
				
			});

			// ???????????? ?????????
			//data-pdt_num="${proVO.pdt_num }" 
			$("table td .btn-buy-edit").change(function(){
				var pdt_num = $(this).attr("data-pdt_num");
				var pdt_buy = $("#pdt_buy option:selected").val();
				console.log(pdt_num);
				console.log(pdt_buy);

				actionForm.append("<input type='hidden' name='pdt_num' value='"+ $(this).attr("data-pdt_num") + "'>");

				actionForm.attr("action", "/admin/product/modify");
				actionForm.attr("method", "post");
				
				$.ajax({
					url : '/admin/product/buy_modify',
					type : 'post',
					data : {pdt_num : pdt_num, pdt_buy: pdt_buy},
					dataType : 'text',  // ???????????? ????????? ??????
					success : function(data){
						if(data == "SUCCESS"){
							alert("??????????????? ?????????????????????.");
						} else {
							alert("?????? ??????????????????.");
							location.href = "/admin/product/pro_list";
						}
					}
				});
			});
			
			var searchForm = $("#searchForm");
	
			$("#searchForm #btnSearch").on("click", function(e){
	
				if(!searchForm.find("option:selected").val()){
					alert("??????????????? ???????????????.");
					return false;
				}
	
				if(!searchForm.find("input[name='keyword']").val()){
					alert("???????????? ???????????????.");
					return false;
				}
	
				searchForm.find("input[name='pageNum']").val("1");
	
				searchForm.submit();
	
			});

			// ?????? ??????
			$("#addPro").on("click", function(){
				console.log('??????');
				location.href = "/admin/product/product_insert";
				
				
			});

		});
	</script>
</body>
</html>