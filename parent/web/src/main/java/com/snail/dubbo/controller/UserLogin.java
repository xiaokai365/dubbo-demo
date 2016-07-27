package com.snail.dubbo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snail.dubbo.pojo.User;
import com.snail.dubbo.service.Userservice;

@Controller
@RequestMapping("/usr")
public class UserLogin {
	
	@Autowired
	private Userservice  userservice;
	
	@RequestMapping("/main")
	public String  main(){
		System.out.println("11111111");
		return "user/main";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String  save(User user){
		userservice.save(user);
		return "success";
	}
	
}
