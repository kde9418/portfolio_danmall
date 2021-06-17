<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

<style>
<%--상품후기 별점 스타일--%>
    #star_grade a{
     	font-size:22px;
        text-decoration: none;
        color: lightgray;
    }
    #star_grade a.on{
        color: black;
    }
    
    #star_grade_modal a{
     	font-size:22px;
        text-decoration: none;
        color: lightgray;
    }
    #star_grade_modal a.on{
        color: black;
    }
    
    .popup {position: absolute;}
    .back { background-color: gray; opacity:0.5; width: 100%; height: 300%; overflow:hidden;  z-index:1101;}
    .front { 
       z-index:1110; opacity:1; boarder:1px; margin: auto; 
      }
     .show{
       position:auto;
       max-width: auto; 
       max-height: auto; 
       overflow: auto;       
     } 
</style>

<!-- config.jsp -->
<%@include file="/WEB-INF/views/common/config.jsp" %>
<!-- Custom styles for this template -->
<link href="/resources/footer.css" rel="stylesheet">

</head>

<body>
    
<!-- nav.jsp -->
<%@include file="/WEB-INF/views/common/nav.jsp"%>

<main role="main" class=".container-fluid">

	<div class="row">
		<!-- 카테고리 목록 -->
		<div class="col-2">
			<%@include file="/WEB-INF/views/common/category_list.jsp"%>
		</div>
		
		<!-- 컨텐츠 -->
		<div class="col-10">
		<h3>주문 내역</h3>
			<div class="panel panel-default">
				<div class="panel-body text-center">
					<!-- 리스트 -->
					<table class="table table-striped">
						<thead>
							<tr>
							<th scope="col">주문번호</th>
							<th scope="col">상품이미지</th>
							<th scope="col">상품정보</th>
							<th scope="col">주문금액/수량</th>
							<th scope="col">결제금액</th>
							<th scope="col">배송상태</th>
							<th scope="col">후기</th>
							<th scope="col">주문일자</th>
							</tr>
						</thead>
						<tbody>
							<%-- 데이터가 존재하지 않는 경우 --%>
							<c:if test="${empty orderList }">
							<tr>
								<td colspan="8">
									<p style="color:red;">주문 내역이 없습니다.</p>
								</td>
							</tr>
							</c:if>
							
							<%-- 변수 선언 --%>
							<c:set var="i" value="1"></c:set>
							<c:set var="price" value="0"></c:set>
							
							<%-- 데이터가 있는 경우 : forEach 작동 --%>
							<c:forEach items="${orderList }" var="orderDTO">
							<c:set var="price" value="${orderDTO.odr_price * orderDTO.odr_amount }"></c:set>
								<tr>
									<td data-odr_code="${orderDTO.odr_code }">
										<c:out value="${orderDTO.odr_code }"></c:out><br>
										<small>(<c:out value="${orderDTO.pdt_num }" />)</small>
									</td>
									<td data-pdt_num="${orderDTO.pdt_num }">
										<a href="/product/product_read?pdt_num=${orderDTO.pdt_num }">
										<img alt="" src="/order/displayFile?fileName=${orderDTO.pdt_img }" /></a>
									</td>
									<td><c:out value="${orderDTO.pdt_name }"></c:out></td>
									<td><fmt:formatNumber type="currency" value="${orderDTO.odr_price }" /><br>
										<c:out value="${orderDTO.odr_amount }"></c:out></td>
									<td><fmt:formatNumber type="currency" value="${price }" /></td>
									<td>배송완료</td>
									<td>
										<button type="button" name="btnReview" data-pdt_num="${orderDTO.pdt_num }" class="btn btn-primary btn-sm" style="margin-bottom:10px;">
											후기작성</button><br>
										<button type="button" name="btnReviewEditView" data-pdt_num="${orderDTO.pdt_num }" class="btn btn-warning btn-sm">후기수정</button>
									</td>
									<td>
	      								<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${orderDTO.odr_date}"/>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				
				
			</div>
			
			<div class="row">
				<div class="col-lg-12">
					<!-- 페이징 표시 -->	
					<div class="panel-footer">
						<ul class="pagination">
							<c:if test="${pageMaker.prev }">
								<li class="page-item">
								<a href="${pageMaker.startPage - 1 }" class="page-link" href="#" tabindex="-1">Prev</a>
								</li>
							</c:if>
							<c:forEach begin="${pageMaker.startPage }" end="${ pageMaker.endPage}" var="num">
								<li class="page-item ${pageMaker.cri.pageNum == num ? 'active' : ''}">
								<a href="${num }" class="page-link" href="#">${num }</a>
								</li>
							</c:forEach>
							<c:if test="${pageMaker.next }">
								<li class="page-item">
								<a href="${pageMaker.endPage + 1 }" class="page-link" href="#">Next</a>
								</li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
			
			
			<!-- 페이지번호 클릭시, 수정클릭시, 상품코드정보 추가, 삭제클릭시 상품코드정보 추가 -->
			<form id="actionForm" action="/order/order_list" method="get">
				<input type="hidden" name="pageNum" value='<c:out value="${pageMaker.cri.pageNum }" />'>
				<input type="hidden" name="amount" value='<c:out value="${pageMaker.cri.amount }" />'>
			</form>
		</div>
	</div>

</main><!-- /.container -->

<!-- 상품후기 모달대화상자 : 후기쓰기, 후기수정, 후기삭제 -->
<div class="modal fade" id="reviewModal" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modalLabel">상품후기</h5>
		
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <label for="review">상품평점</label><br>
		<div class="rating">
			<p id="star_grade">
		        <a href="#">★</a>
		        <a href="#">★</a>
		        <a href="#">★</a>
		        <a href="#">★</a>
		        <a href="#">★</a>
			</p>
		</div>
      </div>
      <div class="modal-body">
        <div class="form-group">
        	<label>상품후기</label>
			<input type="hidden" class="form-control" name="rv_pdt_num" id="rv_pdt_num">
			<input type="hidden" class="form-control" name="rv_num" id="rv_num">
			<input type="hidden" class="form-control" name="odr_code" id="odr_code">
        	<textarea class="form-control" name="rv_content" id="rv_content"></textarea>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" id="btnReviewAdd" class="btn btn-primary btnModal">후기 작성</button>
		<button type="button" id="btnReviewEdit" class="btn btn-warning btnModal">후기 수정</button>
		<button type="button" id="btnReviewDel" class="btn btn-danger btnModal">후기 삭제</button>
      </div>
      <form id="order_direct_form" method="get" action="/order/order">
				<input type="hidden" name="type" value="1">
		</form>
    </div>
  </div>
</div>

<%@include file="/WEB-INF/views/common/footer.jsp" %>

<script type="text/javascript" src="/js/order/order_list.js"></script>


</body>
</html>
