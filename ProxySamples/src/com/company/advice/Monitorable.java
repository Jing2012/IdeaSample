package com.company.advice;

/**
 * Created by jingjing.hu on 2017/9/20.
 */
public interface Monitorable {

    /**
     * 是否支持性能监视
     * @param active
     */
    void setMonitorActive(boolean active);
}
