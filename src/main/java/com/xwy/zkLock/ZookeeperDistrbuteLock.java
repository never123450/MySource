
package com.xwy.zkLock;

import java.util.concurrent.CountDownLatch;

import org.I0Itec.zkclient.IZkDataListener;


public class ZookeeperDistrbuteLock extends ZookeeperAbstractLock {

	@Override
	boolean tryLock() {
		try {
			zkClient.createEphemeral(lockPath);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	void waitLock() {

		// 使用zk临时事件监听
		IZkDataListener iZkDataListener = new IZkDataListener() {

			public void handleDataDeleted(String path) throws Exception {
				if (countDownLatch != null) {
					countDownLatch.countDown();
				}
			}

			public void handleDataChange(String arg0, Object arg1) throws Exception {

			}
		};
		// 注册事件通知
		zkClient.subscribeDataChanges(lockPath, iZkDataListener);
		if (zkClient.exists(lockPath)) {
			countDownLatch = new CountDownLatch(1);
			try {
				countDownLatch.await();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		// 监听完毕后，移除事件通知
		zkClient.unsubscribeDataChanges(lockPath, iZkDataListener);
	}

}
