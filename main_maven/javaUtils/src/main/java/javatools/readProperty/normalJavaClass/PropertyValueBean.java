package javatools.readProperty.normalJavaClass;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertyValueBean {

    @Value("${server.port}")
    public int serverPort;

    @Value("${spring.application.name}")
    public String serverName;
}
