package com.danmall.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProductVO {
	
	private int pdt_num;
	private String cg_code;			// 2차 카테고리(하위)
	private String cg_code_prt;		// 1차 카테고리(상위)
	private String pdt_name;
	private int pdt_price;
	private int pdt_discount;
	private String pdt_company;
	private String pdt_detail;
	private String pdt_img;
	private int pdt_amount;
	private String pdt_buy;
	private Date pdt_date_sub;
	private Date pdt_date_up;
	private int ord_amount;
	private int rv_count;
	
	// 업로드 파일
	private MultipartFile file1; // product_insert.jsp name과 일치해야 함 <input type="file" id="file1" name="file1" class="form-control" />
}
