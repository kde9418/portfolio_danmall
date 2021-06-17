package com.danmall.service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.danmall.dto.EmailDTO;

import lombok.Setter;

@Service
public class EmailServiceImpl implements EmailService {

	// DB와 관련된 작업 없음.
	
	@Setter(onMethod_ = @Autowired)
	JavaMailSender mailSender;  // root-context.xml의 JavaMailSender bean객체의 id
	
	// message : 이메일 인증 코드 전송 or 비밀번호 전송
	@Override
	public void sendMail(EmailDTO dto, String message) {
		MimeMessage msg = mailSender.createMimeMessage();
		
		try {
			// 받는 사람 설정(이메일)
			msg.addRecipient(RecipientType.TO, new InternetAddress(dto.getReceiveMail()));
			// 보내는 사람 설정(이메일, 이름)
			msg.addFrom(new InternetAddress[] { new InternetAddress(dto.getSenderMail(), dto.getSenderName())});
			// 이메일 제목
			msg.setSubject(dto.getSubject(), "utf-8");
			// 이메일 본문(인증코드 추가)
			msg.setText(dto.getMessage() + message, "utf-8");
			
			// 메일 보내기
			mailSender.send(msg); // 진행 전에 gmail 보안설정을 낮게 바꿔줘야 한다.
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
