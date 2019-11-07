package com.xwy.rmi.rpc.server;

import com.xwy.rmi.rpc.zk.IRegisterCenter;
import com.xwy.rmi.rpc.zk.RegisterCenterImpl;

import java.io.IOException;

public class LBServerDemo2 {

    // /registers/com.xwy.rmi.rpc.server.IHello-1.0/127.0.0.1:8082
    public static void main(String[] args) throws IOException {

        IHello hello2 = new HelloImplNoVersion2();
        IRegisterCenter registerCenter = new RegisterCenterImpl();

        RpcServer rpcServer = new RpcServer(registerCenter, "127.0.0.1:8082");
        rpcServer.bind(hello2);
        rpcServer.publisher();
        System.in.read();

        // 服务注册成功：/registers/com.xwy.rmi.rpc.server.IHello-1.0/127.0.0.1:8080
        // 服务注册成功：/registers/com.xwy.rmi.rpc.server.IHello-2.0/127.0.0.1:8080

        /*
            [zk: localhost:2181(CONNECTED) 38] ls /registers/com.xwy.rmi.rpc.server.IHello
            [127.0.0.1:8081, 127.0.0.1:8082]
         */
    }
}