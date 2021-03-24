package com.xwy.MySpringIOC.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 请求url 
 * @author xwy
 * @date 2021/3/24
 * @param  
 * @return 
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XwyRequestMapping {
	String value() default "";
}
