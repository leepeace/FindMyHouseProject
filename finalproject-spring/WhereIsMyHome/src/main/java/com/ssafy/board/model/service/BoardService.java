package com.ssafy.board.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.board.BoardDto;
import com.ssafy.util.PageDto;

public interface BoardService {

	int writeArticle(BoardDto boardDto) throws Exception;
	List<BoardDto> listArticle(Map<String, Object> maps) throws Exception;
	BoardDto getArticle(int articleNo) throws Exception;
	List<BoardDto> searchArticle(Map<String, String> map) throws Exception;
	void updateHit(int articleNo) throws Exception;
	void modifyArticle(BoardDto boardDto) throws Exception;
	void deleteArticle(int articleNo) throws Exception;
	int totalArticleCount() throws Exception;
	
	//paging 처리
	PageDto paging(int pageNo) throws Exception;
}
