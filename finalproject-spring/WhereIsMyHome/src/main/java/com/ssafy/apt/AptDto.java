package com.ssafy.apt;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AptDto {
	
	private int id;
	private int build_year;
	private String dong;
	private String name;
	private String area;
	private String jibun;
	private String regional_code;
	
}
