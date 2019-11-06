package com.xwy.rmi.rpc.client;

import java.lang.reflect.Proxy;

public class RpcClientProxy {

    public <T> T clientProxy(final Class<T> interfceClass, final String host, final int port) {

        return (T) Proxy.newProxyInstance(interfceClass.getClassLoader(),
                new Class[]{interfceClass}, new RemoteInvocationHanler(host, port));
    }


}