package com.xwy.zkLock2;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DistributeLock {

    private static final String ROOT_LOCKS = "/LOCKS";

    private ZooKeeper zooKeeper;

    private int sessionTimeout = 5000;//会话超时时间

    private String lockID;//记录锁节点id

    private final static byte[] data = {1, 2};//节点的数据

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public DistributeLock() throws IOException, InterruptedException {
        this.zooKeeper = ZookeeperClient.getInstance();
        this.sessionTimeout = ZookeeperClient.getSessionTimeout();
    }


    //获取锁
    public synchronized boolean lock() throws KeeperException, InterruptedException {
        lockID = zooKeeper.create(ROOT_LOCKS + "/", data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        System.out.println(Thread.currentThread().getName() + "->成功创建了lock节点{" + lockID + "}，开始竞争锁");
        List<String> childrenNodes = zooKeeper.getChildren(ROOT_LOCKS, true);//获取根节点下的所有子节点

        //排序，从小到大
        SortedSet<String> sortedSet = new TreeSet<String>();
        for (String children : childrenNodes) {
            sortedSet.add(ROOT_LOCKS + "/" + children);
        }
        String first = sortedSet.first();//拿到最小的节点
        if (lockID.equals(first)) {
            //表示当前就是最小的节点
            System.out.println(Thread.currentThread().getName() + "->成功获取锁，lock节点为：[" + lockID + "]");
            return true;
        }
        SortedSet<String> lessThanLockId = sortedSet.headSet(lockID);
        if (!lessThanLockId.isEmpty()) {
            String prevLockId = lessThanLockId.last();//拿到比当前lockId更小的上一个节点
            zooKeeper.exists(prevLockId, new LockWatcher(countDownLatch));
            countDownLatch.await(sessionTimeout, TimeUnit.MILLISECONDS);
            //上面这个段代码意味着如果会话超时或者节点被删除了
            System.out.println(Thread.currentThread().getName() + "成功获取锁{" + lockID + "}");
            return true;
        }

        return false;
    }

    //释放锁
    public synchronized boolean unlock() {
        try {
            zooKeeper.delete(lockID, -1);
            System.out.println(Thread.currentThread().getName() + "开始释放锁：{" + lockID + "}");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                DistributeLock lock = null;
                try {
                    lock = new DistributeLock();
                    latch.countDown();
                    latch.await();
                    lock.lock();
                    Thread.sleep(random.nextInt(50));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (lock != null) {
                        lock.unlock();
                    }
                }
            });
        }
    }

}