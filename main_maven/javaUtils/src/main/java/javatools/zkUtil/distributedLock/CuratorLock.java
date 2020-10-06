package javatools.zkUtil.distributedLock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;

import java.util.concurrent.TimeUnit;

public class CuratorLock {

    private static final String ZK_ADDRESS = "192.168.238.132:2181";
    private static final String ZK_LOCK_PATH = "/locks/aaa";

    /**
     * 下面的程序会启动几个线程去争夺锁，拿到锁的线程会占用5秒
     */
    public static void main(String[] args) throws InterruptedException {
        // 1.Connect to zk
        final CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));
        client.start();
        System.out.println("zk client start successfully!client.getState() : " + client.getState());
        final InterProcessMutex mutex = new InterProcessMutex(client, ZK_LOCK_PATH);
        for (int i = 0; i < 3; i++) {
            Runnable myRunnable = new Runnable() {
                public void run() {
                    doWithLock(client, mutex);
                }
            };
            Thread thread = new Thread(myRunnable, "----1111Thread-" + i);
            thread.start();
        }
    }

    private static void doWithLock(CuratorFramework client, InterProcessMutex mutex) {
        try {
            String name = Thread.currentThread().getName();
            if (mutex.acquire(1, TimeUnit.SECONDS)) {
                System.out.println(name + "--获取到了锁，client.getChildren().forPath ： " + client.getChildren().forPath(ZK_LOCK_PATH));
                Thread.sleep(5000L);
                System.out.println(name + " 休息5秒后开始释放");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                mutex.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
