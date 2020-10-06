package netty.rpc.study.nettyclient;

import netty.rpc.study.annos.NettyRpcClientAnno;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NettyRpcClientRegistrar implements ImportBeanDefinitionRegistrar, BeanClassLoaderAware {

    ClassLoader classLoader;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        //Spring提供的工具，可以按自定义的类型，查找classpath下符合要求的class文件
        ClassPathScanningCandidateComponentProvider classPathScan = new ClassPathScanningCandidateComponentProvider(false) {
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                // 判断候选人的条件:必须是独立的，然后是接口
                return beanDefinition.getMetadata().isIndependent() && beanDefinition.getMetadata().isInterface();
            }
        };

        //指定注解，类似于Feign注解
        classPathScan.addIncludeFilter(new AnnotationTypeFilter(NettyRpcClientAnno.class));

        Set<BeanDefinition> candidateComponents = new HashSet<>();
        for (String basePackage : getBasePackages(importingClassMetadata)) {
            candidateComponents.addAll(classPathScan.findCandidateComponents(basePackage));
        }

        candidateComponents.forEach(beanDefinition -> {
            if (!registry.containsBeanDefinition(beanDefinition.getBeanClassName())) {
                if (beanDefinition instanceof AnnotatedBeanDefinition) {
                    AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
                    AnnotationMetadata annotationMetadata = annotatedBeanDefinition.getMetadata();
                    Assert.isTrue(annotationMetadata.isInterface(), "@NettyRpcClientAnno can only be specified on an interface");
                    Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(NettyRpcClientAnno.class.getCanonicalName());
                    this.registerNettyRpcClient(registry, annotationMetadata, attributes);
                }
            }
        });
    }

    /**
     * 获取指定扫描@NettyRpcClientAnno注解的包路径
     *
     * @param annotationMetadata 依靠 AnnotationMetadata 接口判断是否存在指定元注解
     * @return 所有带有@NettyRpcClientAnno注解的包路径
     */
    private Set<String> getBasePackages(AnnotationMetadata annotationMetadata) {
        Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(NettyRpcClientAnno.class.getCanonicalName());
        Set<String> basePackages = new HashSet<>();
        if (attributes == null || attributes.size() == 0) {
            basePackages.add(ClassUtils.getPackageName(annotationMetadata.getClassName()));
        } else {
            for (String pkg : (String[]) attributes.get("basePackages")) {
                if (StringUtils.hasText(pkg)) {
                    basePackages.add(pkg);
                }
            }
        }
        return basePackages;
    }

    private void registerNettyRpcClient(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata, Map<String, Object> attributes) {
        String className = annotationMetadata.getClassName();
        //指定工厂，使用@NettyRpcClient注解的接口，当代码中注入时，是从指定工厂获取，而这里的工厂返回的是代理
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(NettyClientFactoryBean.class);
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);        //@Autowrie：根据类型注入
        definition.addPropertyValue("type", className);             //指定type属性
        String name = attributes.get("name") == null ? "" : (String) (attributes.get("name"));
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        beanDefinition.setPrimary(true);
        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className, new String[]{name + "NettyRpcClient"});
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);         //注册BeanDefinition
    }

}
