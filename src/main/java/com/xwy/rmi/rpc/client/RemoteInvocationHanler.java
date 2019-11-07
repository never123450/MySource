package com.xwy.rmi.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHanler implements InvocationHandler {

//    private String host;
//    private int port;
//
//    public RemoteInvocationHanler(String host, int port) {
//        this.host = host;
//        this.port = port;
//    }

    private IServiceDiscovery serviceDiscovery;
    private String version;

    public RemoteInvocationHanler(IServiceDiscovery serviceDiscovery,String version) {
        this.serviceDiscovery = serviceDiscovery;
        this.version = version;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameters(args);
        request.setVersion(version);

        //根据接口名称得到对应的服务地址
        String className = request.getClassName();
        if (version != null && !version.equals("")){
            className = className + "-" + version;
        }
        String serviceAddress = serviceDiscovery.discover(className);


        TCPTransport tcpTransport = new TCPTransport(serviceAddress);

        return tcpTransport.send(request);
    }
}