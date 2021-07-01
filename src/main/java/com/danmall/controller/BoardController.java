package com.danmall.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.danmall.domain.BoardVO;
import com.danmall.domain.MemberVO;
import com.danmall.dto.BoardDTO;
import com.danmall.dto.BoardListDTO;
import com.danmall.dto.Criteria;
import com.danmall.dto.PageDTO;
import com.danmall.service.BoardService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
public class BoardController {
	
	@Setter(onMethod_ = @Autowired)
	private BoardService boardService;
	
	// 상품문의 조회(페이징기능포함)_제품별
	@GetMapping(value = "/pages/{pdt_num}/{page}",
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	private ResponseEntity<BoardDTO> getBoardListPage(@PathVariable("pdt_num") int pdt_num, @PathVariable("page") int page){
		
		ResponseEntity<BoardDTO> entity = null;
		
		Criteria cri = new Criteria(page, 5);
		
		log.info("상품번호: " + pdt_num);
		log.info("cri: " + cri);
		
		try {
			entity = new ResponseEntity<BoardDTO>(boardService.board_list(cri, pdt_num), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<BoardDTO>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}

	// 상품문의 조회(페이징기능포함)_회원별
	@GetMapping("/board_list")
	public String board_list(HttpSession session, @ModelAttribute("cri") Criteria cri, Model model) throws Exception {
		
		String mem_id =  ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		
		cri.setAmount(10);
		
		model.addAttribute("boardList", boardService.board_list_my(cri, mem_id));
		
		int total = boardService.board_cnt_my(cri, mem_id);
		
		model.addAttribute("pageMaker", new PageDTO(cri, total));
		model.addAttribute("myActive", "board");
		
		return "/board/board_list";
	}
	
	// 상품문의 상세조회
	@ResponseBody
	@PostMapping(value = "/board_detail_list")
	public ResponseEntity<List<BoardListDTO>> board_detail_list(long brd_num) throws Exception {
		
		ResponseEntity<List<BoardListDTO>> entity = null;
		
		try {
			entity = new ResponseEntity<List<BoardListDTO>>(boardService.board_detail_list(brd_num), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
	
	// 상품문의 상세조회2
	@PostMapping(value = "/board_detail_list2")
	public ResponseEntity<BoardListDTO> board_detail_list2(long brd_num) throws Exception {
		
		ResponseEntity<BoardListDTO> entity = null;
		
		try {
			entity = new ResponseEntity<BoardListDTO>(boardService.board_detail_list2(brd_num), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
	
	// 상품등록
	@PostMapping("/board_register")
	@ResponseBody
	public ResponseEntity<String> board_register(BoardVO vo, HttpSession session) throws Exception{
		
		ResponseEntity<String> entity = null;
		
		String mem_id =  ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		vo.setMem_id(mem_id);
		
		try {
			boardService.board_register(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	// 상품문의 수정
	@PostMapping("/board_modify")
	@ResponseBody
	public ResponseEntity<String> board_modify(BoardVO vo) throws Exception{
		ResponseEntity<String> entity = null;
		
		log.info(vo);
		
		try {
			boardService.board_modify(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	// 상품문의 삭제
	@PostMapping("/board_delete")
	@ResponseBody
	public ResponseEntity<String> board_delete(long brd_num) throws Exception{
		ResponseEntity<String> entity = null;
		
		log.info(brd_num);
		
		try {
			boardService.board_delete(brd_num);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}

}
