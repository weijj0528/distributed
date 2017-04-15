package com.vijay.distributed.core.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2017/4/13.
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RemoveLoginCheck {
}
