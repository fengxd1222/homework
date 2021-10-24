package com.example.demo.autowiringModel.model4;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ByConstrucorBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        GenericBeanDefinition definition = (GenericBeanDefinition) configurableListableBeanFactory.getBeanDefinition("testBeanByConstrucor");
        //将装配模式改为3
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
    }
}
