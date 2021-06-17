package com.danmall.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberOrderDTO {

	// mem_id, pdt_num, odr_code, pdt_name, pdt_img, odr_price, odr_amount, odr_date
	
	private String	mem_id;
	private long pdt_num;
	private long odr_code;
	private String pdt_name;
	private String pdt_img;
	private int odr_price;
	private int odr_amount;
	private Date odr_date;
	
}
