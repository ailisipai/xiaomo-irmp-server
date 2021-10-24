package com.xiaomo.gateway.annotation;

import java.lang.annotation.*;

/**
 * @author xiaomo
 * 增加切面验证注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JWTCheck {
    String value() default "";
}
