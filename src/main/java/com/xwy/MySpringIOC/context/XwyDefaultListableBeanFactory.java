package com.xwy.MySpringIOC.context;


import com.xwy.MySpringIOC.beans.XwyBeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @param
 * @author xwy
 * @Description
 * @date 2021/3/24
 * @return
 */
public class XwyDefaultListableBeanFactory extends XwyAbstractApplicationContext {

    //beanDefinitionMap用来保存配置信息
    protected Map<String, XwyBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, XwyBeanDefinition>();

    protected void onRefresh() {

    }

    @Override
    protected void refreshBeanFactory() {

    }
}
