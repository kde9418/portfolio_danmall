package com.danmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danmall.domain.BoardVO;
import com.danmall.mapper.BoardMapper;

import lombok.Setter;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;

	// 게시글 등록
	@Override
	public void board_insert(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		boardMapper.board_insert(vo);
	}

}
