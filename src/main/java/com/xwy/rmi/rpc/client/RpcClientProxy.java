package com.xwy.rmi.rpc.client;

import java.lang.reflect.Proxy;

public class RpcClientProxy {

    private IServiceDiscovery serviceDiscovery;

    public RpcClientProxy(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public <T> T clientProxy(final Class<T> interfceClass,String version) {

        return (T) Proxy.newProxyInstance(interfceClass.getClassLoader(),
                new Class[]{interfceClass}, new RemoteInvocationHanler(serviceDiscovery,version));
    }


}