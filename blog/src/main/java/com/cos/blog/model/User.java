package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;



@Entity
@Data
public class User {
	@Id //primary Key //javax.persistence.Id import
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Enumerated(EnumType.STRING)
	private RoleType role; // ROLE_USER, ROLE_ADMIN
	
	@CreationTimestamp
	private Timestamp createDate; //java sql import
}
