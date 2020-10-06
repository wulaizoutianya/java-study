package netty.rpc.study.nettyclient;

import netty.rpc.study.entity.RpcRequestInfo;
import netty.rpc.study.entity.RpcResponseInfo;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

@Component
public class NettyClientFactoryBean implements FactoryBean<Object> {

    private Class<?> type;

    @Override
    public Object getObject() {
        // 注意这里的interfaces就是type，因为我们现在是给接口做代理，千万别写type.getInterfaces(),不然启动会报错
        return Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, (proxy, method, args) -> {
            RpcRequestInfo requestPacket = new RpcRequestInfo();
            requestPacket.setClazz(type);
            requestPacket.setMethodName(method.getName());
            requestPacket.setParameterTypes(method.getParameterTypes());
            requestPacket.setParameterValues(args);
            RpcResponseInfo rpcResponseInfo = NettyRpcClientUtil.rpcClientNetty(requestPacket);
            if (rpcResponseInfo.isReturnFlag()) {
                return rpcResponseInfo.getReturnValue();
            } else {
                throw new Exception(rpcResponseInfo.getErrorMsg());
            }
        });
    }

    @Override
    public Class<?> getObjectType() {
        return this.type;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
