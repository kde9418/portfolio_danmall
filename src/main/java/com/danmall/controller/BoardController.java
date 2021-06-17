package com.danmall.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danmall.domain.BoardVO;
import com.danmall.domain.MemberVO;
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
	
	
	@GetMapping("/list")
	public void list(@ModelAttribute("cri") Criteria cri, Model model) throws Exception {
		log.info("list");
		
		model.addAttribute("board_list", cri);
		
		int totalCount = 0;
		
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
	}
	
	@PostMapping("inseret")
	public String insert(BoardVO vo, HttpSession session, RedirectAttributes rttr) throws Exception{
		
		String mem_id =  ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		vo.setMem_id(mem_id);
		
		String result = "";
		boardService.board_insert(vo);
		
		result = "insertSuccess";
		
		rttr.addFlashAttribute("status", result);
		
		return "redirect:/board/list";
	}

}
