package com.xwy.rmi.rpc.MyRpc.client;

/**
 * @Description
 * @author xwy
 * @date 2021/6/30
 * @param
 * @return
 */
public class ClientDemo {

    public static void main(String[] args) {
        RpcClientProxy rpcClientProxy=new RpcClientProxy();

        IHello hello=rpcClientProxy.clientProxy
                (IHello.class,"localhost",8888);
        System.out.println(hello.sayHello("xwy"));
    }
}
