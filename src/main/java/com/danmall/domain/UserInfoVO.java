package com.danmall.domain;

import java.util.Date;

import lombok.Data;

@Data
public class UserInfoVO {
	
	private String	user_id;
	private String	user_name;
	private String	user_pw;
	private String	user_sex;
	private String	user_email;
	private String	user_zipcode;
	private String	user_addr;
	private String	user_addr_d;
	private String	user_phone;
	private String	user_accept_e;
	private String	user_accept_p;
	private int		user_point;
	private Date	user_date_sub;
	private Date	user_date_up;
	private Date	user_date_last;

}
