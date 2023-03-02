package io.github.zhaofanzhe.scaffold.lock;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {

    @AliasFor("key")
    String value() default "";

    /**
     * 类型
     */
    Type type() default Type.System;

    /**
     * 键
     */
    @AliasFor("value")
    String key() default "";

    /**
     * 等待锁时间
     * 单位: 秒
     */
    int waitLockTime() default 5;

}
