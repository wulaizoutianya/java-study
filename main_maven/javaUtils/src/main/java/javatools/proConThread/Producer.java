package javatools.proConThread;

public class Producer implements Runnable {

    private String goods;
    private WareHouse wh;

    public Producer(String goods, WareHouse wh) {
        this.goods = goods;
        this.wh = wh;
    }

    @Override
    public void run() {
        while (true) {
            try {
                wh.add(goods);
                System.out.println("产品《" + goods + "》被添加进了仓库，等待被消费");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
