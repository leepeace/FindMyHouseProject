package com.ssafy.qna.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.qna.QnaDto;
import com.ssafy.qna.ReplyDto;
import com.ssafy.qna.model.mapper.QnaMapper;
import com.ssafy.util.PageDto;

@Service
public class QnaServiceImpl implements QnaService {

	@Autowired
	private QnaMapper qnaMapper;
	
	@Override
	public int writeQna(QnaDto qnaDto) throws Exception {
		// TODO Auto-generated method stub
		return qnaMapper.writeQna(qnaDto);
	}

	@Override
	public List<QnaDto> listQna(Map<String, Object> pagingMaps) throws Exception {
		return qnaMapper.listQna(pagingMaps);
	}

	@Override
	public int totalQnaCount() throws Exception {
		return qnaMapper.totalQnaCount();
	}

	@Override
	public QnaDto getQna(int QnaNo) throws Exception {
		// TODO Auto-generated method stub
		return qnaMapper.getQna(QnaNo);
	}

	@Override
	public List<QnaDto> searchQna(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return qnaMapper.searchQna(map);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		// TODO Auto-generated method stub
		qnaMapper.updateHit(articleNo);
	}

	@Override
	public void modifyQna(QnaDto qnaDto) throws Exception {
		// TODO Auto-generated method stub
		qnaMapper.modifyQna(qnaDto);
	}

	@Override
	public void deleteQna(int qnaNo) throws Exception {
		// TODO Auto-generated method stub
		qnaMapper.deleteQna(qnaNo);
	}

	@Override
	public int writeReply(ReplyDto replyDto) throws Exception {
		// TODO Auto-generated method stub
		return qnaMapper.writeReply(replyDto);
	}

	@Override
	public List<ReplyDto> getReply(int ReplyNo) throws Exception {
		// TODO Auto-generated method stub
		return qnaMapper.getReply(ReplyNo);
	}

	@Override
	public int modifyReply(ReplyDto replyDto) throws Exception {
		// TODO Auto-generated method stub
		return qnaMapper.modifyReply(replyDto);
	}

	@Override
	public int deleteReply(int ReplyNo) throws Exception {
		// TODO Auto-generated method stub
		return qnaMapper.deleteReply(ReplyNo);
	}

	
	@Override
	public PageDto paging(int pageNo) throws Exception {
		PageDto pageDto = new PageDto();

		int total = totalQnaCount();//전체 데이터 수
		int amount = pageDto.getAmount();//보여줄 페이지 수
		int endPage = (int)(Math.ceil(pageNo / (double)amount)) * amount;
		int startPage = pageNo*amount - amount;//시작 페이지 번호
		int lastPage = (int)(Math.ceil((total * 1.0)/amount));//제일 마지막 페이지 번호 계산
		
		//계산된 마지막 페이지가 끝 페이지 번호보다 작은 경우
		if(lastPage < endPage) {
			endPage = lastPage;
		}
//		System.out.println(endPage);
//		System.out.println(startPage);
		boolean prev = startPage > 1;
		boolean next = endPage < lastPage;
		
		pageDto.setEndPage(endPage);
		pageDto.setLastPage(lastPage);
		pageDto.setNext(next);
		pageDto.setNowPageNum(pageNo);
		pageDto.setPrev(prev);
		pageDto.setStartPage(startPage);
		pageDto.setTotal(total);
		
		return pageDto;
	}
	
	
}
