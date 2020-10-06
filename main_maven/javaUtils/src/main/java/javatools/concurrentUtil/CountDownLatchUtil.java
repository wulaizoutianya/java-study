package javatools.concurrentUtil;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchUtil {

    public static void main(String[] args) {
        /*try {
            EatFood ef = new EatFood();
            Thread t1 = new Thread(ef, "老二");
            Thread t2 = new Thread(ef, "老大");
            Thread t3 = new Thread(ef, "老爸");
            t1.start();
            t1.join();
            t2.start();
            t2.join();
            t3.start();
            t3.join();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            CountDownLatch cdl = new CountDownLatch(3);
            new Thread("老二") {
                public void run() {
                    try {
                        Thread.sleep(500);
                        System.out.println(Thread.currentThread().getName() + " : 正在吃饭");
                        cdl.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            new Thread("老大") {
                public void run() {
                    try {
                        Thread.sleep(500);
                        System.out.println(Thread.currentThread().getName() + " : 正在吃饭");
                        cdl.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            new Thread("老爸") {
                public void run() {
                    try {
                        Thread.sleep(500);
                        System.out.println(Thread.currentThread().getName() + " : 正在吃饭");
                        cdl.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            System.out.println("等待三个男人吃完,女人才能上桌吃饭,等....");
            cdl.await();
            System.out.println("女人们可以上桌吃饭了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class EatFood implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + " : 正在吃饭");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
