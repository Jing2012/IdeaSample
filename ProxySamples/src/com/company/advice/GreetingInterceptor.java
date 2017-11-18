package com.company.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


/**
 * Created by jingjing.hu on 2017/9/20.
 * 环绕增强
 */
public class GreetingInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object[] args = methodInvocation.getArguments();
        String clientName = (String) args[0];
        System.out.println("How are you! Mr. " + clientName + ".");

        //通过反射机制调用目标方法
        Object obj = methodInvocation.proceed();

        System.out.println("Please enjoy yourself!");
        return obj;
    }
}
