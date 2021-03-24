package com.xwy.MySpringIOC.core;

/**
 * @param
 * @author xwy
 * @Description
 * @date 2021/3/24
 * @return
 */
public interface XwyBeanFactory {

    /**
     * 根据beanName从IOC容器之中获得一个实例Bean
     *
     * @param beanName
     * @return
     */
    Object getBean(String beanName);

}
