package com.danmall.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.danmall.domain.CartVO;
import com.danmall.domain.CartVOList;
import com.danmall.dto.CartDTO;

public interface CartService {

	// 장바구니
	public void add_cart(CartVO vo) throws Exception;
	
	// 장바구니 목록
	public List<CartVOList> list_cart(String mem_id) throws Exception;
	
	// 장바구니 전체 비우기
	public void delete_cartAll(String mem_id) throws Exception;
	
	// 장바구니 제품 수량 수정
	public void cart_amount_update(long cart_code, int cart_amount) throws Exception;
	
	// 장바구니 체크 삭제
	public void cart_check_delete(List<Integer> checkArr) throws Exception;
	
	// 장바구니 체크 구매(선택된 것을 제외한 상품삭제)
	public void cart_not_check_delete(List<Integer> checkArr) throws Exception;
	
	//
	public List<CartDTO> cart_money() throws Exception;
	
	public JSONObject getChartData() throws Exception;

}
