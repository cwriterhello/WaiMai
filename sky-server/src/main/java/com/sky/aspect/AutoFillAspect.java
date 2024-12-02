package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import nonapi.io.github.classgraph.utils.VersionFinder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){
    }

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("开始进行公共字段填充");

        //目的：修改被拦截对象的属性，以下是思路

        //获取被拦截方法的数据库操作类型（因为方法不一样，需要修改的对象属性也不一样）
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        //获取被拦截方法的参数（对象）
        Object[] objects = joinPoint.getArgs();
        if (objects==null || objects.length==0){
            return;
        }
        Object object = objects[0];

        //准备赋值的数据:这个过程用到了ThreadLocal
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        //使用反射为对象的属性赋值:这个过程用到了反射
        if (operationType == OperationType.INSERT){
            try {
                Method createTime = object.getClass().getMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class);
                Method updateTime = object.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                Method createUser = object.getClass().getMethod(AutoFillConstant.SET_CREATE_USER,Long.class);
                Method updateUser = object.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);
                createTime.invoke(object,now);
                updateTime.invoke(object,now);
                createUser.invoke(object,currentId);
                updateUser.invoke(object,currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (operationType == OperationType.UPDATE) {
            try {
                Method updateTime = object.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                Method updateUser = object.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);
                updateTime.invoke(object,now);
                updateUser.invoke(object,currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
