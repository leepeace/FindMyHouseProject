package com.ssafy.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {

	private String userId;
	private String userName;
	private String userPwd;
	private String emailId;
	private String emailDomain;
	private String joinDate;
	private String token;
	private String loginType;
	private int age;
}
