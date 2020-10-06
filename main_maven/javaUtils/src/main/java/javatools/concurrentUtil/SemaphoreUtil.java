package javatools.concurrentUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreUtil {

    public static void main(String[] args) {
        //Semaphore.acquire方法是拿掉一个许可，没有许可，线程是阻塞，Semaphore.release是添加一个许可
        Semaphore s = new Semaphore(2);
        /*for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    s.acquire();
                    System.out.println(Thread.currentThread().getName() + " 正在运行");
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + " 运行结束");
                    s.release();
                }
            }).start();
        }*/
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            Runnable r = () -> {
                try {
                    s.acquire();
                    System.out.println(Thread.currentThread().getName() + " 正在运行");
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + " 运行结束");
                    s.release();
                }
            };
            es.execute(r);
        }
        es.shutdown();
    }
}
