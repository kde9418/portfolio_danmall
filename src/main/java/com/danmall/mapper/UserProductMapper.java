package com.danmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.danmall.domain.CategoryVO;
import com.danmall.dto.Criteria;
import com.danmall.dto.ProductDTO;

public interface UserProductMapper {

	// 1차 카테고리
	public List<CategoryVO> getCategoryList() throws  Exception;
	
	// 2차 카테고리
	public List<CategoryVO> getSubCategoryList(String cg_code) throws  Exception;
	
	// 2차 카테고리에 해당하는 상품목록
	// mapepr 인터페이스에서 xml mapper파일의 sql 구분에 파라미터 적용하는 규칙
	// 파라미터가 1개일 경우에는 필드명을 사용하여 getter메서드를 호출한다.
	// 파리마터가 2개일 경우는 타입이 참조형일 경우에는 객체명.필드 형태로 사용해야 한다.
	// String 과 기본형일 경우에는 직접 사용해야 한다. 
	public List<ProductDTO> getProductListBySubCate(@Param("cri") Criteria cri, @Param("cg_code") String cg_code) throws  Exception;
	
	public int getTotalCountProductBySubCate(String cg_code) throws Exception;
	
	// 상품 상세설명
	public ProductDTO getProductByNum(long pdt_num) throws Exception;
	
	
	
}
