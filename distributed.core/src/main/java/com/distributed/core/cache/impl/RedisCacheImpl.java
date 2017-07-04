package com.distributed.core.cache.impl;

import com.distributed.core.base.BaseFront;
import com.distributed.core.cache.Cache;
import com.distributed.core.exception.ErrorMsgException;
import com.distributed.core.util.Transcoder;
import org.apache.commons.lang.StringUtils;
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
public class RedisCacheImpl extends BaseFront implements Cache {

    private static final String LOG_ERROR_REDIS_CACHE = "[RedisCacheError]";

    private StringRedisTemplate redisTemplate;

    public StringRedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, Object value) {
        set(key, value, 0);
    }

    public void set(String key, Object value, int expirationTime) {
        set(key, value, 0, expirationTime);
    }

    public void set(String key, Object value, int timeToIdleSeconds, int expirationTime) {
        RedisConnection conn = null;
        try {
            conn = redisTemplate.getConnectionFactory().getConnection();
            if (!conn.isClosed() && value != null) {
                conn.set(key.getBytes(), Transcoder.serialize(value));
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

    public Object get(String key) {
        RedisConnection conn = null;
        try {
            Object obj = null;
            conn = redisTemplate.getConnectionFactory().getConnection();
            if (StringUtils.isNotEmpty(key) && !conn.isClosed()) {
                byte[] in = conn.get(key.getBytes());
                if (in != null && in.length > 0) {
                    obj = Transcoder.deserialize(in);
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

    public <T> T get(String key, Class<T> clazz) {
        try {
            Object object = get(key);
            if (object == null) {
                return null;
            }
            if (object.getClass() == clazz) {
                return (T) object;
            } else {
                throw new ErrorMsgException(" 缓存类型不匹配  redis cache class !=  Class<T> clazz");
            }
        } catch (Exception e) {
            logger.error(LOG_ERROR_REDIS_CACHE, e);
        }
        return null;
    }

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
