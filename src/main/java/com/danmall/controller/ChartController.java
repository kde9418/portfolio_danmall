package com.danmall.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.danmall.dto.CartDTO;
import com.danmall.service.CartService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController  // @ResponseBody 기본적으로 제공 됨
@Log4j
@AllArgsConstructor
@RequestMapping("/chart/*")
public class ChartController {
	
	private CartService cartService;

	/*
	@ResponseBody
	@GetMapping("/chart_example")
	public ResponseEntity<String[][]> getChartDate(){
		
		log.info("getChartDate");
		
		ResponseEntity<String[][]> entity = null;
		
		String[][] chartData = null;
		
		try {
			// 장바구니 더미데이터 준비해서 실행
//			List<CartDTO> items = cartService.cart_money();
			
			// 데이터가 준비가  안되어, 코드로 샘플데이터 작업
			List<CartDTO> items = new ArrayList<CartDTO>();
			
			Random random = new Random();
			
			for(int i=0;i<5;i++) {
				CartDTO cart = new CartDTO();
				int pirce = random.nextInt(10000 - 1000 + 1) + 1000;
				cart.setAmount(pirce);
				cart.setPdt_name("전자제품" + i);
				
				items.add(cart);
			}
			
			JSONArray row = new JSONArray();
			
			// 제목 작업
			JSONObject name = new JSONObject();
			name.put("상품명", "가격");
			row.add(name);
			
			// 데이터 작업
			for(CartDTO dto : items) {
				JSONObject cell = new JSONObject();
				cell.put(dto.getPdt_name(), dto.getAmount());
				
				row.add(cell);
			}
			
			
			
			entity = new ResponseEntity<String[][]>(chartData, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String[][]>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	*/
	
	// 차트에 사용할 데이터를 2차원 배열형태로 작업하여, String 변수에 저장 
	@GetMapping("/chart_example")
	public ResponseEntity<String> getChartDate2(){
		
		ResponseEntity<String> entity = null;
		
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
		
		String str = "[";
		str += "['상품명', '가격']";
		for(CartDTO dto : items) {
			str += "['";
			str += dto.getPdt_name();
			str += "', ";
			str += dto.getAmount();
			str += "]";
		}
		str += "]";
		
		log.info(str);
		
		entity = new ResponseEntity<String>(str, HttpStatus.OK);
		
		
		return entity;
	}
	
	// 차트에 필요한 데이터를 json포맷의 클라이언트에 보냄
	@GetMapping("/chartData")
	public ResponseEntity<JSONObject> getCharData() {
		
		ResponseEntity<JSONObject> entity = null;
		
		try {
			entity = new ResponseEntity<JSONObject>(cartService.getChartData(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<JSONObject>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	
	@GetMapping("/chart") // /chart/chart(뷰만 요청)
	public ModelAndView chart() {
		/*
		ModelAndView mv = new ModelAndView();
		mv.setViewName("jsp파일명"); // 뷰
		mv.addObject("memberVO", "db에서 불러온 데이터"); // 모델작업
	
		return mv;
		*/
		return new ModelAndView("/chart/chart");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
