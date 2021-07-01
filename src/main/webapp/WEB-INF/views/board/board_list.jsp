<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

<style>
	.dy_board_detail th{
	background-color: #ffde4d;
	}
	
	.dy_board_detail td{
	background-color: bisque;
	}
	
	.board_detail {
	color: black;
	}
</style>
    
<title>DAN_MALL</title>

<!-- Custom styles for this template -->
<link href="/resources/footer.css" rel="stylesheet">

</head>
<body>
    
<!-- nav.jsp -->
<%@include file="/WEB-INF/views/common/nav.jsp"%>

<main role="main" class="container">
	<div class="wrapper">
		<%-- Main content --%>
		<%@include file="/WEB-INF/views/common/mypage_nav.jsp"%>
		
		<div class="panel-body">
			<!-- 리스트 -->
			<table class="table table-striped text-center">
				<thead>
					<tr>
					<th scope="col">문의유형</th>
					<th scope="col">문의번호</th>
					<th scope="col">상품명 / 상품번호</th>
					<th scope="col">제목</th>
					<th scope="col">작성자</th>
					<th scope="col">작성일</th>
					<th scope="col">수정및삭제</th>
					</tr>
				</thead>
				<tbody>
					<%-- 데이터가 존재하지 않는 경우 --%>
					<c:if test="${empty boardList }">
					<tr>
						<td colspan="6">
							<p>상품문의가 없습니다.</p>
						</td>
					</tr>
					</c:if>
					<c:forEach items="${boardList }" var="board">
						<tr>
							<td><c:out value="${board.brd_cg }"></c:out></td>
							<td><c:out value="${board.brd_num }" /></td>
							<td>
								<c:out value="${board.pdt_name }" /><br>
								<c:out value="${board.pdt_num }" />
							</td>
							<td>
								<a class="board_detail" data-brd_num="${board.brd_num}" href="/board/board_detail_list?brd_num=<c:out value='${board.brd_num }' />">
									<b><c:out value="${board.brd_title }" /></b>
								</a>
							</td>
							<td><c:out value="${board.mem_id }" /></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.brd_date_reg }"/></td>
							<td>
								<button type="button" name="btnEdit" data-brd_num="${board.brd_num}" class="btn btn-warning btn-sm" style="margin-bottom:10px;">후기수정</button><br>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
				
		<!-- 페이징 표시 -->	
		<div class="row">
			<div class="col-lg-12">
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
					
					<hr>
					${pageMaker }
				</div>
			</div>
		</div>
		
		<form id="actionForm" action="/board/board_list" method="get">
			<input type="hidden" name="pageNum" value='<c:out value="${pageMaker.cri.pageNum }" />'>
			<input type="hidden" name="amount" value='<c:out value="${pageMaker.cri.amount }" />'>
		</form>
		<!-- /.content -->
	</div>
	<!-- /.content-wrapper -->

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
		<label><input type="radio" name="brd_cg" value="상품" style="margin-left: 10px;">상품</label>
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
        	<input type="hidden" class="form-control" name="brd_num" id="brd_num">
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		<button type="button" id="btnBoardEdit" class="btn btn-warning btnModal">후기 수정</button>
		<button type="button" id="btnBoardDel" class="btn btn-danger btnModal">후기 삭제</button>
      </div>
    </div>
  </div>
</div>

<%@include file="/WEB-INF/views/common/footer.jsp" %>

<script>
	var boardDetailView = function(boardDetailData, boardDetailTarget, boardDetailTemplate){
		var detailTemplate = Handlebars.compile(boardDetailTemplate.html());
		var details = detailTemplate(boardDetailData);
		
		$(".dy_board_detail").remove(); // 상세출력된 태그 제거
		boardDetailTarget.after(details);
	}
</script>

<script id="boardDetailTemplate" type="text/x-handlebars-template">
	<tr class="dy_board_detail">
		<th colspan="7" style="text-align: center;">상품문의 상세내역</th>
	</tr>
	<tr class="dy_board_detail">
		<th>문의유형</th>
		<th colspan="4">상세내용</th>
		<th>작성자</th>
		<th>작성일</th>
	</tr>
	{{#each .}}
	<tr class="dy_board_detail">
		<td>{{brd_cg}}</td>
		<td colspan="4">{{brd_content}}</td>
		<td>{{mem_id}}</td>
		<td>{{displayTime brd_date_reg}}</td>
	</tr>
	{{/each}}
</script>

<script type="text/javascript" src="/js/board/board_list.js"></script>




<script>

	$(document).ready(function(){

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
	});
</script>

</body>
</html>
