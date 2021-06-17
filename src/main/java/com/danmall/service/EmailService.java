package com.danmall.service;

import com.danmall.dto.EmailDTO;

public interface EmailService {

	public void sendMail(EmailDTO dto, String message);
	
}
