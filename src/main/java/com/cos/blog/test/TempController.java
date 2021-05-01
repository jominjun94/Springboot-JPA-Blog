package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempController {
	
	
	// - > src/main/resoures/static -> 정적인 파일은 가능 ex> html,png 등등
	//http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		return "home.html";
	}
	// - > src/main/resoures/static -> 정적인 파일은 가능 ex> html,png 등등
	@GetMapping("/temp/png")
	public String tempPng() {
		return "fucktomcatlogin.png";
	}
	// jsp 파일은 정적파일이 아닌 동적 파일이므로 
	//src main 밑으로 webapp/WEB-INF/views 폴더 생성후 JSP 저장
	
	@GetMapping("/temp/sein")
	public String tempJsp() {
		
		//prefix : /WEB-INF/views/
		//suffix : .jsp
		//풀네임 :  /WEB-INF/views/test.jsp
		return "test";
	}
}
