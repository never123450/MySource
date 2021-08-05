package com.xwy.redisLock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @projectName:source
 * @see:com.xwy.redisLock
 * @author:xwy
 * @createTime:5/8/2021 下午9:06
 * @version:1.0
 */
public class RedissonLock {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("127.0.0.1");
        RedissonClient redissonClient = Redisson.create(config);
        RLock rLock = redissonClient.getLock("updateOrder");
        try {
            rLock.tryLock(100,10, TimeUnit.SECONDS);
            System.out.println("test");
            Thread.sleep(1000);
            rLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            rLock.unlock();
            redissonClient.shutdown();
        }
    }
}
