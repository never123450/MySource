package com.xwy.rmi.rpc.server;

import com.xwy.rmi.rpc.anno.RpcAnnotation;

@RpcAnnotation(value = IHello.class,version = "1.0")
public class HelloImpl implements IHello {
    @Override
    public String sayHello(String msg) {
        return "Hello  1.0: " + msg;
    }
}