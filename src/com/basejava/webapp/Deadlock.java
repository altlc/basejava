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
            synchronized (lock1) {
                System.out.println("lock1");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (lock2) {
                    System.out.println("lock2");
                }
            }
        });
        thread.start();
    }

}
