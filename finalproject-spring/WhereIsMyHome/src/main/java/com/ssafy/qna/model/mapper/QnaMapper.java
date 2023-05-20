package com.ssafy.qna.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.BoardDto;
import com.ssafy.qna.QnaDto;
import com.ssafy.qna.ReplyDto;

@Mapper
public interface QnaMapper {
	//crud
	int writeQna(QnaDto qnaDto) throws SQLException;
	List<QnaDto> listQna(Map<String, Object> pagingMaps) throws SQLException;
	void modifyQna(QnaDto qnaDto) throws SQLException;
	void deleteQna(int qnaNo) throws SQLException;

	int totalQnaCount() throws SQLException;
	QnaDto getQna(int QnaNo) throws SQLException;
	List<QnaDto> searchQna(Map<String, String> map) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	
	// reply mapper
	int writeReply(ReplyDto replyDto) throws SQLException;
	List<ReplyDto> getReply(int ReplyNo) throws SQLException;
	int modifyReply(ReplyDto replyDto) throws SQLException;
	int deleteReply(int ReplyNo) throws SQLException;
	
	
}
