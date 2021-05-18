package com.basejava.webapp;

public class Deadlock {

    private static final Object LOCK_1 = new Object();
    private static final Object LOCK_2 = new Object();

    public static void main(String[] args) {
        lock(LOCK_1, LOCK_2);
        lock(LOCK_2, LOCK_1);
    }

    private static void lock(Object lock1, Object lock2) {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " trying to acquire first lock");
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + " acquire first lock");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " trying to acquire second lock");
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + " acquire second lock");
                }
            }
        });
        thread.start();
    }

}
