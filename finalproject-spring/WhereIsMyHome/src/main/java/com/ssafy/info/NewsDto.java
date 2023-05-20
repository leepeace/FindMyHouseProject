package com.ssafy.info;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewsDto {
	private String title;
	private String link;
	private String description;
	private Date pubDate;
	
}
