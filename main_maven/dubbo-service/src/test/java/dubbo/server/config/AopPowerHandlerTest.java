package dubbo.server.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.method.HandlerMethod;

import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@RunWith(SpringJUnit4ClassRunner.class)
public class AopPowerHandlerTest {

    @InjectMocks
    AopPowerHandler aopPowerHandler;

    @Test
    public void testPreHandle() throws Exception {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        Class<TestAnoAop> tt = TestAnoAop.class;
        Method method = tt.getMethod("test");
        HandlerMethod handlerMethod = new HandlerMethod(TestAnoAop.class, method);

        aopPowerHandler.preHandle(httpServletRequest, httpServletResponse, handlerMethod);
    }
}

class TestAnoAop {

    @Authority(value = {"admin"})
    public String test() {
        return "ok";
    }
}
