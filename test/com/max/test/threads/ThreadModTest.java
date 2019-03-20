package com.max.test.threads;

import java.util.concurrent.*;

public class ThreadModTest {
    public static void main(String[] arg) throws Exception {
        scheduledExecutorService();
    }

    //ScheduledExecutorService  线程池
    private static void scheduledExecutorService() {
        ScheduledExecutorService sync = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "testThread1");
            }
        });
        //每10秒执行一次
        sync.scheduleWithFixedDelay(new TestThread1(), 0, 10, TimeUnit.SECONDS);
        //延迟运行（只执行一次） 可以是call
        sync.schedule(new TestThread2(), 2, TimeUnit.SECONDS);
    }

    static int count = 0;

    public static class TestThread1 implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + ":" + count);
                count++;

            } catch (Exception e) {

            }
        }
    }

    public static class TestThread2 implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("thread2:" + count);
                count++;

            } catch (Exception e) {

            }
        }
    }
}
