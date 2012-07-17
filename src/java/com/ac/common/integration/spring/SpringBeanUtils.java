package com.ac.common.integration.spring;

import java.beans.Introspector;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanUtils implements ApplicationContextAware{
	private ApplicationContext appCtx;

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.appCtx = ctx;
	}
	
	public void autowireBean(Object bean){
		appCtx.getAutowireCapableBeanFactory().autowireBean(bean);
		String beanName = Introspector.decapitalize(bean.getClass().getSimpleName());
		appCtx.getAutowireCapableBeanFactory().initializeBean(bean, beanName);
	}
	
}
