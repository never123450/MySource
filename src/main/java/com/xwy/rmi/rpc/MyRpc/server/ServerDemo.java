package com.xwy.rmi.rpc.MyRpc.server;

import com.xwy.rmi.rpc.MyRpc.client.IHello;

/**
 * @Description  
 * @author xwy
 * @date 2021/6/30
 * @param  
 * @return 
 */
public class ServerDemo {
    public static void main(String[] args) {
        IHello iHello=new HelloImpl();
        RpcServer rpcServer=new RpcServer();
        rpcServer.publisher(iHello,8888);
    }
}
