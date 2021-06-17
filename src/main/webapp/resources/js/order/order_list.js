$(document).ready(function(){
		
	// 페이지번호 클릭
	var actionForm = $("#actionForm");
	$(".page-item a").on("click", function(e){
		e.preventDefault();

		actionForm.find("input[name='pageNum']").val($(this).attr("href"));
		actionForm.submit();

	});

	// 후기작성 클릭(모달보기)
	$("button[name='btnReview']").on("click", function(){
		
		$("#modalLabel").html("상품 후기 작성");
		$("#rv_content").val();

		$("button.btnModal").hide();
		$("#btnReviewAdd").show();
		
		var pdt_num = $(this).attr('data-pdt_num');
		$("#rv_pdt_num").val(pdt_num);
		
		var odr_code =$(this).parents("tr").find("td[data-odr_code]").attr("data-odr_code");
		$("#odr_code").val(odr_code);			
		
		$("#reviewModal").modal("show");
	});
	
	// 후기수정 클릭(모달보기)
	$("button[name='btnReviewEditView']").on("click", function(){
		var odr_code =$(this).parents("tr").find("td[data-odr_code]").attr("data-odr_code");
		$("#odr_code").val(odr_code);
		
		var pdt_num = $(this).attr('data-pdt_num');
		$("#rv_pdt_num").val(pdt_num);
		
		// ajax 호출
		// 후기입력데이터를 호출
		$.ajax({
			url: "/review/review_cnt",
			type: "post",
			data: {odr_code : odr_code, pdt_num : pdt_num},
			dataType: "json",
			success : function(data){
				$("#modalLabel").html("상품 후기 수정" + " : " + data.rv_num);
				$("#rv_content").val();

				$("#rv_num").val(data.rv_num);
				$("#rv_content").val(data.rv_content);
				
				// 상품후기 수정 모달대화상자에서 별점표시 작업
				$("#star_grade a").each(function(index, item){
					if(index < data.rv_score){
						$(item).addClass("on");
					}else{
						$(item).removeClass("on");
					}
				});

				$("button.btnModal").hide(); // 추가, 수정, 삭제버튼 모두 표시 안함
				$("#btnReviewEdit").show();
				$("#btnReviewDel").show();
				
				$("#reviewModal").modal("show");
			},
			error : function(data){
				alert("상품후기를 등록해주세요.");
			}
		});
	});

	// 별점 색상변경
	$("#star_grade a").click(function(e){
		e.preventDefault();
		$(this).parent().children("a").removeClass("on");
		$(this).addClass("on").prevAll("a").addClass("on");
	});

	// 상품후기 등록
	$("#btnReviewAdd").on("click", function(){
		var rv_score = 0;
		var rv_content = $("#rv_content").val();
		var pdt_num = $("#rv_pdt_num").val();
		var odr_code = $("#odr_code").val();

		$("#star_grade a").each(function(i,e){ //i: 인덱스, e: a태그를 가리키는 것
			if($(this).attr("class") == "on"){
				rv_score += 1;
			}
		});
		
		if(rv_score == 0){
			alert("별점을 선택해 주세요.");
			return;
		}else if(rv_content == "" || rv_content == null){
			alert("후기 내용을 입력해 주세요.");
			return;
		}
		
		// ajax 호출
		// 후기입력데이터를 전송
		$.ajax({
			url: "/review/review_register",
			type: "post",
			data: {rv_score : rv_score, rv_content : rv_content, pdt_num : pdt_num, odr_code : odr_code},
			dataType: "text",
			success : function(data){
				alert("상품 후기 등록 완료");
				$("#star_grade a").parent().children("a").removeClass("on");
				$("#rv_content").val("");
				$("#reviewModal").modal("hide");
			}
		});
		
	});

	// 상품후기 수정하기
	$("#btnReviewEdit").on("click", function(){
		var rv_score = 0;
		var rv_content = $("#rv_content").val();

		var rv_num = $("#rv_num").val();

		$("#star_grade a").each(function(i,e){ //i: 인덱스, e: a태그를 가리키는 것
			if($(this).attr("class") == "on"){
				rv_score += 1;
			}
		});
		
		if(rv_score == 0){
			alert("별점을 선택해 주세요.");
			return;
		}else if(rv_content == "" || rv_content == null){
			alert("후기 내용을 입력해 주세요.");
			return;
		}
		
		// ajax 호출
		// 후기입력데이터를 전송
		$.ajax({
			url: "/review/review_modify",
			type: "post",
			data: {rv_score : rv_score, rv_content : rv_content, rv_num : rv_num},
			dataType: "text",
			success : function(data){
				alert("상품 후기 수정 완료");
				$("#star_grade a").parent().children("a").removeClass("on");
				$("#rv_content").val("");
				$("#reviewModal").modal("hide");
			}
		});
		
	});

	// 상품후기목록 삭제클릭시
	$("#btnReviewDel").on("click", function(){

		var rv_num = $("#rv_num").val();
	
		// ajax 호출
		// 후기입력데이터를 전송
		$.ajax({
			url: "/review/review_delete",
			type: "post",
			data: {rv_num : rv_num},
			dataType: "text",
			success : function(data){
				alert("상품 후기 삭제 완료");
				$("#star_grade a").parent().children("a").removeClass("on");
				$("#rv_content").val("");
				$("#reviewModal").modal("hide");
			}
		});
	});

	
	/* 수정 후 사용 안함
	
	// 로그인 사용자와 상품후기 작성자 동일하면, 수정, 삭제 표시
	Handlebars.registerHelper("eqReplyer", function(replyer, rv_num){
		var str = "";
		var login_id = "${sessionScope.loginStatus.mem_id}";

		if(replyer == login_id){
			str += '<li class="list-group-item">';
			str += '<button type="button" class="btn btn-primary btn-edit">Modify</button>';
			str += '<button type="button" class="btn btn-primary btn-del">Delete</button>';
			str += '</li>';
		}

		// 태그 문자열을 처리할 때 사용
		return new Handlebars.SafeString(str);
		
	});
	
	// 상품후기목록 수정클릭시 (동적으로 추가된 태그를 이벤트 설정하는 구문)
	$("#reviewListView").on("click", ".btn-edit", function(){
		console.log("후기수정버튼");

		// 모달대화상자 표시 - 수정내용 반영

		var rv_num, mem_id, pdt_num, rv_content, rv_score, rv_date_reg;

		rv_num = $(this).parents("ul.list-group").find("li[data-rv_num]").attr("data-rv_num");
		rv_content = $(this).parents("ul.list-group").find("li[data-rv_content]").attr("data-rv_content");
		rv_score = $(this).parents("ul.list-group").find("li[data-rv_score]").attr("data-rv_score");
		mem_id = $(this).parents("ul.list-group").find("li[data-mem_id]").attr("data-mem_id");
		rv_date_reg = $(this).parents("ul.list-group").find("li[data-rv_date_reg]").attr("data-rv_date_reg");
		
		$("#modalLabel").html("Product Review Modal-Modify" + " : " + rv_num);
		
		$("#rv_num").val(rv_num);
		$("#rv_content").val(rv_content);
		
		// 상품후기 수정 모달대화상자에서 별점표시 작업
		$("#star_grade a").each(function(index, item){

			if(index < rv_score){
				$(item).addClass("on");
			}else{
				$(item).removeClass("on");
			}
			
		});

		$("button.btnModal").hide(); // 추가, 수정, 삭제버튼 모두 표시 안함
		$("#btnReviewEdit").show();
		
		$("#reviewModal").modal("show");

	});
	
	// 상품후기목록 삭제보기
	$("#reviewListView").on("click", ".btn-del", function(){
		console.log("후기삭제버튼");

		// 모달대화상자 표시 - 수정내용 반영

		var rv_num, mem_id, pdt_num, rv_content, rv_score, rv_date_reg;

		rv_num = $(this).parents("ul.list-group").find("li[data-rv_num]").attr("data-rv_num");
		rv_content = $(this).parents("ul.list-group").find("li[data-rv_content]").attr("data-rv_content");
		rv_score = $(this).parents("ul.list-group").find("li[data-rv_score]").attr("data-rv_score");
		mem_id = $(this).parents("ul.list-group").find("li[data-mem_id]").attr("data-mem_id");
		rv_date_reg = $(this).parents("ul.list-group").find("li[data-rv_date_reg]").attr("data-rv_date_reg");
		
		$("#modalLabel").html("Product Review Modal-Delete" + " : " + rv_num);
		
		$("#rv_num").val(rv_num);
		$("#rv_content").val(rv_content);
		
		// 상품후기 수정 모달대화상자에서 별점표시 작업
		$("#star_grade a").each(function(index, item){

			if(index < rv_score){
				$(item).addClass("on");
			}else{
				$(item).removeClass("on");
			}
			
		});

		$("button.btnModal").hide(); // 추가, 수정, 삭제버튼 모두 표시 안함
		$("#btnReviewDel").show();
		
		$("#reviewModal").modal("show");


	});
	
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
	
	//리뷰 페이징
	$("#reviewPaging").on("click", "li.page-item a", function(e){
		e.preventDefault();

		console.log("페이지번호클릭");

		curPage = $(this).attr("href");
		showReviewList(curPage);
	});
	*/
});