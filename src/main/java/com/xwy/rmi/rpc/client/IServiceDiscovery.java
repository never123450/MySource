package com.xwy.rmi.rpc.client;

/**
 *
 * @description: 
 *
 * @author: xwy
 *
 * @create: 4:10 PM 2020/8/31
**/

public interface IServiceDiscovery {

    /**
     * 根据请求的服务地址，获得对应的调用地址
     *
     * @param serviceName
     * @return
     */
    String discover(String serviceName);
}
