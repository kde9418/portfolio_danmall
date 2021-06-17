<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	
	.percent {
		color:#ff8e77;
		font-size:20px;
	}
	
	.sell {
		margin-right:5px;
		color:#636363;
		font-size:16px;
		text-decoration:line-through	
	}
	
	.consumer {
		color:#4b4b4b;
		font-size:20px;
		font-weight:500;
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
		<div class="container">
			
			<div class="text-center">
			<%-- 데이터가 존재하지 않는 경우 --%>
			<c:if test="${empty productDTOList }">
				<h3 style="padding-top: 50px;">상품 준비중입니다.</h3>
			</c:if>
			</div>
		
			<div class="card-deck mb-3 text-center">
				<c:forEach items="${productDTOList}" var="productDTO">
					<div class="card mb-4 shadow-sm" style="max-width: 255px; min-width: 254px">
						<div class="card-body">
							<a href="/product/product_read?pdt_num=${productDTO.pdt_num }">
							<img alt="" src="/product/displayFile?fileName=${productDTO.pdt_img }" />
							</a>
							<br>
							<input type="hidden" name="pdt_num" value="${productDTO.pdt_num }" />
							<span><c:out value="${productDTO.pdt_name }" /></span><br>
							
							<c:choose>
							<c:when test="${productDTO.pdt_discount eq '0' }">
								<span class="percent" style="color: white"><c:out value="${productDTO.pdt_discount }" />%</span><br>
								<span class="consumer"><fmt:formatNumber type="currency" value="${productDTO.pdt_price }"></fmt:formatNumber></span><br>
							</c:when>
							<c:when test="${productDTO.pdt_discount ne '0' }">
								<span class="sell"><fmt:formatNumber type="currency" value="${productDTO.pdt_price }"></fmt:formatNumber></span>
								<span class="percent"><c:out value="${productDTO.pdt_discount }" />%</span><br>
								<span class="consumer"><fmt:formatNumber type="currency" value="${productDTO.pdt_consumer }"></fmt:formatNumber></span><br>
							</c:when>
							</c:choose>
							
							<c:choose>
							<c:when test="${productDTO.pdt_buy eq 'Y'}">
								<input type="number" name="odr_amount" value="1" style="margin: 10px 0px"><br>
								<button type="button" name="btn_direct_buy" class="btn btn-warning" style="margin-right: 7px">구매하기</button>
								<button type="button" name="btn_cart_add" class="btn btn-primary">장바구니</button>
							</c:when>
							<c:when test="${productDTO.pdt_buy eq 'N'}">
								<input type="number" name="odr_amount" value="1" style="margin: 10px 0px" disabled><br>
								<button type="button" class="btn btn-secondary">SOLD OUT</button>
							</c:when>
							</c:choose>
						</div>
					</div>
				</c:forEach>
				<!-- 즉시구매 버튼을 클릭하면, 필요한 정보를 보내기 위한 작업 -->
				<form id="order_direct_form" method="get" action="/order/order">
					<input type="hidden" name="type" value="1">
				</form>
			</div>
		
		</div>
		</div>
	</div>

</main><!-- /.container -->

<%@include file="/WEB-INF/views/common/footer.jsp" %>

<script>

	// 즉시구매 클릭 <button type="button" name="btn_direct_buy" class="btn btn-link">즉시구매</button>
	$("button[name='btn_direct_buy']").on("click", function(){

		// 상품코드, 구매수량
		var pdt_num = $(this).parent().find("input[name='pdt_num']").val(); //<input type="hidden" name="pdt_num" value="${productDTO.pdt_num }" />
		var odr_amount = $(this).parent().find("input[name='odr_amount']").val();

		var order_direct_form = $("#order_direct_form");
		order_direct_form.append("<input type='hidden' name='pdt_num' value='"+ pdt_num + "'>");
		order_direct_form.append("<input type='hidden' name='odr_amount' value='"+ odr_amount + "'>");

		order_direct_form.submit();
	});

	$("button[name='btn_cart_add']").on("click", function(){
		
		var pdt_num = $(this).parent().find("input[name='pdt_num']").val(); //<input type="hidden" name="pdt_num" value="${productDTO.pdt_num }" />
		var odr_amount = $(this).parent().find("input[name='odr_amount']").val();
		
		console.log("상품코드: " + pdt_num);
		console.log("구매수량: " + odr_amount);
			
		$.ajax({
			url : '/cart/add',
			type : 'post',
			data : {pdt_num : pdt_num, odr_amount : odr_amount},
			dataType : 'text',  // 리턴되는 데이터 타입
			success : function(data){
				if(data == "SUCCESS"){
					if(confirm("장바구니가 추가 되었습니다. \n 확인 하시겠습니가?")){
						location.href = "/cart/cart_list";
						// odr_amount.html("1");
					} 
				} else if(data == "LoginRequired"){
					alert("로그인이 안 되어있습니다. \n로그인 후 이용해주세요.");
					location.href = "/member/login";
				}
			}
		});
	});

</script>

</body>
</html>
