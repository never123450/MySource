package com.xwy.MySpringIOC.context;


import com.xwy.MySpringIOC.annotation.XwyAutowired;
import com.xwy.MySpringIOC.annotation.XwyController;
import com.xwy.MySpringIOC.annotation.XwyService;
import com.xwy.MySpringIOC.aop.XwyAopConfig;
import com.xwy.MySpringIOC.beans.XwyBeanDefinition;
import com.xwy.MySpringIOC.beans.XwyBeanPostProcessor;
import com.xwy.MySpringIOC.beans.XwyBeanWrapper;
import com.xwy.MySpringIOC.context.support.XwyBeanDefinitionReader;
import com.xwy.MySpringIOC.core.XwyBeanFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @param
 * @author xwy
 * @Description
 * @date 2021/3/24
 * @return
 */
public class XwyApplicationContext extends XwyDefaultListableBeanFactory implements XwyBeanFactory {

    private String[] configLocations;

    private XwyBeanDefinitionReader reader;

    //用来保证注册式单例的容器
    private Map<String, Object> beanCacheMap = new HashMap<String, Object>();

    //用来存储所有的被代理过的对象
    private Map<String, XwyBeanWrapper> beanWrapperMap = new ConcurrentHashMap<String, XwyBeanWrapper>();


    public XwyApplicationContext(String... configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    public void refresh() {
        //定位
        this.reader = new XwyBeanDefinitionReader(configLocations);

        //加载
        List<String> beanDefinitions = reader.loadBeanDefinitions();

        //注册
        doRegisty(beanDefinitions);

        //依赖注入（lazy-init = false），要是执行依赖注入
        //在这里自动调用getBean方法
        doAutowrited();

//        MyAction myAction = (MyAction)this.getBean("myAction");
//        myAction.query(null,null,"学习");
    }


    //开始执行自动化的依赖注入
    private void doAutowrited() {

        for (Map.Entry<String, XwyBeanDefinition> beanDefinitionEntry : this.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()) {
                Object obj = getBean(beanName);
                System.out.println(obj.getClass());
            }
        }

        for (Map.Entry<String, XwyBeanWrapper> beanWrapperEntry : this.beanWrapperMap.entrySet()) {
            populateBean(beanWrapperEntry.getKey(), beanWrapperEntry.getValue().getOriginalInstance());
        }

    }


    public void populateBean(String beanName, Object instance) {

        Class clazz = instance.getClass();

        //不是所有牛奶都叫特仑苏
        if (!(clazz.isAnnotationPresent(XwyController.class) ||
                clazz.isAnnotationPresent(XwyService.class))) {
            return;
        }

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(XwyAutowired.class)) {
                continue;
            }
            XwyAutowired autowired = field.getAnnotation(XwyAutowired.class);

            String autowiredBeanName = autowired.value().trim();

            if ("".equals(autowiredBeanName)) {
                autowiredBeanName = field.getType().getName();
            }

            field.setAccessible(true);

            try {
                System.out.println("=======================" + instance + "," + autowiredBeanName + "," + this.beanWrapperMap.get(autowiredBeanName));
                field.set(instance, this.beanWrapperMap.get(autowiredBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    //真正的将BeanDefinitions注册到beanDefinitionMap中
    private void doRegisty(List<String> beanDefinitions) {
        //beanName有三种情况:
        //1、默认是类名首字母小写
        //2、自定义名字
        //3、接口注入
        try {
            for (String className : beanDefinitions) {

                Class<?> beanClass = Class.forName(className);

                //如果是一个接口，是不能实例化的
                //用它实现类来实例化
                if (beanClass.isInterface()) {
                    continue;
                }

                XwyBeanDefinition beanDefinition = reader.registerBean(className);
                if (beanDefinition != null) {
                    this.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
                }

                Class<?>[] interfaces = beanClass.getInterfaces();
                for (Class<?> i : interfaces) {
                    //如果是多个实现类，只能覆盖
                    //为什么？因为Spring没那么智能，就是这么傻
                    //这个时候，可以自定义名字
                    this.beanDefinitionMap.put(i.getName(), beanDefinition);
                }

                //到这里为止，容器初始化完毕
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //依赖注入，从这里开始，通过读取BeanDefinition中的信息
    //然后，通过反射机制创建一个实例并返回
    //Spring做法是，不会把最原始的对象放出去，会用一个BeanWrapper来进行一次包装
    //装饰器模式：
    //1、保留原来的OOP关系
    //2、我需要对它进行扩展，增强（为了以后AOP打基础）
    @Override
    public Object getBean(String beanName) {

        XwyBeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);

        String className = beanDefinition.getBeanClassName();

        try {

            //生成通知事件
            XwyBeanPostProcessor beanPostProcessor = new XwyBeanPostProcessor();

            Object instance = instantionBean(beanDefinition);
            if (null == instance) {
                return null;
            }

            //在实例初始化以前调用一次
            beanPostProcessor.postProcessBeforeInitialization(instance, beanName);

            XwyBeanWrapper beanWrapper = new XwyBeanWrapper(instance);
            beanWrapper.setAopConfig(instantionAopConfig(beanDefinition));
            beanWrapper.setPostProcessor(beanPostProcessor);
            this.beanWrapperMap.put(beanName, beanWrapper);

            //在实例初始化以后调用一次
            beanPostProcessor.postProcessAfterInitialization(instance, beanName);

//            populateBean(beanName,instance);

            //通过这样一调用，相当于给我们自己留有了可操作的空间
            return this.beanWrapperMap.get(beanName).getWrappedInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private XwyAopConfig instantionAopConfig(XwyBeanDefinition beanDefinition) throws Exception {
        XwyAopConfig config = new XwyAopConfig();
        String expression = reader.getConfig().getProperty("pointCut");
        String[] before = reader.getConfig().getProperty("aspectBefore").split("\\s");
        String[] after = reader.getConfig().getProperty("aspectAfter").split("\\s");

        String className = beanDefinition.getBeanClassName();
        Class<?> clazz = Class.forName(className);

        Pattern pattern = Pattern.compile(expression);

        Class aspectClass = Class.forName(before[0]);
        //在这里得到的方法都是原生的方法
        for (Method m : clazz.getMethods()) {

            //public .* com\.gupaoedu\.vip\.spring\.demo\.service\..*Service\..*\(.*\)
            //public java.lang.String com.gupaoedu.vip.spring.demo.service.impl.ModifyService.add(java.lang.String,java.lang.String)
            Matcher matcher = pattern.matcher(m.toString());
            if (matcher.matches()) {
                //能满足切面规则的类，添加的AOP配置中
                config.put(m, aspectClass.newInstance(), new Method[]{aspectClass.getMethod(before[1]), aspectClass.getMethod(after[1])});
            }
        }


        return config;
    }


    //传一个BeanDefinition，就返回一个实例Bean
    private Object instantionBean(XwyBeanDefinition beanDefinition) {
        Object instance = null;
        String className = beanDefinition.getBeanClassName();
        try {


            //因为根据Class才能确定一个类是否有实例
            if (this.beanCacheMap.containsKey(className)) {
                instance = this.beanCacheMap.get(className);
            } else {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                this.beanCacheMap.put(className, instance);
            }

            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }


    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }


    public Properties getConfig() {
        return this.reader.getConfig();
    }

}
