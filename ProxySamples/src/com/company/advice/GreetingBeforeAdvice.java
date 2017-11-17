package com.company.advice;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by jingjing.hu on 2017/9/20.
 * 前置增强
 */
public class GreetingBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        String clientName = (String) args[0];
        System.out.println("How are you! Mr. " + clientName + ".");
    }
}
