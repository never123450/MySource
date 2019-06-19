package com.xwy.SpringXmlParse.proxy;


import com.xwy.SpringXmlParse.service.UserService;

// 静态代理设计模式
public class UserServiceProxy {
	private UserService userService;

	public UserServiceProxy(UserService userService) {
		this.userService = userService;
	}

	public void add() {
		System.out.println("静态代理 开启事务");
		userService.add();
		System.out.println("静态代理  提交事务");
	}

}
