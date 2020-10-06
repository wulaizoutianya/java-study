package netty.rpc.study.service;

import netty.rpc.study.annos.NettyRpcClientAnno;

@NettyRpcClientAnno
public interface UserService {

    String callRpc(String param);

}
