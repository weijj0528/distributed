package com.weiun.core.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2017/4/13.
 */
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginCheck {
}
