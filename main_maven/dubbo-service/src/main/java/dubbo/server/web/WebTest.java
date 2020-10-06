package dubbo.server.web;

import dubbo.server.service.impl.DubboTestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebTest {

    @Autowired
    DubboTestServiceImpl dubboTestService;

    @GetMapping("/tet")
    @ResponseBody
    public String tet() {
        dubboTestService.insertUserInfo(null);
        return "ok";
    }
}
