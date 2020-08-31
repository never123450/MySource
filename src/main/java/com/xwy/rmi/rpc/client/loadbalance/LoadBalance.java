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

public interface LoadBalance {

    String selectHost(List<String> repos);
}
