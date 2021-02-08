package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;


@Builder
@Entity
@Data
public class User {
	@Id //primary Key //javax.persistence.Id import
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;
	
	private String provider;
	
	private String providerId;
	
	@OneToMany(mappedBy="user")
	private List<Board> board = new ArrayList<Board>();
	
	private String role; // ROLE_USER, ROLE_ADMIN
	
	@CreationTimestamp
	private Timestamp createDate; //java sql import
}
