package com.danmall.dto;

import java.util.List;

import com.danmall.domain.ReviewVO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // 2개의 필드에 대한 생서앚메서드 생성
public class ReviewPageDTO {
	
	private int reviewCnt; // 상품후기 개수
	private List<ReviewVO> list; // 상품후기 목록
	
	
}
