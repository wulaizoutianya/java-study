package javatools.javabase.reflectUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {

        /*Reflections通过扫描classpath，索引元数据，并且允许在运行时查询这些元数据。
        使用Reflections可以很轻松的获取以下元数据信息：
        1、获取某个类型的所有子类；比如，有一个父类是TestInterface，可以获取到TestInterface的所有子类。  Reflections.getSubTypesOf
        2、获取某个注解的所有类型/字段变量，支持注解参数匹配。
        3、使用正则表达式获取所有匹配的资源文件
        4、获取特定签名方法*/

        try {
            //获取类的三种方式之一forName
            Class c1 = Class.forName("javatools.javabase.reflectUtil.ReflectTest");
            //获取类的三种方式之一class
            Class c2 = ReflectTest.class;
            //获取类的三种方式之一getClass
            ReflectTest r0 = new ReflectTest();
            Class c3 = r0.getClass();
            System.out.println(c1 + " --- " + c2 + " --- " + c3);

            System.out.println("**************************************");

            // getDeclaredConstructors();　获取所有的构造函数    getDeclaredConstructor(参数类型);　获取一个所有的构造函数
            //getConstructors();　 获取所有公开的构造函数    getConstructor(参数类型);　获取单个public的构造函数，非公共报错
            Constructor[] constructors = c1.getDeclaredConstructors();
            Arrays.stream(constructors).forEach(con -> {
                System.out.println(con + " --- this is all constructors");
            });
            System.out.println(c1.getConstructor(String.class, int.class));

            //getModifiers();　　//获取所有修饰符        0--默认不写 1--public 2--private 4--protected   8--static 16--final
            //32--synchronized  64--volatile 128--transient 256--native     512--interface 1024--abstract
            System.out.println(c1.getDeclaredConstructor(String.class).getModifiers() + " ---Modifier");

            //getName();　//获取全名  例如：com.bean.Book       getSimpleName()　//获取类名 例如：Book
            System.out.println(c1.getName() + " --- " + c1.getSimpleName() + " ---name");

            //getPackage();  返回类型：Package
            System.out.println(c1.getPackage() + " ---Package");

            //获取所有的公开方法       它会将系统自带的方法也得到
            Method[] methods = c1.getMethods();
            for (Method method : methods) {
                System.out.println(method.getName() + " ---method");
            }
            System.out.println(c1.getMethod("publicMethodNonParam") + " ---publicMethodNonParam");

            //getDeclaredMethods()　//获取所有的方法        它不会获取系统自带的方法
            Method[] methods1 = c1.getDeclaredMethods();    //获取所有方法
            for (Method method1 : methods1) {
                System.out.println(method1.getName() + " ---DeclaredMethod");
            }
            //使用方法   method.invoke(Object obj,Object... args)  obj：如果是实例方法，则放个该方法的类对象给它
            //obj：静态方法，写null        args：方法的参数值，没有写null，或不写都行  invoke的作用就是传入参数调用方法
            c1.getMethod("publicMethodOneParam", String.class).invoke(c1.newInstance(), "789");

            //getFields() -- 获取所有的公开字段    getField(String name) --获取指定的单个public字段
            //getDeclaredFields() --获取所有的字段     getDeclaredField(String name) --获取指定字段(范围所有)
            //设置访问属性    clz.setAccessible(true) --可访问   clz.setAccessible(false) --不可访问
            Field[] fields = c1.getDeclaredFields();   //所有公开字段
            Arrays.asList(fields).forEach(fi -> {
                fi.setAccessible(false);
                System.out.println(fi + " ---DeclaredField");
            });
            Field fieldAge = c1.getField("age");
            System.out.println(fieldAge + " ---type = " + fieldAge.getAnnotatedType().getType());   //只能获取公共的
            Object obj = c1.newInstance();
            fieldAge.set(obj, 78);
            System.out.println(fieldAge.get(obj) + "  &&&&&&& ");

            //实例化对象  newInstance(Object... obj)
            ReflectTest obj11 = (ReflectTest) c1.newInstance();
            obj11.publicMethodOneParam(" OK ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
