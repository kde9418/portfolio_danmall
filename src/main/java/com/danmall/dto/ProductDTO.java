package com.danmall.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDTO {

	private int pdt_num;
	private String cg_code;			// 2차 카테고리(하위)
	private String cg_code_prt;		// 1차 카테고리(상위)
	private String pdt_name;
	private int pdt_price;
	private int pdt_discount;
	private int pdt_consumer;
	private String pdt_company;
	private String pdt_detail;
	private String pdt_img;
	private int pdt_amount;
	private String pdt_buy;
}
