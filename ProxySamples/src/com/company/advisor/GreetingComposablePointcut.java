package com.company.advisor;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.NameMatchMethodPointcut;

/**
 * Created by jingjing.hu on 2017/10/5.
 */
public class GreetingComposablePointcut {

    public Pointcut getIntersectionPointcut() {
        ComposablePointcut composablePointcut = new ComposablePointcut();
        Pointcut pointcut1 = new ControlFlowPointcut(WaiterDelegate.class, "service");
        NameMatchMethodPointcut pointcut2 = new NameMatchMethodPointcut();
        pointcut2.addMethodName("greetTo");

        return composablePointcut.intersection(pointcut1).intersection((Pointcut) pointcut2);
    }
}
