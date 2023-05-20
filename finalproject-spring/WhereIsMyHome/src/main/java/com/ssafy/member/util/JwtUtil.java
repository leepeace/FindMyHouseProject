package com.ssafy.member.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.taglibs.standard.tag.common.core.SetSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
	
	@Value("${jwt.salt}")
	private String salt;
	
	@Value("${jwt.expmin}")
	private Long expireMin;
	
	
	
	/**
	 * 로그인 성공 시 사용자 정보를 기반으로 JWT 토큰을 생성해서 반환함
	 * @param <T>
	 * @param key
	 * @param data 담고 싶은 정보
	 * @param subject
	 * @param expireMin
	 * @return
	 */
	public <T> String create(String key, T data, String subject, long expireMin) {
		JwtBuilder jwtBuilder = Jwts.builder();//jwt builder
		
		String jwt = jwtBuilder.setHeaderParam("typ", "JWT").setHeaderParam("regDate", System.currentTimeMillis()) //header setting
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))//payload setting
				.setSubject(subject)
				.claim(key, data)
				.signWith(SignatureAlgorithm.HS256, this.generateKey())//signature setting
				.compact();
		
		log.debug("토큰 발생 : " + jwt);
		
		return jwt;
	}
	
	
	/**
	 * refresh 토큰 생성
	 * @param <T>
	 * @param key
	 * @param data
	 * @return
	 */
	public <T> String createRefreshToken(String key, T data) {  
		return create(key, data, "refresh-token", expireMin * 5);
	}
	
	
	/**AccessToken 발행
	 * @param <T>
	 * @param key
	 * @param data
	 * @return
	 */
	public <T> String createAccessToken(String key, T data) {  
		return create(key, data, "access-token", expireMin);
	}
	
	
	/**
	 * signature 설정에 들어갈 key 생성
	 * @return
	 */
	private byte[] generateKey() {
		byte[] key = null;
		try {
			key = salt.getBytes("UTF-8");//key를 만듬
		}catch (UnsupportedEncodingException e) {
			if(log.isInfoEnabled()) {
				e.printStackTrace();
			}else {
				log.error("Making JWT key error : " + e.getMessage());
			}
		}
		return key;
	}
	
	public boolean checkToken(String jwt) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);//유효성 검증
			log.info("claims : " + claims);
			return true;
		}catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
}
