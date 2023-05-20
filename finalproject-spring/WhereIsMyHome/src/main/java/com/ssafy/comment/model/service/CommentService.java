package com.ssafy.comment.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.comment.model.CommentDto;

public interface CommentService {
	
	//comment 등록
	void registComment(CommentDto comments) throws Exception;
	
	//comment 삭제
	void deleteComment(int commentIdx) throws Exception;
	
	//목록 출력
	List<CommentDto> getList(Map<String, Object> maps) throws Exception;
	
	//나의 comment 목록 보여주기
	List<CommentDto> getUserComment(Map<String, Object> maps) throws SQLException;
}
