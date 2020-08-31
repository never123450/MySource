package com.xwy.rmi.rpc.zk;

/**
 *
 * @description:
 *
 * @author: xwy
 *
 * @create: 3:11 PM 2020/8/31
**/

public interface IRegisterCenter {

    /**
     * 注册服务名称和服务地址
     * @param serviceName
     * @param serviceAddress
     */
    void register(String serviceName,String serviceAddress);
}