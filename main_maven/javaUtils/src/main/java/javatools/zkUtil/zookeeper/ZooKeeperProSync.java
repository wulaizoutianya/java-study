package javatools.zkUtil.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class ZooKeeperProSync implements Watcher {

    //  ./zkServer.sh start 启动zk   ./zkCli.sh  进入zk操作台    ls /  查看根目录下所有节点
    //  create [-s] [-e] /testZnode testValue  创建带值的节点  -s为顺序节点，-e临时节点    get /testZnode  获取节点对应的值
    //  set /testZnode newValue  重新设置值     delete /testZnode   删除没有子节点的对应的节点  rmr可以删除有子节点的节点

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws Exception {
        //zookeeper配置数据存放路径
        String path = "/test";
        //连接zookeeper并且注册一个默认的监听器
        zk = new ZooKeeper("192.168.238.132:2181", 5000, new ZooKeeperProSync());
        //等待zk连接成功的通知
        connectedSemaphore.await();
        //判断指定的节点是否存在
        Stat exists = zk.exists(path, false);
        if (exists == null) {
            System.out.println("对应的节点不存在");
            //创建节点  1、节点    2、节点值   3、ACL权限   4、节点类型（临时、持久、顺序）
            zk.create(path, "不存在就直接创建对应的节点".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            System.out.println("对应的节点《" + path + "》已经创建了");
        }
        //获取对应节点的值，并注册默认的监听器
        byte[] data = zk.getData(path, true, stat);
        if (data != null) {
            System.out.println("这是节点《" + path + "》对应的值：" + new String(data));
        } else {
            System.out.println("对应节点《" + path + "》还没有值：");
        }
        //重新给指定节点设置新的值
        zk.setData(path, "重新设置节点新的值".getBytes(StandardCharsets.UTF_8), -1);
        System.out.println("获取新设置的值：" + new String(zk.getData(path, true, stat)));
        //删除对应的节点
        zk.delete(path, -1);
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {
        System.out.println("事件状态:" + event.getState() + ",事件类型:" + event.getType() + ",事件涉及路径:" + event.getPath());
        if (Event.KeeperState.SyncConnected == event.getState()) {  //zk连接成功通知事件
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
                System.out.println("连接zookeeper成功，CountDownLatch减一");
            } else if (event.getType() == Event.EventType.NodeDataChanged) {  //zk目录节点数据变化通知事件
                try {
                    System.out.println("配置已修改，新值为：" + new String(zk.getData(event.getPath(), true, stat)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
