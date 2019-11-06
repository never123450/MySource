package com.xwy.rmi.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHanler implements InvocationHandler {

    private String host;
    private int port;

    public RemoteInvocationHanler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameters(args);

        TCPTransport tcpTransport = new TCPTransport(host,port);

        return tcpTransport.send(request);
    }
}