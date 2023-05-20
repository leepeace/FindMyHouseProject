package com.ssafy.member.model.service;

import java.sql.SQLException;

import org.springframework.mail.SimpleMailMessage;

import com.ssafy.member.model.MemberDto;

public interface MemberService {

	int idCheck(String userId) throws Exception; // 아이디 중복검사
	int joinMember(MemberDto memberDto) throws Exception; // 회원가입
	MemberDto loginMember(String userId, String userPwd) throws Exception; // 로그인
	int modifyMember(MemberDto memberDto) throws SQLException;



	int delMember(String userId) throws Exception;
	String searchPwd(String userId) throws Exception;
	MemberDto getMember(String userId) throws Exception;
	
	//token 기능
	public void saveRefreshToken(String userid, String refreshToken) throws Exception;
	public Object getRefreshToken(String userid) throws Exception;
	public void deleRefreshToken(String userid) throws Exception;
	

	//email 기능
	MemberDto searchEmailUser(String emailId, String emailDomain) throws Exception;
	//임시 비밀번호 세팅
	void setPassword(String emailId, String emailDomain,  String userPwd) throws Exception;
	

}
