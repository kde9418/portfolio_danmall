package com.danmall.service;

import com.danmall.domain.BoardVO;

public interface BoardService {

	// 게시글 등록
	public void board_insert(BoardVO vo) throws Exception;
}
