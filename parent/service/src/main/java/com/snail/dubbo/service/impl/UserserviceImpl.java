package com.snail.dubbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snail.dubbo.mapper.UserMapper;
import com.snail.dubbo.pojo.User;
import com.snail.dubbo.service.Userservice;

@Service("userservice")
public class UserserviceImpl implements Userservice{

	@Autowired
	private UserMapper userMapper; 
	
	public int save(User user) {
		
		return userMapper.save(user);
	}

}
