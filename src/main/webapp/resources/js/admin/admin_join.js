$(document).ready(function() {
	
	var form = $("#joinForm");
	
	// 아이디중복체크 기능 사용여부확인 변수
	var isCheckId = "false";

		
	/* 아이디 중복체크 클릭 시 */
	$("#btn_checkAdminId").on("click", function(){

		if($("#admin_id").val()=="" || $("#admin_id").val()== null){
			$("#id_availability").css("color", "red");
			$("#id_availability").html("아이디를 먼저 입력해주세요.");
			return;
		} 
		
		var admin_id = $("#admin_id").val();

		$.ajax({
			url: '/admin/checkAdminIdDuplicate',
			type: 'post',
			dataType: 'text',
			data: {admin_id : admin_id},
			success : function(data){
				if(data== 'SUCCESS'){
					// 사용 가능한 아이디
					$("#id_availability").css("color", "blue");
					$("#id_availability").html("사용가능한 아이디 입니다.");
					
					isCheckId = "true";  // 아이디 중복체크를 한 용도
				} else {
					// 사용 불가능 - 이미 존재하는 아이디
					$("#id_availability").css("color", "red");
					$("#id_availability").html("이미 존재하는 아이디입니다. \n다시 시도해주세요.");
				}
			}
		});
	});
	
	/* 회원가입 취소 버튼 클릭 시 */
	$("#btn_cancle").on("click", function(){
		
		var result = confirm("가입을 취소하시겠습니까?");
		if(result){
			location.href="/admin/"; 
		} else{}
	});
	
	/* 비밀번호 확인 코드 */
	$("#admin_pw_check").on("change", function(){
		
		if($("#admin_pw").val() == $("#admin_pw_check").val()){
			$("#pw_check").css("color", "blue");
			$("#pw_check").html("일치합니다.");
		} else if($("#admin_pw").val() != $("#admin_pw_check").val()){
			$("#pw_check").css("color", "red");
			$("#pw_check").html("입력하신 비밀번호가 다릅니다.");
		}
	});
	
	/* 회원가입 버튼 클릭 시 : <button type='button'> */ 
	$("#btn_join").on("click", function(){
		
		var admin_id = $("#admin_id");
		var admin_pw = $("#admin_pw");
		var admin_pw_check = $("#admin_pw_check");
		var admin_name = $("#admin_name");
		
		/* 유효성 검사 */
		if(admin_id.val()==null || admin_id.val()==""){
			alert("아이디를 입력해주세요");
			admin_id.focus();
			
		} else if(isCheckId =="false"){   // 아이디 중복기능 사용여부 확인
			alert("아이디 중복 체크를 해주세요.");
			$("#btn_checkAdminId").focus();
			
		} else if(admin_pw.val()==null || admin_pw.val()==""){
			alert("비밀번호를 입력해주세요.");
			admin_pw.focus();
			
		} else if(admin_pw_check.val()==null || admin_pw_check.val()==""){
			alert("비밀번호 확인 란을 입력해주세요.");
			admin_pw_check.focus();
			
		} else if(admin_pw.val() != admin_pw_check.val()){
			alert("입력하신 비밀번호가 다릅니다.\n비밀번호를 다시 확인해주세요.");
			admin_pw_check.focus();

		} else if(admin_name.val()==null || admin_name.val()==""){
			alert("이름을 입력해주세요.");
			admin_name.focus();
		
		} else {
			form.submit();  // 전송태그를 <button type="button"> 를 사용하여 서브밋메서들 호출함.
		}
	});
	
});

