package com.danmall.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardListDTO {

	//brd_num, brd_cg, mem_id, pdt_num, pdt_name, brd_title, brd_content, brd_date_reg
	private long brd_num;
	private String brd_cg;
	private String mem_id;
	private long pdt_num;
	private String pdt_name;
	private String brd_title;
	private String brd_content;
	private Date brd_date_reg;
}
