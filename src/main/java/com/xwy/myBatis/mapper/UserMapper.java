package com.xwy.myBatis.mapper;


import com.xwy.myBatis.entity.User;
import com.xwy.myBatis.orm.annotation.ExtInsert;
import com.xwy.myBatis.orm.annotation.ExtParam;
import com.xwy.myBatis.orm.annotation.ExtSelect;

public interface UserMapper {

    @ExtInsert("insert into user(userName,userAge) values(#{userName},#{userAge})")
    public int insertUser(@ExtParam("userName") String userName, @ExtParam("userAge") Integer userAge);

    @ExtSelect("select * from User where userName=#{userName} and userAge=#{userAge} ")
    User selectUser(@ExtParam("userName") String name, @ExtParam("userAge") Integer userAge);

}
