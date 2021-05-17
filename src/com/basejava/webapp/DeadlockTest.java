package com.basejava.webapp;

public class DeadlockTest {

    private static final Object LOCK_1 = new Object();
    private static final Object LOCK_2 = new Object();

    public static void main(String[] args) {
        //JOINdeadlock();

        Thread thread1 = new Thread(() -> {
            synchronized (LOCK_1) {
                System.out.println("t1 -> lock1");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (LOCK_2) {
                    System.out.println("t1 -> lock2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (LOCK_2) {
                System.out.println("t2 -> lock2");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (LOCK_1) {
                    System.out.println("t2 -> lock1");
                }
            }
        });

        thread1.start();
        thread2.start();

    }

    public synchronized static void JOINdeadlock() {
        try {
            Thread thread = new Thread(DeadlockTest::JOINdeadlock);
            thread.start();
            //Ждем себя же в другом потоке
            thread.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
