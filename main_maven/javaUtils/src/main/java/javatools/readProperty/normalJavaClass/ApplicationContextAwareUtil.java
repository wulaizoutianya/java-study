package javatools.readProperty.normalJavaClass;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextAwareUtil implements ApplicationContextAware {

    public static final ApplicationContextAwareUtil instance = new ApplicationContextAwareUtil();

    //用于保存ApplicationContext的引用，set方式注入
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;//获得该ApplicationContext引用
    }

    public static Object getBean() {
        Object propertyValueBean = applicationContext.getBean("propertyValueBean");
        System.out.println("111 : " + propertyValueBean);
        return propertyValueBean;
    }

}
