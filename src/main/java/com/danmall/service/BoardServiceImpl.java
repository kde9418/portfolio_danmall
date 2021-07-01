package com.danmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danmall.domain.BoardVO;
import com.danmall.dto.BoardDTO;
import com.danmall.dto.BoardListDTO;
import com.danmall.dto.Criteria;
import com.danmall.mapper.BoardMapper;

import lombok.Setter;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;

	// 게시글 등록
	@Override
	public void board_register(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		boardMapper.board_register(vo);
	}

	// 상품문의 목록(제품별) + 개수 조회
	@Override
	public BoardDTO board_list(Criteria cri, int pdt_num) throws Exception {

		return new BoardDTO(boardMapper.board_cnt(pdt_num), boardMapper.board_list(cri, pdt_num));
	}

	// 상품문의 목록(회원별)
	@Override
	public List<BoardListDTO> board_list_my(Criteria cri, String mem_id) throws Exception {
		// TODO Auto-generated method stub
		return boardMapper.board_list_my(cri, mem_id);
	}

	// 상품문의 댓글 개수(회원별)
	@Override
	public int board_cnt_my(Criteria cri, String mem_id) throws Exception {
		// TODO Auto-generated method stub
		return boardMapper.board_cnt_my(cri, mem_id);
	}

	// 상품문의 상세조회
	@Override
	public List<BoardListDTO> board_detail_list(long brd_num) throws Exception {
		// TODO Auto-generated method stub
		return boardMapper.board_detail_list(brd_num);
	}

	// 상품문의 상세조회2
	@Override
	public BoardListDTO board_detail_list2(long brd_num) throws Exception {
		// TODO Auto-generated method stub
		return boardMapper.board_detail_list2(brd_num);
	}

	// 상품문의 수정
	@Override
	public void board_modify(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		boardMapper.board_modify(vo);
	}

	// 상품문의 삭제
	@Override
	public void board_delete(long brd_num) throws Exception {
		// TODO Auto-generated method stub
		boardMapper.board_delete(brd_num);
	}

}
