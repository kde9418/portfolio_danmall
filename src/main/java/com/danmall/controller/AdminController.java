package com.danmall.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danmall.domain.AdminVO;
import com.danmall.service.AdminService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/admin/*")
public class AdminController {
	
	@Setter(onMethod_ = @Autowired)
	private AdminService adminService;
	
	@Inject
	private BCryptPasswordEncoder cryPassEnc;
	
	
	@GetMapping("")
	public String admin_main() {
		
		log.info("=====admin_main");
		
		return "/admin/admin_main";
	}
	
	/* 로그인(GET)  /admin/admin_login */
	@GetMapping("/admin_login")
	public void admin_login() {
		
		log.info("admin_login");
	}
	
	/* 로그인(POST)  /admin/loginPost */
	@PostMapping("/adminPost")
	public String adminPost(AdminVO vo, RedirectAttributes rttr, HttpSession session) throws Exception {
		
		log.info("adminPost");
		
		AdminVO adVO = null;
		adVO = adminService.login(vo);
		
		String result = "";
		String url = "";
		
		
		if(adVO != null) {
			if(cryPassEnc.matches(vo.getAdmin_pw(), adVO.getAdmin_pw())) {
				session.setAttribute("adLoginStatus", adVO);
				result = "loginSuccess";
				url = "redirect:/admin/";
			}else {
				result = "loginFail";
				url = "redirect:/admin/admin_login";
			}
		} 
		
		rttr.addFlashAttribute("status", result);
		
		return url;
	}

	/* 로그아웃 : 메인페이지("/") */
	@PostMapping("/admin_logout")
	public String admin_logout(HttpSession session, RedirectAttributes rttr) {
		
		log.info("admin_logout");
		
		session.invalidate();
		
		rttr.addFlashAttribute("status", "logout");
		
		return "redirect:/admin/";
	}
	
	/* 계정추가(GET)  /admin/admin_join */
	@GetMapping("/admin_join")
	public void join() {
		log.info("admin_join");
	}
	
	/* 계정추가(POST)  /admin/join */
	@PostMapping("/admin_joinPOST")
	public String joinPOST(AdminVO vo, RedirectAttributes rttr) throws Exception {
		log.info("admin_joinPOST");
		
		vo.setAdmin_pw(cryPassEnc.encode(vo.getAdmin_pw()));
		
		adminService.join(vo);
		
		String result = "";
		result = "joinSuccess";
		rttr.addFlashAttribute("status", result);
		
		return "redirect:/admin/";
	}
	
	/* 아이디 중복체크(ajax요청)   /admin/checkIdDuplicate  */
	@ResponseBody
	@PostMapping("/checkAdminIdDuplicate")
	public ResponseEntity<String> checkAdminIdDuplicate(@RequestParam("admin_id") String admin_id) throws Exception {
		
		log.info("id check");
		
		ResponseEntity<String> entity = null;
		try {
			int count = adminService.checkAdminIdDuplicate(admin_id);
			// count 가 0이면 아이디 사용가능, 1 이면 사용 불가능.

			if(count != 0) {
				// 아이디가 존재해서 사용이 불가능.
				entity = new ResponseEntity<String>("FAIL", HttpStatus.OK);
			} else {
				// 사용가능한 아이디
				entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST); // 요청이 문제가 있다.
		}
		
		return entity;
	}
}
