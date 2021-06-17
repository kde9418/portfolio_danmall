package com.danmall.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OrderVO {
	
	/*odr_code, mem_id, odr_name, odr_zipcode, odr_addr, odr_addr_d, odr_phone, odr_total_price, odr_date, ord_state, ord_count, ord_updatedate, state_updatedate*/
	
	private long odr_code;  // 시퀀스
	private String mem_id;  // session정보 참조
	private String odr_name;
	private String odr_zipcode;
	private String odr_addr;
	private String odr_addr_d;
	private String odr_phone;
	private int odr_total_price;
	private Date odr_date;  // DB에서 default 처리
	private int ord_state;
	private int ord_count;
	private Date ord_updatedate;
	private Date state_updatedate;
	
}
