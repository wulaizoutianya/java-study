package javatools.zkUtil.distributedLock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.CreateMode;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DistributedLock {

    // 常亮
    static class Constant {
        private static final int SESSION_TIMEOUT = 10000;
        private static final String CONNECTION_STRING = "192.168.238.132:2181";
        private static final String LOCK_NODE = "/locks";
        private static final String CHILDREN_NODE = "/task_";
    }

    private ZkClient zkClient;

    public DistributedLock() {
        // 连接到Zookeeper
        zkClient = new ZkClient(new ZkConnection(Constant.CONNECTION_STRING, Constant.SESSION_TIMEOUT));
        if (!zkClient.exists(Constant.LOCK_NODE)) {
            zkClient.create(Constant.LOCK_NODE, "分布式锁节点", CreateMode.PERSISTENT);
        }
    }

    public String getLock() {
        try {
            // 1。在Zookeeper指定节点下创建临时顺序节点
            String lockName = zkClient.createEphemeralSequential(Constant.LOCK_NODE + Constant.CHILDREN_NODE, "");
            System.out.println(Thread.currentThread().getName() + " 开始创建临时节点：" + lockName);
            // 尝试获取锁
            acquireLock(lockName);
            return lockName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取锁
     *
     * @throws InterruptedException
     */
    public Boolean acquireLock(String lockName) throws InterruptedException {
        // 2.获取lock节点下的所有子节点
        List<String> childrenList = zkClient.getChildren(Constant.LOCK_NODE);
        // 3.对子节点进行排序，获取最小值
        /*Collections.sort(childrenList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.parseInt(o1.split("_")[1]) - Integer.parseInt(o2.split("_")[1]);
            }
        });*/
        childrenList.sort(Comparator.comparingInt(o -> Integer.parseInt(o.split("_")[1])));
        // 4.判断当前创建的节点是否在第一位
        int lockPostion = childrenList.indexOf(lockName.split("/")[lockName.split("/").length - 1]);
        if (lockPostion < 0) {
            // 不存在该节点
            throw new ZkNodeExistsException("不存在的节点：" + lockName);
        } else if (lockPostion == 0) {
            // 获取到锁
            System.out.println(Thread.currentThread().getName() + "获取---到锁：" + lockName);
            return true;
        } else {
            // 未获取到锁，阻塞
            System.out.println(Thread.currentThread().getName() + "...... 未获取到锁++++阻塞等待 。。。。。。");
            // 5.如果未获取得到锁，监听当前创建的节点前一位的节点
            final CountDownLatch latch = new CountDownLatch(1);
            IZkDataListener listener = new IZkDataListener() {
                @Override
                public void handleDataDeleted(String dataPath) throws Exception {
                    // 6.前一个节点被删除,当不保证轮到自己
                    System.out.println("。。。。。。前一个节点被删除  。。。。。。" + dataPath);
                    acquireLock(lockName);
                    latch.countDown();
                }

                @Override
                public void handleDataChange(String dataPath, Object data) {
                    // 不用理会
                }
            };
            try {
                zkClient.subscribeDataChanges(Constant.LOCK_NODE + "/" + childrenList.get(lockPostion - 1), listener);
                latch.await();
            } finally {
                zkClient.unsubscribeDataChanges(Constant.LOCK_NODE + "/" + childrenList.get(lockPostion - 1), listener);
            }
        }
        return false;
    }

    /**
     * 释放锁（删除节点）
     *
     * @param lockName lockName
     */
    public void releaseLock(String lockName) {
        zkClient.delete(lockName);
    }

    public void closeZkClient() {
        zkClient.close();
    }

}
