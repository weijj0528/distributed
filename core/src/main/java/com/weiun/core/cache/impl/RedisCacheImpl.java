package com.weiun.core.cache.impl;

import com.weiun.base.exception.ErrorMsgException;
import com.weiun.core.cache.Cache;
import com.weiun.core.util.KryoTranscoder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * redis缓存 实现类 ##使用方法 直接在类中 增加 注解配置 即可
 *
 * @author Johnson.Jia
 * @Autowired
 * @Qualifier("redisCache")
 */
@SuppressWarnings("unchecked")
public class RedisCacheImpl implements Cache {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheImpl.class);

    private static final String LOG_ERROR_REDIS_CACHE = "[RedisCacheError]";

    private StringRedisTemplate redisTemplate;

    public StringRedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void set(String key, Object value) {
        set(key, value, 0);
    }

    @Override
    public void set(String key, Object value, int expirationTime) {
        set(key, value, 0, expirationTime);
    }

    @Override
    public void set(String key, Object value, int timeToIdleSeconds, int expirationTime) {
        RedisConnection conn = null;
        try {
            conn = redisTemplate.getConnectionFactory().getConnection();
            if (!conn.isClosed() && value != null) {
                conn.set(key.getBytes(), KryoTranscoder.serialize(value));
                if (expirationTime > 0) {
                    conn.expire(key.getBytes(), expirationTime);
                }
            }
        } catch (Exception e) {
            logger.error(LOG_ERROR_REDIS_CACHE, e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public void remove(String key) {
        RedisConnection conn = null;
        try {
            conn = redisTemplate.getConnectionFactory().getConnection();
            Set<byte[]> keys = conn.keys(key.getBytes());
            if (!keys.isEmpty()) {
                conn.del(key.getBytes());
            }
        } catch (Exception e) {
            logger.error(LOG_ERROR_REDIS_CACHE, e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public Object get(String key) {
        RedisConnection conn = null;
        try {
            Object obj = null;
            conn = redisTemplate.getConnectionFactory().getConnection();
            if (StringUtils.isNotEmpty(key) && !conn.isClosed()) {
                byte[] in = conn.get(key.getBytes());
                if (in != null && in.length > 0) {
                    obj = KryoTranscoder.deserialize(in, Object.class);
                }
            }
            return obj;
        } catch (Exception e) {
            logger.error(LOG_ERROR_REDIS_CACHE, e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }

    private <T> T getObject(String key, Class<T> clazz) {
        RedisConnection conn = null;
        try {
            conn = redisTemplate.getConnectionFactory().getConnection();
            if (StringUtils.isNotEmpty(key) && !conn.isClosed()) {
                byte[] in = conn.get(key.getBytes());
                if (in != null && in.length > 0) {
                    return KryoTranscoder.deserialize(in, clazz);
                }
            }
        } catch (Exception e) {
            logger.error(LOG_ERROR_REDIS_CACHE, e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }

    @Override
    public void removeAll() {
        RedisConnection conn = null;
        try {
            conn = redisTemplate.getConnectionFactory().getConnection();
            conn.flushDb();
        } catch (Exception e) {
            logger.error(LOG_ERROR_REDIS_CACHE, e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        try {
            return getObject(key, clazz);
        } catch (Exception e) {
            logger.error(LOG_ERROR_REDIS_CACHE, e);
        }
        return null;
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clazz) {
        try {
            Object object = get(key);
            if (object == null) {
                return null;
            }
            List<?> list = ((List<?>) object);
            if (list.get(0).getClass() == clazz) {
                return (List<T>) list;
            } else {
                throw new ErrorMsgException(" 缓存类型不匹配  redis cache class !=  Class<T> clazz");
            }
        } catch (Exception e) {
            logger.error(LOG_ERROR_REDIS_CACHE, e);
        }
        return null;
    }

    @Override
    public <K, T> Map<K, T> getMap(String key, Class<K> keyClazz, Class<T> valClazz) {
        try {
            Object object = get(key);
            if (object == null) {
                return null;
            }
            Map<?, ?> map = ((Map<?, ?>) object);
            Iterator<Map.Entry<?, ?>> iterator = (Iterator<Map.Entry<?, ?>>) ((Set<?>) map.entrySet()).iterator();
            if (iterator.hasNext()) {
                Map.Entry<?, ?> next = iterator.next();
                if (next.getKey().getClass() == keyClazz && next.getValue().getClass() == valClazz) {
                    return (Map<K, T>) map;
                } else {
                    throw new ErrorMsgException(" 缓存类型不匹配  redis cache class !=  Class<T> clazz");
                }
            }
        } catch (Exception e) {
            logger.error(LOG_ERROR_REDIS_CACHE, e);
        }
        return null;
    }
}
