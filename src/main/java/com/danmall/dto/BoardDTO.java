package com.danmall.dto;

import java.util.List;

import com.danmall.domain.BoardVO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardDTO {
	
	private int boardCnt; // 상품문의 개수
	private List<BoardVO> list; // 상품문의 목록
	
	
}
