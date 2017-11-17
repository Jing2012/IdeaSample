package com.company.advisor;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingjing.hu on 2017/10/5.
 */
public class GreetingDynamicPointcut extends DynamicMethodMatcherPointcut {

    private static List<String> specialClientList = new ArrayList<String>();

    static {
        specialClientList.add("John");
        specialClientList.add("Tom");
    }

    @Override
    public ClassFilter getClassFilter() {
        return new ClassFilter() {
            @Override
            public boolean matches(Class clazz) {
                System.out.println("调用getClassFilter()对" + clazz.getName() + "做静态检查.");
                return Waiter.class.isAssignableFrom(clazz);
            }
        };
    }

    @Override
    public boolean matches(Method method, Class clazz) {
        System.out.println("调用matches(method,clazz) " + clazz.getName() + "." + method.getName() + "做静态检查.");
        return "greetTo".equals(method.getName());
    }

    //动态检查
    @Override
    public boolean matches(Method method, Class clazz, Object[] args) {
        System.out.println("调用matches(method,clazz,args) " + clazz.getName() + "." + method.getName() + "做动态检查.");
        String clientName = (String) args[0];
        return specialClientList.contains(clientName);
    }
}
