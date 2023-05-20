package com.ssafy.qna.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.qna.QnaDto;
import com.ssafy.qna.ReplyDto;
import com.ssafy.util.PageDto;

public interface QnaService {
	//crud
	int writeQna(QnaDto qnaDto) throws Exception;
	List<QnaDto> listQna(Map<String, Object> pagingMaps) throws Exception;
	void deleteQna(int qnaNo) throws Exception;
	void modifyQna(QnaDto qnaDto) throws Exception;

	int totalQnaCount() throws Exception;
	QnaDto getQna(int QnaNo) throws Exception;
	List<QnaDto> searchQna(Map<String, String> map) throws Exception;
	void updateHit(int articleNo) throws Exception;
	
	// reply mapper
	int writeReply(ReplyDto replyDto) throws Exception;
	List<ReplyDto> getReply(int ReplyNo) throws Exception;
	int modifyReply(ReplyDto replyDto) throws Exception;
	int deleteReply(int ReplyNo) throws Exception;
	
	//paging 처리
	PageDto paging(int pageNo) throws Exception;
}
