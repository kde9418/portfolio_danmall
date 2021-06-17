package com.danmall.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Criteria {
	
	/* 페이징 */
	private int pageNum;  // 페이지번호. 1 2 3 4 5
	private int amount;  // 페이지마다 출력될 게시물 개수 (예 10개)
	
	/* 검색 */
	// 검색종류
	// 검색방법?
	// 1) 단일 항목검색 제목(T), 내용(C), 작성자(W)
	// 2) 다중 항목검색 제목(T), 내용(C), 작성자(W), 제목+내용(TC), 제목+작성자(TW), 제목+내용+작성자(TCW)
	private String type;
	private String keyword; // 검색어
	
	public Criteria() {
		this(1,10);
	}

	public Criteria(int pageNum, int amount) {
//		super(); 컴파일 과정에서 자동생성
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	/*
	public String getType() {
		return type;
	}
	*/
	
	public String[] getTypeArr() {
//		System.out.println("called getTypeArr");
		
		return type == null? new String[] {} : type.split("");
	}
}
