package com.fno.test.threads;

import ch.qos.logback.core.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyTest {
    public static void main(String[] args) throws InterruptedException {
        Data data = new Data();

        new Thread(()->{
            data.write();
        }).start();
        for(int i = 1; i <= 10; i++){
            new Thread(()->{
                data.read();
            }).start();
        }
    }
}
class Data{
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    public void write(){
        readWriteLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"开始写");
            TimeUnit.SECONDS.sleep(2);
        }catch (Exception e){

        }finally {
            readWriteLock.writeLock().unlock();
        }
    }
    public void read(){
        readWriteLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"开始读");
        }catch (Exception e){

        }finally {
            readWriteLock.readLock().unlock();
        }
    }
}
