package com.cos.blog.config.oauth;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;





@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{

	
	@Autowired
	UserRepository userRepository; 
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	
	// 후처리 되는 함수 데이터를 후처리되는 함수
	// 여기에 토큰 , 정보 들어온다
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		//System.out.println(userRequest.getClientRegistration()); -> goolge
		//System.out.println(userRequest.getAccessToken());
		//System.out.println(super.loadUser(userRequest).getAttributes());
		
		OAuth2User oauth2User =   super.loadUser(userRequest);
		//OAuth2UserRequest userRequest 안에 이것저것잇음
		System.out.println(oauth2User.getAttributes());
		
		
		OAuth2UserInfo oauth2UserInfo  = null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
			
			oauth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
			//map으로 받아줘서 Map<String, Object> 으로 리턴한 값을 보내고 받아줌
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			
			oauth2UserInfo  = new FacebookUserInfo(oauth2User.getAttributes());
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			
			oauth2UserInfo  = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
	
	//{resultcode=00, 
	//		message=success, 
	//				response={id=YHyLY37dvP97gJko1IiRoLLm3q68RncBHU-VT_xYMpE,
	//				email=jch941022@naver.com, 
	//				name=조민준}}       
            
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
		/*
		 * {id=1720353958, connected_at=2021-05-04T08:57:47Z, 
		 * properties={nickname=이세인, profile_image=http://k.kakaocdn.net/dn/ovZje/btqZ3s4XXQ0/fvwQzcXaXr1rbwPx06g3B1/img_640x640.jpg, thumbnail_image=http://k.kakaocdn.net/dn/ovZje/btqZ3s4XXQ0/fvwQzcXaXr1rbwPx06g3B1/img_110x110.jpg},
		 *  kakao_account={profile_needs_agreement=false, 
		 *  				profile={nickname=이세인, thumbnail_image_url=http://k.kakaocdn.net/dn/ovZje/btqZ3s4XXQ0/fvwQzcXaXr1rbwPx06g3B1/img_110x110.jpg, profile_image_url=http://k.kakaocdn.net/dn/ovZje/btqZ3s4XXQ0/fvwQzcXaXr1rbwPx06g3B1/img_640x640.jpg, is_default_image=false},
		 *   				has_email=true, email_needs_agreement=false, is_email_valid=true, is_email_verified=true, email=sein990102@naver.com}}	
		 */
			oauth2UserInfo  = new KakaoUserInfo(oauth2User.getAttributes());};
		
		
		
		String provider =  oauth2UserInfo.getProvider(); // -> google,facebook
		String providerId  = oauth2UserInfo.getProviderId();
		String email = oauth2UserInfo.getEmail();
		String username = provider+"_"+providerId;
		String nickname = oauth2UserInfo.getNickname();
		String password = bcryptPasswordEncoder.encode("겟인데어");
		String role = "ROLE_USER";
		
		
		User user = userRepository.findByUsername(username);
		
		if(user == null) {
			user = User.builder()
					.username(username)
					.password(password)
					.nickname(nickname)
					.email(email)
					.role(role)
					.privider(provider)
					.prividerId(providerId)
					.build();
					
			userRepository.save(user);
		
			
			
		}else {
			
		}
		
		
		
		//세션에 저장한다 라고 생각하자
		//세션에 저장한다 라고 생각하자
		//세션에 저장한다 라고 생각하자
		//세션에 저장한다 라고 생각하자
		//세션에 저장한다 라고 생각하자
		//세션에 저장한다 라고 생각하자
	return new PrincipalDetail(user,oauth2User.getAttributes());
	}
}
/*

구글 로그인 시 가져올수 있는값 
return super.loadUser(userRequest); 가지고 있는값들 

{sub=105880950095432112145, 
name=조민준, 
given_name=민준, 
family_name=조, 
picture=https://lh3.googleusercontent.com/a-/AOh14GgjAfEqrl5bcne5EspPFCv4WXw45wBxHPhN5ziY=s96-c, 
email=jch941022@naver.com, 
email_verified=true, 
locale=ko}



회원 가입 강제 
username = "google_105880950095432112145"
nickname = "조민준"  ----------------- name=조민준, 
password = "암호화"
email = "jch941022@naver.com"
provider = "google"
providerId = "105880950095432112145"
/ROLE_USER










*/