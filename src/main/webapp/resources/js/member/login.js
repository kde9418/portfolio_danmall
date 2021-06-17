$(document).ready(function() {
	
	var form = $("#loginForm");
	
    var userInputId = getCookie("userInputId");
    var setCookieYN = getCookie("setCookieYN");
    
    if(setCookieYN == 'Y') {
        $("#remember").prop("checked", true);
    } else {
        $("#remember").prop("checked", false);
    }
    
    $("#mem_id").val(userInputId); 

	// 로그인 버튼 클릭 시 
	$("#btn_login").on("click", function(e){
	
		e.preventDefault(); // 전송기능 취소
		
		var mem_id = $("#mem_id");
		var mem_pw = $("#mem_pw");

		if(mem_id.val()==null || mem_id.val()==""){
			alert("아이디를 입력해 주세요.");
			mem_id.focus();
			
		} else if(mem_pw.val()==null || mem_pw.val()==""){
			alert("비밀번호를 입력해 주세요.");
			mem_pw.focus();

		} else {
            if($("#remember").is(":checked")){ 
                var userInputId = $("#mem_id").val();
                setCookie("userInputId", userInputId, 60); 
                setCookie("setCookieYN", "Y", 60);
            } else {
                deleteCookie("userInputId");
                deleteCookie("setCookieYN");
            }
			form.submit();
		}
	});
	
});

//쿠키값 Set
function setCookie(cookieName, value, exdays){
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + 
    exdate.toGMTString());
    document.cookie = cookieName + "=" + cookieValue;
}

//쿠키값 Delete
function deleteCookie(cookieName){
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}

//쿠키값 가져오기
function getCookie(cookie_name) {
    var x, y;
    var val = document.cookie.split(';');
    
    for (var i = 0; i < val.length; i++) {
        x = val[i].substr(0, val[i].indexOf('='));
        y = val[i].substr(val[i].indexOf('=') + 1);
        x = x.replace(/^\s+|\s+$/g, ''); // 앞과 뒤의 공백 제거하기
        
        if (x == cookie_name) {
          return unescape(y); // unescape로 디코딩 후 값 리턴
        }
    }
}
