package com.danmall.service;

import java.util.List;

import com.danmall.domain.MemberVO;
import com.danmall.domain.UserInfoVO;
import com.danmall.dto.LoginDTO;

public interface MemberService {
	
	// 아이디 중복체크
	public int checkIdDuplicate(String mem_id) throws Exception;
	
	// 이메일 중복체크
	public int checkEmail(String mem_email) throws Exception;
	
	// 로그인
	public MemberVO login_ok(LoginDTO dto) throws Exception;
	
	// 회원가입 저장
	public void join(MemberVO vo) throws Exception;
	
	// 회원수정 폼
	public MemberVO member_info(String mem_id) throws Exception;
	
	// 회원수정 저장
	public boolean modifyPOST(MemberVO vo) throws Exception;
	
	// 아이디 찾기 : 화면 출력
	public String find_id(String mem_name, String mem_email) throws Exception;
	
	// 비밀번호 찾기 : 메일 전송
	public MemberVO find_pw(String mem_id, String mem_name) throws Exception;
	
	// 비밀번호 찾기 : 임시비밀번호 저장
	public void tmp_pw(LoginDTO dto) throws Exception;
	
	// 비밀번호 변경
	public void pwUpdate(LoginDTO dto) throws Exception;
	
	// 회원 탈퇴
	public void delete_member(String mem_id) throws Exception;
	
	// VO클래스 필드와 테이블컬럼명이 다른 경우
	public List<UserInfoVO> userinfo_list() throws Exception;
}
