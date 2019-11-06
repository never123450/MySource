package com.xwy.rmi.rpc.server;

public class Hello implements IHello {
    @Override
    public String sayHello(String msg) {
        return "Hello : " + msg;
    }
}