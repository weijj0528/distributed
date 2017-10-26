package com.weiun.core.lock.impl;

import com.weiun.core.lock.Lock;

import java.util.concurrent.TimeUnit;


/**
 * 分布式锁  基础类
 * @author Johnson.Jia
 */
public abstract class AbstractLock implements Lock {
	
	/**
	 * 当前是否 持有锁  状态 
	 */
	protected volatile boolean locked;

	public void lock() {
		lock(false, 0L , null);
	}

	public boolean tryLock(long time, TimeUnit timeUnit) {
		return lock(true, time, timeUnit);
	}

	/**
	 * 具体 锁   阻塞 实现 
	 * @author Johnson.Jia
	 * @date 2017年3月23日 上午11:23:16
	 * @param useTimeout		是否使用 阻塞超时  方式  true 使用    false 不使用
	 * @param timeout			阻塞超时时间
	 * @param unit				超时时间单位
	 * @return
	 * @throws InterruptedException
	 */
	protected abstract boolean lock(boolean useTimeout,long timeout,TimeUnit unit);
	
}
