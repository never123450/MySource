package com.xwy.MySpringIOC.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 请求参数映射 
 * @author xwy
 * @date 2021/3/24
 * @param  
 * @return 
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XwyRequestParam {
	
	String value() default "";
	
	boolean required() default true;

}
