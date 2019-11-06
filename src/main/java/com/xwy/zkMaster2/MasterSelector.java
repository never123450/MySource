package com.xwy.zkMaster2;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description: 选主的服务
 * @author: xwy
 * @create: 8:40 PM 2019/7/31
 **/

public class MasterSelector {
    private ZkClient zkClient;

    private final static String MASTER_PATH = "/master";//需要争抢的节点

    private IZkDataListener dataListener;//注册节点内容变化

    private UserCenter servier;//其他服务器

    private UserCenter master;//master节点

    private static boolean isRunning = false;

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public MasterSelector(UserCenter servier, ZkClient zkClient) {
        this.servier = servier;
        this.zkClient = zkClient;
        this.dataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                //节点如果被删除，发起选主操作


            }
        };
    }

    public void start() {
        //开始选举
        if (!isRunning) {
            isRunning = true;
            zkClient.subscribeChildChanges(MASTER_PATH, (IZkChildListener) dataListener);//注册节点时间
            chooseMaster();
        }
    }

    public void stop() {
        //停止
        if (isRunning) {
            isRunning = false;
            scheduledExecutorService.shutdown();
            zkClient.unsubscribeChildChanges(MASTER_PATH, (IZkChildListener) dataListener);
            releaseMaster();
        }
    }

    //选master
    private void chooseMaster() {
        if (!isRunning) {
            System.out.println("当前没有服务启动");
            return;
        }
        try {
            zkClient.createEphemeral(MASTER_PATH, servier);
            master = servier;//server节点为master
            System.out.println(master.getMc_name() + "->我现在是master");

            //定时器
            //master释放（master出现故障），每5s释放一次
            scheduledExecutorService.schedule(() -> {
                releaseMaster();
            }, 5, TimeUnit.SECONDS);
        } catch (ZkNodeExistsException e) {
            //master已经存在
            UserCenter userCenter = zkClient.readData(MASTER_PATH, true);
            if (userCenter == null) {
                chooseMaster();//再次获取master
            } else {
                master = userCenter;
            }
        }
    }

    private void releaseMaster() {
        //释放锁
        //判断当前是不是master，只有master才需要释放
        if (checkIsMaster()) {
            zkClient.deleteRecursive(MASTER_PATH);//删除
        }

    }

    private boolean checkIsMaster() {
        //判断当前的server是不是master
        UserCenter userCenter = zkClient.readData(MASTER_PATH);
        if (userCenter.getMc_name().equals(servier.getMc_name())) {
            master = userCenter;
            return true;
        }
        return false;
    }
}