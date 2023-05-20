package com.ssafy.info.model.mapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.apt.HouseInfoDto;
import com.ssafy.apt.SidoGugunCodeDto;
import com.ssafy.info.AccomodationDto;
import com.ssafy.info.ClinicDto;
import com.ssafy.info.CommercialDto;
import com.ssafy.info.DongDto;
import com.ssafy.info.HospitalDto;

@Mapper
public interface InfoMapper {
	
	ArrayList<ClinicDto> getClinicInfo(String gugun, String dong) throws Exception;

	ArrayList<CommercialDto> getCommercialInfo() throws Exception;

	ArrayList<HospitalDto> getHospitalInfo(String gugun, String dong) throws Exception;

	ArrayList<SidoGugunCodeDto> favorite(String userId) throws SQLException;
	
	ArrayList<AccomodationDto> getAccommodationDtoInfo(String gugun, String dong) throws SQLException;

	int registArea(Map<String,String> map) throws SQLException;
	
	int checkfavorite(Map<String,String> map) throws SQLException;
	
	int checkbookmark(Map<String,Object> map) throws SQLException;
	int insertbookmark(Map<String,Object> map) throws SQLException;
	int deletebookmark(Map<String,Object> map) throws SQLException;
	
	ArrayList<HouseInfoDto> mybookmark(String user_id) throws SQLException;

	ArrayList<HouseInfoDto> getRecommendhouse(Map<String,Object> map) throws SQLException;

	int deletefavorite(Map<String, String> map);
}
