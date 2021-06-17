package com.danmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danmall.domain.AdminVO;
import com.danmall.mapper.AdminMapper;

import lombok.Setter;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Setter(onMethod_ = @Autowired)
	private AdminMapper adminMapper;

	// 로그인
	@Transactional
	@Override
	public AdminVO login(AdminVO vo) throws Exception {
		// TODO Auto-generated method stub
		
		AdminVO adVO = adminMapper.login(vo);
		
		if(adVO != null) {
			adminMapper.login_update(vo.getAdmin_id()); // 로그인 시간 업데이트
		}
		
		return adVO; // 이전 로그인 시간정보
	}

	// 계정추가
	@Override
	public void join(AdminVO vo) throws Exception {
		// TODO Auto-generated method stub
		adminMapper.join(vo);
	}

	// 아이디 중복체크
	@Override
	public int checkAdminIdDuplicate(String admin_id) throws Exception {
		// TODO Auto-generated method stub
		return adminMapper.checkAdminIdDuplicate(admin_id);
	}


}
