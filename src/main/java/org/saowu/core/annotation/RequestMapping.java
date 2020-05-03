package org.saowu.core.annotation;

import org.saowu.core.pojo.RequestMethod;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    /**
     * 请求路径
     **/
    String path() default "";

    /**
     * 请求方法
     **/
    RequestMethod method() default RequestMethod.GET;
}
