package javatools.proConThread;

public class Consumer implements Runnable {

    private String name;
    private WareHouse wh;

    public Consumer(String name, WareHouse wh) {
        this.name = name;
        this.wh = wh;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("客户《" + name + "》准备从仓库消费尿不湿");
                wh.get("客户《" + name + "》");
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
