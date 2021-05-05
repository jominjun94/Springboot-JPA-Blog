package com.cos.blog.config.oauth;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
	
	
	
	
	
	private Map<String, Object> attributesUser;
	
	public KakaoUserInfo(Map<String, Object> attributesUser) {
		// TODO Auto-generated constructor stub
		this.attributesUser = attributesUser; 
	}
	
	
	
	
	@Override
	public String getProviderId() {
		// TODO Auto-generated method stub
		// 들어올때 int 형으로 들어와서 id string으로 변형
		//String.valueOf(attributesUser.get("id"));
	
		return String.valueOf(attributesUser.get("id"));
	}

	@Override
	public String getProvider() {
		// TODO Auto-generated method stub
		return "kakao";
	}

	@Override
	public String getNickname() {
		// TODO Auto-generated method stub
	 Map<String, Object> properties	=  (Map<String, Object>) attributesUser.get("properties");
	 properties.get("nickname");
		
		return (String)properties.get("nickname");
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
	Map<String, Object> kakaoAccount	=  (Map<String, Object>) attributesUser.get("kakao_account");
	
	kakaoAccount.get("email");
	
		return (String)kakaoAccount.get("email");
	}

	@Override
	public String getUsername() {
		Map<String, Object> kakaoAccount	=  (Map<String, Object>) attributesUser.get("kakao_account");
		
		kakaoAccount.get("email");
		
			return (String)kakaoAccount.get("email");
	}

}
