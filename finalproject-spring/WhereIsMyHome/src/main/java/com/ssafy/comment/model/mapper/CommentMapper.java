package com.ssafy.comment.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.comment.model.CommentDto;

@Mapper
public interface CommentMapper {
	
	//comment 등록
	void registComment(CommentDto comments) throws SQLException;
	//comment 삭제
	void deleteComment(int commentIdx) throws SQLException;
	
	//comment 목록
	List<CommentDto> getList(Map<String, Object> maps) throws SQLException;
	
	//나의 comment 목록 보여주기
	List<CommentDto> getUserComment(Map<String, Object> maps) throws SQLException;
}
