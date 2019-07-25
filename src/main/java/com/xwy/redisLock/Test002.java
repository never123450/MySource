
package com.xwy.redisLock;

public class Test002 {

	public static void main(String[] args) {
		LockService lockService = new LockService();
		for (int i = 0; i < 50; i++) {
			new ThreadRedis(lockService).start();
		}
	}

}
