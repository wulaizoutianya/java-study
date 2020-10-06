package javatools.concurrentUtil;

public class DeadlockUtil implements Runnable {
    private final static Object o1 = new Object(), o2 = new Object();
    private boolean flag;

    public DeadlockUtil(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            if (flag) {
                synchronized (o1) {
                    System.out.println("this is o1 sleep start");
                    Thread.sleep(1500);
                    System.out.println("this is o1 sleep end");
                    synchronized (o2) {

                    }
                    System.out.println("this is o1 all end");
                }
            } else {
                synchronized (o2) {
                    System.out.println("this is o2 sleep start");
                    Thread.sleep(1500);
                    System.out.println("this is o2 sleep end");
                    synchronized (o1) {

                    }
                    System.out.println("this is o2 all end");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /*new Thread(new DeadlockUtil(true)).start();
        new Thread(new DeadlockUtil(false)).start();*/
        // 写一个循环程序，算出计算机一秒钟的时间内循环的次数。
        long i = 0, startTime = System.currentTimeMillis();
        try {
            while (System.currentTimeMillis() - startTime < 1000) {
                i++;
                Thread.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("一秒钟的时间内循环" + i + "次");
    }
}
