package com.xwy.SpringXmlParse;

import com.xwy.SpringXmlParse.proxy.UserServiceProxy;
import com.xwy.SpringXmlParse.service.Impl.UserServiceImpl;
import com.xwy.SpringXmlParse.service.UserService;

public class Test001 {

	public static void main(String[] args) throws Exception {
//		ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("spring.xml");
//		UserService userService = (UserService) app.getBean("userServiceImpl");
//		userService.add();


		UserService userService = new UserServiceImpl();
		// userService.add();
		UserServiceProxy userServiceProxy = new UserServiceProxy(userService);
		userServiceProxy.add();
	}

}
