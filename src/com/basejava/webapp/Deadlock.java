package com.basejava.webapp;

public class Deadlock {

    private static final String LOCK_1 = "LOCK1";
    private static final String LOCK_2 = "LOCK2";

    public static void main(String[] args) {
        lock(LOCK_1, LOCK_2);
        lock(LOCK_2, LOCK_1);
    }

    private static void lock(Object lock1, Object lock2) {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " trying to acquire " + lock1);
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + " acquire " + lock1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " trying to acquire " + lock2);
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + " acquire " + lock2);
                }
            }
        });
        thread.start();
    }

}
