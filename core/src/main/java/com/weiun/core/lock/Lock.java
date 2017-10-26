package com.weiun.core.lock;

import java.util.concurrent.TimeUnit;


/**
 * 分布式锁 接口类
 * @author Johnson.Jia
 * @date 2017年1月16日 上午9:27:20
 */
public interface Lock {

	/**
	 *	尝试获取锁，获取成功则返回，否则阻塞当前线程   直到获取到锁 方不阻塞
	 */
	void lock();
	
	/**
	 * 尝试获取锁, 获取不到立即返回, 不阻塞
	 * 获取锁成功则返回true，否则返回false 
	 */
	boolean tryLock();
	
	/**
	 * 尝试获取锁，若在规定时间内获取到锁，则返回true，否则返回false 
	 * @param time		超时时间
	 * @param unit		超时时间单位
	 * @return  true 若成功获取到锁,  false 若在指定时间内未获取到锁
     *         
	 */
	boolean tryLock(long time, TimeUnit timeUnit);

	/**
	 * 释放锁
	 */
	void unlock();
	
}
