package javatools.proConThread;

public class Test {

    public static void main(String[] args) {
        WareHouse wh = new WareHouse();
        Producer p = new Producer("尿不湿", wh);
        Consumer c1 = new Consumer("c1", wh);
        Consumer c2 = new Consumer("c2", wh);
        new Thread(p).start();
        new Thread(c1).start();
        new Thread(c2).start();
    }
}
