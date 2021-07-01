package com.danmall.service;

import java.util.List;

import com.danmall.domain.BoardVO;
import com.danmall.dto.BoardDTO;
import com.danmall.dto.BoardListDTO;
import com.danmall.dto.Criteria;

public interface BoardService {

	// 게시글 등록
	public void board_register(BoardVO vo) throws Exception;
	
	// 상품문의 목록(제품별) + 개수 조회
	public BoardDTO board_list(Criteria cri, int pdt_num) throws Exception;
	
	// 상품문의 목록(회원별)
	public List<BoardListDTO> board_list_my(Criteria cri, String mem_id) throws Exception;
	
	// 상품문의 댓글 개수(회원별)
	public int board_cnt_my(Criteria cri, String mem_id) throws Exception;
	
	// 상품문의 상세조회
	public List<BoardListDTO> board_detail_list(long brd_num) throws Exception;
	
	// 상품문의 상세조회
	public BoardListDTO board_detail_list2(long brd_num) throws Exception;
	
	// 상품문의 수정
	public void board_modify(BoardVO vo) throws Exception;
	
	// 상품문의 삭제
	public void board_delete(long brd_num) throws Exception;
}
