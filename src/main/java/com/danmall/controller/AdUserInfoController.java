package com.danmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danmall.service.MemberService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/admin/member/*")
public class AdUserInfoController {
	
	@Setter(onMethod_ = @Autowired)
	private MemberService memberService;

	@GetMapping("/userInfo_list")
	public void userInfo_list(Model model) throws Exception{
		log.info("userInfo_list");
		
		model.addAttribute("user_list", memberService.userinfo_list());
	}
}
