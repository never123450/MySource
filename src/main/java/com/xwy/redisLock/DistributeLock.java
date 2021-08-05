package com.xwy.redisLock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.UUID;

/**
 * @description: 锁
 * @projectName:source
 * @see:com.xwy.redisLock
 * @author:xwy
 * @createTime:5/8/2021 下午8:04
 * @version:1.0
 */
public class DistributeLock {

    /**
     * @param lockName       锁的名称
     * @param acquireTimeout 获得锁的超时时间
     * @param lockTime       锁本身的过期时间
     * @return java.lang.String
     * @Description 获取锁
     * @author xwy
     * @date 5/8/2021
     */
    public String acquireLock(String lockName, long acquireTimeout, long lockTime) {
        String identifire = UUID.randomUUID().toString();//保证释放锁的时候是同一个持有锁的人
        String lockKey = "lock:" + lockName;
        int lockExpire = (int) (lockTime / 1000);
        Jedis jedis = null;
        try {
            jedis = JedisConnectionUtil.getJedis();

            long end = System.currentTimeMillis() + acquireTimeout;
            // 获取锁的限定时间
            while (System.currentTimeMillis() < end) {
                if (jedis.setnx(lockKey, identifire) == 1) {//设置成功
                    jedis.expire(lockKey, lockExpire);//设置超时时间
                    return identifire;//获得锁成功
                }
                if (jedis.ttl(lockKey) == -1) {
                    jedis.expire(lockKey, lockExpire);//设置超时时间
                }
                try {
                    // 等待片刻后进行获取锁的重试
                    ThreadRedis.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            jedis.close();
        }
        return null;
    }

    /**
     * @param
     * @return boolean
     * @Description 获取锁
     * @author xwy
     * @date 5/8/2021
     */
    public boolean relaeseLock(String lockName, String identifier) {
        String lockKey = "lock:" + lockName;
        Jedis jedis = null;
        boolean isRelease = false;
        try {
            jedis = JedisConnectionUtil.getJedis();
            while (true) {
                jedis.watch(lockKey);
                //判断是否为同一把锁
                if (identifier.equals(jedis.get(lockKey))) {
                    Transaction transaction = jedis.multi();
                    transaction.del(lockKey);
                    if (transaction.exec().isEmpty()) {
                        continue;
                    }
                    isRelease = true;
                }
                jedis.unwatch();
                break;
            }
        } finally {
            jedis.close();
        }
        return isRelease;
    }

    public boolean releaseLockWithLua(String lockName, String identifier) {
        Jedis jedis = JedisConnectionUtil.getJedis();
        String lockKey = "lock:" + lockName;
        String lua = "if redis.call(\"get\",KEYS[1])==ARGV[1] then " +
                "return redis.call(\"del\",KEYS[1]) " +
                "else return 0 end";
        Long rs = (Long) jedis.eval(lua, 1, new String[]{lockKey, identifier});
        if (rs.intValue() > 0) {
            return true;
        }
        return false;
    }
}
