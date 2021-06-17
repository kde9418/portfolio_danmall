$(document).ready(function() {
	
	var form = $("#orderForm");
		
	$("#btn_submit").on("click", function(){
		
		var odr_name = $("#odr_name");
		var sample2_postcode = $("#sample2_postcode");
		var sample2_address = $("#sample2_address");
		var sample2_detailAddress = $("#sample2_detailAddress");
		var odr_phone = $("#odr_phone");
		
		/* 유효성 검사 */
		if(odr_name.val()==null || odr_name.val()==""){
			alert("이름을 입력해주세요");
			odr_name.focus();
			
		} else if(sample2_postcode.val()==null || sample2_postcode.val()==""){
			alert("우편번호를 입력해주세요");
			
		} else if(sample2_address.val()==null || sample2_address.val()==""){
			alert("주소를 입력해주세요.");
			
		} else if(sample2_detailAddress.val()==null || sample2_detailAddress.val()==""){
			alert("상세주소를 입력해주세요.");
			sample2_detailAddress.focus();
			
		} else if(odr_phone.val()==null || odr_phone.val()==""){
			alert("휴대폰 번호를 입력해주세요.");
			odr_phone.focus();

		} else {
			var result = confirm("구매하시겠습니까?");
			if(result){
				form.submit();
			} else{}
		}
	});

	$("#defaultCheck1").click(function(){
		var same = this.checked;

		$('#odr_name').val(same ? $('#mem_name').val():'');
		$('#sample2_postcode').val(same ? $('#mem_zipcode').val():'');
		$('#sample2_address').val(same ? $('#mem_addr').val():'');
		$('#sample2_detailAddress').val(same ? $('#mem_addr_d').val():'');
		$('#odr_phone').val(same ? $('#mem_phone').val():'');
	});

	$("#btn_cancle").on("click", function(){
			
		var result = confirm("구매를 취소하시겠습니까?");
		if(result){
			location.href="/cart/cart_list"; 
		} else{}
	});

	
});

