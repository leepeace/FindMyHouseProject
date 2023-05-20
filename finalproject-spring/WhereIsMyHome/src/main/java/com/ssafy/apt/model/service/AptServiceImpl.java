package com.ssafy.apt.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.apt.AptDto;
import com.ssafy.apt.AptSearchDto;
import com.ssafy.apt.TradeDto;
import com.ssafy.apt.model.mapper.AptMapper;
import com.ssafy.apt.HouseInfoDto;
import com.ssafy.apt.SidoGugunCodeDto;

@Service
public class AptServiceImpl implements AptService {
	
	@Autowired
	private AptMapper aptMapper;

	@Override
	public void regist(AptDto aptDto) throws Exception {
		aptMapper.regist(aptDto);
	}

	@Override
	public List<AptSearchDto> search(Map<String, String> condition) throws Exception {
		// 값 설정
		condition.put("gugun", condition.get("gugun").substring(0, 5));
		condition.put("dong", condition.remove("dododong"));

		System.out.println(condition.toString());
		
		// 가격 정렬 (Merge 정렬 사용)
		List<AptSearchDto> list = aptMapper.search(condition); // 검색 목록의 리스트를 list에 담음
		
		System.out.println(list.toString());
		
//		mergeSort(list, 0, list.size() - 1);
		
//		System.out.println(list.toString());
		
		return list; // 정렬한 list를 리턴
	}

	private void mergeSort(List<AptSearchDto> list, int left, int right) {
		int mid;
		// Divide and Conquer
		if (left < right) {
			mid = (left + right) / 2;
			mergeSort(list, left, mid);
			mergeSort(list, mid + 1, right);
			merge(list, left, mid, right);
		}
	}

	private void merge(List<AptSearchDto> list, int left, int mid, int right) {
		// 정렬된 정보를 담는 배열
		AptSearchDto[] sorted = new AptSearchDto[list.size()];
		int l = left;
		int r = mid + 1;
		int k = left;

		while (l <= mid && r <= right) {
			// 리스트 왼쪽 인덱스의 가격 정보 받아옴
			int la = Integer.parseInt(list.get(l).getDealAmount().trim().replaceAll(",", ""));
			// 리스트 오른쪽 인덱스의 가격 정보 받아옴
			int ra = Integer.parseInt(list.get(r).getDealAmount().trim().replaceAll(",", ""));
			// 왼 <= 오 이면, 배열에 왼쪽 값을 넣음
			if (la <= ra) {
				sorted[k++] = list.get(l++);
			} else if (la > ra) {
				sorted[k++] = list.get(r++);
			}

		}
		// 왼쪽 정보를 다 담았다면, 남은 오른쪽 정보를 sorted 배열에 다 넣어줌
		if (l > mid) {
			for (int i = r; i <= right; i++) {
				sorted[k++] = list.get(i);

			}
		}
		// 오른쪽 정보를 다 담았다면, 남은 왼쪽 정보를 sorted 배열에 다 넣어줌
		if (r > right) {
			for (int i = l; i <= mid; i++) {
				sorted[k++] = list.get(i);
			}
		}
		// list에 정렬된 배열 정보 넣어줌
		for (int i = left; i <= right; i++) {
			list.set(i, sorted[i]);
		}
	}

	@Override
	public void deleteTrade(int id) throws Exception {
		aptMapper.deleteTrade(id);
	}

	@Override
	public void updateTrade(TradeDto tradeDto) throws Exception {
		aptMapper.updateTrade(tradeDto);
	}
	
	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return aptMapper.getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return aptMapper.getGugunInSido(sido);
	}

	@Override
	public List<HouseInfoDto> getDongInGugun(String gugun) throws Exception {
		return aptMapper.getDongInGugun(gugun);
	}

	@Override
	public List<HouseInfoDto> getAptInDong(String dong) throws Exception {
		return aptMapper.getAptInDong(dong);
	}

	@Override
	public List<TradeDto> getHouseDeal(String aptCode) {
		// TODO Auto-generated method stub
		return aptMapper.getHouseDeal(aptCode);
	}

	@Override
	public List<HouseInfoDto> searchApt(String aptName) {
		// TODO Auto-generated method stub
		return aptMapper.searchApt(aptName);
	}
	
}
