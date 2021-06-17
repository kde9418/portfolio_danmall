package com.danmall.controller;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.danmall.domain.MemberVO;
import com.danmall.domain.ReviewVO;
import com.danmall.dto.Criteria;
import com.danmall.dto.ReviewPageDTO;
import com.danmall.service.ReviewService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/review/*")
public class ReviewController {
	
	@Setter(onMethod_ = @Autowired)
	private ReviewService reviewService; // @AllArgsConstructor : 모든 필드에 생성자메서드가 생성이 되고, 생성자는 어노테이션을 생략하고, 자동으로 주입이 이루어짐.
	
	// 제품 댓글 조회(페이징기능포함)
	@GetMapping(value = "/pages/{pdt_num}/{page}",
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	private ResponseEntity<ReviewPageDTO> getReviewListPage(@PathVariable("pdt_num") int pdt_num, @PathVariable("page") int page) {
		
		ResponseEntity<ReviewPageDTO> entity = null;
		
		Criteria cri = new Criteria(page, 5);
		
		log.info("상품번호: " + pdt_num);
		log.info("cri: " + cri);
		
		// 상품후기 개수(페이징)
		// 상품후기 목록
		
		try {
			entity = new ResponseEntity<ReviewPageDTO>(reviewService.getReviewListWithPaging(cri, pdt_num), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<ReviewPageDTO>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	// 제품 후기 등록
	@PostMapping(value = "/review_register")
	@ResponseBody
	private ResponseEntity<String> review_register(ReviewVO vo, HttpSession session) throws Exception{
		
		String mem_id =  ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		vo.setMem_id(mem_id);
		log.info("review_register: " + vo);
		
		ResponseEntity<String> entity = null;
		
		try {
			reviewService.review_register(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	
	// 제품 후기 수정
	@PostMapping(value = "/review_modify")
	@ResponseBody
	private ResponseEntity<String> review_modify(ReviewVO vo) throws Exception{
		
		log.info("review_modify: " + vo);
		
		ResponseEntity<String> entity = null;
		
		try {
			reviewService.review_modify(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	// 제품 후기 삭제
	@PostMapping(value = "/review_delete")
	@ResponseBody
	private ResponseEntity<String> review_modify(int rv_num) throws Exception{
		
		log.info("review_delete: " + rv_num);
		
		ResponseEntity<String> entity = null;
		
		try {
			reviewService.review_delete(rv_num);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	// 제품 후기 조회
	@PostMapping("/review_cnt")
	private ResponseEntity<ReviewVO> review_cnt(HttpSession session, int odr_code, int pdt_num) throws Exception {
		
		String mem_id =  ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		ResponseEntity<ReviewVO> entity = null;
		
		try {
			
			ReviewVO vo =reviewService.review_list(mem_id, odr_code, pdt_num);
			
			entity = new ResponseEntity<ReviewVO>(vo, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<ReviewVO>(HttpStatus.BAD_REQUEST);
		}
		/*
		try {
			int count = reviewService.review_cnt(mem_id, odr_code, pdt_num);
			
			if(count != 0) {
				//리뷰 있음
				ReviewVO vo =reviewService.review_list(mem_id, odr_code, pdt_num);
				model.addAttribute("ReviewVO", vo);
				
				entity = new ResponseEntity<ReviewVO>(vo, HttpStatus.OK);
			} else {
				//리뷰 없음
				entity = new ResponseEntity<ReviewVO>(vo, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<ReviewVO>(HttpStatus.BAD_REQUEST);
		}*/
		
		return entity;
	}
}
