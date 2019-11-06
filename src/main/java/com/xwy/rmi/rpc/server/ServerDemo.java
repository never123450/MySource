package com.xwy.rmi.rpc.server;

public class ServerDemo {

    public static void main(String[] args) {
        IHello hello = new Hello();
        RpcServer rpcServer = new RpcServer();
        rpcServer.publisher(hello,8888);

    }
}