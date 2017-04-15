package com.vijay.distributed.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * main 方法程序启动入口
 * 
 * @author Johnson.Jia
 */
public class Main {

	private static ClassPathXmlApplicationContext context;

	public static void main(String[] args) throws InterruptedException {

		context = new ClassPathXmlApplicationContext(new String[] { "/spring/applicationContext.xml" });
		context.start();
		while (true) {
			Thread.sleep(10000);
		}

	}

}
