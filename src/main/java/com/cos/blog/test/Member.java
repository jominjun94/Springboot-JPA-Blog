package com.cos.blog.test;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//@Getter
//@Setter
@Data //getter + setter
//@AllArgsConstructor // 전부 생성자
@NoArgsConstructor // 빈 생성자
public class Member {

		private int id;
		private String username;
		private String password;
		private String email;
	
		
	
    	@Builder
	    public Member(int id, String username, String password, String email) {
		 this.id = id;
		 this.username = username;
		 this.password = password;
		 this.email = email;
	}
	
	/*
	 * //bulider 연습 넣고 싶은거만 넣을수 있습니다.
	 * 순서를 안지키고 할수 있습니다.
		Member mem = Member.builder().username("민준").password("1234").email("22@naver").build();
	 */
	
	
	
	
}