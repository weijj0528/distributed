package com.distributed.core.lock.impl;

import java.util.concurrent.TimeUnit;

import com.distributed.core.spring.SpringInit;
import org.apache.log4j.Logger;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;


/**
 * redis 缓存 分布式锁 直接使用 new 创建锁对象
 *
 * @Autowired
 */
public class RedisLock extends AbstractLock {

    private static Logger logger = Logger.getLogger(RedisLock.class);

    private static final String REDIS_LOCK_KEY = "LOCK.";

    private static final String DEFAULT_LOCK_KEY = "DEFAULT_LOCK";

    private static final long DEFAULT_LOCK_EXPIRES = 3000L;

    /**
     * 锁 key
     */
    protected String lockKey;
    /**
     * 锁的有效时长(毫秒)
     */
    protected Long lockExpires;

    /**
     * key 锁 过期 时间戳
     */
    protected Long lockExpireTime;

    /**
     * redis 连接
     */
    private RedisConnection redis = null;

    public RedisLock() {
        this(DEFAULT_LOCK_KEY, DEFAULT_LOCK_EXPIRES);
    }

    /**
     * 创建锁 对象 指定时间 格式
     *
     * @param lockKey     锁的key
     * @param lockExpires 锁过期时间
     * @param timeUnit    锁过期时间单位
     * @author Johnson.Jia
     * @date 2017年3月23日 下午4:55:43
     */
    public RedisLock(String lockKey, long lockExpires, TimeUnit timeUnit) {
        this(lockKey, timeUnit.toMillis(lockExpires));
    }

    /**
     * 创建锁 对象
     *
     * @param lockKey     锁的 key
     * @param lockExpires 锁的有效期 （毫秒）
     * @author Johnson.Jia
     * @date 2017年3月23日 上午11:26:27
     */
    public RedisLock(String lockKey, long lockExpires) {
        this.lockKey = REDIS_LOCK_KEY + lockKey;
        this.lockExpires = lockExpires;
        RedisConnectionFactory connectionFactory = SpringInit.getBean(StringRedisTemplate.class).getConnectionFactory();
        try {
            if (connectionFactory != null)
                this.redis = connectionFactory.getConnection();
        } catch (Exception e) {
            logger.error("【==== Redis分布式锁异常 ====】--->", e);
        }
    }

    @Override
    protected boolean lock(boolean useTimeout, long time, TimeUnit timeUnit) {
        try {
            locked = false;
            byte[] synKey = lockKey.getBytes();
            if (useTimeout) {
                long timeout = timeUnit.toMillis(time);
                long startTime = localTimeMillis();
                while (isTimeout(startTime, timeout) && !locked) {
                    locked = lockImpl(synKey);
                }
                return locked;
            }
            while (!locked) {
                locked = lockImpl(synKey);
            }
            return locked;
        } catch (Exception e) {
            logger.error("【==== Redis分布式锁异常 ====】--->", e);
            return locked;
        }
    }

    public boolean tryLock() {
        try {
            byte[] synKey = lockKey.getBytes();
            locked = lockImpl(synKey);
            return locked;
        } catch (Exception e) {
            logger.error("【==== Redis分布式锁异常 ====】--->", e);
            locked = false;
            return false;
        }
    }

    /**
     * 锁实现
     *
     * @param conn   redis 连接
     * @param synKey 锁 key
     * @return
     * @author Johnson.Jia
     * @date 2017年3月23日 下午1:09:05
     */
    private boolean lockImpl(byte[] synKey) {
        if (this.redis == null) {
            return false;
        }
        lockExpireTime = redisTimeMillis() + lockExpires + 1l;
        byte[] expireMillis = String.valueOf(lockExpireTime).getBytes();
        if (redis.setNX(synKey, expireMillis)) {
            redis.pExpire(synKey, lockExpires);
            return true;
        }
        byte[] value = redis.get(synKey);
        if (value != null) {
            String valueStr = new String(value);
            if (isTimeExpired(Long.valueOf(valueStr))) { // 已经过期 获取锁
                byte[] oldValue = redis.getSet(synKey, expireMillis);
                if (oldValue != null && valueStr.equals(new String(oldValue))) { // 锁获取成功
                    redis.pExpire(synKey, lockExpires);
                    return true;
                }
            }
        }
        return false;
    }

    public void unlock() {
        try {
            if (locked) {
                byte[] synKey = lockKey.getBytes();
                byte[] value = redis.get(synKey);
                if (value != null) {
                    if (new String(value).equals(lockExpireTime.toString())) {
                        redis.del(synKey);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("【==== Redis分布式锁异常 ====】--->", e);
        } finally {
            // 释放锁 释放redis连接
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * 效验是否超时
     *
     * @param start
     * @param timeout
     * @return
     * @author Johnson.Jia
     * @date 2017年1月13日 下午4:44:36
     */
    private boolean isTimeout(long time, long millis) {
        return (time + millis) > System.currentTimeMillis();
    }

    /**
     * 效验 锁 是否过期
     *
     * @param time 锁的过期时间
     * @param conn reids 连接 用于获取服务器时间
     * @return 过期 true 未过期 false
     * @author Johnson.Jia
     * @date 2017年3月23日 上午10:32:36
     */
    private boolean isTimeExpired(Long time) {
        return time <= redisTimeMillis();
    }

    /**
     * 获取 redis 服务器时间
     *
     * @param conn
     * @return
     * @author Johnson.Jia
     * @date 2017年3月23日 上午10:24:15
     */
    private Long redisTimeMillis() {
        try {
            return redis.time();
        } catch (Exception e) {
            return System.currentTimeMillis();
        }
    }

    /**
     * 本地时间
     *
     * @param conn
     * @return
     * @author Johnson.Jia
     * @date 2017年3月23日 上午10:42:57
     */
    private Long localTimeMillis() {
        return System.currentTimeMillis();
    }

}
