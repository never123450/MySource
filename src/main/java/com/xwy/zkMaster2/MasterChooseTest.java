package com.xwy.zkMaster2;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MasterChooseTest {
    private final static String CONNECTSSTRING = "192.168.11.1:2181,192.168.11.2:2181,192.168.11.3:2181";

    public static void main(String[] args) {
        List<ZkClient> clients = new ArrayList<>();

        try {
            for (int i = 0; i < 10; i++) {
                ZkClient zkClient = new ZkClient(CONNECTSSTRING, 5000, 5000, new SerializableSerializer());

                UserCenter userCenter = new UserCenter();
                userCenter.setMc_id(i);
                userCenter.setMc_name("客户端：" + i);

                MasterSelector selector = new MasterSelector(userCenter,zkClient);
                selector.start();
                TimeUnit.SECONDS.sleep(4);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}