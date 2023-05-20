package com.ssafy.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.board.BoardDto;
import com.ssafy.board.model.mapper.BoardMapper;
//import com.ssafy.util.SizeConstant;
import com.ssafy.util.PageDto;

@Service
public class BoardServiceImpl implements BoardService {
	
	private BoardMapper boardMapper;
	
	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}

	@Override
	public int writeArticle(BoardDto boardDto) throws Exception {
		return boardMapper.writeArticle(boardDto);
	}

	@Override
	public List<BoardDto> listArticle(Map<String, Object> maps) throws Exception {
//		int pgno = Integer.parseInt(map.get("pgno"));
//		int spl = SizeConstant.SIZE_PER_LIST;
//		int start = (pgno - 1) * spl;
//		map.put("start", start + "");
		return boardMapper.listArticle(maps);
	}

	@Override
	public BoardDto getArticle(int articleNo) throws Exception {
		return boardMapper.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		boardMapper.updateHit(articleNo);
	}

	@Override
	public void modifyArticle(BoardDto boardDto) throws Exception {
		boardMapper.modifyArticle(boardDto);
	}

	@Override
	public void deleteArticle(int articleNo) throws Exception {
		boardMapper.deleteArticle(articleNo);
	}

	@Override
	public List<BoardDto> searchArticle(Map<String, String> map) throws Exception {
		return boardMapper.searchArticle(map);
	}

	@Override
	public int totalArticleCount() throws Exception {
		return boardMapper.totalArticleCount();
	}
	
	@Override
	public PageDto paging(int pageNo) throws Exception {
		PageDto pageDto = new PageDto();

		int total = totalArticleCount();//전체 데이터 수
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
