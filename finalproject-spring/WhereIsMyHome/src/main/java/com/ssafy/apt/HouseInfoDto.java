package com.ssafy.apt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "HouseInfoDto : 아파트정보", description = "아파트의 상세 정보를 나타낸다.")
public class HouseInfoDto {

	@ApiModelProperty(value = "아파트일련번호")
	private int aptCode;
	@ApiModelProperty(value = "아파트이름")
	private String aptName;
	@ApiModelProperty(value = "동코드")
	private String dongCode;
	@ApiModelProperty(value = "동이름")
	private String dongName;
	@ApiModelProperty(value = "시도이름")
	private String sidoName;
	@ApiModelProperty(value = "구군이름")
	private String gugunName;
	@ApiModelProperty(value = "건축년도")
	private int buildYear;
	@ApiModelProperty(value = "지번")
	private String jibun;
	@ApiModelProperty(value = "위도")
	private String lat;
	@ApiModelProperty(value = "경도")
	private String lng;
	@ApiModelProperty(value = "아파트이미지")
	private String img;
	@ApiModelProperty(value = "매매가격")
	private String recentPrice;
	@ApiModelProperty(value = "평균거래가격")
	private String dealAvg;
	@ApiModelProperty(value = "북마크수")
	private int bookmark;
	@ApiModelProperty(value = "평점")
	private int scoreAvg;
	
	
}
