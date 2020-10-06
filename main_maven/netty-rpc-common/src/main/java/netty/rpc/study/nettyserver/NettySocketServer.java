package netty.rpc.study.nettyserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import netty.rpc.study.config.NettyPropVal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class NettySocketServer {

    private Logger logger = LoggerFactory.getLogger(NettySocketServer.class);

    @Autowired
    private NettyPropVal nettyPropVal;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap serverBootstrap;

    public NettySocketServer() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        //ServerBootstrap 是一个启动NIO服务的辅助启动类 你可以在这个服务中直接使用Channel
        serverBootstrap = new ServerBootstrap();
        //设置线程池  这一步是必须的，如果没有设置group将会报java.lang.IllegalStateException: group not set异常
        serverBootstrap.group(bossGroup, workerGroup);
        //设置socket工厂  ServerSocketChannel以NIO的selector为基础进行实现的，用来接收新的连接，这里告诉Channel如何获取新的连接.
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                //获取管道
                ChannelPipeline pipeline = socketChannel.pipeline();
                //字符串解码器
                pipeline.addLast(new ObjectEncoder());
                //字符串编码器
                pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                //处理类
                pipeline.addLast(new NettySocketHandler());
            }
        });
    }

    public void startNettyServer() {
        try {
            if (nettyPropVal.getServerPort() != null) {
                ChannelFuture future = serverBootstrap.bind(nettyPropVal.getServerPort()).sync();
                if (future.isSuccess()) {
                    logger.info("netty服务启动成功，端口号为：" + nettyPropVal.getServerPort());
                }
            }

        } catch (InterruptedException e) {
            logger.error("netty服务启动失败");
        }
    }

    /**
     * 优雅的关闭
     */
    @PreDestroy
    public void shutDown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
