package com.xwy.luban.spring.service;


import com.xwy.luban.spring.dao.UserDao;

public class UserServiceImpl implements UserService {

    UserDao dao;

    @Override
    public void find() {
        System.out.println("service");
        dao.query();
    }

    //public void setDao(UserDao dao) {
       // this.dao = dao;
   // }
}
