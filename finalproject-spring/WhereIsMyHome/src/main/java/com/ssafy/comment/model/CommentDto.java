package com.ssafy.comment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDto {
	private int commentIdx;
	private int score;
	private String content;
	private String userId;
	private String registerTime;
	private int aptCode;
	private int AVG;
	private String aptName;
}
