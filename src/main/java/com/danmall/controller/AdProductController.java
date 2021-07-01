package com.danmall.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danmall.domain.CategoryVO;
import com.danmall.domain.ProductVO;
import com.danmall.dto.Criteria;
import com.danmall.dto.PageDTO;
import com.danmall.service.AdProductService;
import com.danmall.util.FileUploadUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/admin/product/*")
public class AdProductController {
	
	@Setter(onMethod_ = @Autowired)
	private AdProductService service;
	
	// 웹 프로젝트 영역 외부에 파일을 저장할 때 사용할 경로
	@Resource(name = "uploadPath") 
	private String uploadPath;
	
	//상품등록폼 : 1차카테고리 정보 출력
	@GetMapping("/product_insert")
	public void product_insert(Model model) throws Exception{
		
		log.info("product_insert");
		
		model.addAttribute("categoryList", service.getCategoryList());
		
	}
	
	// 2차카테고리 정보
	@ResponseBody // ajax 호출시 반드시 사용
	@GetMapping("/subCategoryList/{cg_code}")
	public ResponseEntity<List<CategoryVO>> subCategoryList(@PathVariable("cg_code") String cg_code) throws Exception{
		
		log.info("subCategoryList: " + cg_code);
		
		ResponseEntity<List<CategoryVO>> entity = null;
		
		try {
			entity = new ResponseEntity<List<CategoryVO>>(service.getSubCategoryList(cg_code), HttpStatus.OK);
		}catch(Exception e) {
			entity = new ResponseEntity<List<CategoryVO>>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	// 상품등록
	// vo 안에 파일첨부에 관한게 다 들어있다.
	@PostMapping("/insert")
	public String product_insert(ProductVO vo, RedirectAttributes rttr) throws Exception{
		
		log.info("insert: " + vo); // 상품이미지명 없는 상태
		
		//1) 파일업로드작업
		//이미지 파일명을 리턴 받아와 vo에 set 해준다.
		vo.setPdt_img(FileUploadUtils.uploadFile(uploadPath, vo.getFile1().getOriginalFilename(), vo.getFile1()));
		
		//2)상품정보DB 삽입작업
		service.product_insert(vo); // 상품이미지명이 있는 상태
		
		return "redirect:/admin/product/pro_list";
	}
	
	// CKEditor 4.0 업로드처리(상품상세설명에서 사용하는 이미지에 대한 업로드)
	@PostMapping("/imgUpload")
	public void imgUpload(HttpServletRequest req, HttpServletResponse res, MultipartFile upload) {
		
		// 1) CKEditor를 통하여 업로드된 작업처리
		// 2) 업로드처리를 한 후, 파일정보를 CKEditor에게 돌려줌.
		
		// 출력스트림을 이용하여, 파일업로드 작업처리
		// 기능핵심 : 클래스의 메서드 학습
		
		log.info("imgUpload execute");
		
		OutputStream out = null;  // 클라이언트의 첨부된 파일을 서버의 upload폴더로 복사작업을 위한 출력스트림
		PrintWriter printWriter = null;  // 서버측의 내용을 클라이언트에게 보낼 때 사용하는 목적
		
		// 설정 : 서버에서 클라이언트로 보내는 데이터 설정
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html; charset=utf-8");
		
		try {
			String fileName = upload.getOriginalFilename();
			byte[] bytes = upload.getBytes(); // 첨부된 파일을 가리키는 원소스(첨부된 파일을 byte로 읽어낸 것)
			
			// 어떤 was를 쓰는지에 따라 설정이 달라질 수 있다.
			// was(tomcat 9.0)에서 관리하는 프로젝트의 루트경로를 참고해서,  << 실제 업로드 경로를 반환 >>
			// 현재 프로젝트의 서버 물리적 경로
			
			String uploadPath = req.getSession().getServletContext().getRealPath("/") + "resources\\upload\\";
//			String uploadPath = "/usr/local/tomcat/tomcat-9/webapps/upload/";
			
			
			if(new File(uploadPath).exists()) new File(uploadPath).mkdir();
			
			uploadPath = uploadPath + fileName;
			
			// 서버측의 업로드되는 실지적인 경로를 포함한 파일명 정보
			log.info("===== ckeditor 파일업로드 경로: " + uploadPath);
			
			// 출력 스트림 생성
			out = new FileOutputStream(new File(uploadPath));
			// 파일 쓰기. bytes : ckeditor의 첨부된 파일 바이트배열
			out.write(bytes);
			
			// CKEditor에게 업로드한 파일정보를 돌려줘야 한다.(ckeditor 사용법)
			
			printWriter = res.getWriter(); // res.getWriter()를 통해 printWriter 참조. Response가 주체
			// 클라이언트가 직접 이미지를 요청하는 경로
			String fileUrl = "/upload/" + fileName; // 클라이언트 관점에서는 슬래시(/)를 써줘야 한다.
			//String fileUrl = "/ckeditor/upload/" + fileName; // 클라이언트 관점에서는 슬래시(/)를 써줘야 한다.
			
			// ckeditor 4에서 제공하는 형식(상세버전에 따라 문법을 확인하여 작업해야 함)
			// {"filename":"abc.gif", "uploaded":1, "url":"/upload/abc.gif "} json포맷
			printWriter.println("{\"filename\":\"" + fileName + "\", \"uploaded\":1,\"url\":\"" + fileUrl + "\"}");
			printWriter.flush(); // 전송 (return과 같은 역할: 클라이언트로 보냄)
			
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(printWriter != null) {
				printWriter.close();
			}
		}
	}  // end imgUpload
	
	// 상품 리스트
	@GetMapping("/pro_list")
	public String product_list(@ModelAttribute("cri") Criteria cri, Model model) throws Exception{
		
		log.info("product_list");
		
		model.addAttribute("pro_list", service.product_list(cri));
		
		int totalCount = service.getTotalCountProduct(cri);
		
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
		
		return "/admin/product/product_list";
		
	}
	
	// 상품이미지 뷰
	@ResponseBody
	@GetMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception{
		
		log.info("fileName: "+fileName);
		
		return FileUploadUtils.getFile(uploadPath, fileName);
	}
	
	// 상품수정 폼
	@GetMapping("/modify")
	public String modify(@RequestParam("pdt_num") Long pdt_num, @ModelAttribute("cri") Criteria cri, Model model) throws Exception {
		
		log.info("modify: " + pdt_num);
		
		// 1차 카테고리
		model.addAttribute("categoryList", service.getCategoryList());
		
		ProductVO vo = service.product_modify(pdt_num);
		
		// 1차카테고리
		String cg_code_prt = vo.getCg_code_prt();
		
		// 상품정보(1차카테고리, 2차카테고리)
		model.addAttribute("productVO", vo);
		
		// 2차카테고리
		model.addAttribute("subCategoryList", service.getSubCategoryList(cg_code_prt));
		
		
		return "/admin/product/product_modify";
		
	}
	
	// 상품수정 하기
	@PostMapping("/modify")
	public String modify(ProductVO vo, Criteria cri, RedirectAttributes rttr) throws Exception {
		
		log.info("modify: " + vo); // 상품이미지명 없는 상태
		
		//1) 파일업로드작업(상품이미지 변경시)
		if(vo.getFile1() != null && vo.getFile1().getSize() > 0) {

			// 기존이미지파일 삭제
			String oldImageFileName = vo.getPdt_img();
			
			FileUploadUtils.deleteFile(uploadPath, oldImageFileName);
			
			// 변경이미지파일 정보
			vo.setPdt_img(FileUploadUtils.uploadFile(uploadPath, vo.getFile1().getOriginalFilename(), vo.getFile1()));
		}
		
		//2)상품정보DB 삽입작업
		service.product_modify(vo); // 상품이미지명이 있는 상태
		
		log.info("Criteria: " + cri);
		
		// 리스트 원래상태정보(Criteria cri -> 페이징, 검색포함)
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/admin/product/pro_list";
	}
	
	// 상품 삭제
	@GetMapping("/delete")
	public String delete(long pdt_num, String pdt_img, Criteria cri, RedirectAttributes rttr) throws Exception {
		
		log.info("delete: " + pdt_num); // 상품이미지명 없는 상태
		
		// DB에서 상품정보 삭제
		service.product_delete(pdt_num);
		
		// 상품이미지 삭제
		
		FileUploadUtils.deleteFile(uploadPath, pdt_img);

		
		// 리스트 원래상태정보(Criteria cri -> 페이징, 검색포함)
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/admin/product/pro_list";
	}
	
	// 상품 구매여부 수정
	@ResponseBody
	@PostMapping("/buy_modify")
	public ResponseEntity<String> buy_modify(@RequestParam("pdt_num") Long pdt_num, @RequestParam("pdt_buy") String pdt_buy) throws Exception {
		
		log.info("buy_modify: " + pdt_num + " + " + pdt_buy);
		int result = service.product_buy_modify(pdt_num, pdt_buy);
		
		ResponseEntity<String> entity = null;
		
		if(result == 1) {
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} else {
			entity = new ResponseEntity<String>(HttpStatus.OK);
		}
		
		return entity;
	}
	
	
	
	
	
	
	
}
