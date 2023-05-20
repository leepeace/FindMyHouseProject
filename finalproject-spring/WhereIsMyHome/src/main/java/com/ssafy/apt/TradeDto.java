package com.ssafy.apt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeDto {

	private int id;
	private int no;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private String dealAmount;
	private int apartId;
	private int aptCode;
	private double area;
	private int floor;
	private int dealAvg;

}
