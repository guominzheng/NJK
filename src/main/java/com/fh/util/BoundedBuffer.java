package com.fh.util;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer implements Runnable {
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

   final   Object[] items = new Object[100];
    static  volatile int putptr, takeptr, count;
    String name ;
    BoundedBuffer(String name){
        this.name=name;
    }

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            System.out.println("我是线程"+name+"===》"+count);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            System.out.println("我是线程"+name+"===》"+count);
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    public void run() {
        try {
            put(10);
            take();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try {
            int index=0;
            while (true){
                index++;
                new Thread(new BoundedBuffer(Integer.toString(index))).start();
                if(index==100){
                    break;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
