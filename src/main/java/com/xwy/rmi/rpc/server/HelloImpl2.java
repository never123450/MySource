package com.xwy.rmi.rpc.server;

import com.xwy.rmi.rpc.anno.RpcAnnotation;

@RpcAnnotation(value = IHello.class,version = "2.0")
public class HelloImpl2 implements IHello {
    @Override
    public String sayHello(String msg) {
        return "Hello  2.0: " + msg;
    }
}