package javatools.zkUtil.distributedLock;

public class ZkLockerTest {

    public static void main(String[] args) throws Exception {
        ZkLocker locker = new ZkLocker();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                locker.lock("user_1", () -> {
                    try {
                        System.out.println(String.format("user_1 time: %d, threadName: %s", System.currentTimeMillis(), Thread.currentThread().getName()));
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }, "Thread-" + i).start();
        }
        for (int i = 10; i < 20; i++) {
            new Thread(() -> {
                locker.lock("user_2", () -> {
                    try {
                        System.out.println(String.format("user_2 time: %d, threadName: %s", System.currentTimeMillis(), Thread.currentThread().getName()));
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }, "Thread-" + i).start();
        }

        System.in.read();
    }
}
