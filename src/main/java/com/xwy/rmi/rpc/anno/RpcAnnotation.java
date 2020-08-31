package com.xwy.rmi.rpc.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @description: 
 *
 * @author: xwy
 *
 * @create: 3:41 PM 2020/8/31
**/

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcAnnotation {

    //对外发布的服务的接口地址
    Class<?> value();

    String version() default "";
}
