package com.danmall.dto;

import lombok.Data;

@Data
public class EmailDTO {

	private String senderName;	// 발신자 이름
	private String senderMail;	// 발신자 이메일 주소
	private String receiveMail; // 수신자 이메일 주소 - 이것만 사용자가 입력한 값 사용
	private String subject;		// 제목
	private String message;		// 본문
	
	/* 
	Constructor 
	[수신자 이메일 주소]를 제외하고,
	인증 코드 전송을 위한 작업을 해둠
	 */
	public EmailDTO() {
		this.senderName = "DAN_MALL";
		this.senderMail = "DAN_MALL";
		this.subject = "DAN_MALL 회원가입 인증코드입니다.";
		this.message = "이메일 인증을 위해,\n아래 인증코드를 이메일 인증코드 란에 입력하세요.\n\n인증코드 : ";
	}
	
}
