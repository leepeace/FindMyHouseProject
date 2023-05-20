package com.ssafy.comment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.comment.model.CommentDto;
import com.ssafy.comment.model.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/comment")
@RestController
@CrossOrigin("*")
@Api("리뷰 컨트롤러  API V1")
public class CommentController {

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	private CommentService service;
	
	@Autowired
	public CommentController(CommentService service) {
		this.service = service;
	}


	//내가 남긴 리뷰 기록 보기
	@GetMapping("/{userId}")
	@ResponseBody
	public  ResponseEntity<?> myReview(@PathVariable("userId") String loginId){
		logger.info("userId = " + loginId);
		
		Map<String, Object> paramMaps = new HashMap<String, Object>();
		paramMaps.put("userId", loginId);
		
		try {
			List<CommentDto> resultList = service.getUserComment(paramMaps);
			System.out.println(resultList.size());
			if(resultList.size() > 0) {
				return new ResponseEntity<List<CommentDto>>(resultList, HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		}catch (Exception e) {
			return exceptionHandling(e);
		}
	}
		
	
	/**
	 * 리뷰를 등록하는 메서드
	 * @return 성공 시 200 반환
	 */
	@PostMapping(value="/")
	@ApiOperation(value = "리뷰 등록", notes = "리뷰를 등록한다.")
	public ResponseEntity<?> registReview(@RequestBody CommentDto commentDto){
		//logger.info("comment = " + commentDto.toString());
		try {
			service.registComment(commentDto);
			
			Map<String, Object> paramMaps = new HashMap<String, Object>();
			paramMaps.put("aptCode", commentDto.getAptCode());
			List<CommentDto> commentLists = service.getList(paramMaps);
			Map<String, Object> resultMaps = new HashMap<String, Object>();
			resultMaps.put("commentList", commentLists);
			
			return new ResponseEntity<Map<String, Object>>(resultMaps, HttpStatus.OK);
		}catch (Exception e) {
			return exceptionHandling(e);
		}
		
	}
	

	@DeleteMapping(value="/delete/{idx}/{userid}")
	@ApiOperation(value = "리뷰 삭제", notes = "리뷰 글 번호와 아이디를 토대로 리뷰를 삭제한다.")
	public ResponseEntity<?> deleteReview(@PathVariable("idx") int commentIdx, @PathVariable("userid") String loginId){
		return null;
	}
	
	@GetMapping("/list/{aptCode}")
	@ApiOperation(value = "리뷰 목록")
	public ResponseEntity<?> listReview(@PathVariable("aptCode") int aptCode){
		//logger.info("aptCode = " + aptCode);
		//logger.info("page num = " + pageNo);
		
		try {
			Map<String, Object> paramMaps = new HashMap<String, Object>();
//			paramMaps.put("start", 1);
//			paramMaps.put("amount", 10);
			paramMaps.put("aptCode", aptCode);
			List<CommentDto> commentLists = service.getList(paramMaps);
			Map<String, Object> resultMaps = new HashMap<String, Object>();
			resultMaps.put("commentList", commentLists);
			
			return new ResponseEntity<Map<String, Object>>(resultMaps, HttpStatus.OK);
		}catch (Exception e) {
			return exceptionHandling(e);
		}
		
		
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
