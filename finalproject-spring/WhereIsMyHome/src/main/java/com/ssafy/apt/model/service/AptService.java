package com.ssafy.apt.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.apt.AptDto;
import com.ssafy.apt.AptSearchDto;
import com.ssafy.apt.TradeDto;
import com.ssafy.apt.HouseInfoDto;
import com.ssafy.apt.SidoGugunCodeDto;

public interface AptService {
	List<AptSearchDto> search(Map<String, String> condition) throws Exception;	// 아파트 정보 읽어오기	

	void regist(AptDto aptDto)throws Exception;

	void deleteTrade(int id) throws Exception;

	void updateTrade(TradeDto tradeDto) throws Exception;
	
	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;
	List<HouseInfoDto> getAptInDong(String dong) throws Exception;

	List<TradeDto> getHouseDeal(String aptCode);

	List<HouseInfoDto> searchApt(String aptName);
	
}
