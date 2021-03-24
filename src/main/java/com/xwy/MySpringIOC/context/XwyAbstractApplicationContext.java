package com.xwy.MySpringIOC.context;

/**
 * @param
 * @author xwy
 * @Description
 * @date 2021/3/24
 * @return
 */
public abstract class XwyAbstractApplicationContext {


    //提供给子类重写
    protected void onRefresh() {
        // For subclasses: do nothing by default.
    }

    protected abstract void refreshBeanFactory();

}
