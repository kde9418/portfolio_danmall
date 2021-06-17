package com.danmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danmall.domain.ReviewVO;
import com.danmall.dto.Criteria;
import com.danmall.dto.ReviewPageDTO;
import com.danmall.mapper.ReviewMapper;

import lombok.Setter;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Setter(onMethod_ = @Autowired)
	private ReviewMapper reviewMapper;

	// 제품 댓글 리스트 및 개수 조회
	@Override
	public ReviewPageDTO getReviewListWithPaging(Criteria cri, int pdt_num) throws Exception {
		
		return new ReviewPageDTO(reviewMapper.getCountByProduct_pdt_num(pdt_num),reviewMapper.getReviewListWithPaging(cri, pdt_num));
	}

	// 제품 후기 등록
	@Override
	public void review_register(ReviewVO vo) throws Exception {
		// TODO Auto-generated method stub
		reviewMapper.review_register(vo);
	}

	// 제품 후기 수정
	@Override
	public void review_modify(ReviewVO vo) throws Exception {
		// TODO Auto-generated method stub
		reviewMapper.review_modify(vo);
	}

	// 제품 후기 삭제
	@Override
	public void review_delete(int rv_num) throws Exception {
		// TODO Auto-generated method stub
		reviewMapper.review_delete(rv_num);
	}

	// 제품별,회원별의 후기 번호 조회
	@Override
	public int review_cnt(String mem_id, int odr_code, int pdt_num) throws Exception {
		// TODO Auto-generated method stub
		return reviewMapper.review_cnt(mem_id, odr_code, pdt_num);
	}

	// 제품별,회원별의 후기 내용 조회
	@Override
	public ReviewVO review_list(String mem_id, int odr_code, int pdt_num) throws Exception {
		// TODO Auto-generated method stub
		return reviewMapper.review_list(mem_id, odr_code, pdt_num);
	}

	
	

}
