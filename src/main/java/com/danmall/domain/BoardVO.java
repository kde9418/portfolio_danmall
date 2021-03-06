package com.danmall.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardVO {

	//brd_num, brd_cg, mem_id, pdt_num, brd_title, brd_content, brd_date_reg
	private long brd_num;
	private String brd_cg;
	private String mem_id;
	private long pdt_num;
	private String brd_title;
	private String brd_content;
	private Date brd_date_reg;
}
