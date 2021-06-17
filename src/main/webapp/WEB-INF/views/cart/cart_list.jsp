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

<!-- config.jsp -->
<%@include file="/WEB-INF/views/common/config.jsp" %>
<!-- Custom styles for this template -->
<link href="/resources/footer.css" rel="stylesheet">

<style>
	.bd-placeholder-img {
		font-size: 1.125rem;
		text-anchor: middle;
		-webkit-user-select: none;
		-moz-user-select: none;
		-ms-user-select: none;
		user-select: none;
	}

	@media (min-width: 768px) {
		.bd-placeholder-img-lg {
		font-size: 3.5rem;
		}
	}

	.sell {
		margin-right:5px;
		color:#636363;
		font-size:16px;
		text-decoration:line-through	
	}
</style>

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
		<h3>장바구니 목록</h3>
			<div class="panel panel-default">
				<div class="panel-heading">
					<button id="btn_cart_check_del" type="button" class="btn btn-primary btn-sm" style="margin-bottom: 5px;">선택삭제</button>
				</div>
				  	
				<div class="panel-body text-center">
					<!-- 리스트 -->
					<table class="table table-striped">
						<thead>
							<tr>
							<th scope="col"><input type="checkbox" id="check_all"></th>
							<th scope="col">번호</th>
							<th scope="col">이미지</th>
							<th scope="col">상품명</th>
							<th scope="col">판매가</th>
							<th scope="col">수량</th>
							<th scope="col">배송비</th>
							<th scope="col">합계</th>
							<th scope="col">취소</th>
							</tr>
						</thead>
						<tbody>
							<%-- 데이터가 존재하지 않는 경우 --%>
							<c:if test="${empty cartVOList }">
							<tr>
								<td colspan="9">
									<p style="color:red;">장바구니가 비었습니다.</p>
								</td>
							</tr>
							</c:if>
							
							<%-- 변수 선언 --%>
							<c:set var="i" value="1"></c:set>
							<c:set var="price" value="0"></c:set>
							
							<%-- 데이터가 있는 경우 : forEach 작동 --%>
							<c:forEach items="${cartVOList }" var="cartList">
								<c:set var="price" value="${cartList.pdt_consumer * cartList.cart_amount }"></c:set>
								<tr>
									<td>
										<input type="checkbox" name="cart_check" value="${cartList.cart_code }">
									</td>
									<th scope="row">${i }</th>
									<td>
										<a class="move" href="/product/product_read?pdt_num=<c:out value='${cartList.pdt_num }' />">
	      								<img src="/cart/displayFile?fileName=${cartList.pdt_img}">
	      								</a>
									</td>
									<td><c:out value="${cartList.pdt_name }"></c:out></td>
									<td>
									<c:choose>
									<c:when test="${cartList.pdt_price eq cartList.pdt_consumer }">
										<fmt:formatNumber type="currency" value="${cartList.pdt_consumer }" />
									</c:when>
									<c:when test="${cartList.pdt_price ne cartList.pdt_consumer }">
										<span class="sell"><fmt:formatNumber type="currency" value="${cartList.pdt_price }" /></span><br>
										<fmt:formatNumber type="currency" value="${cartList.pdt_consumer }" />
									</c:when>
									</c:choose>
									</td>
									<td>
										<input type="number" value="<c:out value='${cartList.cart_amount }'></c:out>" name="cart_amount" style="width: 60px;" />
										<button type="button" name="btnCartEdit" data-cart_code="${cartList.cart_code }" class="btn btn-primary btn-sm">수정</button>
									</td>
									<td>[기본배송조건]</td>
									<td data-price="${price }">
										<fmt:formatNumber type="currency" value="${price }" />
									</td>
									<td>
										<button type="button" name="btnCartDel" data-cart_code="${cartList.cart_code }" class="btn btn-danger">취소</button>
									</td>
								</tr>
								<c:set var="i" value="${i+1 }"></c:set>
								<c:set var="sum" value="${sum + price }"></c:set>
							</c:forEach>
						</tbody>
					</table>
				</div>
				
				<div id="sum_price" class="panel-body">
					<table class="table table-striped">
						<tr>
							<td>총 금액</td>
							<td data-sum="${sum }"><fmt:formatNumber type="currency" value="${sum }" /></td>
						</tr>
					</table>
				</div>
				
				<div id="sum_price" class="panel-footer">
					<table class="table table-striped">
						<tr>
							<td>
								<button name="btn_cart_clear2" type="button" class="btn btn-link">장바구니 전체 비우기</button>
								<button name="btn_order" type="button" class="btn btn-link">전체상품 주문</button>
								<button name="btn_chk_order" type="button" class="btn btn-link">선택상품 주문</button>
							</td>
						</tr>
					</table>
				</div>
				
			</div>
		</div>
	</div>

</main><!-- /.container -->

<%@include file="/WEB-INF/views/common/footer.jsp" %>

