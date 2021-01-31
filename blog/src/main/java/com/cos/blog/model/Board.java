package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;
	
	@Column(nullable = false, length = 100) // notNull
	private String title;
	
	@Lob //대용량 데이터
	private String content; // 섬모노트 라이브러리 <html>태그가 섞여서 디자인 됨
	
	@ColumnDefault("0")
	private int count; //조회수
	
//				//만약 OnetoOne이면 한명의 유저는 하나의 게시글만 쓸수있다
//	@ColumnDefault("0")
//	@ManyToOne // Many = Board , User = One -->한명의 유저가 여러개의 글을 쓸수있다
//	@JoinColumn(name = "userId") //userId라는 필드값이 들어감
//	private User user; //필드명은 userId로 만들어지고 방식은 ManyToOne방식을 가지고 자동으로 FK가 만들어진다
//	
	@CreationTimestamp // 현재시간
	private Timestamp createDate;

}
