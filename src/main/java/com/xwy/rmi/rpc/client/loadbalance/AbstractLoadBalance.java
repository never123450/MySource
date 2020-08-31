package com.xwy.rmi.rpc.client.loadbalance;

import java.util.List;

/**
 *
 * @description: 
 *
 * @author: xwy
 *
 * @create: 4:16 PM 2020/8/31
**/

public abstract class AbstractLoadBalance implements LoadBalance {

    protected abstract String doSelect(List<String> repos);

    @Override
    public String selectHost(List<String> repos) {

        if (repos == null || repos.size() == 0) {
            return null;
        }
        if (repos.size() == 1) {
            return repos.get(0);
        }
        return doSelect(repos);
    }
}