package com.mapbar.netobd.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @description: 获取spring容器，以访问容器中定义的其他bean
 * @author xubh
 * @date: 2016年4月29日
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);

	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 * 
	 * @param applicationContext
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
		logger.info("set application context complete !");
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		T t = null;

		// 优先按照名称获取， 名称获取不到再按照类型获取
		try {
			t = applicationContext.getBean(name, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage(), e);
		}

		if (t == null) {
			try {
				t = getBeanByType(clazz);
			} catch (Exception e) {
				e.printStackTrace();
				logger.info(e.getMessage(), e);
			}
		}
		if (t == null) {
			logger.error("can not found required bean !", new BeanCreationException(new StringBuffer("can not find bean [ ").append(name).append(" ] ! ").toString()));
		}

		return t;
	}

	public static <T> T getBeanByType(Class<T> clazz) {
		T t = applicationContext.getBean(clazz);
		if (t == null) {
			logger.error("can not found required bean !", new BeanCreationException(new StringBuffer("can not find bean by type [ ").append(clazz.getName()).append(" ] ! ").toString()));
		}
		return t;
	}

	/**
	 * 获取对象 这里重写了bean方法，起主要作用
	 * 
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

}
