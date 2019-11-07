package com.xwy.rmi.rpc.server;

import com.xwy.rmi.rpc.anno.RpcAnnotation;

@RpcAnnotation(value = IHello.class)
public class HelloImplNoVersion2 implements IHello {
    @Override
    public String sayHello(String msg) {
        return "------Hello 8081" + msg;
    }
}