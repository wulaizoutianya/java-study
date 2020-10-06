package javatools.zkUtil.distributedLock;

import org.apache.zookeeper.*;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class ZkLocker {

    public void lock(String key, Runnable command) {
        ZkLockerWatcher watcher = ZkLockerWatcher.conn(key);
        try {
            if (watcher.getLock()) {
                command.run();
            }
        } finally {
            watcher.releaseLock();
        }
    }

    private static class ZkLockerWatcher implements Watcher {
        private static final String connAddr = "192.168.238.132:2181";
        private static final int timeout = 6000;
        private static final String LOCKER_ROOT = "/locks";

        ZooKeeper zooKeeper;
        String parentLockPath;
        String childLockPath;
        Thread thread;

        public static ZkLockerWatcher conn(String key) {
            ZkLockerWatcher watcher = new ZkLockerWatcher();
            try {
                ZooKeeper zooKeeper = watcher.zooKeeper = new ZooKeeper(connAddr, timeout, watcher);
                watcher.thread = Thread.currentThread();
                // 阻塞等待连接建立完毕
                LockSupport.park();
                // 根节点如果不存在，就创建一个（并发问题，如果两个线程同时检测不存在，两个同时去创建必须有一个会失败）
                if (zooKeeper.exists(LOCKER_ROOT, false) == null) {
                    try {
                        zooKeeper.create(LOCKER_ROOT, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    } catch (KeeperException e) {
                        // 如果节点已存在，则创建失败，这里捕获异常，并不阻挡程序正常运行
                        System.out.println("创建节点 " + LOCKER_ROOT + " 失败");
                    }
                }
                // 当前加锁的节点是否存在
                watcher.parentLockPath = LOCKER_ROOT + "/" + key;
                if (zooKeeper.exists(watcher.parentLockPath, false) == null) {
                    try {
                        zooKeeper.create(watcher.parentLockPath, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    } catch (KeeperException e) {
                        // 如果节点已存在，则创建失败，这里捕获异常，并不阻挡程序正常运行
                        System.out.println("创建节点 " + watcher.parentLockPath + " 失败");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return watcher;
        }

        public boolean getLock() {
            try {
                // 创建子节点【本篇文章由公众号“彤哥读源码”原创】
                this.childLockPath = zooKeeper.create(parentLockPath + "/", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                // 检查自己是不是最小的节点，是则获取成功，不是则监听上一个节点
                return getLockOrWatchLast();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("get lock error");
            }
        }

        public void releaseLock() {
            try {
                if (childLockPath != null) {
                    // 释放锁，删除节点
                    zooKeeper.delete(childLockPath, -1);
                }
                // 最后一个释放的删除锁节点
                List<String> children = zooKeeper.getChildren(parentLockPath, false);
                if (children.isEmpty()) {
                    try {
                        zooKeeper.delete(parentLockPath, -1);
                    } catch (KeeperException e) {
                        // 如果删除之前又新加了一个子节点，会删除失败
                        System.out.println("删除节点 " + parentLockPath + " 失败");
                    }
                }
                // 关闭zk连接
                if (zooKeeper != null) {
                    zooKeeper.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("release lock error");
            }
        }

        private boolean getLockOrWatchLast() throws KeeperException, InterruptedException {
            List<String> children = zooKeeper.getChildren(parentLockPath, false);
            // 必须要排序一下，这里取出来的顺序可能是乱的
            Collections.sort(children);
            // 如果当前节点是第一个子节点，则获取锁成功
            if ((parentLockPath + "/" + children.get(0)).equals(childLockPath)) {
                return true;
            }

            // 如果不是第一个子节点，就监听前一个节点
            String last = "";
            for (String child : children) {
                if ((parentLockPath + "/" + child).equals(childLockPath)) {
                    break;
                }
                last = child;
            }

            if (zooKeeper.exists(parentLockPath + "/" + last, true) != null) {
                this.thread = Thread.currentThread();
                // 阻塞当前线程
                LockSupport.park();
                // 唤醒之后重新检测自己是不是最小的节点，因为有可能上一个节点断线了
                return getLockOrWatchLast();
            } else {
                // 如果上一个节点不存在，说明还没来得及监听就释放了，重新检查一次
                return getLockOrWatchLast();
            }
        }

        @Override
        public void process(WatchedEvent event) {
            if (this.thread != null) {
                // 唤醒阻塞的线程（这是在监听线程，跟获取锁的线程不是同一个线程）
                LockSupport.unpark(this.thread);
                this.thread = null;
            }
        }
    }
}
