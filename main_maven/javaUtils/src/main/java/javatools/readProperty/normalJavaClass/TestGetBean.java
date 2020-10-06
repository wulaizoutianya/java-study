package javatools.readProperty.normalJavaClass;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestGetBean {

    @GetMapping("/testBean")
    public String getTest() {
        Object bean = ApplicationContextAwareUtil.getBean();
        return "ok";
    }
}
