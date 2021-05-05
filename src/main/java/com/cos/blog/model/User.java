package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 
 	jpa:
    open-in-view: true
    hibernate:
      ddl-auto: create ----------- 새로운 table을 생성해줍니다
      naming:
        
        
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
      	private int id; 그대로 table을 생성 해주겠다
      	만약 SpringPhysiaNamingStrategy를 쓰게 되면 
        필드명 myEmail -> 칼럼명이 my_email 로 저장 되게 됩니다. 
      
      
     use-new-id-generator-mappings: false jpa의 넘버링 전략을 따라가자 읺는다
     show-sql: true console 창에 나타냅니다
     properties:
     hibernate.format_sql: true 원래는 한줄로 보이는걸 잘 정리해서 보여준다
 
 */


@Data
@NoArgsConstructor
@Entity // User클래스가 알아서 자동으로 mysql에 테이블이 생성된다
//@DynamicInsert // insert시에 null인 필드를 제외 시켜준다! 덕지덕지 붙이는건 좋은건 아니다 일단 빼고 작업하자.
public class User {

	@Id // primaryKey //   use-new-id-generator-mappings: false 
	@GeneratedValue(strategy = GenerationType.IDENTITY)// 오라클이면 시퀀스 mysql increment
	private int id; //시퀀스 , auto_increment
	
	
	@Column(nullable = true, length = 100 , unique=true)
	private String username; // 아이디
	
	@Column(nullable= true, length = 100)
	private String password;
	
	@Column(nullable= true, length = 100)
	private String email;
	
	//DB에는 RoleType 이라는게 없다.
	//@ColumnDefault("'user'")
	//@Enumerated(EnumType.STRING)
	//private RoleType role; // 매니저 , 유저 타입을 3개 
	private String role;
	private String privider; //google
	private String prividerId; 
	private String nickname;
	//private String oauth; // kakao , google 등등 null 허용
	
	@CreationTimestamp // 시간 자동으로 입력
	private Timestamp createDate;
	
	@Builder
	public User(String nickname, String username, String password, String email, String role, String privider,
			String prividerId, Timestamp createDate) {
		
		this.nickname = nickname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.privider = privider;
		this.prividerId = prividerId;
		this.createDate = createDate;
	
	
}




	
}