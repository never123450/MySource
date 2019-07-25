
package com.xwy.redisLock;

import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


public class LockRedis {
    // redis线程池
    private JedisPool jedisPool;
    // 同时在redis上创建相同的一个key 相同key 名称
    private String redislockKey = "redis_lock";

    public LockRedis(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
    // redis 以key （redislockKey） 和value（随机不能够重复数字 锁的id）方式进行存储

    // redis实现分布式锁 有两个超时 时间问题
    /**
     * 两个超时时间含义：<br>
     * 1.在获取锁之前的超时时间----在尝试获取锁的时候，如果在规定的时间内还没有获取锁，直接放弃。<br>
     * 2.在获取锁之后的超时时间---当获取锁成功之后，对应的key 有对应有效期，对应的key 在规定时间内进行失效
     */

    /**
     * acquireTimeout
     *
     * @param acquireTimeout 在获取锁之前的超时时间
     * @param timeOut        在获取锁之后的超时时间
     */
    // 基于redis实现分布式锁代码思路 核心方法 获取锁 、释放锁
    public String getRedisLock(Long acquireTimeout, Long timeOut) {
        Jedis conn = null;

        try {
            // 1.建立redis连接
            conn = jedisPool.getResource();
            // 2.定义 redis 对应key 的value值( uuid) 作用 释放锁 随机生成value
            String identifierValue = UUID.randomUUID().toString();

            // 3.定义在获取锁之后的超时时间
            int expireLock = (int) (timeOut / 1000);// 以秒为单位
            // 4.定义在获取锁之前的超时时间
            // 5.使用循环机制 如果没有获取到锁，要在规定acquireTimeout时间 保证重复进行尝试获取锁（乐观锁）
            // 使用循环方式重试的获取锁
            Long endTime = System.currentTimeMillis() + acquireTimeout;
            while (System.currentTimeMillis() < endTime) {
                // 获取锁
                // 6.使用setnx命令插入对应的redislockKey ，如果返回为1 成功获取锁
                if (conn.setnx(redislockKey, identifierValue) == 1) {
                    // 设置对应key的有效期
                    conn.expire(redislockKey, expireLock);
                    return identifierValue;
                }

                // 为什么获取锁之后，还要设置锁的超时时间 目的是为了防止死锁
                // zookeeper实现分布式锁通过什么方式 防止死锁 设置session 有效期
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return null;

    }

    // 如果直接使用 conn.del(redislockKey); 保证对应是自己的创建redislockKey 删除对应自己的。

    // 释放redis锁
    public void unRedisLock(String identifierValue) {
        Jedis conn = null;
        // 1.建立redis连接
        conn = jedisPool.getResource();
        try {
            // 如果该锁的id 等于identifierValue 是同一把锁才可以删除
            if (conn.get(redislockKey).equals(identifierValue)) {
                System.out.println("释放锁..." + Thread.currentThread().getName() + ",identifierValue:" + identifierValue);
                conn.del(redislockKey);
            }
        } catch (Exception e)

        {
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        // 释放锁有两种 key自动有有效期
        // 整个程序执行完毕情况下，删除对应key

    }
}
