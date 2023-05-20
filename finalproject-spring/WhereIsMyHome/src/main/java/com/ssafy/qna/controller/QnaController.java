package com.ssafy.qna.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.board.controller.BoardController;
import com.ssafy.qna.QnaDto;
import com.ssafy.qna.ReplyDto;
import com.ssafy.qna.model.service.QnaService;
import com.ssafy.util.PageDto;

@Controller
@RequestMapping("/qna")
@CrossOrigin("*")
public class QnaController {

	private final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	private QnaService qnaService;

	@Autowired
	public QnaController(QnaService QnaService) {
		logger.info("qnaController 생성자 호출");
		this.qnaService = QnaService;
	}
	
	// 리스트 출력 
	@GetMapping("/list/{pageNum}")
	@ResponseBody
	public ResponseEntity<?> list(@PathVariable("pageNum") int pageNo) {
		
		logger.info("page num = " + pageNo);
		try {
			//페이징 처리
			PageDto pageDto = qnaService.paging(pageNo);
			Map<String, Object> pageMaps = new HashMap<String, Object>();
			pageMaps.put("start", pageDto.getStartPage());
			pageMaps.put("amount", pageDto.getAmount());
			//System.out.println(pageMaps.get("start"));
			List<QnaDto> list = qnaService.listQna(pageMaps);
			Map<String, Object> resultMap = new HashMap<String, Object>();
	
			if (list != null && !list.isEmpty()) {
				resultMap.put("boardList", list);
				resultMap.put("pageDto", pageDto);
				return new ResponseEntity<>(resultMap, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	
	
	// 글작성 
	@PostMapping("/write")
	@ResponseBody
	private ResponseEntity<?> write(@RequestBody QnaDto qnaDto) throws Exception {
		//System.out.println(qnaDto.toString());
		try {
			qnaService.writeQna(qnaDto);
			
			Map<String, Object> pageMaps = new HashMap<String, Object>();
			pageMaps.put("start", 1);
			pageMaps.put("amount", 10);
			List<QnaDto> list = qnaService.listQna(pageMaps);
			
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<QnaDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 상세보기
	@GetMapping("/view/{qnaNo}")
	@ResponseBody
	private ResponseEntity<?> view(@PathVariable int qnaNo) throws Exception {
		try {
			QnaDto qnaDto = qnaService.getQna(qnaNo);
			qnaService.updateHit(qnaNo);
			if (qnaDto != null) {
				return new ResponseEntity<QnaDto>(qnaDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	

	// 수정하기
	@PutMapping("/modify/{qnaNo}")
	@ResponseBody
	private ResponseEntity<?> modify(@RequestBody QnaDto qnaDto, @PathVariable int qnaNo) throws Exception {
		try {

			qnaService.modifyQna(qnaDto);
			
			Map<String, Object> pageMaps = new HashMap<String, Object>();
			pageMaps.put("start", 1);
			pageMaps.put("amount", 10);
			
			List<QnaDto> list = qnaService.listQna(pageMaps);

			return new ResponseEntity<List<QnaDto>>(list, HttpStatus.OK);
		
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 큐엔에이 글 삭제 
	@DeleteMapping("/delete/{qnaNo}")
	@ResponseBody
	private ResponseEntity<?> delete(@PathVariable int qnaNo) throws Exception {
		try {
			qnaService.deleteQna(qnaNo);
			
			Map<String, Object> pageMaps = new HashMap<String, Object>();
			pageMaps.put("start", 1);
			pageMaps.put("amount", 10);
			
			List<QnaDto> list = qnaService.listQna(pageMaps);

			return new ResponseEntity<List<QnaDto>>(list, HttpStatus.OK);
	
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 검색
	@GetMapping("/search")
	@ResponseBody
	private ResponseEntity<?> search(@RequestParam Map<String, String> map) throws Exception {
		try {
			List<QnaDto> list = qnaService.searchQna(map);
			for(QnaDto qna : list) {
				System.out.println(qna.toString());
			}
			
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<QnaDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 답변 달기 
	@PostMapping("/reply")
	@ResponseBody
	private ResponseEntity<?> reply(@RequestBody ReplyDto replyDto) throws Exception {
		System.out.println(replyDto.toString());
		try {
			int cnt = qnaService.writeReply(replyDto);
			List<ReplyDto> list = qnaService.getReply(replyDto.getQnaNo());
			
			if(cnt>0) {
				return new ResponseEntity<List<ReplyDto>>(list,HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 답변 불러오기 
	@GetMapping("/reply/{qnaNo}")
	@ResponseBody
	private ResponseEntity<?> getReply(@PathVariable int qnaNo) throws Exception {
		try {
			List<ReplyDto> replyDto = qnaService.getReply(qnaNo);
			
			if(replyDto!=null) {
				return new ResponseEntity<List<ReplyDto>>(replyDto,HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	// 답변 수정 
	// 수정하기
	@PutMapping("/modifyReply/{replyNo}")
	@ResponseBody
	private ResponseEntity<?> replyModify(@RequestBody ReplyDto replyDto, @PathVariable int replyNo) throws Exception {
		try {

			qnaService.modifyReply(replyDto);
			List<ReplyDto> reply = qnaService.getReply(replyNo);
			
			if (reply != null) {
				return new ResponseEntity<List<ReplyDto>>(reply, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 답변 삭제
	@DeleteMapping("/deleteReply/{replyNo}")
	@ResponseBody
	private ResponseEntity<?> replyDelete(@PathVariable int replyNo) throws Exception {
		try {
			int cnt = qnaService.deleteReply(replyNo);
			
			if (cnt>0) {
				return new ResponseEntity<Void>(HttpStatus.OK);
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
