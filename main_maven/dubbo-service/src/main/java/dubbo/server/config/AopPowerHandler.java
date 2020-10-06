package dubbo.server.config;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AopPowerHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("entry ... ");
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;

            Method m = hm.getMethod();
            try {
                // 如果方法和类声明中同时存在这个注解，那么方法中的会覆盖类中的设定。
                if (m.isAnnotationPresent(Authority.class)) {
                    Authority authority = m.getAnnotation(Authority.class);
                    String[] value = authority.value();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
