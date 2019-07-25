
package com.xwy.zkLock;

import java.util.concurrent.CountDownLatch;

import org.I0Itec.zkclient.ZkClient;

public abstract class ZookeeperAbstractLock implements ExtLock {
	// 集群连接地址
	protected String CONNECTION = "127.0.0.1:2181";
	// zk客户端连接
	protected ZkClient zkClient = new ZkClient(CONNECTION);
	// path路径
	protected String lockPath = "/path";
	protected CountDownLatch countDownLatch = new CountDownLatch(1);

	public void getLock() {
		if (tryLock()) {
			System.out.println("####获取锁成功######");
		} else {
			waitLock();
			getLock();
		}
	}

	// 获取锁
	abstract boolean tryLock();

	// 等待锁
	abstract void waitLock();

	public void unLock() {
		if (zkClient != null) {
			System.out.println("#######释放锁#########");
			zkClient.close();
		}
	}

}
