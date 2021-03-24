package com.fno.test.threads;

public class MyThread1 extends Thread {

    @Override
    public void run() {
        int i = 15;
        while(i>=0){
            System.out.println(currentThread()+"执行......");
            i--;
        }
    }
}
