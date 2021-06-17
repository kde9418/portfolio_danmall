package com.danmall.mapper;

import com.danmall.domain.AdminVO;

public interface AdminMapper {

	// 로그인
	public AdminVO login(AdminVO vo) throws Exception;
	
	// 로그인 시간 업데이트
	public void login_update(String admin_id) throws Exception;
	
	// 계정추가
	public void join(AdminVO vo) throws Exception;
	
	// 아이디 중복체크
	public int checkAdminIdDuplicate(String admin_id) throws Exception;
	
}
