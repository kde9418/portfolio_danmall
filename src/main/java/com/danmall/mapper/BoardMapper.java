package com.danmall.mapper;

import com.danmall.domain.BoardVO;

public interface BoardMapper {
	
	// 게시글 등록
	public void board_insert(BoardVO vo) throws Exception;

}
