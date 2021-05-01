package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//인증이 안된 사용자들이 출입할수 있는 경로를 /auth/**
//index 허용
//static , js, css 허용




@Controller
public class UserController {

	@Value("${cos.key}")
	private String cosKey;
	
	
	
	@Autowired
	UserService userService;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
	}
	
	
	@GetMapping("/auth/kakao/callback")
	public String kakaocallback(String code) {
		// POST방식으로 key=value 데이터를 요청 (카카오쪽으로) ----------------------> 토큰 을 받기 위해 카카오로 데이터를 보내는 과정입니다.
				// Retrofit2
				// OkHttp
				// RestTemplate
				
				RestTemplate rt = new RestTemplate();
				
				// HttpHeader 오브젝트 생성
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
				
				// HttpBody 오브젝트 생성
				MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
				params.add("grant_type", "authorization_code");
				params.add("client_id", "f17fad21a564bc45dd0ec7a1b9836378");
				params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
				params.add("code", code);
				
				// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
				HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
						new HttpEntity<>(params, headers);
				
				// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
				ResponseEntity<String> response = rt.exchange(
						"https://kauth.kakao.com/oauth/token",
						HttpMethod.POST,
						kakaoTokenRequest,
						String.class
				);
				
				
				// 1 . json 데이터인 토큰을 받아드리는 과정 , token 클래스 만들어준다
				//json을 받아올 objectMapper를 사용한다
				ObjectMapper objectMapper = new ObjectMapper();
				OAuthToken oauthToken = null;
				try {
					oauthToken =objectMapper.readValue(response.getBody(), OAuthToken.class);
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//자바로 토큰 가져오기 성공
				//System.out.println("카카오 엑세스 토큰 : " + oauthToken.getAccess_token());
				
				
				
				// 2, 토큰을 다시 카카오 측으로 요청 보낸다 왜 ? 데이터를 받아오기 위해서 
				// 토큰 보내기 
				RestTemplate rt2 = new RestTemplate();
				
				// HttpHeader 오브젝트 생성
				HttpHeaders headers2 = new HttpHeaders();
				headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
				headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
				
				// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
				HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
						new HttpEntity<>(headers2);
				
				// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
				ResponseEntity<String> response2 = rt2.exchange(
						"https://kapi.kakao.com/v2/user/me",
						HttpMethod.POST,
						kakaoProfileRequest2,
						String.class
				);
				
				System.out.println(response2.getBody());
	
				
				
				// 3. 이제 카카오가 json 형태로 데이터를 뿌려준다
				// 토큰 받을때 처럼 json 형태의 파일을 자바로 받아줘야한다 ! -> 클래스 생성 후 json 파일을 받아서 정보를 받아본다
				
				
				ObjectMapper objectMapper2 = new ObjectMapper();
				KakaoProfile kakaoProfile = null;
				try {
					kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				
				// User 오브젝트 : username, password, email
				System.out.println("카카오 아이디(번호) : "+kakaoProfile.getId());
				System.out.println("카카오 이메일 : "+kakaoProfile.getKakao_account().getEmail());
				
				System.out.println("블로그서버 유저네임 : "+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
				System.out.println("블로그서버 이메일 : "+kakaoProfile.getKakao_account().getEmail());
				// UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘
			
				
				User kakaoUser = User.builder()
						.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
						.password(cosKey)
						.email(kakaoProfile.getKakao_account().getEmail())
						.oauth("kakao")
						.build();
				
				// 가입자 혹은 비가입자 체크 해서 처리
				User originUser = userService.회원찾기(kakaoUser.getUsername());
				
				if(originUser.getUsername() == null) {
					System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다");
					userService.회원가입(kakaoUser);
				}
		
				System.out.println("자동 로그인을 진행합니다.");
				// 로그인 처리
				Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				return "redirect:/";
		}
	
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		
		return "user/updateForm";
	}
}
