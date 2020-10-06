package javatools.concurrentUtil;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierUtil {

    public static void main(String[] args) {
        /*CyclicBarrier cb = new CyclicBarrier(4);
        new GoFuck("主管", cb).start();
        new GoFuck("小明", cb).start();
        new GoFuck("小王", cb).start();*/
        CyclicBarrier cb = new CyclicBarrier(5, new Leader());
        for (int i = 0; i < 5; i++) {
            new Employee(cb).start();
        }
    }
}

class GoFuck extends Thread {

    private String name;
    private CyclicBarrier cb;

    public GoFuck(String name, CyclicBarrier cb) {
        this.name = name;
        this.cb = cb;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + "已经到达万达商场等待");
            cb.await();
            //Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("人已经到齐，" + name + "开始上车");
    }
}

class Leader extends Thread {
    @Override
    public void run() {
        try {
            System.out.println("吃饭前我先说几句");
            Thread.sleep(15000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Employee extends Thread {

    private CyclicBarrier cb;

    public Employee(CyclicBarrier cb) {
        this.cb = cb;
    }

    @Override
    public void run() {
        try {
            System.out.println("都在等领导说完话准备吃");
            cb.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("说了30分钟终于吃完，用筷子开始吃了");
    }
}
