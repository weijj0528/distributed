package com.vijay.distributed.core.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring 容器 主要手动获取bean、
 * @author Johnson.Jia
 */
public class SpringInit implements ApplicationContextAware {

	private static ApplicationContext context = null;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}

	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	public static <T> T getBean(Class<T> clazz) throws BeansException {
		return context.getBean(clazz);
	}

}
