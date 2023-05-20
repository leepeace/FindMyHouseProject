package com.ssafy.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.board.BoardDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.util.PageDto;

@Controller
@RequestMapping("/board")
@CrossOrigin("*")
public class BoardController extends HttpServlet {
	private final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	private BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		logger.info("boardController 생성자 호출");
		this.boardService = boardService;
	}
	
	@GetMapping("/list/{pageNum}")
	@ResponseBody
	public ResponseEntity<?> list(@PathVariable("pageNum") int pageNo) {
		logger.info("pageNo = " + pageNo);
		try {
			//페이징 처리
			PageDto pageDto = boardService.paging(pageNo);
			Map<String, Object> pageMaps = new HashMap<String, Object>();
			pageMaps.put("start", pageDto.getStartPage());
			pageMaps.put("amount", pageDto.getAmount());
			
			List<BoardDto> resultList = boardService.listArticle(pageMaps);
			System.out.println(resultList.size());
			if (resultList != null && !resultList.isEmpty()) {
				Map<String, Object> resultMaps = new HashMap<String, Object>();
				resultMaps.put("pageDto", pageDto);
				resultMaps.put("boardList", resultList);
				
				return new ResponseEntity<>(resultMaps, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		}catch (Exception e) {
			return exceptionHandling(e);
		}
		
	}

	@PostMapping("/")
	@ResponseBody
	private ResponseEntity<?> write(@RequestBody BoardDto boardDto) throws Exception {
		try {
			boardService.writeArticle(boardDto);
	
			//페이징 처리
			int pageNo=1;
			PageDto pageDto = boardService.paging(pageNo);
			Map<String, Object> pageMaps = new HashMap<String, Object>();
			pageMaps.put("start", pageDto.getStartPage());
			pageMaps.put("amount", pageDto.getAmount());
			
			List<BoardDto> resultList = boardService.listArticle(pageMaps);
			System.out.println(resultList.size());
			if (resultList != null && !resultList.isEmpty()) {
				Map<String, Object> resultMaps = new HashMap<String, Object>();
				resultMaps.put("pageDto", pageDto);
				resultMaps.put("boardList", resultList);
				
				return new ResponseEntity<>(resultMaps, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/view/{articleNo}")
	@ResponseBody
	private ResponseEntity<?> view(@PathVariable int articleNo) throws Exception {
		try {
			BoardDto boardDto = boardService.getArticle(articleNo);
			boardService.updateHit(articleNo);
			if (boardDto != null) {
				return new ResponseEntity<BoardDto>(boardDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	

	@PutMapping("/modify")
	@ResponseBody
	private ResponseEntity<?> modify(@RequestBody BoardDto boardDto) throws Exception {
		logger.info("board Info = " + boardDto);
		try {
			boardService.modifyArticle(boardDto);
			
			//페이징 처리
			int pageNo = 1;
			PageDto pageDto = boardService.paging(pageNo);//임의로 설정함(나중에 추가)
			Map<String, Object> pageMaps = new HashMap<String, Object>();
			pageMaps.put("start", pageDto.getStartPage());
			pageMaps.put("amount", pageDto.getAmount());
			
			List<BoardDto> resultList = boardService.listArticle(pageMaps);
			if (resultList != null && !resultList.isEmpty()) {
				Map<String, Object> resultMaps = new HashMap<String, Object>();
				resultMaps.put("pageDto", pageDto);
				resultMaps.put("boardList", resultList);
				
				return new ResponseEntity<>(resultMaps, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@DeleteMapping("/{articleNo}")
	@ResponseBody
	private ResponseEntity<?> delete(@PathVariable("articleNo") int articleNo) throws Exception {
		try {
			boardService.deleteArticle(articleNo);
			
			//페이징 처리
			int pageNo = 1;
			PageDto pageDto = boardService.paging(pageNo);//임의로 설정함(나중에 추가)
			Map<String, Object> pageMaps = new HashMap<String, Object>();
			pageMaps.put("start", pageDto.getStartPage());
			pageMaps.put("amount", pageDto.getAmount());
			
			List<BoardDto> resultList = boardService.listArticle(pageMaps);
			if (resultList != null && !resultList.isEmpty()) {
				Map<String, Object> resultMaps = new HashMap<String, Object>();
				resultMaps.put("pageDto", pageDto);
				resultMaps.put("boardList", resultList);
				
				return new ResponseEntity<>(resultMaps, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/search")
	@ResponseBody
	private ResponseEntity<?> search(@RequestBody Map<String, String> map) throws Exception {
		try {
			List<BoardDto> list = boardService.searchArticle(map);
			
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
}
