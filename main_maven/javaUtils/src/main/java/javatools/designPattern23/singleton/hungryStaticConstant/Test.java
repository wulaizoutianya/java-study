package javatools.designPattern23.singleton.hungryStaticConstant;

public class Test {

    public static void main(String[] args) {
        StaticLoad staticLoad = StaticLoad.getStaticLoadInstance();

        System.out.println("count1 = " + staticLoad.count1);
        System.out.println("count2 = " + staticLoad.count2);
        System.out.println(Test.class.getClassLoader());
    }

}
