package com.ssafy.apt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "SidoGugunCodeDto : 시도, 구군정보", description = "시도, 구군의 이름을 나타낸다.")
public class SidoGugunCodeDto {

	@ApiModelProperty(value = "시도코드")
	private String sidoCode;
	@ApiModelProperty(value = "시도이름")
	private String sidoName;
	@ApiModelProperty(value = "구군코드")
	private String gugunCode;
	@ApiModelProperty(value = "구군이름")
	private String gugunName;
	@ApiModelProperty(value = "구군코드")
	private String dongCode;
	@ApiModelProperty(value = "구군이름")
	private String dongName;

}