<script>

	$(document).ready(function(){

		// 장바구니 개별 삭제
		$("button[name='btnCartDel']").on("click", function(){
			console.log("장바구니 삭제");

			var cart_code = $(this).attr("data-cart_code");

			var cart_tr = $(this).parents("tr");

			var cart_price = cart_tr.find("td[data-price]").attr("data-price");

			console.log(cart_price);
			
			var sum_price = $("div#sum_price td[data-sum]").attr("data-sum");

			console.log(sum_price);

			
			$.ajax({
			url: '/cart/delete',
			type: 'post',
			dataType: 'text',
			data: {cart_code : cart_code},
			success : function(data){
				if(data == 'SUCCESS'){
					alert("삭제되었습니다.");
					// location.href = "/cart/cart_list";  // DB에서 새로 불러온다.
					

					// 행을 화면에서 제거
					cart_tr.remove();

					// 합계 계산

					sum_price = parseInt(sum_price) - parseInt(cart_price);
					
					$("div#sum_price td[data-sum]").attr("data-sum", sum_price); // 삭제시 값 변경이 지속적으로 처리해야 한다.
					$("div#sum_price td[data-sum]").text(sum_price);
				}
			}
			});


		});

		// 수량텍스트박스 변경이벤트 작업
		/*
		$("input[name='cart_amount']").on("change", function(){
			console.log('수량 변경');
		});
		*/

		// 수량변경 수정버튼 클릭시
		$("button[name='btnCartEdit']").on("click", function(){
			console.log('수량 수정 클릭');

			// 주요 파라미터 : 장바구니 코드, 변경된 수량

			var cart_code = $(this).attr("data-cart_code");

			var cart_tr = $(this).parents("tr");
			var cart_amount = cart_tr.find("input[name='cart_amount']").val();
			// var cart_amount = $(this).siblings("input[name='cart_amount']").val();

			console.log(cart_code);
			console.log(cart_amount);
			
			$.ajax({
			url: '/cart/cart_amount_update',
			type: 'post',
			data: {cart_code : cart_code, cart_amount : cart_amount},
			dataType: 'text',
			success : function(data){
				if(data == 'SUCCESS'){
					alert("수량 변경 되었습니다.");
					location.href = "/cart/cart_list";
				}
			}
			});
			
		});

		// 주문하기 이동 <button name="btn_order"
		$("button[name='btn_order']").on("click", function(){
			location.href = "/order/order?type=2";
		});

		// 장바구니 전체 비우기
		$("button[name='btn_cart_clear']").on("click", function(){
			location.href = "/cart/cart_all_delete";
		});

		// 장바구니 전체 비우기(ajax)
		$("button[name='btn_cart_clear2']").on("click", function(){

			$.ajax({
				url: '/cart/cart_all_delete2',
				type: 'post',
				dataType: 'text',
				success : function(data){
					if(data == 'SUCCESS'){
						alert("장바구니가 비워졌습니다.");
						location.href = "/cart/cart_list";
						
					}
				}
			});
		});
		
		
		// 장바구니 선택구매 btn_chk_order
		$("button[name='btn_chk_order']").on("click", function(){
			
			// console.log($("input[name='cart_check']:checked").length);

			if($("input[name='cart_check']:checked").length == 0){
				alert("구매할 상품을 선택하세요.");
				return;
			}

			var result = confirm("선택한 상품을 구매하시겠습니까?");

			if(result){
				var checkArr = [];

				$("input[name='cart_check']:checked").each(function(){
					var cart_code = $(this).val();
					checkArr.push(cart_code);
				});
				console.log(checkArr);

				$.ajax({
					url: "/cart/cart_check_order",
					type: 'post',
					data: {checkArr : checkArr},
					dataType: 'text',
					success : function(data){
						if(data == 'SUCCESS'){
							//alert("성공함");
							location.href = "/order/order?type=2";
							
						}
					}
				});
			}

		});

		// 장바구니 선택삭제 <input type="checkbox" name="cart_check" value="${cartList.cart_code }">
		$("#btn_cart_check_del").on("click", function(){
			
			// console.log($("input[name='cart_check']:checked").length);

			if($("input[name='cart_check']:checked").length == 0){
				alert("삭제할 상품을 선택하세요.");
				return;
			}

			var result = confirm("선택한 상품을 삭제하시겠습니까?");

			if(result){
				var checkArr = [];

				$("input[name='cart_check']:checked").each(function(){
					var cart_code = $(this).val();
					checkArr.push(cart_code);
				});

				$.ajax({
					url: "/cart/cart_check_delete",
					type: 'post',
					data: {checkArr : checkArr},
					dataType: 'text',
					success : function(data){
						if(data == 'SUCCESS'){
							alert("선택된 상품이 삭제 되었습니다.");
							location.href = "/cart/cart_list";
							
						}
					}
				});
			}

		});

		// 제목행의 체크박스 선택시 전체 선택기능
		$("#check_all").on("click",function(){
			$("input[name='cart_check']").prop("checked", this.checked); // this.checked : #check_all의 선택상태
		});

		// 데이터 행의 체크박스 클릭
		$("input[name='cart_check']").on("click",function(){
			if($("input[name='cart_check']:checked").length ==$("input[name='cart_check']").length){
				$("#check_all").prop("checked", true);
			}else{
				$("#check_all").prop("checked", false);
			}
		});



	});

</script>



</body>
</html>
