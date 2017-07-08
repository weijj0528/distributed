package com.william.base.util;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * ID生成
 * 
 * @author weiun
 *
 */
public class IdKit {
	private static final ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>();
	private static final CountDownLatch latch = new CountDownLatch(1);

	/**
	 * 获取通用ID
	 * 
	 * @return
	 */
	public static String getUniversalId() {
		return ObjectId.get().toHexString();
	}

	/**
	 * 每毫秒生成订单号数量最大值，约定取整百，整千。
	 */
	public static final int maxPerMSECSize = 1000;

	private static void init() {
		for (int i = 0; i < maxPerMSECSize; i++) {
			queue.offer(i);
		}
		latch.countDown();
	}

	private static Integer poll() {
		try {
			if (latch.getCount() > 0) {
				init();
				latch.await(1, TimeUnit.SECONDS);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Integer i = queue.poll();
		queue.offer(i);
		return i;
	}

	public static String getNumberId() {
		long nowLong = System.currentTimeMillis();
		int number = maxPerMSECSize + poll();
		return nowLong + String.valueOf(number);
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 300; i++) {
			System.out.println(new Long("1"+getNumberId()));
		}
	}

}
