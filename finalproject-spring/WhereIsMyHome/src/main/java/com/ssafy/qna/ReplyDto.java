package com.ssafy.qna;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ReplyDto {
	private int replyNo;
	private String content;
	private String userId;
	private String userName;
	private int qnaNo;
	private String registerTime;
}
