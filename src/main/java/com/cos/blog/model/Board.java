package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto _ increment
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob//대용량 데이터 저장 
	private String content;// 섬머노트라이브러리 <html> 태그가 섞여서 디자인이 됨.
	

	private int count;// 조회수
	
	@ManyToOne(fetch= FetchType.EAGER) // Many = Board , User = One//(fetch= FetchType.EAGER) 무조건 들고오기 안붙여도 되는데 이건 select 하면 가져 오겠다.
	@JoinColumn(name = "userId")
	private User user; // DB 오브젝트를 저장 할수 없다.FK 자바는 오브젝트를 저장할수 있다.
	
	// cascade = CascadeType.REMOVE) board 삭제시 댓글 관련도 전부 날려버릴것이다 설정한것!
	@OneToMany(mappedBy = "board",fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)// mapperBy 연관관계의 주인이 아니다.(난 FK가 아니에요) DB에 컬럼 안만들어요.fetch = FetchType.LAZY 들고 올수도 있음 안들고 와도됨 안붙여도 되는데 붙이면 가져올수도 안가져올수도 잇다.
	@JsonIgnoreProperties({"board"}) // reply의 board를 찾지 않는다! 무한 참조 방지
	@OrderBy("id desc")
	private List<Reply> replys; // 여기서만 무시하고 다른곳에선는 무시하지 않고 실행시킨다
	
	
	@CreationTimestamp
	private Timestamp createDate;
}
