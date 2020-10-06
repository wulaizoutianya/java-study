package netty.rpc.study.web;

import netty.rpc.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/callRpc")
    public String callRpcTest(@RequestParam String num) {
        System.out.println("entry client ----------");

        int kk = 10;
        if (num != null) {
            kk = Integer.parseInt(num);
        }

        for (int i = 0; i < kk; i++) {
            new Thread(() -> {
                userService.callRpc("this is client pass param value");
            }).start();
        }

        String s = userService.callRpc("this is client pass param value");

        System.out.println("netty服务调用结束，服务端返回值：" + s);

        return s;
    }
}
