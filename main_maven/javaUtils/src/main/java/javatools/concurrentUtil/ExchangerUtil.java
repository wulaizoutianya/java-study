package javatools.concurrentUtil;

import java.util.concurrent.Exchanger;

public class ExchangerUtil {

    public static void main(String[] args) {
        Exchanger<Integer> ex = new Exchanger<>();
        new Man(ex).start();
        new Girl(ex).start();
    }
}

class Man extends Thread {
    private Exchanger<Integer> ex;

    public Man(Exchanger<Integer> ex) {
        this.ex = ex;
    }

    @Override
    public void run() {
        int money = 0;
        try {
            for (int i = 0; i < 4; i++) {
                money += 100000;
                ex.exchange(money);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Girl extends Thread {
    private Exchanger<Integer> ex;

    public Girl(Exchanger<Integer> ex) {
        this.ex = ex;
    }

    @Override
    public void run() {
        try {
            int money = 0;
            for (int i = 0; i < 4; i++) {
                money = ex.exchange(money);
                System.out.println(money > 300000 ? "亲爱的" + money + "万我们可以结婚了" : money + "块这么少,臭屌丝活该单身,还不去赚钱娶老婆");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
