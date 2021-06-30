package com.xwy.rmi.rpc.MyRpc.server;

import com.xwy.rmi.rpc.MyRpc.client.IHello;

/**
 * @Description
 * @author xwy
 * @date 2021/6/30
 * @param
 * @return
 */
public class HelloImpl implements IHello {
    @Override
    public String sayHello(String msg) {
        return "Hello , "+msg;
    }
}
