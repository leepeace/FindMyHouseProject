package com.ssafy.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageDto {
	
	private int total;//전체 데이터 수
	private int nowPageNum = 1;//현재 페이지 번호
	private int amount = 10;//한 페이지 당 데이터 수
	
	private int startPage;//시작 번호
	private int endPage;//끝 번호
	private int lastPage;//마지막 번호
	
	private boolean prev, next;
	
	
	
}
