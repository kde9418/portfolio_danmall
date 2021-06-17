package com.danmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danmall.domain.CategoryVO;
import com.danmall.dto.Criteria;
import com.danmall.dto.ProductDTO;
import com.danmall.mapper.UserProductMapper;

import lombok.Setter;

@Service
public class UserProductServiceImpl implements UserProductService {
	
	@Setter(onMethod_ = @Autowired)
	private UserProductMapper userProductMapper;

	// 1차 카테고리
	@Override
	public List<CategoryVO> getCategoryList() throws Exception {
		// TODO Auto-generated method stub
		return userProductMapper.getCategoryList();
	}

	// 2차 카테고리
	@Override
	public List<CategoryVO> getSubCategoryList(String cg_code) throws Exception {
		// TODO Auto-generated method stub
		return userProductMapper.getSubCategoryList(cg_code);
	}

	// 2차 카테고리에 해당하는 상품목록
	@Override
	public List<ProductDTO> getProductListBySubCate(Criteria cri, String cg_code) throws Exception {
		// TODO Auto-generated method stub
		return userProductMapper.getProductListBySubCate(cri, cg_code);
	}

	@Override
	public int getTotalCountProductBySubCate(String cg_code) throws Exception {
		// TODO Auto-generated method stub
		return userProductMapper.getTotalCountProductBySubCate(cg_code);
	}

	// 상품 상세설명
	@Override
	public ProductDTO getProductByNum(long pdt_num) throws Exception {
		// TODO Auto-generated method stub
		return userProductMapper.getProductByNum(pdt_num);
	}

}
