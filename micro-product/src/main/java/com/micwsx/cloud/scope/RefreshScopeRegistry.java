package com.micwsx.cloud.scope;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author Michael
 * @create 8/20/2020 3:57 PM
 * 自定义名为refresh的RefreshScope对象
 */
//@Component
public class RefreshScopeRegistry implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        RefreshScope refreshScope = new RefreshScope();
        configurableListableBeanFactory.registerScope(RefreshScope.SCOPE_NAME, refreshScope);
    }
}
