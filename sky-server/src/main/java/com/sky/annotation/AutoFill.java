package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//指定添加在方法上
@Target(ElementType.METHOD)
//保留策略
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    OperationType value();
}

