package com.xwy.myBatis.orm.mybatis.aop;


import com.xwy.myBatis.entity.User;
import com.xwy.myBatis.mapper.UserMapper;
import com.xwy.myBatis.sql.SqlSession;

/*
    1.mapper接口的方法需要和sql语句绑定
    2.接口不能被实例化，如何进行调用？
    3.
 */
public class Test0003 {

    //字节码虚拟产生子类
    //匿名内部类
    //动态代理
    public static void main(String[] args) {
        // 使用动态代理虚拟调用方法
        UserMapper userMapper = SqlSession.getMapper(UserMapper.class);
        User selectUser = userMapper.selectUser("张三", 644064);
        System.out.println(
                "结果:" + selectUser.getUserName() + "," + selectUser.getUserAge());
        // // 先走拦截invoke
         int insertUserResult = userMapper.insertUser("张三", 644064);
         System.out.println("insertUserResult:" + insertUserResult);
    }

}
