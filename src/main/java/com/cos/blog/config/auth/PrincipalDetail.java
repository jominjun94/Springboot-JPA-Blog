package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blog.model.User;


import lombok.Getter;

//////////////////////////////////////UserDetails, OAuth2User{ 컨트롤러에서 한번 사용하기 위해 타입을 변경해서 만들었다!


/*
 * 
 * 
 * 
 * @GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		Map<String, Object> users	=  principalDetails.getAttributes();
		users.get("email");
		System.out.println(users.get("email"));
		return "유저 페이지입니다.";
	}


 * 
 * 
 * 
 */


//세션에 저장을 위한 값!
// 스프링시큐리티가 로그인요청을 가로채서 로그인을 진해하고 완료가되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션 저장소에 저장을 해준다!
@Getter
public class PrincipalDetail implements UserDetails, OAuth2User{

	
	private Map<String, Object> attributes;
	
	private User user;
	
	

	public PrincipalDetail(User user) {
		super();
		this.user = user;
	}
	
	
	public PrincipalDetail( User user ,Map<String, Object> attributes) {
		super();
		this.attributes = attributes;
		this.user = user;
	}
	

	@Override
	public String getPassword() {
	
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	
	//계정 만료되지 않았는지 리턴한다 (true : 만료안됨)
	@Override
	public boolean isAccountNonExpired() {
	
		return true;
	}

	// 계정이 잠겨잇는지 확인 (true: 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	//비밀번호가 만료되지 않았는지 리턴한다 (true: 만료 안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	//계정 활성화(사용가능)인지 리턴 (true : 활성화)
	@Override
	public boolean isEnabled() {
		
		return true;
	}
	// 계정이 갖고 있는 권한 목록을 리턴한다. (권한이 여러개 있을 수 잇어서 루프를 돌아야한다. 우리는 한개다)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		Collection<GrantedAuthority> collectors = new ArrayList<GrantedAuthority>();
		collectors.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				
				return "ROLE_" + user.getRole(); // ROLE_USER => 무조건 이렇게 해줘야한다!
			}
		});
		
		return collectors;
	}

	@Override
	public Map<String, Object> getAttributes() {
		
		return attributes;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}





	
	
}
