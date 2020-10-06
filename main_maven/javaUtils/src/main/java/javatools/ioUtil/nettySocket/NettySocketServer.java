package javatools.ioUtil.nettySocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class NettySocketServer {

    public static void main(String[] args) {
        //boss线程监听端口，worker线程负责数据读写
        //NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器，
        //Netty提供了许多不同的EventLoopGroup的实现用来处理不同传输协议。 在这个例子中我们实现了一个服务端的应用，
        //因此会有2个NioEventLoopGroup会被使用。 第一个经常被叫做‘boss’，用来接收进来的连接。
        //第二个经常被叫做‘worker’，用来处理已经被接收的连接， 一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上。
        //如何知道多少个线程已经被使用，如何映射到已经创建的Channels上都需要依赖于EventLoopGroup的实现，
        //并且可以通过构造函数来配置他们的关系。
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            //ServerBootstrap 是一个启动NIO服务的辅助启动类 你可以在这个服务中直接使用Channel
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置线程池  这一步是必须的，如果没有设置group将会报java.lang.IllegalStateException: group not set异常
            serverBootstrap.group(boss, worker);
            //设置socket工厂  ServerSocketChannel以NIO的selector为基础进行实现的，用来接收新的连接，这里告诉Channel如何获取新的连接.
            serverBootstrap.channel(NioServerSocketChannel.class);
            //设置管道工厂
            //这里的事件处理类经常会被用来处理一个最近的已经接收的Channel。 ChannelInitializer是一个特殊的处理类，
            // 他的目的是帮助使用者配置一个新的Channel。
            // 也许你想通过增加一些处理类比如NettyServerHandler来配置一个新的Channel
            // 或者其对应的ChannelPipeline来实现你的网络程序。 当你的程序变的复杂时，可能你会增加更多的处理类到pipline上，
            //然后提取这些匿名类到最顶层的类上。
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    //获取管道
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    //字符串解码器
                    pipeline.addLast(new StringDecoder());
                    //字符串编码器
                    pipeline.addLast(new StringEncoder());
                    //处理类
                    pipeline.addLast(new ServerHandler4());
                }
            });

            //设置TCP参数
            //1.链接缓冲池的大小（ServerSocketChannel的设置）
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            //维持链接的活跃，清除死链接(SocketChannel的设置)
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            //关闭延迟发送
            serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            //绑定端口
            ChannelFuture future = serverBootstrap.bind(8866).sync();
            System.out.println("server start ...... ");
            //等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅退出，释放线程池资源
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}

class ServerHandler4 extends SimpleChannelInboundHandler<String> {

    //读取客户端发送的数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println("client response :" + msg);
        ctx.channel().writeAndFlush("i am server !");
        new Thread(() -> {
            try {
                //等待连接建立
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入服务端的消息");
            System.out.println("=========================================================");
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                try {
                    System.out.println(s);
                    ctx.channel().writeAndFlush(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //新客户端接入
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channelActive");
    }

    //客户端断开
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("channelInactive");
    }

    //异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //关闭通道
        ctx.channel().close();
        //打印异常
        cause.printStackTrace();
    }
}
