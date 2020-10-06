package javatools.proConThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {

    public static void Tt() {
        final BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);

        Runnable producer = () -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String msg = "消息：" + i;
                try {
                    queue.put(msg);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println(msg + " 已发送");
            }
        };

        Runnable consumer = () -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                String msg = null;
                try {
                    msg = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(msg + "已消费");
            }
        };
        new Thread(producer).start();

        new Thread(consumer).start();
    }

    public static void main(String[] args) {
        BlockingQueueTest.Tt();
    }
}
