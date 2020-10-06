package netty.rpc.study.config;

import netty.rpc.study.annos.NettyRpcService;
import netty.rpc.study.nettyserver.NettySocketServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//Netty服务提供者启动&扫面存储提供服务的实现类
@Component
public class NettyServerContainer implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    //提供RPC服务的实现类  key为接口名，value为接口实现类的实例
    public static final Map<String, Object> serviceImplMap = new HashMap<>();

    @Autowired
    private NettySocketServer nettySocketServer;

    @Autowired
    NettyPropVal nettyPropVal;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 遍历带有NettyRpcService注释的服务实现类
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(NettyRpcService.class);
        if (beans.size() > 0) {
            for (Object serviceBean : beans.values()) {
                String interfaceName = serviceBean.getClass().getAnnotation(NettyRpcService.class).value().getName();
                serviceImplMap.put(interfaceName, serviceBean);
            }
        }
    }

    //当ApplicationContext初始或刷新完毕触发
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (nettyPropVal.getServerPort() != 0) {
            nettySocketServer.startNettyServer();
        }
    }

}
