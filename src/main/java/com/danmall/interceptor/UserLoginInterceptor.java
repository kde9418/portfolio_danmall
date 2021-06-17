package com.danmall.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// 인증처리작업 : HttpSession 로그인 생성
// @Log4j : logger을 쉽게 사용할 수 있게 해준 것.
public class UserLoginInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginInterceptor.class);
	private static final String LOGIN = "loginStatus";
	
	// Object handler : URL Mapping 주소에 해당하는 메서드 자체를 가리킴
	// 빼면 부모에 있는 것이 작동
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		return true;
	}

	// 컨트롤러의 매핑주소- /member/loginPost(메서드호출) -> postHandle 메서드 -> 뷰(jsp)화면처리 작업이 진행됨.
	// modelAndView : (Model + View)
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		/*
		MemberVO vo = new MemberVO();
		modelAndView.setViewName("member"); // 뷰의 경로(뷰이름)
		modelAndView.addObject("MemberVO", vo);
		*/
		
		logger.info("메서드 작동");
		
		// 로그인 인증 처리하기 위한 세션객체 확보
		HttpSession session = request.getSession();
		
		// 로그인시 Model 정보를 참조하는 작업
		ModelMap modelMap = modelAndView.getModelMap();
		Object memberVO = modelMap.get("memberVO"); // memberVO : model.addAttribute("memberVO", vo); 에서 받은 model
		
		if(memberVO != null) {
			logger.info("로그인 성공");
			session.setAttribute(LOGIN, memberVO);
			
			Object targetUrl = session.getAttribute("targetUrl");
			
			response.sendRedirect(targetUrl != null ? (String)targetUrl : "/");
		}
		
//		response.sendRedirect("/"); 이 위치에서는 지원이 안됨. (loginPost.jsp가 필요 없다는 말이 되기 때문에)
//		ajax 요청시 인터셉터는 정상작동 되지 않음. View단 작업이 이루어 지지 않기 때문에(jsp를 사용하지 않기 때문에)
		
	}
}
