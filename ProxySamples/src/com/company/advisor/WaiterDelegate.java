package com.company.advisor;

/**
 * Created by jingjing.hu on 2017/10/5.
 */
public class WaiterDelegate {

    private Waiter waiter;

    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    public void service(String clientName) {
        waiter.greetTo(clientName);
        waiter.serveTo(clientName);
    }
}
