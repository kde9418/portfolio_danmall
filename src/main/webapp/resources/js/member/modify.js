$(document).ready(function() {

	var form = $("#modifyForm");
	
	/* 가입 버튼 클릭 시 */
	$("#btn_submit").on("click", function(){
		// 유효성검사 제외

		var mem_pw = $("#mem_pw");
		var mem_name = $("#mem_name");
		var mem_email = $("#mem_email");
		var mem_phone = $("#mem_phone");
		var mem_zipcode = $("input[name='mem_zipcode']");
		var mem_addr = $("input[name='mem_addr']");
		var mem_addr_d = $("input[name='mem_addr_d']");
		
		/* 유효성 검사 */
		 if(mem_pw.val()==null || mem_pw.val()==""){
			alert("현재 비밀번호를 입력해주세요.");
			mem_pw.focus();
			return;
			
		} else if(mem_name.val()==null || mem_name.val()==""){
			alert("이름을 입력해주세요.");
			mem_name.focus();
			return;
		
		} else if(mem_email.val()==null || mem_email.val()==""){
			alert("이메일을 입력해주세요.");
			mem_email.focus();
			return;

		} else if(mem_phone.val()==null || mem_phone.val()==""){
			alert("휴대폰 번호를 입력해주세요.");
			mem_phone.focus();
			return;

		} else if(mem_zipcode.val()==null || mem_zipcode.val()==""){
			alert("우편번호를 입력해주세요.");
			$("#btn_postCode").focus();
			return;
			
		} else if(mem_addr.val()==null || mem_addr.val()==""){
			alert("주소를 입력해주세요.");
			$("#btn_postCode").focus();
			return;
			
		} else if(mem_addr_d.val()==null || mem_addr_d.val()==""){
			alert("상세 주소를 입력해주세요.");
			mem_addr_d.focus();
			return;
		} 

		form.submit();
	});
	
	/* 취소 버튼 클릭 시 */
	$("#btn_cancle").on("click", function(){
		
		var result = confirm("회원 정보 수정을 취소하시겠습니까?");
		if(result){
			location.href="/member/mypage"; 
		} else{}
	});
	
});

