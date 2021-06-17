package com.danmall.service;

import com.danmall.domain.AdminVO;

public interface AdminService {
	
	// 로그인
	public AdminVO login(AdminVO vo) throws Exception;
	
	// 계정추가
	public void join(AdminVO vo) throws Exception;

	// 아이디 중복체크
	public int checkAdminIdDuplicate(String admin_id) throws Exception;
}
