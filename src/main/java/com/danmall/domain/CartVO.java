package com.danmall.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class CartVO {
	
	private long cart_code;
	private long pdt_num;
	private String mem_id;
	private int cart_amount;
	
}
