package com.ssafy.apt.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.apt.AptDto;
import com.ssafy.apt.AptSearchDto;
import com.ssafy.apt.TradeDto;
import com.ssafy.apt.HouseInfoDto;
import com.ssafy.apt.SidoGugunCodeDto;

public interface AptMapper {
	List<AptSearchDto> search(Map<String, String> condition) throws SQLException;
	void regist(AptDto aptDto)throws SQLException;
	void deleteTrade(int id)throws SQLException;
	void updateTrade(TradeDto tradeDto) throws SQLException;
	
	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	List<HouseInfoDto> getDongInGugun(String gugun) throws SQLException;
	List<HouseInfoDto> getAptInDong(String dong) throws SQLException;
	List<TradeDto> getHouseDeal(String aptCode);
	List<HouseInfoDto> searchApt(String aptName);
}
