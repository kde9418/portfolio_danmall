$(document).ready(function(){

	// 페이징
	var actionForm = $("#actionForm");

	$(".page-item a").on("click", function(e){
		e.preventDefault();

		console.log("click");

		actionForm.find("input[name='pageNum']").val($(this).attr("href"));
		actionForm.submit();

	});

	// 상품문의 상세내용
	$("a[class='board_detail']").on("click", function(e){

		e.preventDefault();
		console.log("상세내용");

		var brd_num = $(this).attr("data-brd_num");
		var currnet_tr = $(this).parents("tr");

		$.ajax({
			url: "/board/board_detail_list",
			type: "post",
			data: {
				brd_num : brd_num
			},
			dataType: "json", // 서버로 부터 받아오는 데이터 타입
			success : function(data){
				boardDetailView(data, currnet_tr, $("#boardDetailTemplate"));
			}
		});

	});
	
	// 상품문의 수정 클릭(모달보기)
	$("button[name='btnEdit']").on("click", function(){
		var brd_num =$(this).attr("data-brd_num");
		$("#brd_num").val(brd_num);
		
		console.log(brd_num);
		
		// ajax 호출
		// 후기입력데이터를 호출
		$.ajax({
			url: "/board/board_detail_list2",
			type: "post",
			data: {brd_num : brd_num},
			dataType: "json",
			success : function(data){
				console.log(data.brd_num);
				
				$("#modalLabel").html("상품문의 수정" + " : " + data.brd_num);
				$("#brd_title").val(data.brd_title);
				$("#brd_content").val();

				$("#brd_num").val(data.brd_num);
				$('input[name="brd_cg"]').val([data.brd_cg]);
				$("input:radio[name=brd_cg]").attr("disabled", true); //비활성화
				$("#brd_content").val(data.brd_content);
				
				
				$("button.btnModal").hide(); // 추가, 수정, 삭제버튼 모두 표시 안함
				$("#btnBoardEdit").show();
				$("#btnBoardDel").show();
				
				$("#boardModal").modal("show");
			},
			error : function(data){
				alert("에러가 발생했습니다.");
			}
		});
	});

    // 상품문의 수정하기
	$("#btnBoardEdit").on("click", function(){
		var brd_content = $("#brd_content").val();
		var brd_num = $("#brd_num").val();
		var brd_title = $("#brd_title").val();

		if(brd_content == "" || brd_content == null){
			alert("상품문의 내용을 입력해 주세요.");
			return;
		}else if(brd_title == "" || brd_title == null){
			alert("상품문의 제목을 입력해 주세요.");
			return;
		}
		
		// ajax 호출
		// 후기입력데이터를 전송
		$.ajax({
			url: "/board/board_modify",
			type: "post",
			data: {brd_content : brd_content, brd_num : brd_num, brd_title : brd_title},
			dataType: "text",
			success : function(){
				alert("상품문의 수정 완료");
				$("#boardModal").modal("hide");
			}
		});
		
	});

	// 상품문의 삭제하기
	$("#btnBoardDel").on("click", function(){

		var brd_num = $("#brd_num").val();
	
		if(confirm("상품문의를 삭제하시겠습니까?") == true){
			// ajax 호출
			// 후기입력데이터를 전송
			$.ajax({
				url: "/board/board_delete",
				type: "post",
				data: {brd_num : brd_num},
				dataType: "text",
				success : function(){
					alert("상품문의가 삭제 되었습니다.");
					$("#boardModal").modal("hide");
				}
			});
		}		
	});

});