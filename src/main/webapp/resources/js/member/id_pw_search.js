$(document).ready(function(){

	// 비밀번호 찾기 : 메일 전송
    $("#btn_PW_Search").on("click", function(){
        // alert('된다!!');

        var userInfo = {mem_id: $("#mem_id").val(), mem_name: $("#mem_name").val()};

        $.ajax({
            url: '/member/find_pw',
            type: 'post',
            data: userInfo,
            dataType: 'text',
            success: function(data){
                // 비밀번호 출력작업

                if(data == "SUCCESS"){
                    alert('가입하신 메일로 비밀번호를 전송했습니다.');
					location.href = "/member/login";
                }else{
                    $("#result").html("입력하신 정보가 틀립니다.");
                    
                    $("#mem_id").val('');
                    $("#mem_name").val('');
                }
            }
        });
    });

	// 아이디 찾기 : 화면 출력
    $("#btn_ID_Search").on("click", function(){
        
        var userInfo = {mem_name: $("#mem_name").val(), mem_email: $("#mem_email").val()};

		console.log(userInfo);

        $.ajax({
            url: '/member/find_id',
            type: 'post',
            data: userInfo,
            dataType: 'text',
            success: function(data){
                // 아이디 출력작업

                if(data != ""){
                    $("#result").html($("#mem_name").val() + "님의 아이디는 " +"<b>[ " + data + " ]</b>" + " 입니다.");

                }else{
                    $("#result").html("입력하신 정보가 틀립니다.");

                    $("#mem_name").val('');
                    $("#mem_email").val('');
                }
            }
        });

    });

});