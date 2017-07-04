package com.distributed.core.spring;

import com.distributed.core.base.BaseFront;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring 容器 主要手动获取bean、
 */
public class SpringInit extends BaseFront implements ApplicationContextAware {

    private static ApplicationContext context = null;

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

    public static Object getBean(String beanName) {
        try {
            return context.getBean(beanName);
        } catch (BeansException e) {
            logger.error(e);
            return null;
        }
    }

    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return context.getBean(clazz);
    }

}
