package com.xwy.luban.spring.test;


import com.xwy.luban.spring.service.UserService;
import com.xwy.luban.spring.util.BeanFactory;

public class Test {
    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory("spring.xml");

        UserService service = (UserService) beanFactory.getBean("service");

        service.find();
    }
}
