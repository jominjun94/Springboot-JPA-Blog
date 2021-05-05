package com.cos.blog.config.auth;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;


@Service
public class PrincipalDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	// 스프링이 로그인을 가로채서 아이디 패스워드 두개 가로챈다
	//password는 알아서 하고 
	//username 이 있는지만 확인하면된다!
	// PrincipalDetailService 호출시 자동으로  loadUserByUsername 값을 실행시킵니다
												
	
																//form 에서 보낸 값을 비교합니다
	@Override							//form 에서 보낸 name ="username" 값을 받아옵니다 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username);
				
		return new PrincipalDetail(principal); // -> 이때 시큐리티의 세션에 유저가 저장이 된다! UserDetail 타입만 가능하다.
	}

}
