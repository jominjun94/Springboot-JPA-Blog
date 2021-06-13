package com.cos.blog.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;
import com.cos.blog.config.oauth.PrincipalOauth2UserService;






//이거 3개는 필수!

//빈등록
@Configuration
@EnableWebSecurity// 세큐리티 필터 등록 하기
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한및 인증을 미리 체크 하겠다
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	
	@Autowired
	private PrincipalDetailService principalDetailSevirce;
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	// 시큐리티는 필터랑 인터셉터를 다가지고 동작한다!
	
	// 스프링이 로그인 요청을 가로챌 때, username, password 변수 2개를 가로채는데
	// password 부분 처리는 알아서 함.
	// username이 DB에 있는지만 확인해주면 됨. -> 이거 안해도 잘 돌아간다!
	//@Override
	//protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	//	auth.userDetailsService(principalDetailSevirce).passwordEncoder(encodePWD());
		//auth.userDetailsService(null) 이부븐은 따로 메서드 따야한다.
	//}
	
	//1. 로그인 요청 -> username, password 변수 2개를 가로채는데 ->인증필터 userpasswordautentictoken 생성 lala 1234 로 토큰 생성 
	//2  AuthenticationManagerBuilder 던져 객체 가 생성됨 -> 세션에 시큐리티 컨텍스트 생성 
	//3.  lala 를 detail service에 보낸다 만약에 있으면 ->  1234 를 인코딩 해서 db 확인 
	//4 . 시큐리티 컨텍스트 에 Authentication를 담는다 끝

	
	//
	
	
	//스프링 세큐리티
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http // xss 자바스크립트 공격 
		.csrf().disable() // 토큰 비활성화 테스트시 사용해야함  // 공격활성화 막기 위한 것이라 잠깐 막아둠 
		.authorizeRequests() // 요청이 들어오묜 
		.antMatchers("/","/auth/**","/js/**","/css/**","/image/**","/dummy/**") // 여기는 열어둔다
		.permitAll() // 이 주소는 허가하고
		.anyRequest() // 나머지 주소는 피터링을 해워야한다
		.authenticated()
			.and()
			.formLogin()
			.loginPage("/auth/loginForm")  // 실패한 페이지를 위임한 페이지
				.loginProcessingUrl("/auth/loginProc")// url 로그인 요청이 form 요청으로 들어오면 
				.defaultSuccessUrl("/") // 성공시 이동 경로
				.and()
				.oauth2Login()
				//oauth2 라이브러리를 사용시 엑스스토큰과 사용자 정보를 한방에 받아온다
				.userInfoEndpoint()
				.userService(principalOauth2UserService);
			//로그인 성공시
				//.failureUrl("/fail"); // 로그인 실패시
	}
	
}
