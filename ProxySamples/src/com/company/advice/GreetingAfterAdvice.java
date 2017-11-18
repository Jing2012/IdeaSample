package com.company.advice;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * Created by jingjing.hu on 2017/9/20.
 * 后置增强
 */
public class GreetingAfterAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnObj, Method method, Object[] args, Object obj) throws Throwable {
        System.out.println("Please enjoy yourself!");
    }
}
