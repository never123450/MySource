/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 * 版权归属:每特教育|蚂蚁课堂所有 www.itmayiedu.com
 */
package com.xwy.rateLimiter.implementation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 功能说明: 自定义服务限流注解框架 原理：参考：RateLimiter <br>
 * *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExtRateLimiter {

	// 以每秒为单位固定的速率值往令牌桶中添加令牌
	double permitsPerSecond();

	// 在规定的毫秒数中,如果没有获取到令牌的话，则直接走服务降级处理
	long timeout();
}
