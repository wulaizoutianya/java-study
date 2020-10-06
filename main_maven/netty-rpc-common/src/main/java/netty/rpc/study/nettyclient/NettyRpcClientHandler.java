package netty.rpc.study.nettyclient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.rpc.study.entity.RpcResponseInfo;

public class NettyRpcClientHandler extends SimpleChannelInboundHandler<RpcResponseInfo> {

    private RpcResponseInfo rpcResponseInfo;

    //接受服务端发来的消息
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponseInfo msg) {
        System.out.println("channelRead0  ----   接受服务端发来的消息 ： " + msg);
        rpcResponseInfo = msg;
        ctx.close();
    }

    //异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.channel().close();          //关闭管道
        cause.printStackTrace();        //打印异常信息
    }

    public RpcResponseInfo getRpcResponseInfo() {
        return rpcResponseInfo;
    }

    public void setRpcResponseInfo(RpcResponseInfo rpcResponseInfo) {
        this.rpcResponseInfo = rpcResponseInfo;
    }
}
