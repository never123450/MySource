package com.xwy.MySpringIOC.beans;

/**
 * @param
 * @author xwy
 * @Description 用做事件监听的
 * @date 2021/3/24
 * @return
 */
public class XwyBeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }

}
