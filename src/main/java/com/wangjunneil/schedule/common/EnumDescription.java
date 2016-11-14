package com.wangjunneil.schedule.common;

import java.lang.annotation.*;


/**
 * Created by yangwanbin on 2016-11-14.
 */
@Documented
@Target({ElementType.TYPE_PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumDescription {
    int code() default  1;
    String desc() default  "";
    String remark() default "";
}
