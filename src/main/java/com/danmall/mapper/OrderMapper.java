package com.danmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.danmall.domain.OrderDetailListVO;
import com.danmall.domain.OrderDetailVO;
import com.danmall.domain.OrderVO;
import com.danmall.dto.Criteria;
import com.danmall.dto.MemberOrderDTO;
import com.danmall.dto.OrderSaleDTO;

public interface OrderMapper {

	// 주문정보
	public void order_add(OrderVO vo) throws Exception;
	
	// 주문상세정보(주문내역)
	public void orderDetail_add(@Param("odr_code") long odr_code, @Param("mem_id") String mem_id) throws Exception;
	
	// 장바구니 전체 삭제
	public void delete_cartAll(String mem_id) throws Exception;
	
	// 즉시구매 추가
	public void orderDirect_add(OrderDetailVO vo) throws Exception;
	
	// admin_주문리스트
	public List<OrderVO> orderInfo_list(Criteria cri) throws Exception;
	
	// admin_주문상품개수(페이징)
	public int getTotalCountOrder(Criteria cri) throws Exception;
	
	// admin_주문상세목록-다은
	public List<OrderDetailListVO> orderDetail_list(String odr_code) throws Exception;
	
	// admin_주문상세목록-강사님
	public List<OrderDetailListVO> order_detail_list(long odr_code) throws Exception;
	
	// 회원별 주문목록
	public List<MemberOrderDTO> order_list(@Param("mem_id") String mem_id, @Param("cri") Criteria cri) throws Exception;
	
	// 회원별 주문상품개수(페이징)
	public int order_count(@Param("mem_id") String mem_id, @Param("cri") Criteria cri) throws Exception;
	
	//
	public List<OrderSaleDTO> order_sale(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
	
}
