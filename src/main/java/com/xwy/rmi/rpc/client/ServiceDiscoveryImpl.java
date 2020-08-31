package com.xwy.rmi.rpc.client;

import com.xwy.rmi.rpc.client.loadbalance.LoadBalance;
import com.xwy.rmi.rpc.client.loadbalance.RandomLoadBalance;
import com.xwy.rmi.rpc.zk.ZKConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: xwy
 * @create: 4:10 PM 2020/8/31
 **/

public class ServiceDiscoveryImpl implements IServiceDiscovery {


    List<String> repos = new ArrayList<>();

    private CuratorFramework curatorFramework;

    private String address;

    public ServiceDiscoveryImpl(String address) {
        this.address = address;

        curatorFramework = CuratorFrameworkFactory.builder().
                connectString(address).
                sessionTimeoutMs(4000).
                retryPolicy(new ExponentialBackoffRetry(1000, 10)).build();
        curatorFramework.start();

    }

    @Override
    public String discover(String serviceName) {

        String path = ZKConfig.ZK_REGISTER_PATH + "/" + serviceName;
        try {
            repos = curatorFramework.getChildren().forPath(path);
        } catch (Exception e) {
            throw new RuntimeException("获取子节点异常：" + e);
        }
        //动态发现服务节点的变化
        registerWatcher(path);
        // 负载均衡
        LoadBalance loadBalance = new RandomLoadBalance();

        return loadBalance.selectHost(repos);
    }

    private void registerWatcher(String path) {
        PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework, path, true);
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                repos = curatorFramework.getChildren().forPath(path);
            }
        };
        childrenCache.getListenable().addListener(pathChildrenCacheListener);
        try {
            childrenCache.start();
        } catch (Exception e) {
            throw new RuntimeException("注册Patchchild wathcer异常" + e);
        }
    }
}