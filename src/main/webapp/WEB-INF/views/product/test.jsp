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

<style>
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
       max-width: 1200px; 
       max-height: 800px; 
       overflow: auto;       
     } 
</style>


<script>

	$(document).ready(function(){

		$("#btnReview").on("click", function(){
			$("#modalLabel").html("Product Review Modal-Register");
			$("#rv_content").val();

			$("#reviewModal").modal("show");
		});

		// 별점 색상변경
		$("#star_grade a").click(function(e){
			e.preventDefault();
			$(this).parent().children("a").removeClass("on");
			$(this).addClass("on").prevAll("a").addClass("on");
		});

	});

</script>

</head>

<body>
    
<!-- nav.jsp -->
<%@include file="/WEB-INF/views/common/nav.jsp"%>

<button type="button" id="btnReview" class="btn btn-link">상품후기</button>

<!-- 상품후기 모달대화상자 : 후기쓰기, 후기수정, 후기삭제 -->
<div class="modal fade" id="reviewModal" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modalLabel">Product Review Modal-Register</h5>
		
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
			<input type="hidden" class="form-control" name="pdt_num" id="pdt_num" value="${productVO.pdt_num}">
        	<textarea class="form-control" name="rv_content" id="rv_content"></textarea>
        </div>
        <!-- 
        <div class="form-group">
        	<label>Replyer</label>
        	<input class="form-control" name="mem_id" id="mem_id" value="" readonly>
        </div>
         -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" id="btnReviewAdd" class="btn btn-primary btnModal">후기 작성</button>
		<button type="button" id="btnReviewEdit" class="btn btn-warning btnModal">후기 수정</button>
		<button type="button" id="btnReviewDel" class="btn btn-danger btnModal">후기 삭제</button>
      </div>
    </div>
  </div>
</div>



</body>
</html>
