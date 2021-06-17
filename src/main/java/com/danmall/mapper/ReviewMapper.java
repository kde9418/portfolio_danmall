package com.danmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.danmall.domain.ReviewVO;
import com.danmall.dto.Criteria;

public interface ReviewMapper {

	// 제품 댓글 리스트
	public List<ReviewVO> getReviewListWithPaging(@Param("cri") Criteria cri, @Param("pdt_num") int pdt_num) throws Exception;
	
	// 제품 댓글 개수
	public int getCountByProduct_pdt_num(int pdt_num) throws Exception;
	
	// 제품 후기 등록
	public void review_register(ReviewVO vo) throws Exception;
	
	// 제품 후기 수정
	public void review_modify(ReviewVO vo) throws Exception;
	
	// 제품 후기 삭제
	public void review_delete(int rb_num) throws Exception;
	
	// 제품별,회원별의 후기 번호 조회
	public int review_cnt(@Param("mem_id") String mem_id, @Param("odr_code") int odr_code, @Param("pdt_num") int pdt_num) throws Exception;
	
	// 제품별,회원별의 후기 내용 조회
	public ReviewVO review_list(@Param("mem_id") String mem_id, @Param("odr_code") int odr_code, @Param("pdt_num") int pdt_num) throws Exception;
	
}
