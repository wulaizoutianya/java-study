package netty.rpc.study.nettyserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.rpc.study.config.NettyServerContainer;
import netty.rpc.study.config.TaskThreadPool;
import netty.rpc.study.entity.RpcRequestInfo;
import netty.rpc.study.entity.RpcResponseInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NettySocketHandler extends SimpleChannelInboundHandler<RpcRequestInfo> {

    //读取客户端发送的数据
    @Override
    @SuppressWarnings("unchecked")
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestInfo msg) {
        TaskThreadPool.SINGLETON_INSTANCE.submit(() -> {
            RpcResponseInfo rpcResponseInfo = new RpcResponseInfo();
            // 根据请求信息调用方法
            Class clazz = msg.getClazz();
            String methodName = msg.getMethodName();
            Object[] params = msg.getParameterValues();
            Class[] paramTypes = msg.getParameterTypes();
            if (NettyServerContainer.serviceImplMap.isEmpty()) {
                rpcResponseInfo.setReturnFlag(false);
                rpcResponseInfo.setReturnValue("无服务提供");
            } else {
                String key = clazz.getName();
                if (NettyServerContainer.serviceImplMap.containsKey(key)) {
                    Object serviceBean = NettyServerContainer.serviceImplMap.get(key);
                    Class serviceClazz = serviceBean.getClass();
                    Method method = null;
                    try {
                        method = serviceClazz.getMethod(methodName, paramTypes);
                    } catch (NoSuchMethodException e) {
                        rpcResponseInfo.setReturnFlag(false);
                        rpcResponseInfo.setReturnValue("调用失败，获取方法出错");
                    }
                    Object result = null;
                    try {
                        if (method != null) {
                            result = method.invoke(serviceClazz.newInstance(), params);
                        } else {
                            rpcResponseInfo.setReturnFlag(false);
                            rpcResponseInfo.setReturnValue("调用失败，没有对应的方法");
                        }
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                        rpcResponseInfo.setReturnFlag(false);
                        rpcResponseInfo.setReturnValue("调用失败，方法反射出错");
                    }
                    rpcResponseInfo.setReturnFlag(true);
                    rpcResponseInfo.setReturnValue(result);
                } else {
                    rpcResponseInfo.setReturnFlag(false);
                    rpcResponseInfo.setReturnValue("无服务提供");
                }
            }
            ctx.channel().writeAndFlush(rpcResponseInfo);
        });
    }

    //异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.channel().close();      //关闭通道
        cause.printStackTrace();        //打印异常
    }
}
