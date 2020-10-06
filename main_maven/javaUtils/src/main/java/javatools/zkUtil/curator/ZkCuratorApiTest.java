package javatools.zkUtil.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.RetryNTimes;

public class ZkCuratorApiTest {

    private static final String ZK_PATH = "/test";

    public static void main(String[] args) throws Exception {
        // 1.Connect to zk
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.238.132:2181", new RetryNTimes(10, 5000));
        client.start();
        System.out.println("zk client start successfully!");
        //2.1 Create node
        client.create().creatingParentsIfNeeded().forPath(ZK_PATH, "hello".getBytes());
        // 2.2 Get node and data
        print(client.getChildren().forPath("/"));
        print(client.getData().forPath(ZK_PATH));

        //Path...Cache：监视一个路径下1）孩子结点的创建、2）删除，3）以及结点数据的更新。产生的事件会传递给注册的PathChildrenCacheListener。
        //NodeCache：监视一个结点的创建、更新、删除，并将结点的数据缓存在本地。
        //TreeCache：Path Cache和Node Cache的“合体”，监视路径下的创建、更新、删除事件，并缓存路径下所有孩子结点的数据。
        // Register watcher
        PathChildrenCache watcher = new PathChildrenCache(client, ZK_PATH, true);
        watcher.getListenable().addListener((client1, event) -> {
            ChildData data = event.getData();
            if (data == null) {
                System.out.println("No data in event[" + event + "]");
            } else {
                System.out.println("Receive event: type=[" + event.getType() + "], path=[" + data.getPath() + "]"
                        + ", data=[" + new String(data.getData()) + "], stat=[" + data.getStat() + "]");
            }
        });
        watcher.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);

        // 2.3 Modify data
        client.setData().forPath(ZK_PATH, "world".getBytes());
        print(client.getData().forPath(ZK_PATH));
        // 2.4 Remove node
        //client.delete().forPath(ZK_PATH);
        //print(client.getChildren().forPath("/"));
        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void print(Object result) {
        System.out.println(result instanceof byte[] ? new String((byte[]) result) : result);
    }
}
