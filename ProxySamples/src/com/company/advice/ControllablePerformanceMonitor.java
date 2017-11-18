package com.company.advice;

import com.company.common.PerformanceMonitor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

/**
 * Created by jingjing.hu on 2017/9/20.
 * 引介增强
 */
public class ControllablePerformanceMonitor extends DelegatingIntroductionInterceptor implements Monitorable {

    private ThreadLocal<Boolean> threadLocalMonitor = new ThreadLocal<>();

    /**
     * 是否支持性能监视
     * @param active
     */
    @Override
    public void setMonitorActive(boolean active) {
        threadLocalMonitor.set(active);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object obj = null;

        if (threadLocalMonitor.get() != null && threadLocalMonitor.get()) {
            PerformanceMonitor.begin(mi.getClass().getName() + "." + mi.getMethod().getName());
            obj = super.invoke(mi);
            PerformanceMonitor.end();
        } else {
            obj = super.invoke(mi);
        }

        return obj;
    }
}
