package com.xwy.springMVC.extannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @param
 * @author xwy
 * @Description 自定义RequestMapping
 * @date 2021/3/24
 * @return
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtRequestMapping {
    String value() default "";
}
