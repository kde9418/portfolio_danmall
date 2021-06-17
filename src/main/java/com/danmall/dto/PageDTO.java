package com.danmall.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 리스트페이지에 페이징번호 출력작업.. [이전]1 2 3 4 5 6...[다음]
@Getter
@Setter
@ToString
public class PageDTO {

	private int startPage;
	private int endPage;
	private boolean prev, next;
	
	private int pageSize = 10; // 페이지 번호 개수
	
	private int total; // 테이블에 모든 데이터 개수
	private Criteria cri; // 현재페이지번호(pageNum), 페이지마다 출력될 게시물 개수(amount)
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		// [이전] 1 2 3 4 5 6 7 8 9 10 [다음]
		this.endPage = (int)(Math.ceil(cri.getPageNum() / (pageSize * 1.0))) * pageSize;
		this.startPage = this.endPage - (pageSize - 1);
		
		int realEnd = (int)(Math.ceil((total * 1.0) / cri.getAmount()));
		
		if(realEnd <= this.endPage) this.endPage = realEnd;
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}
}
