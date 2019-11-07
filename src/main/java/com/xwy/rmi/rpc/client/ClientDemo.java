package com.xwy.rmi.rpc.client;

import com.xwy.rmi.rpc.server.IHello;
import com.xwy.rmi.rpc.zk.ZKConfig;

public class ClientDemo {
    public static void main(String[] args) {

        IServiceDiscovery serviceDiscovery = new ServiceDiscoveryImpl(ZKConfig.CONNECTION_STR);

        RpcClientProxy rpcClientProxy = new RpcClientProxy(serviceDiscovery);

//        IHello hello = rpcClientProxy.clientProxy(IHello.class, "2.0");// 支持版本

        for (int i=0;i<10;i++){
            IHello hello = rpcClientProxy.clientProxy(IHello.class, null);// 支持版本
            System.out.println(hello.sayHello("ni hao"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}