package com.school.grade.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RuntimeBeanBuilder {

    @Autowired
    private ApplicationContext applicationContext;

    private SingletonBeanRegistry getBeanRegistry(){
        ConfigurableApplicationContext configContext = (ConfigurableApplicationContext) applicationContext;
        return configContext.getBeanFactory();
    }
    public Object createOrLoadBean(String beanName, Object myObject) {
        SingletonBeanRegistry beanRegistry = getBeanRegistry();

        if (!beanRegistry.containsSingleton(beanName)) {
            beanRegistry.registerSingleton(beanName, myObject);
        }

        return beanRegistry.getSingleton(beanName);
    }

    public void destroyBean(String beanName){
        DefaultSingletonBeanRegistry registry = (DefaultSingletonBeanRegistry) applicationContext.getAutowireCapableBeanFactory();
        registry.destroySingleton(beanName);
    }

    public Map<String, ?> getAllBeans(Class<?> myObject) {
        SingletonBeanRegistry beanRegistry = getBeanRegistry();
        return ((DefaultListableBeanFactory) beanRegistry).getBeansOfType(myObject);
    }
}