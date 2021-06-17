package com.danmall.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.log4j.Log4j;

@Log4j
public class SampleInterceptor extends HandlerInterceptorAdapter {
	
	// 인터셉터 기능을 갖는 클래스를 만들려면, HandlerInterceptorAdapter 추상클래스를 상속받아야한다.
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		log.info("preHandle.....");
		
		/*
		 * return false; -> 진행될 경우에는 컨트롤러로 제어가 넘어가지 않음.(preHandle만 작동하고, URL Mapping 주소를 가지고 있는 컨트롤러 주소로 넘어가지 못한다.)
		 * return true; -> 진행될 경우에는 컨트롤러로 제어가 넘어 갔다가 다시 postHandle로 돌아옴.
		 */
		
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		log.info("postHandle.....");
		
		super.postHandle(request, response, handler, modelAndView);
	}
	
	
	

}
