package javatools.proConThread;

import java.util.ArrayList;
import java.util.List;

public class WareHouse {

    public List<String> goodsList = new ArrayList<>();     //用于存放商品的仓库

    public void add(String goods) {
        if (goodsList.size() < 20) {
            goodsList.add(goods);
        } else {
            try {
                this.notifyAll();
                this.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void get(String name) {
        if (goodsList.size() > 0) {
            goodsList.remove(0);
            System.out.println(name + "从仓库真正消费了尿不湿");
        } else {
            try{
                System.out.println(name + "正在休息，因为没有商品了");
                this.notifyAll();
                this.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
