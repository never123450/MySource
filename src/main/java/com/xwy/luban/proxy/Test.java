package com.xwy.luban.proxy;

import sun.misc.ProxyGenerator;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;


public class Test {
    public static void main(String[] args) {
//          //自定义
//        LubanDao proxy = (LubanDao) ProxyUtil.newInstance(LubanDao.class,new TestCustomHandler(new LubanDaoImpl()));
//        try {
//            proxy.proxy();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        byte[] bytes=ProxyGenerator.generateProxyClass("$Proxy18",new Class[]{LubanDao.class});

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("d:\\$Proxy18.class");
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //        System.out.println(proxy.proxy());
        LubanDao jdkproxy = (LubanDao) Proxy.newProxyInstance(Test.class.getClassLoader(),
                new Class[]{LubanDao.class},new LubanInvocationHandler(new LubanDaoImpl()));

        //jdkproxy.query();
        try {
            jdkproxy.proxy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
