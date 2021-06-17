package com.danmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danmall.domain.CategoryVO;
import com.danmall.domain.ProductVO;
import com.danmall.dto.Criteria;
import com.danmall.mapper.AdProductMapper;

import lombok.Setter;

@Service
public class AdProductServiceImpl implements AdProductService {

	@Setter(onMethod_ = @Autowired)
	private AdProductMapper pro_mapper;
	
	// 1차 카테고리
	@Override
	public List<CategoryVO> getCategoryList() throws Exception {
		// TODO Auto-generated method stub
		return pro_mapper.getCategoryList();
	}

	// 2차 카테고리
	@Override
	public List<CategoryVO> getSubCategoryList(String cg_code) throws Exception {
		// TODO Auto-generated method stub
		return pro_mapper.getSubCategoryList(cg_code);
	}

	// 상품등록
	@Override
	public void product_insert(ProductVO vo) throws Exception {
		// TODO Auto-generated method stub
		pro_mapper.product_insert(vo);
	}

	// 상품리스트
	@Override
	public List<ProductVO> product_list(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return pro_mapper.product_list(cri);
	}

	// 상품개수(페이징)
	@Override
	public int getTotalCountProduct(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return pro_mapper.getTotalCountProduct(cri);
	}

	// 상품수정 폼
	@Override
	public ProductVO product_modify(Long pdt_num) throws Exception {
		// TODO Auto-generated method stub
		return pro_mapper.product_modify(pdt_num);
	}

	// 상품수정하기
	@Override
	public void product_modify(ProductVO vo) throws Exception {
		// TODO Auto-generated method stub
		pro_mapper.product_modifyOk(vo);
	}

	// 상품 삭제하기
	@Override
	public void product_delete(long pdt_num) throws Exception {
		// TODO Auto-generated method stub
		pro_mapper.product_deleteOk(pdt_num);
	}

	// 상품 구매여부 수정
	@Override
	public int product_buy_modify(Long pdt_num, String pdt_buy) throws Exception {
		// TODO Auto-generated method stub
		return pro_mapper.product_buy_modify(pdt_num, pdt_buy);
	}

	

}
