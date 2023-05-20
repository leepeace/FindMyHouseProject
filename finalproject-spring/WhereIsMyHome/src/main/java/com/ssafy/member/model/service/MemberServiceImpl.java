package com.ssafy.member.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberMapper memberMapper;

	
	@Override
	public int idCheck(String userId) throws Exception {
		return memberMapper.idCheck(userId);
	}

	@Override
	public int joinMember(MemberDto memberDto) throws Exception {
		return memberMapper.joinMember(memberDto);
	}

	@Override
	public MemberDto loginMember(String userId, String userPwd) throws Exception {
		return memberMapper.loginMember(userId, userPwd);
	}

	@Override
	public int modifyMember(MemberDto memberDto) throws SQLException {
		return memberMapper.modifyMember(memberDto);
	}

	@Override
	public int delMember(String userid) throws SQLException {
		System.out.println(userid);
		memberMapper.delBoard(userid);
		memberMapper.delComment(userid);
		memberMapper.delFavoriteApt(userid);
		memberMapper.delFavoriteDong(userid);
		memberMapper.delQna(userid);
		memberMapper.delReply(userid);
		
		return memberMapper.delMember(userid);
	}

	@Override
	public String searchPwd(String userid) throws SQLException {
		return memberMapper.searchPwd(userid);
	}

	@Override
	public MemberDto getMember(String userId) throws Exception {
		return memberMapper.getMember(userId);
	}

	
	//token 기능
	@Override
	public void saveRefreshToken(String userid, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", refreshToken);
		memberMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userid) throws Exception {
		return memberMapper.getRefreshToken(userid);
	}

	@Override
	public void deleRefreshToken(String userid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", null);
		memberMapper.deleteRefreshToken(map);
	}
	
	//email 기능
	@Override
	public MemberDto searchEmailUser(String emailId, String emailDomain) throws Exception {
		return memberMapper.searchEmailUser(emailId, emailDomain);
	}
	



	@Override
	public void setPassword(String emailId, String emailDomain, String userPwd) throws Exception {
		memberMapper.setPassword(emailId, emailDomain,  userPwd);
	}

}
