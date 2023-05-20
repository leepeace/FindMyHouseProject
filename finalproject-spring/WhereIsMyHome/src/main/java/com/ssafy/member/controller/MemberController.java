package com.ssafy.member.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.service.JwtService;
import com.ssafy.member.model.service.MemberService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;



@Controller
@RequestMapping("/user")
@CrossOrigin("*")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private JwtService jwtService;

	@Autowired
	private JavaMailSender javaMailSender;
	
	static String SUCCESS = "success";
	static String FAIL = "fail";
	
	@GetMapping()
	public String index() {
		return "index";
	}
	
	// idcheck
	@GetMapping("/idCheck/{userid}")
	@ResponseBody
	public ResponseEntity<?> idCheck(@PathVariable("userid") String userid){
		try {
			logger.info("info level id check : " + userid);
			int cnt = memberService.idCheck(userid);

			if(cnt>0) {
				return new ResponseEntity<Void>(HttpStatus.OK);	
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);	
			}
			
		} catch (Exception e) {
			logger.error("error message : " + e);
			return exceptionHandling(e);
			
		}
	}
	
	// modify
	@PutMapping("/user/modify")
	@ResponseBody
	public ResponseEntity<?> idCheck(@RequestBody MemberDto memberDto){
		try {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String pwd = memberDto.getUserPwd();
			System.out.println(pwd);
			memberDto.setUserPwd(encoder.encode(pwd));
			System.out.println(memberDto.getUserPwd());
			int cnt = memberService.modifyMember(memberDto);
			if(cnt>0) {
				return new ResponseEntity<Void>(HttpStatus.OK);		
			}else {
				return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);	
			}
			
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	// join 
	@PostMapping(value = "/user")
	@ResponseBody
	public ResponseEntity<?> userRegister(@RequestBody MemberDto memberDto) {
		logger.info("dto = " + memberDto.toString());
		try {
			int idcheckResult = memberService.idCheck(memberDto.getUserId());
			if(idcheckResult > 0) {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);	
			}
			
			//암호화
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String pwd = memberDto.getUserPwd();
			memberDto.setUserPwd(encoder.encode(pwd));

			int cnt = memberService.joinMember(memberDto);
			if(cnt>0) {
				return new ResponseEntity<Void>(HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);	
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}

	}
	
	

	
	//mypage
	@ResponseBody
	@GetMapping(value="/mypage/{userid}")
	public ResponseEntity<?> myPage(@PathVariable("userid") String userId) {
		MemberDto memberDto = null;
		try {
			memberDto = memberService.getMember(userId);
			if(memberDto != null) {
				logger.info("mypage : " + memberDto);
				return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);	
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	

	
	/** login
	 * @param memberDto
	 * @return 성공시 "success", 실패 시 "fail"
	 */
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String,Object> map) {
		logger.info("memberDto >> " + map.toString());
		String id = (String) map.get("userid");
		String pwd = (String) map.get("userpwd");

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodedPassword = memberService.searchPwd(id);
			
			if(encodedPassword != null && encoder.matches(pwd, encodedPassword)) {				
				MemberDto loginUser = memberService.loginMember(id, encodedPassword);
				if (loginUser != null) {
					String accessToken = jwtService.createAccessToken("userid", loginUser.getUserId());// key, data
					String refreshToken = jwtService.createRefreshToken("userid", loginUser.getUserId());// key, data
					memberService.saveRefreshToken(id, refreshToken);
					logger.info("로그인 accessToken 정보 : {}", accessToken);
					logger.info("로그인 refreshToken 정보 : {}", refreshToken);
					
					resultMap.put("access-token", accessToken);
					resultMap.put("refresh-token", refreshToken);
					resultMap.put("message", SUCCESS);
					status = HttpStatus.ACCEPTED;
				} else {
					resultMap.put("message", FAIL);
					status = HttpStatus.ACCEPTED;
				}
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid,
			HttpServletRequest request) {
		logger.info("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.getMember(userid);
				
				if(memberDto != null) {
					resultMap.put("userInfo", memberDto);
					resultMap.put("message", SUCCESS);
					status = HttpStatus.ACCEPTED;					
				}else {
					resultMap.put("message", FAIL);
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", FAIL);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	
	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
	@GetMapping("/logout/{userid}")
	public ResponseEntity<?> removeToken(@PathVariable("userid") String userid) {
		logger.info("logout method call");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			memberService.deleRefreshToken(userid);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}
	
	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody MemberDto memberDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		logger.debug("token : {}, memberDto : {}", token, memberDto);
		if (jwtService.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(memberDto.getUserId()))) {
				String accessToken = jwtService.createAccessToken("userid", memberDto.getUserId());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	

	
	
	// search - 비밀번호 찾기 
	@GetMapping("/find/{email}")
	@ApiOperation(value = "비밀번호 찾기", notes = "임시 비밀번호를 발급하여 이메일에 전송")
	@ResponseBody
	public ResponseEntity<?> search(@PathVariable("email") String userEmail) {
		logger.info("user email" , userEmail);
		try {
			//존재하는 회원인지 확인함
			String[] emails = userEmail.split("@");
			MemberDto searchMember = memberService.searchEmailUser(emails[0], emails[1]);
			
			if(searchMember != null) {
				//이메일에 보낼 메시지 생성
				

				String sendEmailAddress = searchMember.getEmailId() + "@" + searchMember.getEmailDomain();
				MimeMessage mailMessage = javaMailSender.createMimeMessage();
				
				String rawPassword = getRandomPassword();
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				String encodePassword = encoder.encode(rawPassword);

				mailMessage.setText("안녕하세요. 임시 비밀번호를 발급합니다. " + rawPassword);
				mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(sendEmailAddress));
				mailMessage.setSubject("임시 비밀번호 발급입니다.");//메일 제목
				
				//DB에 임시 비밀번호로 세팅
				memberService.setPassword(emails[0], emails[1], encodePassword);
				
				//이메일에 임시 비밀번호 전송

				javaMailSender.send(mailMessage);
			
								
				return new ResponseEntity<Void>(HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}			

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	

	
	
	// delete - 회원 탈퇴 
	@DeleteMapping("/{userid}")
	@ResponseBody
	public ResponseEntity<?> userDelete(@PathVariable("userid") String userId) {
		try {
			int cnt = memberService.delMember(userId);
			logger.info("delete count ", cnt);
			if(cnt>0) {
				return new ResponseEntity<Void>(HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);	
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "회원수정")
	@PutMapping("/modify")
	@ResponseBody
	public ResponseEntity<?> modify(@RequestBody MemberDto memberDto){
		try {
			//암호화
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String pwd = memberDto.getUserPwd();
			memberDto.setUserPwd(encoder.encode(pwd));
			
			int cnt = memberService.modifyMember(memberDto);
			if(cnt > 0) {
 				return new ResponseEntity<Void>(HttpStatus.OK);
			}else {				
				return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);	
			}
		}catch (Exception e) {
			return exceptionHandling(e);
		}
		
	}
	
	@PostMapping("/kakao")
	@ResponseBody
	public ResponseEntity<?> kakaoLogin(@RequestBody Map<String, Object> maps){
		logger.info("kakao login call");
		System.out.println(maps.toString());
		
		//id_token이 유효한지 검사
		//유효한 경우 기존에 가입된 회원인지 확인 후 그렇지 않은 경우 회원가입 진행, 아닌 경우 로그인 진행
		String token = (String) maps.get("id_token");

		if(token != null && jwtService.checkKakaoToken(token)) {
			Map<String, Object> userInfo = getUserInfo(token);
			String loginId = (String) userInfo.get("email");
			String userName = (String)userInfo.get("nickname");
			String access_token = (String)userInfo.get("access_token");
			
			try {
				Map<String, Object> resultMap = new HashMap<>();

				if(memberService.getMember(loginId) == null) {
					MemberDto member = new MemberDto();
					member.setLoginType("kakao");
					member.setUserId(loginId);
					member.setUserName(userName);
					member.setUserPwd(getRandomPassword());
					member.setToken(access_token);
					
					String[] email = loginId.split("@");
					
					member.setEmailId(email[0]);
					member.setEmailDomain(email[1]);
					
					memberService.joinMember(member);
				}
				MemberDto memberDto = memberService.getMember(loginId);
				resultMap.put("userInfo", memberDto);
				
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		
	}

	

	
	private Map<String,Object> getUserInfo(String token) {
		String payloadJWT = token.split("\\.")[1];
		
		
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(payloadJWT));
		
		JsonParser jsonParser = new BasicJsonParser();
		Map<String, Object> jsonArray = jsonParser.parseMap(payload);
		
		return jsonArray;
	}
	
	private String getRandomPassword() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		return uuid;
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
