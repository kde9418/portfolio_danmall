package com.danmall.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartVOList {
	
	private long cart_code;
	private String pdt_img;
	private String pdt_name;
	private int cart_amount;
	private long pdt_price;
	private long pdt_consumer;
	private long pdt_num; // 내가 추가 21.04.15. 이미지 눌렀을 때 해당 제품페이지로 가기 위해서

}
