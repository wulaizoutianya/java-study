package netty.rpc.study.nettyclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import netty.rpc.study.entity.RpcRequestInfo;
import netty.rpc.study.entity.RpcResponseInfo;

import java.net.InetSocketAddress;

public class NettyRpcClientUtil {

    public static RpcResponseInfo rpcClientNetty(RpcRequestInfo rpcRequestInfo) {
        RpcResponseInfo rpcResponseInfo = new RpcResponseInfo();
        //worker负责读写数据
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            //辅助启动类
            Bootstrap bootstrap = new Bootstrap();
            //设置线程池
            bootstrap.group(worker);
            //设置socket工厂
            bootstrap.channel(NioSocketChannel.class);
            //设置管道
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    //获取管道
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    //字符串解码器
                    pipeline.addLast(new ObjectEncoder());
                    //字符串编码器
                    pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                    //处理类
                    pipeline.addLast("testResponseHandler", new NettyRpcClientHandler());
                }
            });
            // 同步等待连接成功
            ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 1000)).sync();
            if (future.isSuccess()) {
                // 同步等待发送成功
                future.channel().writeAndFlush(rpcRequestInfo).sync();
                // 死循环等待服务端响应结果
                while (true) {
                    NettyRpcClientHandler nettySocketHandler = (NettyRpcClientHandler) future.channel().pipeline().get("testResponseHandler");
                    if (nettySocketHandler.getRpcResponseInfo() != null) {
                        rpcResponseInfo = nettySocketHandler.getRpcResponseInfo();
                        nettySocketHandler.setRpcResponseInfo(null);
                        return rpcResponseInfo;
                    }
                }
            }
            //等待客户端链路关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅的退出，释放NIO线程组
            worker.shutdownGracefully();
        }
        return rpcResponseInfo;
    }
}

