package com.xwy.rmi.rpc.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @description:
 * @author: xwy
 * @create: 3:11 PM 2020/8/31
 **/

public class RegisterCenterImpl implements IRegisterCenter {

    private CuratorFramework curatorFramework;

    {
        curatorFramework = CuratorFrameworkFactory.builder().
                connectString(ZKConfig.CONNECTION_STR).
                sessionTimeoutMs(4000).
                retryPolicy(new ExponentialBackoffRetry(1000, 10)).build();
        curatorFramework.start();
    }

    @Override
    public void register(String serviceName, String serviceAddress) {

        //注册相应的服务
        String servicePath = ZKConfig.ZK_REGISTER_PATH + "/" + serviceName;
        try {
            //判断 /registrys/product-service 是否存在，不存在则创建   持久节点
            if (curatorFramework.checkExists().forPath(servicePath) == null) {
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                        .forPath(servicePath, "0".getBytes());
            }

            String addressPath = servicePath + "/" + serviceAddress;
            System.out.println(addressPath);

            // /registrys/product-service/com.xwy.rmi.rpc.server.IHello/127.0.0.1:2181
            // /registrys/product-service/com.xwy.rmi.rpc.server.IHello/192.0.0.1:2181
            // /com.xwy.rmi.rpc.server.IHello    /127.0.0.1:2181 临时节点
            String rsNode = curatorFramework.create().withMode(CreateMode.EPHEMERAL)
                    .forPath(addressPath, "0".getBytes());
            System.out.println("服务注册成功：" + rsNode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}