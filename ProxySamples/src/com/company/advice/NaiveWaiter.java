package com.company.advice;

/**
 * Created by jingjing.hu on 2017/9/20.
 */
public class NaiveWaiter implements Waiter {

    @Override
    public void greetTo(String name) {
        System.out.println("greet to " + name + "...");
    }

    @Override
    public void serveTo(String name) {
        System.out.println("serving " + name + "...");
    }
}
