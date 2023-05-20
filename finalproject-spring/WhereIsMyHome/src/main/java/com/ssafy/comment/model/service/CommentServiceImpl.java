package com.ssafy.comment.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.comment.model.CommentDto;
import com.ssafy.comment.model.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentMapper commentMapper;	

	@Autowired
	public CommentServiceImpl(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}
	

	@Override
	public void registComment(CommentDto comments) throws Exception {
		commentMapper.registComment(comments);
	}

	@Override
	public void deleteComment(int commentIdx) throws Exception {
		commentMapper.deleteComment(commentIdx);
	}


	@Override
	public List<CommentDto> getList(Map<String, Object> maps) throws Exception {
		return commentMapper.getList(maps);
	}
	
	@Override
	public List<CommentDto> getUserComment(Map<String, Object> maps) throws SQLException {
		return commentMapper.getUserComment(maps);
	}

}
