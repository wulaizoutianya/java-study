package netty.rpc.study.service;

import netty.rpc.study.annos.NettyRpcService;
import org.springframework.stereotype.Service;

@Service
@NettyRpcService(UserService.class)
public class UserServiceImpl implements UserService {

    @Override
    public String callRpc(String param) {
        System.out.println("从客户端传入的参数值 ：" + param);
        return param + " -- 这是服务返回的值";
    }
}
