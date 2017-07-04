package com.distributed.core.cache.impl;

import com.distributed.core.base.BaseFront;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;


/**
 * Ehcache缓存 实现类 ##使用方法 直接在类中 增加 注解配置 即可
 *
 * @author Johnson.Jia
 * @Autowired
 * @Qualifier("ehCache")
 */
public class EhcacheImpl extends BaseFront implements com.distributed.core.cache.Cache {
    private static final String LOG_ERROR_EH_CACHE = "[EhcacheError]";
    /**
     * 缓存 key 锁
     */
    private final String EHCACHE_KEY_SYN = "EHCACHE_KEY_SYN";

    private Cache cache;

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public void set(String key, Object value) {
        set(key, value, 0);
    }

    public void set(String key, Object value, int expirationTime) {
        set(key, value, 0, expirationTime);
    }

    public void set(String key, Object value, int timeToIdleSeconds, int expirationTime) {
        try {
            String syn = EHCACHE_KEY_SYN + key;
            synchronized (syn.intern()) {
                Element element = null;
                if (cache.get(key.trim()) != null) {
                    cache.remove(key.trim());
                }
                if (value != null) {
                    element = new Element(key.trim(), value);
                    if (expirationTime > 0) {
                        element.setTimeToLive(expirationTime);
                    }
                    if (timeToIdleSeconds > 0) {
                        element.setTimeToIdle(timeToIdleSeconds);
                    }
                    cache.put(element);
                }
            }
        } catch (Exception e) {
            logger.error(LOG_ERROR_EH_CACHE, e);
        }
    }

    public Object get(String key) {
        try {
            Object result = null;
            Element element = null;
            if (StringUtils.isNotEmpty(key) && cache.get(key.trim()) != null) {
                element = cache.get(key.trim());
                if (element.getObjectValue() != null) {
                    result = element.getObjectValue();
                }
            }
            return result;
        } catch (Exception e) {
            logger.error(LOG_ERROR_EH_CACHE, e);
        }
        return null;
    }

    public void remove(String key) {
        try {
            if (cache.get(key.trim()) != null) {
                cache.remove(key);
            }
        } catch (Exception e) {
            logger.error(LOG_ERROR_EH_CACHE, e);
        }
    }

    /**
     * 把所有cache中的内容删除，但是cache对象还是保留. Clears the contents of all caches in the
     * CacheManager, but without removing any caches.
     */
    public void removeAll() {
        try {
            cache.removeAll();
        } catch (Exception e) {
            logger.error(LOG_ERROR_EH_CACHE, e);
        }
    }


    public <T> T get(String key, Class<T> clazz) {
        try {
            Object object = get(key);
            return (T) object;
        } catch (Exception e) {
            logger.error(LOG_ERROR_EH_CACHE, e);
        }
        return null;
    }


    public <T> List<T> getList(String key, Class<T> clazz) {
        return null;
    }


    public <K, T> Map<K, T> getMap(String key, Class<K> keyClazz, Class<T> valClazz) {
        return null;
    }
}
