package com.danmall.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReviewVO {
	
	//rv_num, mem_id, pdt_num, rv_content, rv_score, rv_date_reg
	
	private long rv_num;
	private String mem_id;
	private long pdt_num;
	private long odr_code;
	private String rv_content;
	private int rv_score;
	private Date rv_date_reg;

}
