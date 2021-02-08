package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthTestController {
	
	@RequestMapping(value = "/user/user")
	public String authUser() {
		return "/authTest/user";
	}
	
	@RequestMapping(value = "/admin/admin")
	public String authAdmin() {
		return "/authTest/admin";
	}
}
