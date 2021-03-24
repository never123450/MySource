package com.xwy.MySpringIOC.beans;

/**
 * @param
 * @author xwy
 * @Description 用来存储配置文件中的信息 相当于保存在内存中的配置
 * @date 2021/3/24
 * @return
 */
public class XwyBeanDefinition {

    private String beanClassName;
    private boolean lazyInit = false;
    private String factoryBeanName;

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

}
