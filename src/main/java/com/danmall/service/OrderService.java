package com.danmall.service;

import java.util.List;

import com.danmall.domain.OrderDetailListVO;
import com.danmall.domain.OrderDetailVO;
import com.danmall.domain.OrderVO;
import com.danmall.dto.Criteria;
import com.danmall.dto.MemberOrderDTO;
import com.danmall.dto.OrderSaleDTO;

public interface OrderService {

	// 주문하기
	public void order_buy(OrderVO vo, String mem_id) throws Exception;
	
	// 즉시주문하기
	public void orderDirect_add(OrderVO vo, OrderDetailVO vo2) throws Exception;
	
	// admin_주문리스트
	public List<OrderVO> orderInfo_list(Criteria cri) throws Exception;
	
	// admin_주문상품개수(페이징)
	public int getTotalCountOrder(Criteria cri) throws Exception;
	
	// admin_주문상세목록-다은
	public List<OrderDetailListVO> orderDetail_list(String odr_code) throws Exception;
	
	// admin_주문상세목록-강사님
	public List<OrderDetailListVO> order_detail_list(long odr_code) throws Exception;
	
	// 회원별 주문목록
	public List<MemberOrderDTO> order_list(String mem_id, Criteria cri) throws Exception;
	
	// 회원별 주문상품개수(페이징)
	public int order_count(String mem_id, Criteria cri) throws Exception;
	
	//
	public List<OrderSaleDTO> order_sale(String startDate, String endDate) throws Exception;
}
