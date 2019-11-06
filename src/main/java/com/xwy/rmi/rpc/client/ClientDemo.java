package com.xwy.rmi.rpc.client;

import com.xwy.rmi.rpc.server.IHello;

public class ClientDemo {
    public static void main(String[] args) {

        RpcClientProxy rpcClientProxy = new RpcClientProxy();

        IHello hello = rpcClientProxy.clientProxy(IHello.class, "localhost", 8888);
        hello.sayHello("ni hao");
    }
}