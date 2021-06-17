package com.danmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danmall.domain.OrderDetailListVO;
import com.danmall.domain.OrderDetailVO;
import com.danmall.domain.OrderVO;
import com.danmall.dto.Criteria;
import com.danmall.dto.MemberOrderDTO;
import com.danmall.dto.OrderSaleDTO;
import com.danmall.mapper.OrderMapper;

import lombok.Setter;

@Service
public class OrderServiceImpl implements OrderService {

	@Setter(onMethod_ = @Autowired)
	private OrderMapper orderMapper;
	
	// 주문하기
	@Transactional
	@Override
	public void order_buy(OrderVO vo, String mem_id) throws Exception {
		// TODO Auto-generated method stub
		orderMapper.order_add(vo);
		orderMapper.orderDetail_add(vo.getOdr_code(), mem_id);  // 동시성 보장
		orderMapper.delete_cartAll(mem_id);
	}

	// 즉시주문하기
	@Transactional
	@Override
	public void orderDirect_add(OrderVO vo, OrderDetailVO vo2) throws Exception {
		// TODO Auto-generated method stub
		orderMapper.order_add(vo); // 파라미터가 참조형(주소값)으로 전달되기 때문에
		vo2.setOdr_code(vo.getOdr_code());
		orderMapper.orderDirect_add(vo2);
	}

	// admin_주문리스트
	@Override
	public List<OrderVO> orderInfo_list(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return orderMapper.orderInfo_list(cri);
	}

	// admin_주문상품개수(페이징)
	@Override
	public int getTotalCountOrder(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return orderMapper.getTotalCountOrder(cri);
	}

	// admin_주문상세목록
	@Override
	public List<OrderDetailListVO> orderDetail_list(String odr_code) throws Exception {
		// TODO Auto-generated method stub
		return orderMapper.orderDetail_list(odr_code);
	}

	// admin_주문상세목록-강사님
	@Override
	public List<OrderDetailListVO> order_detail_list(long odr_code) throws Exception {
		// TODO Auto-generated method stub
		return orderMapper.order_detail_list(odr_code);
	}

	//
	@Override
	public List<OrderSaleDTO> order_sale(String startDate, String endDate) throws Exception {
		// TODO Auto-generated method stub
		return orderMapper.order_sale(startDate, endDate);
	}

	// 회원별 주문목록
	@Override
	public List<MemberOrderDTO> order_list(String mem_id, Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return orderMapper.order_list(mem_id, cri);
	}

	// 회원별 주문상품개수(페이징)
	@Override
	public int order_count(String mem_id, Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return orderMapper.order_count(mem_id, cri);
	}
	
	

}
