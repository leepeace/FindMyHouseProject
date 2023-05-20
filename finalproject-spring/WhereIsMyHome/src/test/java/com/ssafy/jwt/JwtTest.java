package com.ssafy.jwt;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.member.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class JwtTest {

	@Autowired
	JwtUtil util;
	
	@Test
	public void tokenGenerateTest() {
		String id = "ssafy";
		String token = util.createAccessToken("userid", id);
		
		assertNotNull(token);
		log.info(token);
	}
	
	
	
}
