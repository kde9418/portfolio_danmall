package com.danmall.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.danmall.domain.CategoryVO;
import com.danmall.dto.Criteria;
import com.danmall.dto.ProductDTO;
import com.danmall.service.UserProductService;
import com.danmall.util.FileUploadUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/product/*")
public class UserProductController {
	
	@Resource(name = "uploadPath")
	private String uploadPath;
	
	// URL Mapping주소
	// 메서드 : 카테고리정보를 불러와서 Model 추가한 후 jsp 사용
	
	@Setter(onMethod_ = @Autowired)
	private UserProductService userProductService;
	
	@ResponseBody
	@GetMapping("/subCategoryList/{cg_code}")
	public ResponseEntity<List<CategoryVO>> subCategoryList(@PathVariable("cg_code") String cg_code) throws Exception {
		
		log.info("subCategoryList: " + cg_code);
		
		ResponseEntity<List<CategoryVO>> entity = null;
		
		try {
			entity = new ResponseEntity<List<CategoryVO>>(userProductService.getSubCategoryList(cg_code), HttpStatus.OK);
		}catch(Exception e) {
			entity = new ResponseEntity<List<CategoryVO>>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	// 2차 카테고리에 의한 상품목록 정보
	@GetMapping("/product_list")
	public String productListBySubCate(@ModelAttribute Criteria cri, String cg_code, Model model) throws Exception {
		
		log.info("productListBySubCate: " + cg_code);
		
		log.info("Criteria: " + cri);
		
		cri.setAmount(9);
		
		model.addAttribute("productDTOList", userProductService.getProductListBySubCate(cri, cg_code));
		
		return "/product/product_list";
	}
	
	
	// 상품이미지 뷰
	@ResponseBody
	@GetMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception{
		
		log.info("fileName: "+fileName);
		
		return FileUploadUtils.getFile(uploadPath, fileName);
	}
	
	// 상품 상세설명(상품조회)
	@GetMapping("/product_read")
	public void product_read(@RequestParam("pdt_num") long pdt_num, Model model) throws Exception {
		
		log.info("product_read: "+ pdt_num);
		
		ProductDTO dto = userProductService.getProductByNum(pdt_num);
		dto.setPdt_img(FileUploadUtils.thumbToOriginName(dto.getPdt_img()));
		
		log.info("원본파일명: " + dto.getPdt_img());
		// 기본이미지를 설정작업
		
		model.addAttribute("productDTO", dto);
		
	}
}
