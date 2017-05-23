package com.haoyu.module.jcstruct.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented

public @interface HandleMessageProtocol {
	// 协议号
	String id();

	// 是否回应信息
	boolean response() default false;
}