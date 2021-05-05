package com.cos.blog.config.oauth;

public interface OAuth2UserInfo {

	
	String getProviderId();
	String getProvider();
	String getNickname();
	String getEmail();
	String getUsername();
}
