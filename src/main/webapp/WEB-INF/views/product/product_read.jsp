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

</style>


<!-- config.jsp -->
<%@include file="/WEB-INF/views/common/config.jsp" %>
<!-- Custom styles for this template -->
<link href="/resources/footer.css" rel="stylesheet">

<!-- 1)handlebars.js 참조 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

<!-- 2)UI Template(상품후기 목록 템플릿) -->
<script id="reviewTemplate" type="text/x-handlebars-template">
<table class="table table-striped">
	<thead>
		<tr>
		<th scope="col">번호</th>
		<th scope="col">별점</th>
		<th scope="col">내용</th>
		<th scope="col">작성자</th>
		<th scope="col">작성일</th>
		</tr>
	</thead>
	<tbody>
	{{#each .}}
		<tr>
			<td data-rv_num="{{rv_num}}">{{rv_num}}</td>
			<td data-rv_score="{{rv_score}}"><strong>{{checkRating rv_score}}</strong></td>
			<td data-rv_content="{{rv_content}}">{{rv_content}}</td>
			<td data-mem_id="{{mem_id}}">{{mem_id}}</td>
			<td data-rv_date_reg="{{displayTime rv_date_reg}}">{{displayTime rv_date_reg}}</td>
		</tr>
	{{/each}}
	</tbody>
</table>
</script>

<!-- 2)UI Template(상품문의 목록 템플릿) -->
<script id="boardTemplate" type="text/x-handlebars-template">
<table class="table table-striped">
	<thead>
		<tr>
		<th scope="col">번호</th>
		<th scope="col">문의유형</th>
		<th scope="col">제목</th>
		<th scope="col">작성자</th>
		<th scope="col">작성일</th>
		</tr>
	</thead>
	<tbody>
	{{#each .}}
		<tr>
			<td data-brd_num="{{brd_num}}">{{brd_num}}</td>
			<td data-brd_cg="{{brd_cg}}">{{brd_cg}}</td>
			<td data-brd_title="{{brd_title}}">{{brd_title}}</td>
			<td data-mem_id="{{mem_id}}">{{mem_id}}</td>
			<td data-brd_date_reg="{{displayTime brd_date_reg}}">{{displayTime brd_date_reg}}</td>
		</tr>
	{{/each}}
	</tbody>
</table>
</script>

<script>
	// 3) 상품후기 목록데이터 출력작업
	var printReviewData = function(reviewData, reviewTarget, reviewTemplate){
			var uiTemplate = Handlebars.compile(reviewTemplate.html());

			var reviewDataResult = uiTemplate(reviewData);
			
			reviewTarget.html(reviewDataResult);
		}
	
	// 상품문의 목록데이터 출력작업
	var printBoardData = function(boardData, boardTarget, boardTemplate){
			var uiTemplate = Handlebars.compile(boardTemplate.html());

			var boardDataResult = uiTemplate(boardData);
			
			boardTarget.html(boardDataResult);
		}
	

	// 상품후기 목록 페이징 구현작업
	//var pageNum = curPage;
	var replyPageDisplay = ""; // [이전] 1 2 3 4 5 6 7 8 9 10 [다음]

	// 댓글 페이징번호를 출력하는 기능.
	var printReviewPaging = function(replyCnt, pageNum){
		
		var displayPageCount = 5;
		
		// 페이징 알고리즘
		var endNum = Math.ceil(pageNum / 10.0) * 10; // 10의 의미는 출력될 페이지 수(pageSize)
		var startNum = endNum - 9;

		var prev = startNum != 1;
		var next = false;

		// 마지막페이지 번호 * 10개 => 총 데이터 개수(실제)
		if(endNum * displayPageCount >= replyCnt){
			endNum = Math.ceil(replyCnt/parseFloat(displayPageCount)); // 실제 데이터를 이용한 전체 페이지 수
		}

		// 실제 데이터가 마지막 페이지 번호*10보다 크면, 다음 데이터를 표시하기 위하여 next = true로 해줘야 한다.
		if(endNum * displayPageCount < replyCnt){
			next = true;
		}

		var str = '<ul class="pagination">';

		// 이전표시여부
		if(prev){
			str += '<li class="page-item"><a class="page-link" href="' + (startNum - 1) + '">Previous</a></li>';
		}
		// 페이지번호 출력
		for(var i=startNum; i<= endNum; i++){
			var active = pageNum == i ? "active":""; // 현재페이지 상태를 나타내는 스타일시트 적용

			str += '<li class="page-item ' + active + ' "><a class="page-link" href="' + i + '">' + i + '</a></li>';
		}
		// 다음표시여부
		if(next){
			str += '<li class="page-item"><a class="page-link" href="' + (endNum + 1) + '">Next</a></li>';
		}

		str += '</ul>';

		console.log(str);

		
		$("#reviewPaging").html(str);

	}
	
	var printBoardPaging = function(replyCnt, pageNum){
		
		var displayPageCount = 5;
		
		// 페이징 알고리즘
		var endNum = Math.ceil(pageNum / 10.0) * 10; // 10의 의미는 출력될 페이지 수(pageSize)
		var startNum = endNum - 9;

		var prev = startNum != 1;
		var next = false;

		// 마지막페이지 번호 * 10개 => 총 데이터 개수(실제)
		if(endNum * displayPageCount >= replyCnt){
			endNum = Math.ceil(replyCnt/parseFloat(displayPageCount)); // 실제 데이터를 이용한 전체 페이지 수
		}

		// 실제 데이터가 마지막 페이지 번호*10보다 크면, 다음 데이터를 표시하기 위하여 next = true로 해줘야 한다.
		if(endNum * displayPageCount < replyCnt){
			next = true;
		}

		var str = '<ul class="pagination">';

		// 이전표시여부
		if(prev){
			str += '<li class="page-item"><a class="page-link" href="' + (startNum - 1) + '">Previous</a></li>';
		}
		// 페이지번호 출력
		for(var i=startNum; i<= endNum; i++){
			var active = pageNum == i ? "active":""; // 현재페이지 상태를 나타내는 스타일시트 적용

			str += '<li class="page-item ' + active + ' "><a class="page-link" href="' + i + '">' + i + '</a></li>';
		}
		// 다음표시여부
		if(next){
			str += '<li class="page-item"><a class="page-link" href="' + (endNum + 1) + '">Next</a></li>';
		}

		str += '</ul>';

		console.log(str);

		
		$("#boardPaging").html(str);

		//페이징정보 표시
	}
</script>

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
		
		<div class="col-10">
			<div class="row no-gutters border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
				<div class="col-auto d-none d-lg-block">
					<img alt="${productDTO.pdt_name }" src="/product/displayFile?fileName=${productDTO.pdt_img }" width="500" height="650">          
				</div>
				<div class="col p-4 d-flex flex-column position-static">
					<table class="table" style="width: 95%; margin-top: 10px;">
						<thead>
							<tr>
								<th scope="col" colspan="2" style="font-size: 25px">${productDTO.pdt_name }</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th scope="row" style="width: 170px">판매가</th>
								<td><fmt:formatNumber type="currency" value="${productDTO.pdt_price }" /></td>
							</tr>
							<tr>
								<th scope="row">할인율</th>
								<c:choose>
									<c:when test="${productDTO.pdt_discount eq '0' }"> 
										<td><b>-</b></td>
									</c:when>
									<c:when test="${productDTO.pdt_discount ne '0' }"> 
										<td style="color:#ff8e77;"><b>${productDTO.pdt_discount }%</b></td>
									</c:when>
								</c:choose>
							</tr>
							<tr>
								<th scope="row">할인판매가</th>
								<td><fmt:formatNumber type="currency" value="${productDTO.pdt_consumer }" /></td>
							</tr>
							<tr>
								<th scope="row">제조사</th>
								<td>${productDTO.pdt_company }</td>
							</tr>
							<tr>
								<th scope="row">재고수량</th>
								<td>${productDTO.pdt_amount }</td>
							</tr>
							<tr>
								<th scope="row">구매수량</th>
								<td>
									<input type="number" id="odr_amount" value="1"><br>
								</td>
							</tr>
						</tbody>
					</table>
					
					<div class="form-group text-center">
						<button type="button" id="btnDirectBuy" class="btn btn-warning" style="width: 40%">구매하기</button>
						<button type="button" id="btnCart" class="btn btn-primary" style="width: 40%">장바구니</button>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col text-center">
					<p class="card-text mb-auto">${productDTO.pdt_detail }</p>
				</div>
			</div>
			
			<hr style="margin: 60px 0px">

			<!-- 상품후기 목록 -->
			<div class="row">
				<div class="col">
		    		<div class="panel panel-default">
		    			<div class="panel-heading text-center">
		    			<h3 style="margin-bottom: 20px;">상품후기</h3>
		    			</div>
		    			<div class="container">
						<!-- 상품후기 목록위치 -->
		    			<div class="panel-body text-center" id="reviewListView">
							<div id="reviewNone">
								<h5>상품후기가 없습니다.</h5>
							</div>
						</div>
						<!-- 페이징 위치 -->
						<div class="panel-footer" id="reviewPaging"></div>
						</div>
					</div>
				</div>
			</div>
			
			<hr style="margin: 60px 0px">

			<!-- 상품문의 목록 -->
			<div class="row">
				<div class="col">
		    		<div class="panel panel-default">
		    			<div class="panel-heading text-center">
		    			<h3 style="margin-bottom: 20px;">상품문의</h3>
		    			</div>
		    			<div class="container">
							<div class="panel-body">
								<button type="button" id="btnBoard" class="btn btn-primary">상품문의 작성</button>
							</div>
							<!-- 상품문의 목록위치 -->
							<div class="panel-body text-center" id="boardListView">
								<div id="boardNone">
									<h5>상품문의가 없습니다.</h5>
								</div>
							</div>
							<!-- 상품문의 위치 -->
							<div class="panel-footer" id="boardPaging"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</main><!-- /.container -->

<!-- 상품후기 모달대화상자 : 후기쓰기, 후기수정, 후기삭제 -->
<div class="modal fade" id="boardModal" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
	  <div class="modal-content">
		<div class="modal-header">
		  <h5 class="modal-title" id="modalLabel">상품문의</h5>
		  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		  </button>
		</div>
		<div class="modal-body">
		  <label for="brd_cg">문의유형</label><br>
		  <label><input type="radio" name="brd_cg" value="상품" style="margin-left: 10px;" checked="checked">상품</label>
		  <label><input type="radio" name="brd_cg" value="배송" style="margin-left: 10px;">배송</label>
		  <label><input type="radio" name="brd_cg" value="교환" style="margin-left: 10px;">교환</label>
		  <label><input type="radio" name="brd_cg" value="반품" style="margin-left: 10px;">반품</label>
		  <label><input type="radio" name="brd_cg" value="기타" style="margin-left: 10px;">기타</label>
		</div>
		<div class="modal-body">
		  <div class="form-group">
			  <label>상품문의 제목</label>
			  <textarea class="form-control" name="brd_title" id="brd_title"></textarea>
		  </div>
		  <div class="form-group">
			  <label>상품문의 내용</label>
			  <textarea class="form-control" name="brd_content" id="brd_content"></textarea>
		  </div>
		</div>
		<div class="modal-footer">
		  <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		  <button type="button" id="btnBoardAdd" class="btn btn-primary btnModal">상품문의 등록</button>
		</div>
	  </div>
	</div>
  </div>

<%@include file="/WEB-INF/views/common/footer.jsp" %>

<script>
	$(document).ready(function(){

		// 장바구니 담기
		$("#btnCart").on("click", function(){
			//console.log("장바구니 test");

		/*
			장바구니코드 : 시퀀스, 로그인ID(세션)
			상품코드, 수량
		*/
			var pdt_num = ${productDTO.pdt_num };
			var odr_amount = $("#odr_amount").val();

			$.ajax({
				url : '/cart/add',
				type : 'post',
				data : {pdt_num : pdt_num, odr_amount : odr_amount},
				dataType : 'text',  // 리턴되는 데이터 타입
				success : function(data){
					if(data == "SUCCESS"){
						if(confirm("장바구니가 추가 되었습니다. \n 확인 하시겠습니가?")){
							location.href = "/cart/cart_list";
						} 
					} else if(data == "LoginRequired"){
						alert("로그인이 안 되어있습니다. \n로그인 후 이용해주세요.");
						location.href = "/member/login";
					}
				}
			});

		});

		// 즉시구매
		$("#btnDirectBuy").on("click", function(){
			console.log("즉시구매 test");

			var pdt_num = ${productDTO.pdt_num };
			var odr_amount = $("#odr_amount").val();

			var order_direct_form = $("#order_direct_form");
			order_direct_form.append("<input type='hidden' name='pdt_num' value='"+ pdt_num + "'>");
			order_direct_form.append("<input type='hidden' name='odr_amount' value='"+ odr_amount + "'>");

			order_direct_form.submit();
		});

		// 상품문의 Modal
		$("#btnBoard").on("click", function(){

			$("#boardModal").modal("show");
			
		});

		// 상품문의 등록 btnBoardAdd
		$("#btnBoardAdd").on("click", function(){
			var brd_cg = $('input[name="brd_cg"]:checked').val();
			var pdt_num = ${productDTO.pdt_num };
			var brd_title = $("#brd_title").val();
			var brd_content = $("#brd_content").val();

			console.log(brd_cg);
			console.log(pdt_num);
			console.log(brd_title);
			console.log(brd_content);

			$.ajax({
			url: "/board/board_register",
			type: "post",
			data: {brd_cg : brd_cg, pdt_num : pdt_num, brd_title : brd_title, brd_content : brd_content},
			dataType: "text",
			success : function(data){
				if(data == "SUCCESS"){
					alert("상품문의가 등록되었습니다.");
					$("#brd_title").val("");
					$('input[name="brd_cg"]').val("상품");
					$("#brd_content").val("");
					$("#boardModal").modal("hide");

					location.href = "/product/product_read?pdt_num=" + pdt_num;
				}				
			}
			});

		});
	});
	
</script>

<script>
	// 상품후기 목록/페이징 기능
	var showReviewList = function(curPage){
		
		//상품코더
		let pdt_num = ${productDTO.pdt_num};
		let page = curPage;
		
		console.log(pdt_num);
		
		let url = "/review/pages/" + pdt_num + "/" + page;
		console.log(url);
	
		
		$.getJSON(url, function(data){
			
			if(data.list.length == 0){
				$("#reviewNone").show();
			} else{
				$("#reviewNone").hide();
				// 1) 상품후기 목록 출력
				printReviewData(data.list, $("#reviewListView"), $("#reviewTemplate"));
		
				// 2) 페이징 출력
				printReviewPaging(data.reviewCnt, page);
			}
	
		});
	}
	
	var curPage = 1;
	
	showReviewList(curPage);
	
	// 상품후기 목록/페이징 기능
	var showBoardList = function(curPage){
		
		//상품코더
		let pdt_num = ${productDTO.pdt_num};
		let page = curPage;
		
		console.log(pdt_num);
		
		let url = "/board/pages/" + pdt_num + "/" + page;
		console.log(url);
	
		
		$.getJSON(url, function(data){
			
			if(data.list.length == 0){
				$("#boardNone").show();
			} else{
				$("#boardNone").hide();
				// 1) 상품후기 목록 출력
				printBoardData(data.list, $("#boardListView"), $("#boardTemplate"));
		
				// 2) 페이징 출력
				printBoardPaging(data.boardCnt, page);
			}
	
		});
	}
	
	showBoardList(curPage);	

</script>

<script>

	$(document).ready(function(){

		// 4)사용자정의 헬퍼(Handlebars 버전의 함수)
		// 댓글 날짜를 하루기준으로 표현을 1)24시간 이전 시:분:초 2)24시간 이후 년/월/일
		Handlebars.registerHelper("displayTime", function(timeValue){
			var today = new Date(); // 1970년1월1일 0시0분0초 0 밀리세컨드
			var gap = today.getTime() - timeValue;

			var dateObj = new Date(timeValue);
			var str = "";

			if (gap < (1000 * 60 * 60 * 24)){
				var hh = dateObj.getHours();
				var mi = dateObj.getMinutes();
				var ss = dateObj.getSeconds();

				return [ (hh > 9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi,
						':', (ss > 9 ? '' : '0') + ss ].join('');
			}else {
				var yy = dateObj.getFullYear();
				var mm = dateObj.getMonth();
				var dd = dateObj.getDate();

				return [ yy, '/', (mm > 9 ? '' : '0') + mm, '/',
						(dd > 9 ? '' : '0') + dd ].join('');
			}
		});

		Handlebars.registerHelper("checkRating", function(rating){
			var stars = "";

			switch(rating){
				case 1:
					stars = "★☆☆☆☆";
					break;
				case 2:
					stars = "★★☆☆☆";
					break;
				case 3:
					stars = "★★★☆☆";
					break;
				case 4:
					stars = "★★★★☆";
					break;
				case 5:
					stars = "★★★★★";
					break;
				default:
					stars = "☆☆☆☆☆";
					break;
			}
			return stars;
		});

		$("#reviewPaging").on("click", "li.page-item a", function(e){
			e.preventDefault();

			console.log("페이지번호클릭");

			curPage = $(this).attr("href");
			showReviewList(curPage);
		});
		
		$("#boardPaging").on("click", "li.page-item a", function(e){
			e.preventDefault();

			console.log("페이지번호클릭");

			curPage = $(this).attr("href");
			showBoardList(curPage);
		});
		
	});

</script>


</body>
</html>
