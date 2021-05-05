package com.cos.blog.config.oauth;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {
	
	private Map<String, Object> attributesUser;
	
	public NaverUserInfo(Map<String, Object> attributesUser) {
		// TODO Auto-generated constructor stub
		this.attributesUser = attributesUser; 
	}
	
	
	
	
	@Override
	public String getProviderId() {
		// TODO Auto-generated method stub
		return (String)attributesUser.get("id");
	}

	@Override
	public String getProvider() {
		// TODO Auto-generated method stub
		return "naver";
	}

	@Override
	public String getNickname() {
		// TODO Auto-generated method stub
		return (String)attributesUser.get("name");
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return (String)attributesUser.get("email");
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return (String)attributesUser.get("username");
	}

}
