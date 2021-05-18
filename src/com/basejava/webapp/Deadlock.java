package com.basejava.webapp;

public class Deadlock {

    private static final String LOCK_1 = "LOCK1";
    private static final String LOCK_2 = "LOCK2";

    public static void main(String[] args) {
        lock(LOCK_1, LOCK_2);
        lock(LOCK_2, LOCK_1);
    }

    private static void lock(Object lock1, Object lock2) {
        new Thread(() -> {
            System.out.println(getThreadName() + " trying to acquire " + lock1);
            synchronized (lock1) {
                System.out.println(getThreadName() + " acquire " + lock1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(getThreadName() + " trying to acquire " + lock2);
                synchronized (lock2) {
                    System.out.println(getThreadName() + " acquire " + lock2);
                }
            }
        }).start();
    }

    private static String getThreadName(){
        return Thread.currentThread().getName();
    }

}
