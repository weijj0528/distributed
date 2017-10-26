package com.weiun.core.cache;

import java.util.List;
import java.util.Map;


/**
 * 缓存接口类
 * @version : 1.0
 * @description :
 */
public interface Cache {

    /**
	 * 添加  元素到 cache
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value);
	
	/**
	 * 添加  元素到 cache  并设置过期时间
	 * @param key
	 * @param value
	 * @param expirationTime ： 缓存过期时间（单位秒）
	 */
	public void set(String key, Object value, int expirationTime);

	/**
	 * 添加  元素到 cache  并设置过期时间
	 * @param key
	 * @param value
	 * @param timeToIdleSeconds	:缓存闲置 时间 （单位秒）
	 * @param expirationTime ： 缓存过期时间（单位秒）
	 */
	public void set(String key, Object value, int timeToIdleSeconds, int expirationTime);
	
	/**
	 * 获取 cache内元素
	 * @param key
	 * @return
	 */
	public Object get(String key);
	
	/**
	 * 获取 cache内元素
	 * @param key
	 * @return
	 */
	public <T> T get(String key, Class<T> clazz);
	
	/**
	 * 获取 list  对象 
	 * @author Johnson.Jia
	 * @date 2017年1月8日 下午3:57:13
	 * @param key
	 * @param clazz  list 对象内数据类型
	 * @return
	 */
	public <T> List<T>  getList(String key, Class<T> clazz);
	

	/**
	 * 获取 map 对象
	 * @author Johnson.Jia
	 * @date 2017年1月8日 下午4:10:36
	 * @param key
	 * @param keyClazz  
	 * @param valClazz  
	 * @return
	 */
	public <K,T> Map<K,T> getMap(String key, Class<K> keyClazz, Class<T> valClazz);

	/**
	 * 删除
	 * @param key
	 */
	public void remove(String key);

	/**
	 * 删除全部 数据
	 */
	public void removeAll();

}
