package com.weiun.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring 容器 主要手动获取bean、
 */
public class SpringInit implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(SpringInit.class);

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

    public static Object getBean(String beanName) {
        try {
            return context.getBean(beanName);
        } catch (BeansException e) {
            logger.error("未发现实例：" + beanName, e);
            return null;
        }
    }

    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return context.getBean(clazz);
    }

}
