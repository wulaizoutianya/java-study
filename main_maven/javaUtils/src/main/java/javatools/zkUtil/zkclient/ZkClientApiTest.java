package javatools.zkUtil.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import java.nio.charset.StandardCharsets;

public class ZkClientApiTest {

    public static void main(String[] args) throws Exception {
        String path = "/test";
        ZkClient zkClient = new ZkClient(new ZkConnection("192.168.238.132:2181"));
        boolean exists = zkClient.exists(path);
        System.out.println(exists + " ---- 当前节点是否存在");
        if (!exists) {
            System.out.println("当前节点不存在");
            zkClient.create(path, "这是创建值".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            System.out.println("当前节点已经被创建");
        }
        //设置序列化，不然传输会报错
        zkClient.setZkSerializer(new MyZkClientSerializer());
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            public void handleDataChange(String dataPath, Object data) {
                if (dataPath.equals(path)) {
                    System.out.println("节点：" + dataPath + ", 数据：" + data.toString() + " - 更新");
                }
            }

            public void handleDataDeleted(String dataPath) {
                if (dataPath.equals(path)) {
                    System.out.println("节点：" + dataPath + "被删除了！");
                }
            }
        });
        //获取节点对应的值
        Object o = zkClient.readData(path);
        System.out.println("节点《" + path + "》对应的值为：" + o);
        //修改节点对应的值
        zkClient.writeData(path, "修改节点对应的值ttt111");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
