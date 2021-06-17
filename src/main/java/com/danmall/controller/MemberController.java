package com.danmall.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danmall.domain.MemberVO;
import com.danmall.dto.EmailDTO;
import com.danmall.dto.LoginDTO;
import com.danmall.service.EmailService;
import com.danmall.service.MemberService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/member/*") // jsp파일 폴더명
public class MemberController {
	
	@Setter(onMethod_ = @Autowired)
	private MemberService service;
	
	@Setter(onMethod_ = @Autowired)
	private EmailService mailService;
	
	@Inject
	private BCryptPasswordEncoder cryPassEnc;
	
	
	private boolean isAuthCheck = false;

	
	/* 로그인(GET)  /member/login */
	@GetMapping("/login")
	public void  login(Model model) {
		log.info("login");
		model.addAttribute("navActive", "login");
	}
	
	/* 로그인(POST)  /member/loginPost */
	@PostMapping("/loginPost")
	public void loginPost(LoginDTO dto, RedirectAttributes rttr, HttpSession session, Model model) throws Exception{
		
		MemberVO vo = service.login_ok(dto);
		
		if(vo == null) return;
		
		// result : session을 이용해 로그인시 어떤 것이 틀린지 알려주는 값
		String result = "loginIDFail";
		
		if(vo != null) {
			
			if(cryPassEnc.matches(dto.getMem_pw(), vo.getMem_pw())) { //vo.getMem_pw().equals(dto.getMem_pw())
//				vo.setMem_pw("");
//				session.setAttribute("loginStatus", vo); // 세션정보로 인증상태를 저장
				
				// 인터셉터에서 참조할 모델작업
				model.addAttribute("memberVO", vo);
				
				result = "loginSuccess";
			} else {
				result = "loginPWFail";
				return;
			}
		}
		
		rttr.addFlashAttribute("status", result); // 회원테이블에 해당되는 정보가 jsp페이지에서 필요로 할 때
		
	}
	
	/* 로그아웃 기능 : 메인페이지("/") */
	@GetMapping("/logout")
	public String logout(HttpSession session, RedirectAttributes rttr, Model model) {
		
		log.info("logout");
		
		session.invalidate(); // 사용자의 기능 소멸. 로그아웃기능
		
		String result = "logout";
		rttr.addFlashAttribute("status", result); // 메세지를 주거나 주소를 이동할 때 파라미터 값을 전달할 때 사용
		
		model.addAttribute("navActive", "logout");
		
		return "redirect:/";
	}
	
	/* 회원가입(GET) 폼 */
	@GetMapping("/join")
	public void  join(Model model) {
		log.info("join");
		model.addAttribute("navActive", "join");
	}
	
	/* 회원가입(POST) */
	@PostMapping("/join")
	public String joinPOST(MemberVO vo, RedirectAttributes rttr) throws Exception {
		
		log.info(vo);
		
		vo.setMem_pw(cryPassEnc.encode(vo.getMem_pw()));
		
		// 인증확인 체크작업
		if(isAuthCheck == false) return "";
		
		String result = "";
		service.join(vo);
		
		result = "insertSuccess";
		
		rttr.addFlashAttribute("status", result); // jsp 페이지에서 쓰기위한 정보
		
		return "redirect:/";    // 루트 주소로 이동. HomeController 에 있음. home.jsp에서 "msg" 키사용
	}
	
	/* 아이디 중복체크(ajax요청)   /member/checkIdDuplicate  */
	@ResponseBody
	@PostMapping("/checkIdDuplicate")
	public ResponseEntity<String> checkIdDuplicate(@RequestParam("mem_id") String mem_id) throws Exception {
		
		log.info("id check");
		
		ResponseEntity<String> entity = null;
		try {
			int count = service.checkIdDuplicate(mem_id);
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
	
	/* 
	 * 이메일 인증 코드 확인   // /member/checkAuthcode
	 * - 입력된 인증 코드와 세션에 저장해 두었던 인증 코드가 일치하는지 확인
	 */
	@ResponseBody
	@PostMapping(value = "/checkAuthcode")
	public ResponseEntity<String> checkAuthcode(@RequestParam("code") String code, HttpSession session){
		
		ResponseEntity<String> entity = null;
		
		try {
			// 사용자가 메일로 받은 인증코드와 세션으로 저장해둔 인증코드를 비교하는 구문
			if(code.equals(session.getAttribute("authcode"))) {
				// 인증코드 일치
				entity= new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
				
				session.removeAttribute("authcode"); // 다 쓰고나서 제거
				
				isAuthCheck = true; // 회원가입시 인증체크 상태를 확인용도로 사용.
			} else {
				// 인증코드 불일치
				entity= new ResponseEntity<String>("FAIL", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			entity= new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	// 회원수정(GET) 폼 : db에서 회원정보를 가져와서 출력
	@GetMapping("/modify")
	public void reg_edit(HttpSession session, Model model) throws Exception{ //데이터베이스와 관계된 메서드는 예외가 발생할 수 있어 throws(예외전가)를 사용 
		
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id(); // MemberVO 성격으로 인식하게 만드는 법
		
		/*
		MemberVO vo = service.memeber_info(mem_id);
		model.addAttribute("vo", vo);
		*/
		
//		model.addAttribute(service.memeber_info(mem_id)); // jsp에 전달되는 데이터의 키? memberVO(리턴타입 : MemberVO가 memberVO로)
		model.addAttribute("vo", service.member_info(mem_id));
		model.addAttribute("myActive", "modify");
	}
	
	// 회원수정(POST)
	@PostMapping("/modify")
	public String modifyPOST(MemberVO vo, RedirectAttributes rttr, HttpSession session) throws Exception {
		
		String result = "";
		
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id(); // MemberVO 성격으로 인식하게 만드는 법
		
		MemberVO vo2 = service.member_info(mem_id);
		
		if(cryPassEnc.matches(vo.getMem_pw(), vo2.getMem_pw())) { //vo.getMem_pw().equals(dto.getMem_pw())
			if(service.modifyPOST(vo) == true) {
				result = "modifySuccess";
			}else {
				result = "modifyFail";
			}
		}else {
			rttr.addFlashAttribute("status", "pwFail");
			return "redirect:/member/modify";
		}
		
//		service.modifyPOST(vo);
//		result = "modifySuccess";
		
		// 로그인시 세션에서 아이디를 참고
		//String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		vo.setMem_id(mem_id);
		
		// MemberVO vo에 회원수정폼에서 아이디 파리미터가 존재한 경우
		
		rttr.addFlashAttribute("status", result);
		
		return "redirect:/";
	}
	
	
	// 아이디 찾기 폼
	@GetMapping("/find_id")
	public void  find_id() {
		
		log.info("find_id");
		
	}
	
	// 아이디 찾기 기능(ajax적용) : 화면출력
	@PostMapping("/find_id") // url은 같아도 요청방식이 다르기 때문에 괜찮음.
	public ResponseEntity<String> find_id(@RequestParam("mem_name") String mem_name, @RequestParam("mem_email") String mem_email) throws Exception {
		
		log.info("이름? " + mem_name);
		log.info("이메일? " + mem_email);
		
		ResponseEntity<String> entity = null;
		
		String mem_id = service.find_id(mem_name, mem_email);
		
		if(mem_id != null) {
			entity = new ResponseEntity<String>(mem_id, HttpStatus.OK);
		} else {
			entity = new ResponseEntity<String>(HttpStatus.OK);
		}
		
		return entity;
	}
	
	
	// 비밀번호 찾기 폼
	@GetMapping("/find_pw")
	public void  find_pw() {
		
		log.info("find_pw");
		
	}
	
	// 비밀번호 찾기 기능(ajax적용)
	@ResponseBody
	@PostMapping("/find_pw") // url은 같아도 요청방식이 다르기 때문에 괜찮음.
	public ResponseEntity<String> find_pw(@RequestParam("mem_id") String mem_id, @RequestParam("mem_name") String mem_name, EmailDTO dto) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		LoginDTO loginDto = new LoginDTO();
		loginDto.setMem_id(mem_id);
		
		MemberVO vo = service.find_pw(mem_id, mem_name);
		
		// 임시비밀번호 설정
		String tmp_pw= "";
		
		if(vo != null) {
			
			String email = vo.getMem_email();
			
			// 임시비밀번호 작업
			for(int i=0; i<12; i++) {
				tmp_pw += (char)(Math.random()*10 + 97);
			}
			
			// 비밀번호 암호화 작업
			String enc_pw = cryPassEnc.encode(tmp_pw);
			loginDto.setMem_pw(enc_pw);
			
			// 암호화된 비밀번호 db에 저장
			service.tmp_pw(loginDto);
			
			// 메일 발송 작업 (기본내용 수정)
			dto.setReceiveMail(email);
			dto.setSubject("DAN_MALL : 요청하신 비밀번호입니다.");
			dto.setMessage(mem_name + " 님의 임시 비밀번호입니다.\n\n로그인 후 비밀번호를 변경해 주세요. \n\n비밀번호 :");
			
			mailService.sendMail(dto, tmp_pw);
			
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
			
		}else {
			entity = new ResponseEntity<String>(HttpStatus.OK);
		}
		
		return entity;
	}
	
	// 회원 탈퇴
	@GetMapping("/delete")
	public void delete(Model model){
		model.addAttribute("myActive", "delete");
	}
	
	@PostMapping("/deletePost")
	public String deletePost(HttpSession session, RedirectAttributes rttr) throws Exception {
		
		log.info("===== delete User");
		
		// 로그인 페이지에서 session에 "loginStatus"로 vo값을 저장해 뒀음.
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id(); // MemberVO 성격으로 인식하게 만드는 법
		
		service.delete_member(mem_id);
		
		session.invalidate(); // 회원 탈퇴시 세션 소멸작업
		rttr.addFlashAttribute("status","deleteMember");
		
		return "redirect:/";
	}
	
	// 마이페이지
	@GetMapping("/mypage")
	public void mypage(Model model) {
		model.addAttribute("navActive", "mypage");
	}
	
	// 비밀번호 수정(폼)
	@GetMapping("/pwUpdate")
	public void pwUpdate(Model model) {
		model.addAttribute("myActive", "pwUpdate");
	}
	
	// 비밀번호 수정
	@PostMapping("/pwUpdatePost")
	public String pwUpdatePost(LoginDTO dto, RedirectAttributes rttr, HttpSession session) throws Exception { //@RequestParam("mem_pw") String mem_pw
		
		String newPW = cryPassEnc.encode(dto.getMem_pw());
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		
		dto.setMem_id(mem_id);
		dto.setMem_pw(newPW);
		service.pwUpdate(dto);
		
		MemberVO memberVO = (MemberVO) session.getAttribute("loginStatus");
		memberVO.setMem_pw(dto.getMem_pw());
		
		rttr.addFlashAttribute("status", "PWUPDATE_SUCCESS");
		
		return "redirect:/member/pwUpdate";
	}
	
	/* 비밀번호 변경 확인(ajax) */
	@ResponseBody
	@PostMapping("/checkPwAjax")
	public ResponseEntity<String> checkPwAjax(@RequestParam("mem_pw") String mem_pw, HttpSession session) {
		
		ResponseEntity<String> entity = null;
		MemberVO vo = (MemberVO) session.getAttribute("loginStatus");
		log.info("mem_pw: " + mem_pw);
		log.info("vo: " + vo);
		
	
		if(cryPassEnc.matches(mem_pw, vo.getMem_pw())) {
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
			
		} else {
			entity = new ResponseEntity<String>("FAIL", HttpStatus.OK);
		}
		
		return entity;
	}
	
	
	
	
	
}
