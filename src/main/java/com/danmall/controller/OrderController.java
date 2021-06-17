package com.danmall.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.danmall.domain.CartVOList;
import com.danmall.domain.MemberVO;
import com.danmall.domain.OrderDetailVO;
import com.danmall.domain.OrderVO;
import com.danmall.dto.Criteria;
import com.danmall.dto.PageDTO;
import com.danmall.dto.ProductDTO;
import com.danmall.service.CartService;
import com.danmall.service.OrderService;
import com.danmall.service.UserProductService;
import com.danmall.util.FileUploadUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/order/*")
public class OrderController {

	@Setter(onMethod_ = @Autowired)
	private CartService cartService;
	
	@Setter(onMethod_ = @Autowired)
	private OrderService orderService;
	
	@Setter(onMethod_ = @Autowired)
	private UserProductService userProductService;
	
	@Resource(name = "uploadPath")
	private String uploadPath;
	
//	@PostMapping("/order") // 즉시구메
//	@GetMapping("/order") // 장바구니 구매                  @RequestParam
	
	// @RequestParam(required = false) : get요청에 의한 해당쿼리스트링이 존재하지 않아도 처리하고자 할 때 사용.(예외발생이 안 됨.)
	
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public void order(HttpSession session, @ModelAttribute("type") String type, @RequestParam(required = false) Long pdt_num, 
						@RequestParam(required = false) Integer odr_amount, Model model) throws Exception{
		
		// type : 1-즉시구매, 2-장바구니 기반으로 주문하기
		
		// 사용자별 장바구니 내역
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		
		if(type.equals("1")) {
			log.info("즉시구매");
			
			// 구매상품에 대한 정보를 표시
			ProductDTO vo = userProductService.getProductByNum((long)pdt_num);

			// 매개변수가 있는 생성자를 만들어서, 디폴트생성자 처리해 줄것.
			CartVOList cartVO = new CartVOList(0, vo.getPdt_img(), vo.getPdt_name(), (int)odr_amount, vo.getPdt_price(), vo.getPdt_consumer(), vo.getPdt_num());
			
			List<CartVOList> cartVOList = new ArrayList<CartVOList>();
			cartVOList.add(cartVO);
			
			model.addAttribute("cartVOList", cartVOList);
			
			// 즉시구매시 상품상세정보를 폼에 삽입하기 위한 Model작업
			// jsp에서 "cartVOList" 값을 같이 사용하기 위해.
			model.addAttribute("pdt_num", pdt_num);
			model.addAttribute("odr_amount", odr_amount);
			model.addAttribute("odr_price", vo.getPdt_consumer());
			
		} else if (type.equals("2")) {
			log.info("장바구니 구매");
			
			// 구매상품을 장바구니에서 가져와서 표시
			model.addAttribute("cartVOList", cartService.list_cart(mem_id));
			
			// 장바구니를 기반으로 진행할 땐 필요 없는 값들이다.
			// 사용하지 않는 값이지만, 주문하기 클릭을 하게되면,  OrderDetailVO vo2 파라미터에서 에러발생이 되므로, 형식만 유지를 했음.
			model.addAttribute("pdt_num", 0);
			model.addAttribute("odr_amount", 0);
			model.addAttribute("odr_price", 0);
		}
		
		// 주문입력폼 구성작업
	}
	
	// 상품이미지 뷰
	@ResponseBody
	@GetMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception{
		
		return FileUploadUtils.getFile(uploadPath, fileName);
		
	}
	
	// 주문하기
	// order.jsp에서 type파라미터 정보를 받아와야, 즉시구매, 장바구니를 통한 구매를 분기작업을 할 수가 있다.
	// OrderDetailVO vo2 : 장바구니 경유에서 주문하기는 사용이 안 된다.
	@PostMapping("/order_buy")
	public String order_buy(OrderVO vo, OrderDetailVO vo2, String type, HttpSession session) throws Exception{
		
		log.info("order_buy: " + vo);
		
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		vo.setMem_id(mem_id);
		
		// 구분확인 할 것.
		if(type.equals("1")) { //즉시구매
			
			log.info("order: " + vo);
			log.info("orderDetail: " + vo2);
			
			// 장바구니 테이블은 제외 ((즉시구매이므로 장바구니에 상품을 저장안함)
			// 주문, 주문상세테이블 작업 : pdt_num, odr_amount, odr_price
			orderService.orderDirect_add(vo, vo2); // 즉시구매한 상품구성 해줄것.
		} else if (type.equals("2")) {
			// 주문, 주문상세, 장바구니 삭제
			orderService.order_buy(vo, mem_id);
		}
		
		return "redirect:/order/order_list";
	}

	// 주문 목록
	@GetMapping("/order_list")
	public void order_list(@ModelAttribute("cri") Criteria cri, HttpSession session, Model model) throws Exception{
		log.info("order_list");
		
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		
		model.addAttribute("orderList", orderService.order_list(mem_id, cri));
		
		int total = orderService.order_count(mem_id, cri);
		
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}
	
}
