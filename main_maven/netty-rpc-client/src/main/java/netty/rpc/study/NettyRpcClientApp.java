package netty.rpc.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import netty.rpc.study.nettyclient.NettyRpcClientRegistrar;

@SpringBootApplication
@Import({NettyRpcClientRegistrar.class})
public class NettyRpcClientApp {

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcClientApp.class, args);
    }

}
