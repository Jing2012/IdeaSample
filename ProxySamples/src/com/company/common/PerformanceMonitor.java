package com.company.common;

/**
 * Created by jingjing.hu on 2017/9/13.
 */
public class PerformanceMonitor {

    private static ThreadLocal<MethodPerformance> performanceRecord = new ThreadLocal<MethodPerformance>();

    public static void begin(String method) {
        System.out.println("monitor begin...");
        MethodPerformance mp = new MethodPerformance(method);
        performanceRecord.set(mp);
    }

    public static void end() {
        System.out.println("monitor end...");
        MethodPerformance mp = performanceRecord.get();
        mp.printPerformance();
    }

}
