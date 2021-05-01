package com.cos.blog.model;

import java.sql.Timestamp;

import javax.jws.soap.SOAPBinding.Use;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reply {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;//시퀀스 auto_increment
	
	@Column(nullable = false, length = 200)
	private String content;
	
	//어디 게시글
	@ManyToOne // 하나의 Board의 여러개의 Reply이 달린다 
	@JoinColumn(name = "boardId")
	private Board board;
	
	@ManyToOne 
	@JoinColumn(name="userId")
	private User user;
		

	@CreationTimestamp
	private Timestamp createDate;
}
