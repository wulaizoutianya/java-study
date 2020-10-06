package javatools.javabase.reflectUtil;

public class ReflectTest {

    private String name;
    public int age;
    private double money;

    public ReflectTest() {
        System.out.println("this is no param constructor");
    }

    private ReflectTest(String name) {
        this.name = name;
        System.out.println("this is one param constructor");
    }

    public ReflectTest(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("this is two param constructor");
    }

    public void publicMethodNonParam() {
        System.out.println("this is a public method with non param");
    }

    public void publicMethodOneParam(String param) {
        System.out.println("this is a public method with one param, this param value is : " + param);
    }

    private void privateMethod() {
        System.out.println("this is a private method with non param");
    }

    void friendlyMethod() {
        System.out.println("this is a friendly method with non param");
    }

}
