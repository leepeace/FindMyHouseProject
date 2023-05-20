package com.ssafy.info.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.apt.HouseInfoDto;
import com.ssafy.apt.SidoGugunCodeDto;
import com.ssafy.info.AccomodationDto;
import com.ssafy.info.ClinicDto;
import com.ssafy.info.CommercialDto;
import com.ssafy.info.DongDto;
import com.ssafy.info.HospitalDto;
import com.ssafy.info.model.service.InfoService;
import com.ssafy.member.model.MemberDto;


@Controller
@RequestMapping("/info")
@CrossOrigin("*")
public class InfoController {
	
	@Autowired
	InfoService infoService;
	
	// register Area 
	@PostMapping("/registArea")
	@ResponseBody
	public ResponseEntity<?> registArea(@RequestBody Map<String,String> map){
		//System.out.println(map.size());
//		String dongCode = map.get("dongCode");
//		String userId = map.get("userId");
		//System.out.println(gugun+" "+dong+" "+userId);
				
		
		try {
			
			int cnt = infoService.registArea(map);
			if(cnt>0) {
				return new ResponseEntity<String>("success",HttpStatus.OK);	
			}else {
				return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);	
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/news")
	@ResponseBody
	public ResponseEntity<?> getNewsList(){
		
		try {
			String news = infoService.getNewslist();
			return new ResponseEntity<Object>(news,HttpStatus.OK);		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return exceptionHandling(e);
		}
		
	}
	
	
	// getinfo
//	@GetMapping("/view")
//	@ResponseBody
//	public ResponseEntity<?> getinfo(HttpSession session){
//
//		//String userId = ((MemberDto)session.getAttribute("userinfo")).getUserId();
//		String userId = "ssafy";
//		//System.out.println(userId);
//		
//		try {
//			// 쿼리 => dongDto
//			DongDto dongDto = infoService.favorite(userId);
//			System.out.println(dongDto.toString());
//			
//			String gugun = dongDto.getSi_code() + "";
//			String dong = dongDto.getBub_code() + "";
//			
//			ArrayList<ClinicDto> clist = infoService.getClinicInfo(gugun, dong);
//			ArrayList<CommercialDto> cmlist = infoService.getCommercialInfo(gugun, dong);
//			ArrayList<AccomodationDto> alist = infoService.getAccommodationDtoInfo(gugun, dong);
//			ArrayList<HospitalDto> hlist = infoService.getHospitalInfo(gugun, dong);
//			
//			Map<String,List<?>> map = new HashMap<>();
//			map.put("clinics", clist);
//			map.put("commercialAreas", cmlist);
//			map.put("accs", alist);
//			map.put("hospitals", hlist);
//			
//			return new ResponseEntity<Object>(map,HttpStatus.OK);		
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			return exceptionHandling(e);
//		}
//		
//	}
	
	@GetMapping("/commercials")
	@ResponseBody
	public ResponseEntity<?> getCommercials(){

		try {
			
			ArrayList<CommercialDto> cmlist = infoService.getCommercialInfo();
			return new ResponseEntity<Object>(cmlist,HttpStatus.OK);		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return exceptionHandling(e);
		}
	}
	
	// 관심지역 가져오기
	@GetMapping("/favorite")
	@ResponseBody
	public ResponseEntity<?> getFavorite(@RequestParam("userId") String userId){

		try {
			ArrayList<SidoGugunCodeDto> flist = infoService.favorite(userId);
			for(SidoGugunCodeDto s : flist) {
				System.out.println(s.toString());
			}
			
			return new ResponseEntity<ArrayList<SidoGugunCodeDto>>(flist,HttpStatus.OK);		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return exceptionHandling(e);
		}
	}
	
	// 관심지역인지 아닌지 체크
	@GetMapping("/checkfavorite")
	@ResponseBody
	public ResponseEntity<?> checkFavorite(@RequestParam Map<String,String> map){

		try {
			System.out.println(map.get("user_id"));
			System.out.println(map.get("dongCode"));
			int cnt = infoService.checkfavorite(map);
			return new ResponseEntity<Integer>(cnt,HttpStatus.OK);		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return exceptionHandling(e);
		}
	}
	
	// 관심지역 삭제하기
	@DeleteMapping("/deletefavorite")
	@ResponseBody
	public ResponseEntity<?> deleteFavorite(@RequestParam Map<String,String> map){

		try {
			System.out.println(map.get("user_id"));
			System.out.println(map.get("dongCode"));
			int cnt = infoService.deletefavorite(map);
			return new ResponseEntity<Integer>(cnt,HttpStatus.OK);		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return exceptionHandling(e);
		}
	}
	
	// 관심단지 등록하기 
	@PostMapping("/bookmark")
	@ResponseBody
	public ResponseEntity<?> insertbookmark(@RequestBody Map<String,Object> map){
//		System.out.println("관심단지등록");
		try {
//			System.out.println((String)map.get("user_id"));
//			System.out.println((String)map.get("aptCode"));
			int cnt = infoService.insertbookmark(map);
			if(cnt>0) {
//				System.out.println("등록결과 "+cnt);
				return new ResponseEntity<Integer>(cnt,HttpStatus.OK);	
			}else {
				return new ResponseEntity<Integer>(cnt,HttpStatus.INTERNAL_SERVER_ERROR);	
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return exceptionHandling(e);
		}
	}
	
	// 관심단지 삭제하기 
	@DeleteMapping("/bookmark/{user_id}/{aptCode}")
	@ResponseBody
	public ResponseEntity<?> deletebookmark(@PathVariable String user_id, @PathVariable int aptCode){

		try {
			System.out.println(user_id);
			System.out.println(aptCode);
			
			Map<String,Object> map = new HashMap<>();
			map.put("user_id",user_id);
			map.put("aptCode",aptCode);
			
			int cnt = infoService.deletebookmark(map);
			if(cnt>0) {
				return new ResponseEntity<Integer>(cnt,HttpStatus.OK);	
			}else {
				return new ResponseEntity<Integer>(cnt,HttpStatus.INTERNAL_SERVER_ERROR);	
			}	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return exceptionHandling(e);
		}
	}
	
	// 관심단지인지 체크 
	@GetMapping("/isbookmark")
	@ResponseBody
	public ResponseEntity<?> checkbookmark(@RequestParam("user_id") String user_id, @RequestParam("aptCode") int aptCode){
		System.out.println("관심단지체크");
		try {
			Map<String,Object> map = new HashMap<>();
			map.put("user_id",user_id);
			map.put("aptCode",aptCode);
			
			System.out.println(map.get("user_id"));
			System.out.println(map.get("aptCode"));
			int cnt = infoService.checkbookmark(map);
			if(cnt>0) {
				System.out.println("체크 결과!!"+cnt);
				return new ResponseEntity<Integer>(cnt,HttpStatus.OK);	
			}else {
				return new ResponseEntity<Integer>(cnt,HttpStatus.INTERNAL_SERVER_ERROR);	
			}	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return exceptionHandling(e);
		}
	}
	
	// 북마크리스트 모두 불러오기
	@GetMapping("/mybookmark")
	@ResponseBody
	public ResponseEntity<?> mybookmark(@RequestParam("user_id") String user_id){
		
		try {
			ArrayList<HouseInfoDto> list = infoService.mybookmark(user_id);
			return new ResponseEntity<ArrayList<HouseInfoDto>>(list,HttpStatus.OK);	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return exceptionHandling(e);
		}
	}
	
	// 동별 추천리스트 불러오기 
	@GetMapping("/recommendHouse")
	@ResponseBody
	public ResponseEntity<?> getRecommendHouse(@RequestParam Map<String,Object> map){
		
		System.out.println(map.get("age"));
		System.out.println(map.get("dongCode"));
		
		try {
			ArrayList<HouseInfoDto> list = infoService.getRecommendhouse(map);
			return new ResponseEntity<ArrayList<HouseInfoDto>>(list,HttpStatus.OK);	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return exceptionHandling(e);
		}
	}
	
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
