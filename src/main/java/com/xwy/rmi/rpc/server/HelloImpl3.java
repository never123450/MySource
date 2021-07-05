package com.xwy.rmi.rpc.server;

import com.xwy.rmi.rpc.anno.RpcAnnotation;

@RpcAnnotation(value = IHello.class)
public class HelloImpl3 implements IHello {
    @Override
    public String sayHello(String msg) {
        return "Hello  無版本 " + msg;
    }
}