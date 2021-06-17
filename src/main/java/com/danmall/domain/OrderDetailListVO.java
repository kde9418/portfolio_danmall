package com.danmall.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDetailListVO {
	
	// od.odr_code, od.pdt_num, od.odr_amount, od.odr_price, p.pdt_name, p.pdt_img
	private long odr_code;
	private long pdt_num;
	private int odr_amount;
	private int odr_price;
	
	private String pdt_name;
	private String pdt_img;
	
	
	/*번호, 상품명, 상품이미지, 수량, 상품가격, 소계, 총액
	 *od.odr_code, p.pdt_name, p.pdt_img, od.odr_amount, od.odr_price
	 * 
	private long odr_code;
	private String pdt_name;
	private String pdt_img;
	private int odr_amount;
	private int odr_price;
	private int sum;
	*/
}
