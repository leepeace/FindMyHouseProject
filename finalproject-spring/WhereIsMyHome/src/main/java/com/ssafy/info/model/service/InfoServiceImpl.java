package com.ssafy.info.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.ssafy.apt.HouseInfoDto;
import com.ssafy.apt.SidoGugunCodeDto;
import com.ssafy.info.AccomodationDto;
import com.ssafy.info.ClinicDto;
import com.ssafy.info.CommercialDto;
import com.ssafy.info.DongDto;
import com.ssafy.info.HospitalDto;
import com.ssafy.info.model.mapper.InfoMapper;

@Service
public class InfoServiceImpl implements InfoService {

	@Autowired
	private InfoMapper infoMapper;
	
	@Value("${naver-client-id}")
	private String clientId;
	@Value("${naver-client-secret}")
	private String clientSecret;

	@Override
	public int registArea(Map<String,String> map) throws SQLException {
		// TODO Auto-generated method stub

		return infoMapper.registArea(map);
	}
	
	@Override
	public ArrayList<ClinicDto> getClinicInfo(String gugun, String dong) throws Exception {
		return infoMapper.getClinicInfo(gugun, dong);
	}

	@Override
	public ArrayList<CommercialDto> getCommercialInfo() throws Exception {
		// TODO Auto-generated method stub
		return infoMapper.getCommercialInfo();
	}

	@Override
	public ArrayList<HospitalDto> getHospitalInfo(String gugun, String dong) throws Exception {
		// TODO Auto-generated method stub
		return infoMapper.getHospitalInfo(gugun, dong);
	}

	@Override
	public ArrayList<SidoGugunCodeDto> favorite(String userId) throws SQLException {
		return infoMapper.favorite(userId);
	}

	@Override
	public ArrayList<AccomodationDto> getAccommodationDtoInfo(String gugun, String dong) throws SQLException {
		// TODO Auto-generated method stub
		return infoMapper.getAccommodationDtoInfo(gugun, dong);
	}
	
	//news api 
	
	@Override
	public String getNewslist() {
		// TODO Auto-generated method stub
		// 뉴스 리스트 가져오기 
		
		String keyword = null; // 검색 키워드
		try {
			keyword = URLEncoder.encode("부동산", "UTF-8");
		}catch(UnsupportedEncodingException e) {
			throw new RuntimeException("검색어 인코딩 실패",e);
		}
		
		// url 
		// 현재는 5개만 가져올 수 있도록 되어있는 상태
		String url = "https://openapi.naver.com/v1/search/news.json?query="+keyword+"&display=5";
//		System.out.println(url);
		
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(url,requestHeaders);
		
        return responseBody;
		
	}
	
	static String get(String apiUrl, Map<String,String> requestHeaders) {
	     HttpURLConnection con = connect(apiUrl);
	        try {
	            con.setRequestMethod("GET");
	            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
	                con.setRequestProperty(header.getKey(), header.getValue());
	            }


	            int responseCode = con.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
	                return readBody(con.getInputStream());
	            } else { // 오류 발생
	                return readBody(con.getErrorStream());
	            }
	        } catch (IOException e) {
	            throw new RuntimeException("API 요청과 응답 실패", e);
	        } finally {
	            con.disconnect();
	        }
	}
	
    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }
    
    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }

	@Override
	public int checkfavorite(Map<String, String> map) throws SQLException {
		// TODO Auto-generated method stub
		return infoMapper.checkfavorite(map);
	}

	@Override
	public int checkbookmark(Map<String, Object> map) throws SQLException {
		// TODO Auto-generated method stub
		return infoMapper.checkbookmark(map);
	}

	@Override
	public int insertbookmark(Map<String, Object> map) throws SQLException {
		// TODO Auto-generated method stub
		return infoMapper.insertbookmark(map);
	}

	@Override
	public int deletebookmark(Map<String, Object> map) throws SQLException {
		// TODO Auto-generated method stub
		return infoMapper.deletebookmark(map);
	}

	@Override
	public ArrayList<HouseInfoDto> mybookmark(String user_id) throws SQLException {
		// TODO Auto-generated method stub
		return infoMapper.mybookmark(user_id);
	}

	@Override
	public ArrayList<HouseInfoDto> getRecommendhouse(Map<String,Object> map) throws SQLException {
		// TODO Auto-generated method stub
		return infoMapper.getRecommendhouse(map);
	}

	@Override
	public int deletefavorite(Map<String, String> map) {
		// TODO Auto-generated method stub
		return infoMapper.deletefavorite(map);
	}

	
}
