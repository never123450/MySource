package com.xwy.rmi.rpc.client;

import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: xwy
 * @create: 4:23 PM 2020/8/31
 **/

public class RpcClientProxy {

    private IServiceDiscovery serviceDiscovery;

    public RpcClientProxy(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public <T> T clientProxy(final Class<T> interfceClass, String version) {

        return (T) Proxy.newProxyInstance(interfceClass.getClassLoader(),
                new Class[]{interfceClass}, new RemoteInvocationHanler(serviceDiscovery, version));
    }


}