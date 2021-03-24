package com.xwy.MySpringIOC.beans;


import com.xwy.MySpringIOC.aop.XwyAopConfig;
import com.xwy.MySpringIOC.aop.XwyAopProxy;
import com.xwy.MySpringIOC.core.XwyFactoryBean;

/**
 * @param
 * @author xwy
 * @Description
 * @date 2021/3/24
 * @return
 */
public class XwyBeanWrapper extends XwyFactoryBean {

    private XwyAopProxy aopProxy = new XwyAopProxy();

    public XwyBeanPostProcessor getPostProcessor() {
        return postProcessor;
    }

    public void setPostProcessor(XwyBeanPostProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }

    //还会用到  观察者  模式
    //1、支持事件响应，会有一个监听
    private XwyBeanPostProcessor postProcessor;

    private Object wrapperInstance;
    //原始的通过反射new出来，要把包装起来，存下来
    private Object originalInstance;

    public XwyBeanWrapper(Object instance) {
        //从这里开始，我们要把动态的代码添加进来了
        this.wrapperInstance = aopProxy.getProxy(instance);
        this.originalInstance = instance;
    }

    public Object getWrappedInstance() {
        return this.wrapperInstance;
    }


    // 返回代理以后的Class
    // 可能会是这个 $Proxy0
    public Class<?> getWrappedClass() {
        return this.wrapperInstance.getClass();
    }


    public void setAopConfig(XwyAopConfig config) {
        aopProxy.setConfig(config);
    }


    public Object getOriginalInstance() {
        return originalInstance;
    }
}
