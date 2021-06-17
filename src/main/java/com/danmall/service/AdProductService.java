package com.danmall.service;

import java.util.List;

import com.danmall.domain.CategoryVO;
import com.danmall.domain.ProductVO;
import com.danmall.dto.Criteria;

public interface AdProductService {

	// 1차 카테고리
	public List<CategoryVO> getCategoryList() throws  Exception;
	
	// 2차 카테고리
	public List<CategoryVO> getSubCategoryList(String cg_code) throws  Exception;

	// 상품등록
	public void product_insert(ProductVO vo) throws Exception;
	
	// 상품리스트
	public List<ProductVO> product_list(Criteria cri) throws Exception;
	
	// 상품개수(페이징)
	public int getTotalCountProduct(Criteria cri) throws Exception;
	
	// 상품수정 폼
	public ProductVO product_modify(Long pdt_num) throws Exception;
	
	// 상품수정하기
	public void product_modify(ProductVO vo) throws Exception;
	
	// 상품 구매여부 수정
	public int product_buy_modify(Long pdt_num, String pdt_buy) throws Exception;
	
	// 상품 삭제하기
	public void product_delete(long pdt_num) throws Exception;
	
}
