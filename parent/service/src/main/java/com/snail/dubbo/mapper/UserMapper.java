package com.snail.dubbo.mapper;

import org.apache.ibatis.annotations.Param;

import com.snail.dubbo.pojo.User;

public interface UserMapper {

	int save(@Param("user")User user);

}
