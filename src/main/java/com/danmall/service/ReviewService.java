package com.danmall.service;

import com.danmall.domain.ReviewVO;
import com.danmall.dto.Criteria;
import com.danmall.dto.ReviewPageDTO;

public interface ReviewService {

	// 제품 댓글 리스트 및 개수 조회
	public ReviewPageDTO getReviewListWithPaging(Criteria cri, int pdt_num) throws Exception;

	// 제품 후기 등록
	public void review_register(ReviewVO vo) throws Exception;
	
	// 제품 후기 수정
	public void review_modify(ReviewVO vo) throws Exception;
	
	// 제품 후기 삭제
	public void review_delete(int rv_num) throws Exception;
	
	// 제품별,회원별의 후기 번호 조회
	public int review_cnt(String mem_id, int odr_code, int pdt_num) throws Exception;
	
	// 제품별,회원별의 후기 내용 조회
	public ReviewVO review_list(String mem_id, int odr_code, int pdt_num) throws Exception;
}
