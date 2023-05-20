package com.ssafy.member.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.member.model.MemberDto;

@Mapper
public interface MemberMapper {
	int idCheck(String userId) throws Exception; // 아이디 중복검사
	int joinMember(MemberDto memberDto) throws Exception; // 회원가입
	MemberDto loginMember(String userId, String userPwd) throws Exception; // 로그인
	int modifyMember(MemberDto memberDto) throws SQLException;
	int delMember(String userId) throws SQLException;
	String searchPwd(String userId) throws SQLException;
	MemberDto getMember(String userId) throws SQLException;
	
	//token 기능
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userid) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	
	//email 기능
	MemberDto searchEmailUser(String emailId, String emailDomain) throws SQLException;
	void setPassword(String emailId, String emailDomain, String userPwd) throws SQLException;
	
	
	
	//연쇄 삭제
	void delComment(String userId);
	void delBoard(String userId);
	void delFavoriteApt(String userId);
	void delFavoriteDong(String userId);
	void delQna(String userId);
	void delReply(String userId);
	
}
