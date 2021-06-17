package com.danmall.domain;

import java.util.Date;

import lombok.Data;

@Data
public class MemberVO {
	
	private String	mem_id;
	private String	mem_name;
	private String	mem_pw;
	private String	mem_sex;
	private String	mem_email;
	private String	mem_zipcode;
	private String	mem_addr;
	private String	mem_addr_d;
	private String	mem_phone;
	private String	mem_accept_e;
	private String	mem_accept_p;
	private int		mem_point;
	private Date	mem_date_sub;
	private Date	mem_date_up;
	private Date	mem_date_last;

}
