package com.danmall.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.danmall.domain.CartVO;
import com.danmall.domain.MemberVO;
import com.danmall.dto.CartDTO;
import com.danmall.service.CartService;
import com.danmall.util.FileUploadUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/cart/*") // 공통주소로 사용 또는 jsp폴더명
public class CartController {
	
	@Setter(onMethod_ = @Autowired)
	private CartService cartService;
	
	@Resource(name = "uploadPath")
	private String uploadPath;
	
	// 장바구니 추가
	@ResponseBody
	@PostMapping("/add")
	public ResponseEntity<String> cart_add(long pdt_num, int odr_amount, HttpSession session, HttpServletResponse response) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		if(session.getAttribute("loginStatus") == null) {
			entity = new ResponseEntity<String>("LoginRequired", HttpStatus.OK);
			
			return entity;
		}
		
		// loginStatus (MemberController)
		
		MemberVO vo = (MemberVO) session.getAttribute("loginStatus");
		
		CartVO cart = new CartVO(0, pdt_num, vo.getMem_id(), odr_amount);
		
		log.info("cart_add: " + cart);
		
		try {
			cartService.add_cart(cart);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	// 장바구니 목록
	@GetMapping("/cart_list")
	public void cart_list(HttpSession session, Model model) throws Exception {
		
		log.info("cart_list");
		
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		model.addAttribute("cartVOList", cartService.list_cart(mem_id));
		model.addAttribute("navActive", "cartlist");
		
	}
	
	// 상품이미지 뷰
	@ResponseBody
	@GetMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception{
		
		log.info("fileName: "+fileName);
		
		return FileUploadUtils.getFile(uploadPath, fileName);
	}

	// 장바구니 개별삭제
	@ResponseBody
	@PostMapping("/delete")
	public ResponseEntity<String> cart_delete(long cart_code) throws Exception{
		
		ResponseEntity<String> entity = null;
		
		// 개별작업
		
		entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		
		return entity;
	}
	
	// 장바구니 수량변경
	/*
	@ResponseBody
	@PostMapping("/modify")
	public ResponseEntity<String> cart_delete(long cart_code) throws Exception{
		
		ResponseEntity<String> entity = null;
		
		// 개별작업
		
		entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		
		return entity;
	}
	*/
	
	// 장바구니 전체 비우기
	@GetMapping("/cart_all_delete")
	public String cart_all_delete(HttpSession session) throws Exception{
	
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		cartService.delete_cartAll(mem_id);
		
		return "redirect:/cart/cart_list";
	}
	
	// 장바구니 전체 비우기(ajax)
	@PostMapping("/cart_all_delete2")
	@ResponseBody
	public ResponseEntity<String> cart_all_ajax_delete(HttpSession session) throws Exception{
		
		ResponseEntity<String> entity = null;
		
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		
		try {
			cartService.delete_cartAll(mem_id);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	// 장바구니 선택삭제
	@PostMapping("/cart_check_delete")
	@ResponseBody
	public ResponseEntity<String> cart_check_delete(@RequestParam("checkArr[]") List<Integer> checkArr) throws Exception{
		
		ResponseEntity<String> entity = null;
		
		log.info("cart_check_delete: " + checkArr);
		
		try {
			cartService.cart_check_delete(checkArr);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	// 장바구니 선택주문
	@PostMapping("/cart_check_order")
	@ResponseBody
	public ResponseEntity<String> cart_check_order(@RequestParam("checkArr[]") List<Integer> checkArr) throws Exception{
		
		ResponseEntity<String> entity = null;
		
		log.info("cart_check_order: " + checkArr);
		
		try {
			cartService.cart_not_check_delete(checkArr);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}

	// 장바구니 제품 수량 변경
	@PostMapping("/cart_amount_update")
	@ResponseBody
	public ResponseEntity<String> cart_amount_update(long cart_code, int cart_amount) throws Exception{
		
		ResponseEntity<String> entity = null;
		
		try {
			cartService.cart_amount_update(cart_code, cart_amount);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@GetMapping("/chart_sample")
	public void chart_sample(Model model) {
		
		List<CartDTO> items = new ArrayList<CartDTO>();
		
		Random random = new Random();
		
		for(int i=1; i<=5; i++) {
			CartDTO cart = new CartDTO();
			int pirce = random.nextInt(10000 - 1000 + 1) + 1000;
			cart.setAmount(pirce);
			cart.setPdt_name("전자제품" + i);
			
			items.add(cart);
		}
		
		/*
		pie chart sample data
		
		[
          ['상품명', '가격'],
          ['전자제품1', 15000],
          ['전자제품2', 25000],
          ['전자제품3', 18000],
          ['전자제품4', 16500],
          ['전자제품5', 25000]
        ]
		 */
		
		int num = 0;
		String str = "[";
		str += "['상품명', '가격'],";
		for(CartDTO dto : items) {
			str += "['";
			str += dto.getPdt_name();
			str += "', ";
			str += dto.getAmount();
			str += "]";
			
			num++;
			if(num<items.size()) str += ",";
			
		}
		str += "]";
		
		log.info(str);
		
		model.addAttribute("chartData", str);
		
	}
	
}
