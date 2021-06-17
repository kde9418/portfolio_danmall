package com.danmall.service;

import java.util.List;

import com.danmall.domain.CategoryVO;
import com.danmall.dto.Criteria;
import com.danmall.dto.ProductDTO;

public interface UserProductService {

	// 1차 카테고리
	public List<CategoryVO> getCategoryList() throws Exception;
	
	// 2차 카테고리
	public List<CategoryVO> getSubCategoryList(String cg_code) throws Exception;

	// 2차 카테고리에 해당하는 상품목록
	public List<ProductDTO> getProductListBySubCate(Criteria cri, String cg_code) throws Exception;
	
	public int getTotalCountProductBySubCate(String cg_code) throws Exception;
	
	// 상품 상세설명
	public ProductDTO getProductByNum(long pdt_num) throws Exception;
	
}
