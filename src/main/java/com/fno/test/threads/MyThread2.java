package com.fno.test.threads;

public class MyThread2 implements Runnable {
    @Override
    public void run() {
        int i = 15;
        while(i>=0){
            System.out.println(Thread.currentThread()+"执行......");
            i--;
        }
    }
}
