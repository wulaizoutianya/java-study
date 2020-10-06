package javatools.sourceCode.xmlIoc;

public class Test {

    public static void main(String[] args) {
        try {
            String xmlUrl = Test.class.getClassLoader().getResource("xmlUrl/spring-test.xml").getFile();
            System.out.println("xmlUrl : " + xmlUrl);
            SimpleIocXml six = new SimpleIocXml(xmlUrl);
            Wheel w = (Wheel) six.getBean("wheel");
            System.out.println(w);
            Car c = (Car) six.getBean("car");
            System.out.println(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
