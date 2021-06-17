package com.danmall.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminVO {

	private String	admin_id;
	private String	admin_pw;
	private String	admin_name;
	private Date admin_date_late;
	
}
