package com.xwy.redisLock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @description:
 * @projectName:source
 * @see:com.xwy.redisLock
 * @author:xwy
 * @createTime:5/8/2021 下午8:10
 * @version:1.0
 */
public class JedisConnectionUtil {
    private static JedisPool pool = null;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);
        pool = new JedisPool(jedisPoolConfig,"127.0.0.1",6379);
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }
}
