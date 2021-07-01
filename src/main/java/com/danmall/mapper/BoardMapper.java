package com.danmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.danmall.domain.BoardVO;
import com.danmall.dto.BoardListDTO;
import com.danmall.dto.Criteria;

public interface BoardMapper {
	
	// 상품문의 목록(제품별)
	public List<BoardVO> board_list(@Param("cri") Criteria cri, @Param("pdt_num") int pdt_num) throws Exception;
	
	// 상품문의 댓글 개수(제품별)
	public int board_cnt(int pdt_num) throws Exception;
	
	// 상품문의 목록(회원별)
	public List<BoardListDTO> board_list_my(@Param("cri") Criteria cri, @Param("mem_id") String mem_id) throws Exception;
	
	// 상품문의 댓글 개수(회원별)
	public int board_cnt_my(@Param("cri") Criteria cri, @Param("mem_id") String mem_id) throws Exception;
	
	// 상품문의 상세조회
	public List<BoardListDTO> board_detail_list(long brd_num) throws Exception;
	
	// 상품문의 상세조회
	public BoardListDTO board_detail_list2(long brd_num) throws Exception;
	
	// 상품문의 등록
	public void board_register(BoardVO vo) throws Exception;
	
	// 상품문의 수정
	public void board_modify(BoardVO vo) throws Exception;
	
	// 상품문의 삭제
	public void board_delete(long brd_num) throws Exception;

}
