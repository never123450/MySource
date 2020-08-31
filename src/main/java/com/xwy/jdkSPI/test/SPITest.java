package com.xwy.jdkSPI.test;

import com.xwy.jdkSPI.service.SomeService;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SPITest {

    public static void main(String[] args) {
        // 加载提供者的配置文件，创建提供者类加载器
        ServiceLoader<SomeService> loader = ServiceLoader.load(SomeService.class);

        // serviceLoader本身就是一个迭代器
        Iterator<SomeService> iterator = loader.iterator();

        System.out.println(iterator.hasNext());

        while (iterator.hasNext()){
            SomeService next = iterator.next();
            next.doSome();
        }
    }


}