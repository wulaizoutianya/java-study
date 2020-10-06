package test;

import java.util.concurrent.Semaphore;

public class SemaphoreTest1 {

    int count = 0;
    final Semaphore put = new Semaphore(1);// 初始令牌个数

    public static void main(String[] args) {
        SemaphoreTest1 bySemaphore = new SemaphoreTest1();
        new Thread(bySemaphore.new Producer(), "p1").start();
        new Thread(bySemaphore.new Producer(), "p2").start();
        new Thread(bySemaphore.new Consumer(), "c1").start();
        new Thread(bySemaphore.new Consumer(), "c2").start();
    }

    class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    put.acquire();// 注意顺序
                    count++;
                    System.out.println("生产者" + Thread.currentThread().getName()
                            + "已生产完成，商品数量：" + count);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    put.release();
                }

            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                try {
                    put.acquire();// 注意顺序
                    count--;
                    System.out.println("消费者" + Thread.currentThread().getName()
                            + "已消费，剩余商品数量：" + count);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    put.release();
                }
            }
        }
    }
}
