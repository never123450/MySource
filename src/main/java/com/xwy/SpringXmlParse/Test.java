package com.xwy.SpringXmlParse;


import com.xwy.SpringXmlParse.entity.User;

public class Test {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
		User user = (User) applicationContext.getBean("user");
		System.out.println(user);
	}

}
