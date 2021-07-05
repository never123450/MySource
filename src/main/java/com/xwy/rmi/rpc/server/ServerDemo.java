package com.xwy.rmi.rpc.server;

import com.xwy.rmi.rpc.zk.IRegisterCenter;
import com.xwy.rmi.rpc.zk.RegisterCenterImpl;

import java.io.IOException;

public class ServerDemo {

    // /registers/com.xwy.rmi.rpc.server.IHello-1.0/127.0.0.1:8080
    public static void main(String[] args) throws IOException {
//        IHello hello = new HelloImpl();
//        RpcServer rpcServer = new RpcServer();
//        rpcServer.publisher(hello,8888);

        IHello hello = new HelloImpl();
        IHello hello2 = new HelloImpl2();
        IHello hello3 = new HelloImpl3();

        IRegisterCenter registerCenter = new RegisterCenterImpl();

        RpcServer rpcServer = new RpcServer(registerCenter, "127.0.0.1:8081");
        rpcServer.bind(hello,hello2,hello3);
        rpcServer.publisher();
        System.in.read();

        // 服务注册成功：/registers/com.xwy.rmi.rpc.server.IHello-1.0/127.0.0.1:8080
        // 服务注册成功：/registers/com.xwy.rmi.rpc.server.IHello-2.0/127.0.0.1:8080

    }
}