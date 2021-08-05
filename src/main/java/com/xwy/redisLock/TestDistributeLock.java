package com.xwy.redisLock;

/**
 * @description:
 * @projectName:source
 * @see:com.xwy.redisLock
 * @author:xwy
 * @createTime:5/8/2021 下午8:35
 * @version:1.0
 */
public class TestDistributeLock extends Thread {
    @Override
    public void run() {
        while (true) {
            DistributeLock distributeLock = new DistributeLock();
            String rs = distributeLock.acquireLock("updateOrder", 2000, 5000);
            if (rs != null) {
                System.out.println(Thread.currentThread().getName() + "=>成功获取锁：" + rs);
                try {
                    Thread.sleep(1000);
                    distributeLock.relaeseLock("updateOrder", rs);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static void main(String[] args) {
        TestDistributeLock testDistributeLock = new TestDistributeLock();
        for (int i = 0; i < 10; i++) {
            new Thread(testDistributeLock, "tName:" + i).start();
        }
    }
}
