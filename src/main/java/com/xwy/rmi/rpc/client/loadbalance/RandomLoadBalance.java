package com.xwy.rmi.rpc.client.loadbalance;

import java.util.List;
import java.util.Random;

/**
 *
 * @description: 
 *
 * @author: xwy
 *
 * @create: 4:22 PM 2020/8/31
**/

public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> repos) {
        int len = repos.size();
        Random random = new Random();
        return repos.get(random.nextInt(len));
    }
}