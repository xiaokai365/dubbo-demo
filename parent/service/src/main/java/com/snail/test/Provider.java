package com.snail.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring-service.xml" });
		context.start();
		System.out.println("请按任意键退出");
		System.in.read(); // 按任意键退出
	}

}
