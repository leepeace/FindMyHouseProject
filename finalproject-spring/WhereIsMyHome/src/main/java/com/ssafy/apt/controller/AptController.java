package com.ssafy.apt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.apt.AptDto;
import com.ssafy.apt.AptSearchDto;
import com.ssafy.apt.TradeDto;
import com.ssafy.apt.model.service.AptService;
import com.ssafy.board.controller.BoardController;
import com.ssafy.apt.HouseInfoDto;
import com.ssafy.apt.SidoGugunCodeDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/apt")
public class AptController {

	private final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	private AptService aptService;

	@Autowired
	public AptController(AptService aptService) {
		logger.info("aptController 생성자 호출");
		this.aptService = aptService;
	}
	
	// start : 지역정보 가져오기
	
	@ApiOperation(value = "시도 정보", notes = "전국의 시도를 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		logger.info("sido - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(aptService.getSido(), HttpStatus.OK);
	}

	@ApiOperation(value = "구군 정보", notes = "전국의 구군을 반환한다.", response = List.class)
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(
			@RequestParam("sido") @ApiParam(value = "시도코드.", required = true) String sido) throws Exception {
		logger.info("gugun - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(aptService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@ApiOperation(value = "동 정보", notes = "구군에 해당하는 동을 반환한다.", response = List.class)
	@GetMapping("/dong")
	public ResponseEntity<List<HouseInfoDto>> dong(@RequestParam("gugun") @ApiParam(value = "구군코드.", required = true) String gugun) throws Exception {
		return new ResponseEntity<List<HouseInfoDto>>(aptService.getDongInGugun(gugun), HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트 거래 내역", notes = "아파트의 모든 거래내역을 반환한다.", response = List.class)
	@GetMapping("/deal")
	public ResponseEntity<List<TradeDto>> deal(@RequestParam("aptCode") @ApiParam(value = "아파트코드.", required = true) String aptCode) throws Exception {
		System.out.println(aptService.getHouseDeal(aptCode));
		return new ResponseEntity<List<TradeDto>>(aptService.getHouseDeal(aptCode), HttpStatus.OK);
	}
	
	// end : 지역 정보 가져오기

	@PostMapping("/list")
	private ResponseEntity<?> search(@RequestParam Map<String, String> map) {
		try {
			ArrayList<AptSearchDto> list = (ArrayList<AptSearchDto>) aptService.search(map);
			
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<AptSearchDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PostMapping("/regist")
	private ResponseEntity<?> regist(@RequestBody AptDto aptDto) {
		try {
			aptService.regist(aptDto);

			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("/delete/{trade_id}")
	private ResponseEntity<?> deleteTrade(@PathVariable("trade_id") int id){
		try {
			aptService.deleteTrade(id);

			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PutMapping("/update")
	private ResponseEntity<?> updateTrade(@RequestBody TradeDto tradeDto) {
		try {
			aptService.updateTrade(tradeDto);

			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	

	@GetMapping("/apt")
	public ResponseEntity<List<HouseInfoDto>> apt(@RequestParam("dong") String dong) throws Exception {
		return new ResponseEntity<List<HouseInfoDto>>(aptService.getAptInDong(dong), HttpStatus.OK);
	}
	
	@GetMapping("/aptSearch")
	public ResponseEntity<List<HouseInfoDto>> aptSearch(@RequestParam("aptName") String aptName) throws Exception {
		return new ResponseEntity<List<HouseInfoDto>>(aptService.searchApt(aptName), HttpStatus.OK);
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
