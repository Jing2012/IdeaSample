package com.company;

import com.company.advice.*;
import com.company.advice.Waiter;
import com.company.advisor.*;
import com.company.common.ForumService;
import com.company.common.ForumServiceImpl;
import com.company.proxy4CGLib.CglibProxy;
import com.company.proxy4JDK.PerformanceHandler;
import org.aopalliance.intercept.Interceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.testng.annotations.Test;

import java.lang.reflect.Proxy;

/**
 * Created by jingjing.hu on 2017/9/20.
 */
public class proxyTest {

    /**
     * 1、JDK代理
     */
    @Test
    public void test001() {
        ForumService target = new ForumServiceImpl();
        PerformanceHandler handler = new PerformanceHandler(target);
        ForumService forumService = (ForumService) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), handler);
        forumService.removeForum(10);
        forumService.removeTopic(1012);
    }

    /**
     * 2、CGLIB代理
     */
    @Test
    public void test002() {
        CglibProxy proxy = new CglibProxy();
        ForumServiceImpl forumService1 = (ForumServiceImpl) proxy.getProxy(ForumServiceImpl.class);
        forumService1.removeForum(20);
        forumService1.removeTopic(2012);
    }

    /**
     * 前置增强
     */
    @Test
    public void test003() {
        Waiter waiter = new NaiveWaiter();
        BeforeAdvice advice = new GreetingBeforeAdvice();

        //代理工厂
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(waiter);                     //设置代理目标
        proxyFactory.addAdvice(advice);                     //为代理目标添加增强
        Waiter proxy = (Waiter) proxyFactory.getProxy();    //生成代理实例

        proxy.greetTo("John");
        proxy.serveTo("Tom");
    }

    /**
     * 后置增强
     */
    @Test
    public void test004() {
        Waiter waiter = new NaiveWaiter();
        AfterReturningAdvice advice = new GreetingAfterAdvice();

        //代理工厂
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(waiter);                     //设置代理目标
        proxyFactory.addAdvice(advice);                     //为代理目标添加增强
        Waiter proxy = (Waiter) proxyFactory.getProxy();    //生成代理实例

        proxy.greetTo("John");
    }

    /**
     * 环绕增强(前、后置增强)
     */
    @Test
    public void test005() {
        Waiter waiter = new NaiveWaiter();
        Interceptor advice = new GreetingInterceptor();

        //代理工厂
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(waiter);                     //设置代理目标
        proxyFactory.addAdvice(advice);                     //为代理目标添加增强
        Waiter proxy = (Waiter) proxyFactory.getProxy();    //生成代理实例

        proxy.greetTo("John");
    }

    /**
     * 引介增强(可设置是否启用性能监视)
     */
    @Test
    public void test006() {
        ForumService forumService = new ForumServiceImpl();
        ControllablePerformanceMonitor advice = new ControllablePerformanceMonitor();

        //代理工厂
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(new Class[]{Monitorable.class});
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.setTarget(forumService);                           //设置代理目标
        proxyFactory.addAdvice(advice);                                 //为代理目标添加增强
        ForumService proxy = (ForumService) proxyFactory.getProxy();    //生成代理实例

        //1、未启用性能监视
        proxy.removeForum(10);
        //2、启用性能监视
        Monitorable monitorable = (Monitorable) proxy;  //代理引入了Monitorable接口方法的实现。。。
        monitorable.setMonitorActive(true);
        proxy.removeForum(10);
    }


    /**
     * 静态普通方法名匹配切面
     */
    @Test
    public void test007() {
        com.company.advisor.Waiter target1 = new com.company.advisor.Waiter();
        Seller target2 = new Seller();
        BeforeAdvice advice = new GreetingBeforeAdvice();                       //增强
        StaticMethodMatcherPointcutAdvisor advisor = new GreetingAdvisor();     //切面
        advisor.setAdvice(advice);  //向切面注入一个前置增强

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvisor(advisor);

        proxyFactory.setTarget(target1);
        com.company.advisor.Waiter proxy1 = (com.company.advisor.Waiter) proxyFactory.getProxy();
        proxy1.greetTo("John1");    //织入切面
        proxy1.serveTo("Tom");      //未织入切面

        proxyFactory.setTarget(target2);
        Seller proxy2 = (Seller) proxyFactory.getProxy();
        proxy2.greetTo("John2");    //未织入切面
    }

    /**
     * 静态正则表达式方法匹配切面
     */
    @Test
    public void test008() {
        com.company.advisor.Waiter target = new com.company.advisor.Waiter();
        BeforeAdvice advice = new GreetingBeforeAdvice();                           //增强
        RegexpMethodPointcutAdvisor advisor = new RegexpMethodPointcutAdvisor();    //切面
        advisor.setPattern(".*greet.*");
        advisor.setAdvice(advice);  //向切面注入一个前置增强

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvisor(advisor);

        proxyFactory.setTarget(target);
        com.company.advisor.Waiter proxy = (com.company.advisor.Waiter) proxyFactory.getProxy();
        proxy.greetTo("John");    //织入切面
        proxy.serveTo("Tom");     //未织入切面
    }

    /**
     * 动态切面
     */
    @Test
    public void test009() {
        com.company.advisor.Waiter target = new com.company.advisor.Waiter();
        BeforeAdvice advice = new GreetingBeforeAdvice();
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        GreetingDynamicPointcut pointcut = new GreetingDynamicPointcut();
        advisor.setAdvice(advice);
        advisor.setPointcut(pointcut);

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvisor(advisor);
        proxyFactory.setTarget(target);

        com.company.advisor.Waiter proxy = (com.company.advisor.Waiter) proxyFactory.getProxy();
        proxy.serveTo("Peter");
        proxy.greetTo("Peter");
        proxy.serveTo("John");
        proxy.greetTo("John");
    }

    /**
     * 流程切面
     */
    @Test
    public void test0010() {
        com.company.advisor.Waiter target = new com.company.advisor.Waiter();
        BeforeAdvice advice = new GreetingBeforeAdvice();
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        ControlFlowPointcut pointcut = new ControlFlowPointcut(WaiterDelegate.class, "service");
        advisor.setAdvice(advice);
        advisor.setPointcut(pointcut);

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvisor(advisor);
        proxyFactory.setTarget(target);

        com.company.advisor.Waiter proxy = (com.company.advisor.Waiter) proxyFactory.getProxy();
        WaiterDelegate delegate = new WaiterDelegate();
        delegate.setWaiter(proxy);
        proxy.serveTo("Peter");
        proxy.greetTo("Peter");
        delegate.service("Peter");
    }

    /**
     * 复合切点切面
     */
    @Test
    public void test0011() {
        com.company.advisor.Waiter target = new com.company.advisor.Waiter();
        BeforeAdvice advice = new GreetingBeforeAdvice();
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();

        GreetingComposablePointcut composablePointcut = new GreetingComposablePointcut();
        Pointcut pointcut = composablePointcut.getIntersectionPointcut();
        advisor.setAdvice(advice);
        advisor.setPointcut(pointcut);

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvisor(advisor);
        proxyFactory.setTarget(target);

        com.company.advisor.Waiter proxy = (com.company.advisor.Waiter) proxyFactory.getProxy();
        WaiterDelegate delegate = new WaiterDelegate();
        delegate.setWaiter(proxy);
        proxy.serveTo("Peter");
        proxy.greetTo("Peter");
        delegate.service("Peter");


    }

}
