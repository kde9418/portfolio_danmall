package com.danmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danmall.domain.MemberVO;
import com.danmall.domain.UserInfoVO;
import com.danmall.dto.LoginDTO;
import com.danmall.mapper.MemberMapper;

import lombok.Setter;

@Service  // bean name : memberServiceImpl
public class MemberServiceImpl implements MemberService {
	
	@Setter(onMethod_ = @Autowired)
	private MemberMapper mapper;

	
	// 아이디 중복체크
	@Override
	public int checkIdDuplicate(String mem_id) throws Exception {
		// TODO Auto-generated method stub
		return mapper.checkIdDuplicate(mem_id);
	}
	
	// 이메일 중복체크
	@Override
	public int checkEmail(String mem_email) throws Exception {
		// TODO Auto-generated method stub
		return mapper.checkEmail(mem_email);
	}
	
	// 로그인
	@Override
	public MemberVO login_ok(LoginDTO dto) throws Exception {
		
		return mapper.login_ok(dto);
	}
	
	// 회원가입 저장
	@Override
	public void join(MemberVO vo) throws Exception {
		// TODO Auto-generated method stub
		mapper.join(vo);
	}

	// 회원수정 폼
	@Override
	public MemberVO member_info(String mem_id) throws Exception {
		// TODO Auto-generated method stub
		return mapper.member_info(mem_id);
	}

	// 회원수정 저장
	@Override
	public boolean modifyPOST(MemberVO vo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.modifyPOST(vo) == 1;
	}
	
	// 아이디 찾기
	@Override
	public String find_id(String mem_name, String mem_email) throws Exception {
		// TODO Auto-generated method stub
		return mapper.find_id(mem_name, mem_email);
	}

	// 비밀번호 찾기
	@Override
	public MemberVO find_pw(String mem_id, String mem_name) throws Exception {
		// TODO Auto-generated method stub
		return mapper.find_pw(mem_id, mem_name);
	}
	
	// 비밀번호 찾기 : 임시비밀번호 저장
	@Override
	public void tmp_pw(LoginDTO dto) throws Exception {
		// TODO Auto-generated method stub
		mapper.tmp_pw(dto);
	}

	// 비밀번호 변경
	@Override
	public void pwUpdate(LoginDTO dto) throws Exception {
		// TODO Auto-generated method stub
		mapper.pwUpdate(dto);
	}
	
	// 회원 탈퇴
	@Override
	public void delete_member(String mem_id) throws Exception {
		// TODO Auto-generated method stub
		mapper.delete_member(mem_id);
	}

	// VO클래스 필드와 테이블컬럼명이 다른 경우
	@Override
	public List<UserInfoVO> userinfo_list() throws Exception {
		// TODO Auto-generated method stub
		return mapper.userinfo_list();
	}


	
}
