package netty.rpc.study.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//任务线程池，服务端每次接收到客户端请求，就会开启线程进行处理
public class TaskThreadPool {

    private Logger log = LoggerFactory.getLogger(TaskThreadPool.class);

    public static final TaskThreadPool SINGLETON_INSTANCE = new TaskThreadPool();
    private final ThreadPoolExecutor threadPoolExecutor;

    private TaskThreadPool() {
        //核心线程数：5
        //最大线程数：10
        //线程保持活跃时间：60s
        //线程保持活跃时间：SECONDS  秒(时间单位)
        //队列：阻塞队列，最多存放20个任务
        //拒绝策略：任务将被放弃
        this.threadPoolExecutor = new ThreadPoolExecutor(5,
                10,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(20),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public Future submit(Runnable task) {
        log.info("业务线程池执行任务中...，当前线程名称：" + Thread.currentThread().getName());
        return threadPoolExecutor.submit(task);
    }
}
