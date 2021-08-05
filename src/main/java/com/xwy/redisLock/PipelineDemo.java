package com.xwy.redisLock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * @description:
 * @projectName:source
 * @see:com.xwy.redisLock
 * @author:xwy
 * @createTime:5/8/2021 下午9:23
 * @version:1.0
 */
public class PipelineDemo {
    public static void main(String[] args) {
        Jedis jedis = JedisConnectionUtil.getJedis();
        Pipeline pipeline = jedis.pipelined();
        pipeline.set("aa","1");
        pipeline.sync();
    }
}
