package dubbo.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("dubbo.server.dao")
@SpringBootApplication
public class DubboServerApp {

    public static void main(String[] args) {
        SpringApplication.run(DubboServerApp.class, args);
    }
}
