package javatools.zkUtil.distributedLock;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

    public static void main(String[] args) {
        ZkThreadTest zkl1 = new ZkThreadTest();
        ZkThreadTest zkl2 = new ZkThreadTest();
        for (int i = 0; i < 5; i++) {
            new Thread(zkl1).start();
            new Thread(zkl2).start();
        }
        /*new Thread(() ->{
            ZkThreadTest zkl2 = new ZkThreadTest();
            for (int i = 0; i < 5; i++) {
                new Thread(zkl2).start();
            }
        });*/
    }
}

class ZkThreadTest extends Thread {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DistributedLock zkl = new DistributedLock();

    @Override
    public void run() {
        try {
            String path = zkl.getLock();
            if (path != null) {
                System.out.println(Thread.currentThread().getName() + "获取到了锁，休息5秒" + sdf.format(new Date()) + " -- " + zkl.hashCode());
                Thread.sleep(5000);
                zkl.releaseLock(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /*if (zkl != null) {
                System.out.println(Thread.currentThread().getName() + " 准备关闭zk连接0000");
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 关闭了zk连接111111");
                zkl.closeZkClient();
            }*/
        }
    }

}
