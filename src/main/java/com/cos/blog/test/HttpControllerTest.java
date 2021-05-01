package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpControllerTest {

	// localhost:8000/blog/http/get
	/*
	 *  server:
  		port: 8000
  		servlet:
    	context-path: /blog
	 */
	//public String getTest(@RequestParam int id) {
	@GetMapping("/http/get")
	public String getTest(Member m) {
		//bulider 연습 //bulider 연습 넣고 싶은거만 넣을수 있습니다.
		Member mem = Member.builder().username("민준").password("1234").email("22@naver").build();
		return "get 요청" + mem.getEmail();
	}
	
	
	
	
	@PostMapping("/http/post")
	public String postTest(@RequestBody String text) {
	return "post 요청 이메일은 :" + text;
	//return "post 요청 이메일은 :" + m;
	}
	/*
	 * {
			"id" : 1,
			"username" : "se",
			"password" : 123,
			"email" : "q@w"
	   }
	 */
	
	@PutMapping("/http/put")
	public String putTest() {
		return "put 요청";
	}
	
	
	
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
